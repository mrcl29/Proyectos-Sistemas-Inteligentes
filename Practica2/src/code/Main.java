package code;

import javax.swing.*;
import java.awt.BorderLayout;

public class Main extends Variables {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Inicialització de variables globals
            Variables.colocar = Constants.BUID;
            Variables.nMonstres = 0;
            Variables.nTresors = 0;

            // Bucle per obtenir la mida de l'escenari
            boolean surt = false;
            while (!surt) {
                String input = JOptionPane.showInputDialog(null, "Tamany de l'escenari (n x n):", "N",
                        JOptionPane.QUESTION_MESSAGE);

                if (input != null) {
                    try {
                        Variables.tamanyEscenari = Integer.parseInt(input);
                        surt = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Ingressa un nombre acceptat.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operació cancelada", "Avís", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }

            // Creació i configuració de la finestra principal
            JFrame frame = new JFrame("La cova del monstre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Afegir el menú a la part superior de la finestra
            Menu menu = new Menu(frame.getWidth());
            frame.add(menu, BorderLayout.NORTH);

            // Crear i afegir l'escenari al centre de la finestra
            escenari = new Escenari();
            frame.add(escenari, BorderLayout.CENTER);

            // Inicialitzar l'agent a la posició inicial
            agent = new Agent(Constants.FILA_INICI, Constants.COLUMNA_INICI);

            // Mostrar la finestra
            frame.setVisible(true);
        });
    }

    // Mètode per reiniciar el joc
    public static void reinici() {
        // Restablir l'estat de la casella actual de l'agent
        if (Variables.matriuEscenari[agent.getFila()][agent.getColumna()].getEsSortida()) {
            Variables.matriuEscenari[agent.getFila()][agent.getColumna()].setEstatCasella(Constants.SORTIDA);
        } else {
            Variables.matriuEscenari[agent.getFila()][agent.getColumna()].setEstatCasella(Constants.BUID);
        }

        // Crear un nou agent a la posició inicial
        agent = new Agent(Constants.FILA_INICI, Constants.COLUMNA_INICI);

        // Aturar el temporitzador i reiniciar l'estat de cerca
        nTresors = 0;
        timer.stop();
    }
}
