import javax.swing.*;
import java.awt.*;

public class Casella extends JPanel {
    private boolean ocupada;

    public Casella() {
        ocupada = false;
        initializeUI();
    }

    private void initializeUI() {
        setOpaque(false);
        setPreferredSize(new Dimension(50, 50)); // Tamaño del cuadrado, puedes ajustarlo
    }

    public boolean getOcupada(){
        return ocupada;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Establecer el color del borde a negro
        g2d.setColor(Color.BLACK);
        
        // Dibujar el borde
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g2d.dispose();
    }
}