import javax.swing.*;
import java.awt.*;

public class Escenari extends JPanel {
    private static final int COLUMNES = 21; // Número de caselles en el eix X
    private static final int FILES = 11; // Número de caselles en el eix Y
    private Casella[][] matriu;
    private JPanel matriuPanell;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialización panell de la matriu
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        // Inicialización de la matriu de Caselles
        matriu = new Casella[FILES][COLUMNES];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / COLUMNES;
        gbc.weighty = 1.0 / FILES;

        for (int i = 0; i < FILES; i++) {
            for (int j = 0; j < COLUMNES; j++) {
                if (i == 0 || i == FILES - 1 || j == 0 || j == COLUMNES - 1) {
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
    }

    public void ajustarTamanyMatriu(int width, int height) {
        int ampladaPantalla = width;
        int alturaPantalla = height;

        double proporcioMatriu = (double) COLUMNES / FILES;
        double proporcionPantalla = (double) ampladaPantalla / (alturaPantalla - Menu.getHeight());

        int ampladaMatriu, alturaMatriu;

        if (proporcionPantalla > proporcioMatriu) {
            // Ajustar por altura
            alturaMatriu = alturaPantalla - Menu.getHeight();
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
        if (fila >= 0 && fila < FILES && columna >= 0 && columna < COLUMNES) {
            matriu[fila][columna].removeAll(); // Eliminar componentes anteriores
            if (component instanceof Robot) {
                matriu[fila][columna].setLayout(new BorderLayout());
                matriu[fila][columna].add(component, BorderLayout.CENTER);
                matriu[fila][columna].setConteRobot(true); // Marcar que contiene un robot
            } else {
                matriu[fila][columna].add(component);
                matriu[fila][columna].setConteRobot(false); // Marcar que no contiene un robot
            }
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    public static int getFILES() {
        return FILES;
    }

    public static int getCOLUMNES() {
        return COLUMNES;
    }

    public boolean getEsParet(int fila, int columna){
        return matriu[fila][columna].isParet();
    }

    public Tuple<Integer, Integer> getCentre(){ 
        int columna = Math.round(COLUMNES / 2);
        int fila = Math.round(FILES / 2);
        Tuple<Integer, Integer> centre = new Tuple<>(fila, columna);
        return centre;
    }
}