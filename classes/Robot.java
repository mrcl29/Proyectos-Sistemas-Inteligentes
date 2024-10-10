import javax.swing.*;
import java.awt.*;

public class Robot extends JPanel {
    private static ImageIcon robotIcon;
    private static final String path = "assets/robot.png";
    private Tuple<Integer, Integer> posicioActual = new Tuple<Integer,Integer>(null, null);

    public Robot() {
        try {
            robotIcon = new ImageIcon(path);
        } catch (Error e) {
            System.err.print("Ha hagut un error amb sa imatge seleccionada");
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (robotIcon != null) {
            Image img = robotIcon.getImage();
            int width = (int) (getWidth() * 0.9); // 90% del ancho de la casilla
            int height = (int) (getHeight() * 0.9); // 90% del alto de la casilla
            int x = (getWidth() - width) / 2; // Centrar horizontalmente
            int y = (getHeight() - height) / 2; // Centrar verticalmente
            g.drawImage(img, x, y, width, height, this);
        }
    }

    public Integer getFila() {
        return posicioActual.getFirst();
    }

    public Integer getColumna() {
        return posicioActual.getSecond();
    }

    public void setPosicioActual(Integer fila, Integer columna){
        posicioActual = new Tuple<>(fila, columna);
    }

    public void movimentNord(){
        posicioActual.setFirst(posicioActual.getFirst() + 1);
    }

    public void movimentEst(){
        posicioActual.setSecond(posicioActual.getSecond() + 1);
    }

    public void movimentSud(){
        posicioActual.setFirst(posicioActual.getFirst() - 1);
    }

    public void movimentOest(){
        posicioActual.setSecond(posicioActual.getSecond() - 1);
    }
}