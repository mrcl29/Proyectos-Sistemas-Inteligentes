package code;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Menu extends JPanel {
    private JButton iniciBoto;
    private JButton monstreBoto;
    private JButton precipiciBoto;
    private JButton tresorBoto;
    private JButton canviarVelocitatBoto;

    @SuppressWarnings("unused")
    public Menu(int width) {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, Constants.ALTURA_MENU));

        // Creació i configuració dels botons
        iniciBoto = crearBoto("INICIAR");
        iniciBoto.addActionListener(e -> {
            if (Variables.cercaIniciada) {
                aturarCerca();
                Main.reinici();
            } else {
                iniciarCerca();
            }
            Variables.cercaIniciada = !Variables.cercaIniciada;
        });

        monstreBoto = crearBoto("MONSTRE");
        monstreBoto.addActionListener(e -> {
            Variables.colocar = "MONSTRE";
        });

        precipiciBoto = crearBoto("PRECIPICI");
        precipiciBoto.addActionListener(e -> {
            Variables.colocar = "PRECIPICI";
        });

        tresorBoto = crearBoto("TRESOR");
        tresorBoto.addActionListener(e -> {
            Variables.colocar = "TRESOR";
        });

        canviarVelocitatBoto = crearBoto("CANVIAR VELOCITAT");
        canviarVelocitatBoto.addActionListener(e -> {
            // Bucle per obtenir la nova velocitat
            boolean surt = false;
            while (!surt) {
                String input = JOptionPane.showInputDialog(null, "Nova velocitat en milisegons (ms):", "ms",
                        JOptionPane.QUESTION_MESSAGE);

                if (input != null) {
                    try {
                        int velocitat = Integer.parseInt(input);
                        Variables.novaVelocitat(velocitat);
                        surt = true;
                    } catch (NumberFormatException error) {
                        JOptionPane.showMessageDialog(null, "Ingressa un nombre acceptat.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operació cancelada", "Avís", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
        });

        // Configuració del layout i addició dels botons
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(iniciBoto);
        add(monstreBoto);
        add(precipiciBoto);
        add(tresorBoto);
        add(canviarVelocitatBoto);
    }

    // Mètode per iniciar la cerca
    public static void iniciarCerca() {
        if (!Variables.timer.isRunning()) {
            Variables.timer.start();
        }
    }

    // Mètode per aturar la cerca
    public static void aturarCerca() {
        if (Variables.timer.isRunning()) {
            Variables.timer.stop();
        }
    }

    // Mètode per crear botons personalitzats
    private JButton crearBoto(String texte) {
        JButton boto = new JButton(texte);
        boto.setPreferredSize(new Dimension(200, 40)); // Ajustar mida del botó
        boto.setFont(new Font("Arial", Font.BOLD, 16)); // Canviar la font i mida
        boto.setForeground(Color.WHITE); // Text en blanc
        boto.setFocusPainted(false); // Eliminar vora de focus
        boto.setBorder(new RoundedBorder(10)); // Vores arrodonides
        boto.setBackground(new Color(70, 130, 180)); // Fons color blau
        return boto;
    }

    // Classe interna per crear vores arrodonides per al botó
    private static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
