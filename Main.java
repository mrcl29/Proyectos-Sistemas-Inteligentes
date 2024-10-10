import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Robot Perímetre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Crear el menú superior
            JPanel menuPanel = new JPanel();
            menuPanel.setBackground(Color.LIGHT_GRAY);
            menuPanel.setPreferredSize(new Dimension(frame.getWidth(), 40)); // Altura del menú

            // Botón de inicio
            JButton inicioButton = new JButton("Inicio");
            inicioButton.addActionListener(e -> {
                // Aquí puedes agregar la lógica para el botón de inicio
                System.out.println("Botón Inicio presionado");
            });

            // Agregar el botón al centro del menú
            menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            menuPanel.add(inicioButton);

            // Agregar el menú al frame
            frame.add(menuPanel, BorderLayout.NORTH);

            // Crear y agregar el escenario
            Escenari escenari = new Escenari(frame);
            frame.add(escenari, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}