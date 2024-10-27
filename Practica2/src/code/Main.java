package code;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {
    public static int tamanyEscenari;
    private static Escenari escenari;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            boolean surt = false;
            while (!surt) {
                String input = JOptionPane.showInputDialog(null, "Tamany de l'escenari (n x n):", "N",
                        JOptionPane.QUESTION_MESSAGE);

                if (input != null) {

                    try {
                        tamanyEscenari = Integer.parseInt(input);
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

            escenari = new Escenari(tamanyEscenari);
            frame.add(escenari, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
