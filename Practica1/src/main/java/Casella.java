import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Casella extends JPanel {
    public boolean paret;
    private boolean editable;
    private static boolean arrastrant = false;
    private static boolean pintantParets = false;
    private boolean conteRobot = false;
    public MouseAdapter mouseAdapter;

    public Casella(boolean esParet, int fila, int columna) {
        this.paret = esParet;
        this.editable = !esParet;

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && editable && !conteRobot) {
                    arrastrant = true;
                    pintantParets = !paret;
                    toggleParet();
                } else if (SwingUtilities.isRightMouseButton(e) && !paret && !conteRobot) {
                    setLayout(new BorderLayout());
                    add(Main.robot, BorderLayout.CENTER);
                    setConteRobot(true);
                    Escenari.matriu[Main.fila][Main.columna].removeAll();
                    Escenari.matriu[Main.fila][Main.columna].setConteRobot(false);
                    Main.fila = fila;
                    Main.columna = columna;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    arrastrant = false;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && editable && arrastrant && !conteRobot) {
                    setParet(pintantParets);
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public boolean esParet() {
        return paret;
    }

    public void setParet(boolean paret) {
        if (editable && !conteRobot) {
            this.paret = paret;
            repaint();
        }
    }

    private void toggleParet() {
        if (!conteRobot) {
            this.paret = !this.paret;
            repaint();
        }
    }

    public void setConteRobot(boolean conteRobot) {
        this.conteRobot = conteRobot;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (paret) {
            if (editable) {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.setColor(new Color(139, 69, 19)); // Marrón
                g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            } else {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.setColor(Color.DARK_GRAY); // Gris oscuro
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
