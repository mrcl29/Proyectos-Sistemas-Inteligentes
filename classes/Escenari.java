import javax.swing.*;
import java.awt.*;

public class Escenari extends JFrame {
    private static final int TAMANY_X = 30; // Número de caselles en el eix X
    private static final int TAMANY_Y = 20; // Número de caselles en el eix Y
    private Casella[][] matriu;
    private JPanel matriuPanell;

    public Escenari() {
        //Inici finestra escenari
        setTitle("Robot Perímetre");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //Inicialització panell de la matriu
        matriuPanell = new JPanel(new GridBagLayout());
        matriuPanell.setOpaque(false);

        //Inicialització de la matriu de Caselles
        matriu = new Casella[TAMANY_Y][TAMANY_X];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0 / TAMANY_X;
        gbc.weighty = 1.0 / TAMANY_Y;

        for (int i = 0; i < TAMANY_Y; i++) {
            for (int j = 0; j < TAMANY_X; j++) {
                matriu[i][j] = new Casella();
                gbc.gridx = j;
                gbc.gridy = i;
                matriuPanell.add(matriu[i][j], gbc);
            }
        }

        //Definim la posició
        setLayout(new BorderLayout());
        add(matriuPanell, BorderLayout.CENTER);

        //Definim que s'ajusti la matriu quan el tamany de la finestra canvia
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarTamanyMatriu();
            }
        });

        setVisible(true);
    }

    //Funció per ajustar el tamany visual de la matriu segons el tamany de la finestra
    private void ajustarTamanyMatriu() {
        int ampladaPantalla = getContentPane().getWidth();
        int alturaPantalla = getContentPane().getHeight();

        double proporcioMatriu = (double) TAMANY_X / TAMANY_Y;
        double proporcionPantalla = (double) ampladaPantalla / alturaPantalla;

        int ampladaMatriu, alturaMatriu;

        if (proporcionPantalla > proporcioMatriu) {
            // Ajustar por altura
            alturaMatriu = alturaPantalla;
            ampladaMatriu = (int) (alturaMatriu * proporcioMatriu);
        } else {
            // Ajustar por anchura
            ampladaMatriu = ampladaPantalla;
            alturaMatriu = (int) (ampladaMatriu / proporcioMatriu);
        }

        matriuPanell.setPreferredSize(new Dimension(ampladaMatriu, alturaMatriu));
        matriuPanell.revalidate();
    }

    //Funció per afegir un component a la casella indicada
    public void afegirComponent(Component component, int fila, int columna) {
        if (fila >= 0 && fila < TAMANY_Y && columna >= 0 && columna < TAMANY_X) {
            matriu[fila][columna].add(component);
            matriu[fila][columna].revalidate();
            matriu[fila][columna].repaint();
        }
    }

    //GETTERS

    public Casella[][] getMatriu() {
        return matriu;
    }
}