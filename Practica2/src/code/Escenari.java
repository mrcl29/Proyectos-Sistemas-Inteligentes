package code;

import javax.swing.*;

import java.awt.*;

public class Escenari extends JPanel {

    private JPanel matriuPanell;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialización del panel de la matriz
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);
        int N = Variables.tamanyEscenari;
        // Inicialización de la matriz de Caselles
        Variables.matriuEscenari = new Casella[N][N];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / N;
        gbc.weighty = 1.0 / N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Variables.matriuEscenari[i][j] = new Casella(i == Constants.FILA_INICI && j == Constants.COLUMNA_INICI);
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(Variables.matriuEscenari[i][j], gbc);
            }
        }

        add(matriuPanell, BorderLayout.CENTER);
    }
}
