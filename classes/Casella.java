import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Casella extends JPanel {
    private boolean paret;
    private boolean editable;
    private static boolean arrastrant = false;
    private static boolean pintantParets = false;

    public Casella(boolean esParet) {
        this.paret = esParet;
        if (esParet) {
            this.editable = false;
        } else {
            this.editable = true;
        }

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (editable) {
                    arrastrant = true;
                    pintantParets = !paret;
                    toggleParet();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                arrastrant = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (editable && arrastrant) {
                    setParet(pintantParets);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public boolean isParet() {
        return paret;
    }

    public void setParet(boolean paret) {
        if (editable) {
            this.paret = paret;
            repaint();
        }
    }

    public boolean isEditable() {
        return editable;
    }

    private void toggleParet() {
        this.paret = !this.paret;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (paret) {
            if(editable){
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.setColor(new Color(139, 69, 19)); // Marrón
                g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            }else{
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.setColor(Color.DARK_GRAY); // Marrón
                g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            }
            

        } else {
            // Si no es paret, solo dibujar el borde negro
            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

        g2d.dispose();
    }
}