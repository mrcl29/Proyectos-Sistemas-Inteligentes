import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Robot extends JPanel {
    private static final String path = "assets/robot.png";
    private int fila, columna;
    private Escenari escenari;
    private Timer timer;
    private int direction = 0; // 0 = derecha, 1 = abajo, 2 = izquierda, 3 = arriba

    public Robot(Escenari escenari) {
        this.escenari = escenari;
        Tuple<Integer, Integer> posInicial = escenari.getCentre();
        this.fila = posInicial.getFirst();
        this.columna = posInicial.getSecond();
        
        // Configuración del Timer para el movimiento
        timer = new Timer(500, new ActionListener() {
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
        switch (direction) {
            case 0: if (!escenari.getEsParet(fila, columna + 1)) { columna++; } else { direction = 1; } break;
            case 1: if (!escenari.getEsParet(fila + 1, columna)) { fila++; } else { direction = 2; } break;
            case 2: if (!escenari.getEsParet(fila, columna - 1)) { columna--; } else { direction = 3; } break;
            case 3: if (!escenari.getEsParet(fila - 1, columna)) { fila--; } else { direction = 0; } break;
        }
        escenari.afegirComponent(this, fila, columna);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Lógica para dibujar el robot si la imagen existe
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
