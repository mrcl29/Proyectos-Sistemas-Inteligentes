import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu {
    private static final int HEIGHT = 40;
    private JPanel menuPanel;
    private JButton inicioButton;
    private boolean isRunning = false;

    public Menu(int width, Runnable onStartStop){
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setPreferredSize(new Dimension(width, HEIGHT));

        inicioButton = new JButton("Inicio");
        inicioButton.addActionListener(e -> {
            isRunning = !isRunning;
            inicioButton.setText(isRunning ? "Detener" : "Inicio");
            onStartStop.run();
        });

        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuPanel.add(inicioButton);
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public boolean isRunning() {
        return isRunning;
    }
}