import javax.swing.*;

import java.awt.*;

public class Main {
    public static final int COLUMNES = 21;
    public static final int FILES = 11;
    public static final int VELOCITAT = 100;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Robot Perímetre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Crear el escenario y el robot
            Escenari escenari = new Escenari();
            Robot robot = escenari.getRobot(); // Obtén el robot creado en el Escenari

            // Crear el menú y controlar el movimiento del robot
            Menu menu = new Menu(frame.getWidth(), robot);

            // Agregar el menú y el escenario al frame
            frame.add(menu.getMenuPanel(), BorderLayout.NORTH);
            frame.add(escenari, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
