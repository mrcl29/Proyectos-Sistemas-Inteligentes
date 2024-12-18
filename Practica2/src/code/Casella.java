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
            // Afegir un escoltador de ratolí per a les caselles que no són sortida
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!Variables.cercaIniciada) {
                        // Gestió del clic esquerre per col·locar elements
                        if (SwingUtilities.isLeftMouseButton(e) && estatCasella == Constants.BUID
                                && ((Variables.nMonstres != Constants.MONSTRES_TOTALS
                                        && Variables.colocar == Constants.MONSTRE)
                                        || (Variables.nTresors != Constants.TRESORS_TOTALS
                                                && Variables.colocar == Constants.TRESOR)
                                        || Variables.colocar == Constants.PRECIPICI)) {

                            setEstatCasella(Variables.colocar);

                            // Actualitzar comptadors
                            if (Variables.colocar == Constants.MONSTRE) {
                                Variables.nMonstres++;
                            } else if (Variables.colocar == Constants.TRESOR) {
                                Variables.nTresors++;
                            }

                        }
                        // Gestió del clic dret per eliminar elements
                        else if (SwingUtilities.isRightMouseButton(e)
                                && estatCasella != Constants.BUID && estatCasella != Constants.AGENT
                                && estatCasella != Constants.SORTIDA) {

                            // Actualitzar comptadors
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

    // Mètodes getters i setters
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

        // Dibuixar el contorn negre de la casella
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth(), getHeight());

        // Calcular la mida màxima per a la imatge mantenint l'aspecte
        int maxWidth = (int) (getWidth() * 0.9);
        int maxHeight = (int) (getHeight() * 0.9);

        // Seleccionar la icona adequada segons l'estat de la casella
        ImageIcon iconToUse = null;
        if (estatCasella == Constants.MONSTRE) {
            iconToUse = Constants.MONSTRE_ICON;
        } else if (estatCasella == Constants.PRECIPICI) {
            iconToUse = Constants.PRECIPICI_ICON;
        } else if (estatCasella == Constants.TRESOR) {
            iconToUse = Constants.TRESOR_ICON;
        } else if (estatCasella == Constants.AGENT || estatCasella == Constants.TRESOR_AGENT) {
            iconToUse = Constants.AGENT_ICON;
        } else if (estatCasella == Constants.SORTIDA) {
            iconToUse = Constants.SORTIDA_ICON;
        }

        if (iconToUse != null) {
            Image img = iconToUse.getImage();
            int originalWidth = img.getWidth(this);
            int originalHeight = img.getHeight(this);

            // Calcular el factor d'escala
            double scale = Math.min((double) maxWidth / originalWidth, (double) maxHeight / originalHeight);

            // Calcular les noves dimensions
            int scaledWidth = (int) (originalWidth * scale);
            int scaledHeight = (int) (originalHeight * scale);

            // Calcular la posició per centrar la imatge
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2;

            // Dibuixar la imatge escalada
            g2d.drawImage(img, x, y, scaledWidth, scaledHeight, this);
        }

        g2d.dispose();
    }
}
