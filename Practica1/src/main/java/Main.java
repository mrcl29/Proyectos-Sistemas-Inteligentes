import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int COLUMNES = 21;
    public static final int FILES = 11;
    public static final int VELOCITAT = 100;// Milisegons

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear finestra de visualització
            JFrame frame = new JFrame("Robot Perímetre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Crear l'escenari i el robot
            Escenari escenari = new Escenari();
            Robot robot = new Robot(escenari);
            escenari.afegirComponent(robot, robot.getFila(), robot.getColumna());// Colocar el robot al centre de
                                                                                 // l'escenari

            // Crear el menú y controlar el moviment del robot
            Menu menu = new Menu(frame.getWidth(), robot);

            // Agregar el menú y l'escenari a la finestra
            frame.add(menu.getMenuPanel(), BorderLayout.NORTH);
            frame.add(escenari, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
