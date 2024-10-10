import javax.swing.*;
import java.awt.*;

public class Escenari extends JPanel {
    private static final int TAMANY_X = 20; // Número de caselles en el eix X
    private static final int TAMANY_Y = 10; // Número de caselles en el eix Y
    private Casella[][] matriu;
    private JPanel matriuPanell;
    private Robot robot;
    private JFrame parentFrame;

    public Escenari(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Inicialización panell de la matriu
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        // Inicialización de la matriu de Caselles
        matriu = new Casella[TAMANY_Y][TAMANY_X];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / TAMANY_X;
        gbc.weighty = 1.0 / TAMANY_Y;

        for (int i = 0; i < TAMANY_Y; i++) {
            for (int j = 0; j < TAMANY_X; j++) {
                if (i == 0 || i == TAMANY_Y - 1 || j == 0 || j == TAMANY_X - 1) {
                    matriu[i][j] = new Casella(true);
                } else {
                    matriu[i][j] = new Casella(false);
                }
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);

        // Inicializar el robot
        robot = new Robot();
        colocarRobotEnInici();

        // Definim que s'ajusti la matriu quan el tamany de la finestra canvia
        parentFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarTamanyMatriu();
            }
        });
    }

    private void colocarRobotEnInici() {
        for (int j = 0; j < TAMANY_X; j++) {
            if (!matriu[1][j].isParet()) {
                afegirComponent(robot, 1, j);
                robot.setPosition(1, j);
                break;
            }
        }
    }

    private void ajustarTamanyMatriu() {
        int ampladaPantalla = parentFrame.getContentPane().getWidth();
        int alturaPantalla = parentFrame.getContentPane().getHeight();
        int alturaMenu = parentFrame.getJMenuBar().getHeight();

        double proporcioMatriu = (double) TAMANY_X / TAMANY_Y;
        double proporcionPantalla = (double) ampladaPantalla / (alturaPantalla - alturaMenu);

        int ampladaMatriu, alturaMatriu;

        if (proporcionPantalla > proporcioMatriu) {
            // Ajustar por altura
            alturaMatriu = alturaPantalla - alturaMenu;
            ampladaMatriu = (int) (alturaMatriu * proporcioMatriu);
        } else {
            // Ajustar por anchura
            ampladaMatriu = ampladaPantalla;
            alturaMatriu = (int) (ampladaMatriu / proporcioMatriu);
        }

        matriuPanell.setPreferredSize(new Dimension(ampladaMatriu, alturaMatriu));
        matriuPanell.revalidate();
    }

    public void afegirComponent(Component component, int fila, int columna) {
        if (fila >= 0 && fila < TAMANY_Y && columna >= 0 && columna < TAMANY_X) {
            matriu[fila][columna].removeAll();  // Eliminar componentes anteriores
            if (component instanceof Robot) {
                matriu[fila][columna].setLayout(new BorderLayout());
                matriu[fila][columna].add(component, BorderLayout.CENTER);
                matriu[fila][columna].setConteRobot(true);  // Marcar que contiene un robot
            } else {
                matriu[fila][columna].add(component);
                matriu[fila][columna].setConteRobot(false);  // Marcar que no contiene un robot
            }
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    // GETTERS

    public Casella[][] getMatriu() {
        return matriu;
    }

    public Robot getRobot() {
        return robot;
    }
}