import javax.swing.*;
import java.awt.*;

public class Escenari extends JPanel {
    private static final int COLUMNES = 21;
    private static final int FILES = 11;
    public Casella[][] matriu;
    private JPanel matriuPanell;
    private Robot robot;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialización del panel de la matriz
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        // Inicialización de la matriz de Caselles
        matriu = new Casella[FILES][COLUMNES];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / COLUMNES;
        gbc.weighty = 1.0 / FILES;

        for (int i = 0; i < FILES; i++) {
            for (int j = 0; j < COLUMNES; j++) {
                matriu[i][j] = new Casella(i == 0 || i == FILES - 1 || j == 0 || j == COLUMNES - 1);
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);

        // Crear y agregar el robot al centro, pero no iniciar su movimiento
        robot = new Robot(this);
        afegirComponent(robot, getCentre().getFirst(), getCentre().getSecond());
    }

    public Robot getRobot() {
        return robot;
    }

    public void afegirComponent(Component component, int fila, int columna) {
        if (fila >= 0 && fila < FILES && columna >= 0 && columna < COLUMNES) {
            matriu[fila][columna].removeAll();
            if (component instanceof Robot) {
                matriu[fila][columna].setLayout(new BorderLayout());
                matriu[fila][columna].add(component, BorderLayout.CENTER);
                matriu[fila][columna].setConteRobot(true);
            }
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    public void eliminarComponent(int fila, int columna) {
        if (fila >= 0 && fila < FILES && columna >= 0 && columna < COLUMNES) {
            matriu[fila][columna].removeAll();
            matriu[fila][columna].setConteRobot(false);
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    public boolean getEsParet(int fila, int columna) {
        return matriu[fila][columna].isParet();
    }

    public Tuple<Integer, Integer> getCentre() {
        return new Tuple<>(FILES / 2, COLUMNES / 2);
    }
}
