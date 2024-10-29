package code;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class Menu extends JPanel {
    private static final int HEIGHT = 50;
    private JButton iniciBoto;
    private JButton monstreBoto;
    private JButton precipiciBoto;
    private JButton tresorBoto;
    private JButton canviarVelocitatBoto;

    private boolean cercaIniciada = false;

    public Menu(int width) {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, HEIGHT));

        iniciBoto = crearBoto("INICIAR");
        iniciBoto.addActionListener(e -> {
            if (cercaIniciada) {
                aturarCerca();
            } else {
                iniciarCerca();
            }
            cercaIniciada = !cercaIniciada;
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
                    JOptionPane.showMessageDialog(null, "Operació cancelada", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
        });

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(iniciBoto);
        add(monstreBoto);
        add(precipiciBoto);
        add(tresorBoto);
        add(canviarVelocitatBoto);
    }

    public static void iniciarCerca() {
        if (!Variables.timer.isRunning()) {
            Variables.timer.start();
        }
    }

    public static void aturarCerca() {
        if (Variables.timer.isRunning()) {
            Variables.timer.stop();
        }
    }

    private JButton crearBoto(String texte) {
        // Crear el botón
        JButton boto = new JButton(texte);
        boto.setPreferredSize(new Dimension(200, 40)); // Ajustar tamaño del botón
        boto.setFont(new Font("Arial", Font.BOLD, 16)); // Cambiar la fuente y tamaño
        boto.setForeground(Color.WHITE); // Texto en blanco
        boto.setFocusPainted(false); // Eliminar borde de enfoque
        boto.setBorder(new RoundedBorder(10)); // Bordes redondeados
        boto.setBackground(new Color(70, 130, 180)); // Fondo color azul
        return boto;
    }

    // Clase para crear bordes redondeados para el botón
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
