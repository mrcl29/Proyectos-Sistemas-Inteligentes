package code;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class Main {
    private static Escenari escenari;
    private static Agent agent;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

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

            Variables.timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });

            Menu menu = new Menu(frame.getWidth());
            frame.add(menu, BorderLayout.NORTH);

            escenari = new Escenari();
            frame.add(escenari, BorderLayout.CENTER);

            agent = new Agent(Constants.filaInicial, Constants.columnaInicial);

            frame.setVisible(true);
        });
    }
}
