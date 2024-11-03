package code;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Casella extends JPanel {
    // estatsPossibles = { "BUID", "MONSTRE", "PRECIPICI", "TRESOR", "AGENT" };
    private String estatCasella = Constants.BUID;
    private boolean esSortida;

    public Casella(boolean esSortida) {
        this.esSortida = esSortida;

        setLayout(new BorderLayout());

        if (!esSortida) {
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!Variables.cercaIniciada) {
                        if (SwingUtilities.isLeftMouseButton(e) && estatCasella == Constants.BUID
                                && ((Variables.nMonstres != Constants.MONSTRES_TOTALS
                                        && Variables.colocar == Constants.MONSTRE)
                                        || (Variables.nTresors != Constants.TRESORS_TOTALS
                                                && Variables.colocar == Constants.TRESOR)
                                        || Variables.colocar == Constants.PRECIPICI)) {

                            setEstatCasella(Variables.colocar);

                            if (Variables.colocar == Constants.MONSTRE) {
                                Variables.nMonstres++;
                            } else if (Variables.colocar == Constants.TRESOR) {
                                Variables.nTresors++;
                            }

                        } else if (SwingUtilities.isRightMouseButton(e)
                                && estatCasella != Constants.BUID && estatCasella != Constants.AGENT
                                && estatCasella != Constants.SORTIDA) {

                            if (estatCasella == Constants.MONSTRE) {
                                Variables.nMonstres--;
                            } else if (estatCasella == Constants.TRESOR) {
                                Variables.nTresors--;
                            }

                            setEstatCasella(Constants.BUID);
                        }
                    }
                }
            };

            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
        } else {
            estatCasella = Constants.SORTIDA;
        }
    }

    public String getEstatCasella() {
        return estatCasella;
    }

    public boolean getEsSortida() {
        return esSortida;
    }

    public void setEstatCasella(String nouEstat) {
        if (nouEstat == Constants.BUID && esSortida) {
            estatCasella = Constants.SORTIDA;
        } else {
            estatCasella = nouEstat;
        }
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
        if (estatCasella == Constants.MONSTRE) {
            iconToUse = Constants.MONSTRE_ICON;
        } else if (estatCasella == Constants.PRECIPICI) {
            iconToUse = Constants.PRECIPICI_ICON;
        } else if (estatCasella == Constants.TRESOR) {
            iconToUse = Constants.TRESOR_ICON;
        } else if (estatCasella == Constants.AGENT) {
            iconToUse = Constants.AGENT_ICON;
        } else if (estatCasella == Constants.SORTIDA) {
            iconToUse = Constants.SORTIDA_ICON;
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
