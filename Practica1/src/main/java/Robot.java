import javax.swing.*;
import java.awt.*;

public class Robot extends JPanel {
    private static final String path = "Practica1/src/main/resources/assets/robot.png";
    private final ImageIcon robotIcon = new ImageIcon(path);

    public Robot() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (robotIcon != null) {
            int width = (int) (getWidth() * 0.9);
            int height = (int) (getHeight() * 0.9);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            g.drawImage(robotIcon.getImage(), x, y, width, height, this);
        }
    }
}
