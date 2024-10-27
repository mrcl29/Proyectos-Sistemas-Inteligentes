import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Menu {
    private static final int HEIGHT = 50;
    private JPanel menuPanel;
    private JButton inicioButton;
    private boolean isRunning = false;

    public Menu(int width, Robot robot) {

        menuPanel = new JPanel();
        menuPanel.setBackground(Color.LIGHT_GRAY);
        menuPanel.setPreferredSize(new Dimension(width, HEIGHT));

        // Crear el botón
        inicioButton = new JButton("INICIAR");
        inicioButton.setPreferredSize(new Dimension(120, 40)); // Ajustar tamaño del botón
        inicioButton.setFont(new Font("Arial", Font.BOLD, 16)); // Cambiar la fuente y tamaño
        inicioButton.setForeground(Color.WHITE); // Texto en blanco
        inicioButton.setFocusPainted(false); // Eliminar borde de enfoque
        inicioButton.setBorder(new RoundedBorder(10)); // Bordes redondeados
        inicioButton.setBackground(new Color(70, 130, 180)); // Fondo color azul

        // Cambiar texto al hacer clic y gestionar el evento
        inicioButton.addActionListener(e -> {
            isRunning = !isRunning;
            inicioButton.setText(isRunning ? "ATURAR" : "INICIAR");
            onStartStop();
        });

        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuPanel.add(inicioButton);
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void onStartStop() {
        if (isRunning) {
            if (!Main.timer.isRunning()) {
                Main.timer.start();
            } // Iniciar movimiento
        } else {
            if (Main.timer.isRunning()) {
                Main.timer.stop();
            } // Aturar movimiento
        }
    }

    // Clase para crear bordes redondeados para el botón
    private static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
