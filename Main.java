import javax.swing.*;
import java.awt.*;

public class Main {
    private static boolean isRunning = false;
    private static final int HEIGHT = 40;
    private static JPanel menuPanel;
    private static JButton inicioButton;
    private static Integer ESPERA = 1000;
    private static Moviment mov;

    public static void main(String[] args) {
        inici();
        mov.iniciMoviment();
    }

    public static void inici() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Robot Perímetre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // MENU ////////////////////////////////////////////////////////////
            menuPanel = new JPanel();
            menuPanel.setBackground(Color.LIGHT_GRAY);
            menuPanel.setPreferredSize(new Dimension(frame.getWidth(), HEIGHT));

            inicioButton = new JButton("Inicio");
            inicioButton.addActionListener(e -> {
                if(isRunning){

                }
                isRunning = !isRunning;
                inicioButton.setText(isRunning ? "Detener" : "Inicio");

            });

            menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            menuPanel.add(inicioButton);

            frame.add(menuPanel, BorderLayout.NORTH);
            ////////////////////////////////////////////////////////////////////

            // ESCENARI ////////////////////////////////////////////////////////
            Escenari escenari = new Escenari();
            frame.addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    int ampladaPantalla = frame.getContentPane().getWidth();
                    int alturaPantalla = frame.getContentPane().getHeight();
                    escenari.ajustarTamanyMatriu(ampladaPantalla, alturaPantalla);
                }
            });
            frame.add(escenari, BorderLayout.CENTER);
            ////////////////////////////////////////////////////////////////////

            // ROBOT ///////////////////////////////////////////////////////////
            Robot robot = new Robot();
            Tuple<Integer, Integer> posInici = escenari.getCentre();
            if (!escenari.getEsParet(posInici.getFirst(), posInici.getSecond())) {
                escenari.afegirComponent(robot, posInici.getFirst(), posInici.getSecond());
                robot.setPosicioActual(posInici.getFirst(), posInici.getSecond());
            } else {
                for (int i = 1; i < Escenari.getFILES() - 1; i++) {
                    for (int j = 1; j < Escenari.getCOLUMNES() - 1; j++) {
                        if (!escenari.getEsParet(i, j)) {
                            escenari.afegirComponent(robot, i, j);
                            robot.setPosicioActual(i, j);
                            break;
                        }
                    }
                }
            }
            ////////////////////////////////////////////////////////////////////

            frame.setVisible(true);

            mov = new Moviment(ESPERA, robot, escenari);
        });
    }
}