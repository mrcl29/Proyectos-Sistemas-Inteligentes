import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Robot extends JPanel {
    private static final String path = "src/main/resources/assets/robot.png";
    private int fila, columna;
    private Escenari escenari;
    private Timer timer;
    private int direction = 3; // 3 = arriba, 0 = derecha, 1 = abajo, 2 = izquierda
    private boolean siguiendoPared = false;

    public Robot(Escenari escenari) {
        this.escenari = escenari;
        Tuple<Integer, Integer> posInicial = escenari.getCentre();
        this.fila = posInicial.getFirst();
        this.columna = posInicial.getSecond();

        timer = new Timer(Main.VELOCITAT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moureRobot();
            }
        });
        setOpaque(false);
    }

    public void iniciarMovimentPerimetre() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void detenerMovimentPerimetre() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    private void moureRobot() {
        escenari.eliminarComponent(fila, columna);
        
        if (!siguiendoPared) {
            // Moverse hacia arriba hasta encontrar una pared
            if (!escenari.getEsParet(fila - 1, columna)) {
                fila--;
            } else {
                siguiendoPared = true;
                direction = 0; // Cambiar a derecha cuando encuentra la pared
            }
        } else {
            // Seguir la pared
            boolean moved = false;
            int attempts = 0;
            
            while (!moved && attempts < 4) {
                switch (direction) {
                    case 0: // Derecha
                        if (!escenari.getEsParet(fila, columna + 1)) {
                            columna++;
                            moved = true;
                        } else {
                            direction = 1; // Cambiar a abajo
                        }
                        break;
                    case 1: // Abajo
                        if (!escenari.getEsParet(fila + 1, columna)) {
                            fila++;
                            moved = true;
                        } else {
                            direction = 2; // Cambiar a izquierda
                        }
                        break;
                    case 2: // Izquierda
                        if (!escenari.getEsParet(fila, columna - 1)) {
                            columna--;
                            moved = true;
                        } else {
                            direction = 3; // Cambiar a arriba
                        }
                        break;
                    case 3: // Arriba
                        if (!escenari.getEsParet(fila - 1, columna)) {
                            fila--;
                            moved = true;
                        } else {
                            direction = 0; // Cambiar a derecha
                        }
                        break;
                }
                attempts++;
            }
            
            // Intentar girar a la izquierda (siguiendo la pared)
            if (moved) {
                int leftDirection = (direction + 3) % 4;
                boolean canTurnLeft = false;
                
                switch (leftDirection) {
                    case 3: canTurnLeft = !escenari.getEsParet(fila - 1, columna); break;
                    case 0: canTurnLeft = !escenari.getEsParet(fila, columna + 1); break;
                    case 1: canTurnLeft = !escenari.getEsParet(fila + 1, columna); break;
                    case 2: canTurnLeft = !escenari.getEsParet(fila, columna - 1); break;
                }
                
                if (canTurnLeft) {
                    direction = leftDirection;
                }
            }
        }
        
        escenari.afegirComponent(this, fila, columna);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon robotIcon = new ImageIcon(path);
        if (robotIcon != null) {
            Image img = robotIcon.getImage();
            int width = (int) (getWidth() * 0.9);
            int height = (int) (getHeight() * 0.9);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            g.drawImage(img, x, y, width, height, this);
        }
    }
}