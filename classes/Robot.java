
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Robot extends JPanel {
    private static final ImageIcon robotIcon = new ImageIcon("assets/robot.png");
    private int fila;
    private int columna;
    URL path = getClass().getResource("assets/robot.png");

    public Robot() {
        setOpaque(false);
    }

    public void setPosition(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
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

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}