package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Casella extends JPanel {
    private final String path = "Practica2/src/assets/";
    private final ImageIcon monstreIcon = new ImageIcon(path + "monstre.png");
    private final ImageIcon precipiciIcon = new ImageIcon(path + "precipici.png");
    private final ImageIcon tresorIcon = new ImageIcon(path + "tresor.png");

    // estatsPossibles = { "BUID", "MONSTRE", "PRECIPICI", "TRESOR", "AGENT" };
    private static final String buid = "BUID";
    private static final String monstre = "MONSTRE";
    private static final String precipici = "PRECIPICI";
    private static final String tresor = "TRESOR";
    private String estatCasella = buid;

    public static String colocar = buid;
    private static boolean monstreColocat = false;
    private static boolean tresorColocat = false;
    public MouseAdapter mouseAdapter;

    public Casella() {
        setLayout(new BorderLayout());

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && estatCasella == buid
                        && ((!monstreColocat && colocar == monstre)
                                || (!tresorColocat && colocar == tresor) || colocar == precipici)) {
                    setEstatCasella(colocar);
                    if (colocar == monstre) {
                        monstreColocat = true;
                    } else if (colocar == tresor) {
                        tresorColocat = true;
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && estatCasella != buid) {
                    if (estatCasella == monstre) {
                        monstreColocat = false;
                    } else if (estatCasella == tresor) {
                        tresorColocat = false;
                    }
                    setEstatCasella(buid);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setEstatCasella(String nouEstat) {
        estatCasella = nouEstat;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Si no es paret, solo dibujar el borde negro
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth(), getHeight());

        // Calcular el tamaño máximo para la imagen manteniendo el aspecto
        int maxWidth = (int) (getWidth() * 0.9);
        int maxHeight = (int) (getHeight() * 0.9);

        ImageIcon iconToUse = null;
        if (estatCasella == monstre) {
            iconToUse = monstreIcon;
        } else if (estatCasella == precipici) {
            iconToUse = precipiciIcon;
        } else if (estatCasella == tresor) {
            iconToUse = tresorIcon;
        }

        if (iconToUse != null) {
            Image img = iconToUse.getImage();
            int originalWidth = img.getWidth(this);
            int originalHeight = img.getHeight(this);

            // Calcular el factor de escala
            double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);

            // Calcular las nuevas dimensiones
            int scaledWidth = (int) (originalWidth * scale);
            int scaledHeight = (int) (originalHeight * scale);

            // Calcular la posición para centrar la imagen
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2;

            // Dibujar la imagen escalada
            g2d.drawImage(img, x, y, scaledWidth, scaledHeight, this);
        }

        g2d.dispose();
    }
}
