package code;

import javax.swing.*;

import java.awt.*;

public class Escenari extends JPanel {
    private Casella[][] matriu;
    private JPanel matriuPanell;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialización del panel de la matriz
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);
        int N = Variables.tamanyEscenari;
        // Inicialización de la matriz de Caselles
        matriu = new Casella[N][N];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / N;
        gbc.weighty = 1.0 / N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matriu[i][j] = new Casella(i == Constants.filaInicial && j == Constants.columnaInicial);
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);
    }

    public String getEstatCasella(int fila, int columna) {
        return matriu[columna][fila].getEstatCasella();
    }

    public boolean setEstatCasella(int fila, int columna, String nouEstat) {
        return matriu[columna][fila].setEstatCasella(nouEstat);
    }
}
