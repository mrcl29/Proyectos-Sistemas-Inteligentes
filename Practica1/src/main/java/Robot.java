import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Robot extends JPanel {
    Random rand = new Random();
    private static final String path = "Practica1/src/main/resources/assets/robot.png";
    private final ImageIcon robotIcon = new ImageIcon(path);
    private int fila, columna; // Posició actual del robot
    private Escenari escenari;
    private Timer timer;
    private String direccio = "NORD"; // NORD, EST, SUD, OEST
    private boolean seguintParet;// Flag per saber si el robot esta seguint una paret o no

    public Robot(Escenari escenari) {
        this.escenari = escenari;
        fila = rand.nextInt(Main.FILES - 2) + 1; // Genera un número entre 1 y Main.FILES - 2
        columna = rand.nextInt(Main.COLUMNES - 2) + 1; // Genera un número entre 1 y Main.COLUMNES - 2
        if (paretsDiagonals() && paretsAlCostat() != "") {
            this.seguintParet = true;
        } else {
            this.seguintParet = false;
        }

        timer = new Timer(Main.VELOCITAT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moureRobot();
            }
        });
        setOpaque(false);
    }

    public void iniciarMovimentPerimetre() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void aturarMovimentPerimetre() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    private void moureRobot() {
        escenari.eliminarComponent(fila, columna);

        if (!seguintParet) {// Si no estaba seguint ninguna paret mirar si ara té alguna paret al costat
            String nova_direccio = paretsAlCostat();
            if (nova_direccio != "") {// Si te una paret al costat donar-li la nova direcció a seguir
                direccio = nova_direccio;
                seguintParet = true;
            }
        }
        switch (direccio) {
            case "NORD":
                if (escenari.getEsParet(fila, columna - 1)) {// Amb paret al costat (esquerre)
                    if (!escenari.getEsParet(fila - 1, columna)) {// Sense paret al front (adalt)
                        fila--;// Moviment recte (adalt)
                    } else {// Amb paret al front (adalt)
                            // Gir al costat oposat de la paret (dreta)
                        direccio = "EST";
                    }
                } else {// Sense paret al costat (esquerre)
                    if (!paretsDiagonals()) {// No tenim parets a les diagonals
                        // Mirar les altres parets al costat
                        String nova_direccio = paretsAlCostat();
                        if (nova_direccio == "") {// Sense parets als costats
                            fila--;// Moviment recte (adalt)
                            seguintParet = false;// No segueix ninguna paret
                            break;
                        } else {// Si hi ha alguna paret al costat seguir el seu perímetre
                            direccio = nova_direccio;
                        }
                    } else {// Tenim parets diagonals
                        columna--;// Gir seguint el cantó de la paret (esquerra)
                        direccio = "OEST";
                    }
                }
                break;
            case "EST":
                if (escenari.getEsParet(fila - 1, columna)) {// Amb paret al costat (adalt)
                    if (!escenari.getEsParet(fila, columna + 1)) {// Sense paret al front (dreta)
                        columna++;// Moviment recte (dreta)
                    } else {// Amb paret al front (dreta)
                            // Gir al costat oposat de la paret (abaix)
                        direccio = "SUD";
                    }
                } else {// Sense paret al costat (adalt)
                    if (!paretsDiagonals()) {// No tenim parets a les diagonals
                        // Mirar les altres parets al costat
                        String nova_direccio = paretsAlCostat();
                        if (nova_direccio == "") {// Sense parets als costats
                            columna++;// Moviment recte (dreta)
                            seguintParet = false;// No segueix ninguna paret
                            break;
                        } else {// Si hi ha alguna paret al costat seguir el seu perímetre
                            direccio = nova_direccio;
                        }
                    } else {// Tenim parets diagonals
                        fila--;// Gir seguint el cantó de la paret (adalt)
                        direccio = "NORD";
                    }
                }
                break;
            case "SUD":
                if (escenari.getEsParet(fila, columna + 1)) {// Amb paret al costat (dreta)
                    if (!escenari.getEsParet(fila + 1, columna)) {// Sense paret al front (abaix)
                        fila++;// Moviment recte (abaix)
                    } else {// Amb paret al front (abaix)
                            // Gir al costat oposat de la paret (esquerra)
                        direccio = "OEST";
                    }
                } else {// Sense paret al costat (dreta)
                    if (!paretsDiagonals()) {// No tenim parets a les diagonals
                        // Mirar les altres parets al costat
                        String nova_direccio = paretsAlCostat();
                        if (nova_direccio == "") {// Sense parets als costats
                            fila++;// Moviment recte (abaix)
                            seguintParet = false;// No segueix ninguna paret
                            break;
                        } else {// Si hi ha alguna paret al costat seguir el seu perímetre
                            direccio = nova_direccio;
                        }
                    } else {// Tenim parets diagonals
                        columna++;// Gir seguint el cantó de la paret (dreta)
                        direccio = "EST";
                    }
                }
                break;
            case "OEST":
                if (escenari.getEsParet(fila + 1, columna)) {// Amb paret al costat (abaix)
                    if (!escenari.getEsParet(fila, columna - 1)) {// Sense paret al front (esquerra)
                        columna--;// Moviment recte (dreta)
                    } else {// Amb paret al front (esquerra)
                            // Gir al costat oposat de la paret (adalt)
                        direccio = "NORD";
                    }
                } else {// Sense paret al costat (abaix)
                    if (!paretsDiagonals()) {// No tenim parets a les diagonals
                        // Mirar les altres parets al costat
                        String nova_direccio = paretsAlCostat();
                        if (nova_direccio == "") {// Sense parets als costats
                            columna--;// Moviment recte (esquerra)
                            seguintParet = false;// No segueix ninguna paret
                            break;
                        } else {// Si hi ha alguna paret al costat seguir el seu perímetre
                            direccio = nova_direccio;
                        }
                    } else {// Tenim parets diagonals
                        fila++;// Gir seguint el cantó de la paret (abaix)
                        direccio = "SUD";
                    }
                }
                break;
            default:
                break;
        }

        escenari.afegirComponent(this, fila, columna);
    }

    private boolean paretsDiagonals() {
        return escenari.getEsParet(fila - 1, columna + 1) || escenari.getEsParet(fila + 1, columna + 1)
                || escenari.getEsParet(fila + 1, columna - 1) || escenari.getEsParet(fila - 1, columna - 1);
    }

    private String paretsAlCostat() {
        if (escenari.getEsParet(fila, columna - 1)) {
            return "NORD";
        } else if (escenari.getEsParet(fila - 1, columna)) {
            return "EST";
        } else if (escenari.getEsParet(fila, columna + 1)) {
            return "SUD";
        } else if (escenari.getEsParet(fila + 1, columna)) {
            return "OEST";
        }
        return "";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (robotIcon != null) {
            int width = (int) (getWidth() * 0.9);
            int height = (int) (getHeight() * 0.9);
            int x = (getWidth() - width) / 2;
            int y = (getHeight() - height) / 2;
            g.drawImage(robotIcon.getImage(), x, y, width, height, this);
        }
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

}
