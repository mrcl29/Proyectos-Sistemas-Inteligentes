package code;

import javax.swing.*;
import java.awt.*;

public class Escenari extends JPanel {

    private JPanel matriuPanell;

    public Escenari() {
        setLayout(new BorderLayout());

        // Inicialització del panell de la matriu
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);
        int N = Variables.tamanyEscenari;

        // Inicialització de la matriu de Caselles
        Variables.matriuEscenari = new Casella[N][N];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / N;
        gbc.weighty = 1.0 / N;

        // Creació i col·locació de les Caselles a la matriu
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Crea una nova Casella, marcant-la com a inicial si correspon
                Variables.matriuEscenari[i][j] = new Casella(i == Constants.FILA_INICI && j == Constants.COLUMNA_INICI);
                gbc.gridx = j;
                gbc.gridy = i;
                // Afegeix la Casella al panell de la matriu
                matriuPanell.add(Variables.matriuEscenari[i][j], gbc);
            }
        }

        // Afegeix el panell de la matriu al centre de l'Escenari
        add(matriuPanell, BorderLayout.CENTER);
    }
}
