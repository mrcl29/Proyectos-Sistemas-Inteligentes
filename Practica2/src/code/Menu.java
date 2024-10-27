package code;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Menu extends JPanel {
    private static final int HEIGHT = 50;
    private JButton iniciBoto;
    private JButton monstreBoto;
    private JButton precipiciBoto;
    private JButton tresorBoto;

    public Menu(int width) {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(width, HEIGHT));

        iniciBoto = crearBoto("INICIAR");
        iniciBoto.addActionListener(e -> {

        });
        monstreBoto = crearBoto("MONSTRE");
        monstreBoto.addActionListener(e -> {
            Casella.colocar = "MONSTRE";
        });
        precipiciBoto = crearBoto("PRECIPICI");
        precipiciBoto.addActionListener(e -> {
            Casella.colocar = "PRECIPICI";
        });
        tresorBoto = crearBoto("TRESOR");
        tresorBoto.addActionListener(e -> {
            Casella.colocar = "TRESOR";
        });

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(iniciBoto);
        add(monstreBoto);
        add(precipiciBoto);
        add(tresorBoto);
    }

    private JButton crearBoto(String texte) {
        // Crear el botón
        JButton boto = new JButton(texte);
        boto.setPreferredSize(new Dimension(120, 40)); // Ajustar tamaño del botón
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
