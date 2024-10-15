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
    private Robot robot; // Referencia al robot que se creó en Escenari

    public Menu(int width, Robot robot) {
        this.robot = robot; // Robot se pasa como parámetro desde Main

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setPreferredSize(new Dimension(width, HEIGHT));

        inicioButton = new JButton("Inici");
        inicioButton.addActionListener(e -> {
            isRunning = !isRunning;
            inicioButton.setText(isRunning ? "Aturar" : "Inici");
            onStartStop();
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

    private void onStartStop() {
        if (isRunning) {
            robot.iniciarMovimentPerimetre(); // Iniciar movimient
        } else {
            robot.aturarMovimentPerimetre(); // Aturar moviment
        }
    }
}
