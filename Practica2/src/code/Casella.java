package code;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Casella extends JPanel {
    private final String path = "Practica2/src/assets/";
    private final ImageIcon monstreIcon = new ImageIcon(path + "monstre.png");
    private final ImageIcon precipiciIcon = new ImageIcon(path + "precipici.png");
    private final ImageIcon tresorIcon = new ImageIcon(path + "tresor.png");
    private final ImageIcon agentIcon = new ImageIcon(path + "agent.png");

    // estatsPossibles = { "BUID", "MONSTRE", "PRECIPICI", "TRESOR", "AGENT" };
    private String estatCasella = Constants.BUID;

    public Casella(boolean inicial) {

        setLayout(new BorderLayout());

        if (inicial) {
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) && estatCasella == Constants.BUID
                            && ((Variables.nMonstres != Constants.MONSTRES_TOTALS
                                    && Variables.colocar == Constants.MONSTRE)
                                    || (Variables.nTresors != Constants.TRESORS_TOTALS
                                            && Variables.colocar == Constants.TRESOR)
                                    || Variables.colocar == Constants.PRECIPICI)) {

                        if (setEstatCasella(Variables.colocar)) {
                            if (Variables.colocar == Constants.MONSTRE) {
                                Variables.nMonstres++;
                            } else if (Variables.colocar == Constants.TRESOR) {
                                Variables.nTresors++;
                            }
                        }

                    } else if (SwingUtilities.isRightMouseButton(e)
                            && estatCasella != Constants.BUID && estatCasella != Constants.AGENT) {

                        if (estatCasella == Constants.MONSTRE) {
                            Variables.nMonstres--;
                        } else if (estatCasella == Constants.TRESOR) {
                            Variables.nTresors--;
                        }

                        setEstatCasella(Constants.BUID);
                    }
                }
            };

            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
        }
    }

    public String getEstatCasella() {
        return estatCasella;
    }

    public boolean setEstatCasella(String nouEstat) {
        if (nouEstat == Constants.BUID || estatCasella == Constants.BUID) {
            estatCasella = nouEstat;
            repaint();
            return true;
        }
        return false;
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
        if (estatCasella == Constants.MONSTRE) {
            iconToUse = monstreIcon;
        } else if (estatCasella == Constants.PRECIPICI) {
            iconToUse = precipiciIcon;
        } else if (estatCasella == Constants.TRESOR) {
            iconToUse = tresorIcon;
        } else if (estatCasella == Constants.AGENT) {
            iconToUse = agentIcon;
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
