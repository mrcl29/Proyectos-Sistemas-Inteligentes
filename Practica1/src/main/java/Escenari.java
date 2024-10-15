import javax.swing.*;

import java.awt.*;

public class Escenari extends JPanel {
    public Casella[][] matriu;
    private JPanel matriuPanell;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialización del panel de la matriz
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        // Inicialización de la matriz de Caselles
        matriu = new Casella[Main.FILES][Main.COLUMNES];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / Main.COLUMNES;
        gbc.weighty = 1.0 / Main.FILES;

        for (int i = 0; i < Main.FILES; i++) {
            for (int j = 0; j < Main.COLUMNES; j++) {
                matriu[i][j] = new Casella(i == 0 || i == Main.FILES - 1 || j == 0 || j == Main.COLUMNES - 1);
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);
    }

    public void afegirComponent(Component component, int fila, int columna) {
        if (fila >= 0 && fila < Main.FILES && columna >= 0 && columna < Main.COLUMNES) {
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
        if (fila >= 0 && fila < Main.FILES && columna >= 0 && columna < Main.COLUMNES) {
            matriu[fila][columna].removeAll();
            matriu[fila][columna].setConteRobot(false);
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    public boolean getEsParet(int fila, int columna) {
        return matriu[fila][columna].esParet();
    }
}
