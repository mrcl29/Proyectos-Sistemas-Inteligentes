package code;

import javax.swing.*;

import java.awt.*;

public class Escenari extends JPanel {
    public static Casella[][] matriu;
    private JPanel matriuPanell;
    private int N;

    public Escenari(int tamanyEscenari) {
        setLayout(new BorderLayout());

        // Inicialización del panel de la matriz
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        // Inicialización de la matriz de Caselles
        N = tamanyEscenari;
        matriu = new Casella[N][N];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / N;
        gbc.weighty = 1.0 / N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matriu[i][j] = new Casella();
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);
    }
}
