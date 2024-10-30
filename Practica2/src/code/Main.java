package code;

import javax.swing.*;

import java.awt.BorderLayout;

public class Main extends Variables {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Variables.colocar = Constants.BUID;
            Variables.nMonstres = 0;
            Variables.nTresors = 0;

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
                    JOptionPane.showMessageDialog(null, "Operació cancelada", "Aviso", JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }

            // Crear finestra de visualització
            JFrame frame = new JFrame("La cova del monstre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            Menu menu = new Menu(frame.getWidth());
            frame.add(menu, BorderLayout.NORTH);

            escenari = new Escenari();
            frame.add(escenari, BorderLayout.CENTER);

            agent = new Agent(Constants.FILA_INICI, Constants.COLUMNA_INICI);

            Variables.novaVelocitat(500);

            frame.setVisible(true);
        });
    }

    public static void reinici() {
        if (Variables.matriuEscenari[agent.getFila()][agent.getColumna()].getEsSortida()) {
            Variables.matriuEscenari[agent.getFila()][agent.getColumna()].setEstatCasella(Constants.SORTIDA);
        } else {
            Variables.matriuEscenari[agent.getFila()][agent.getColumna()].setEstatCasella(Constants.BUID);
        }
        agent = new Agent(Constants.FILA_INICI, Constants.COLUMNA_INICI);
    }
}
