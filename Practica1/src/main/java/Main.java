import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main {
    public static final int COLUMNES = 21;
    public static final int FILES = 11;
    public static final int VELOCITAT = 100;// Milisegons
    public static Timer timer;
    private static Escenari escenari;
    public static Robot robot;
    public static Integer fila, columna; // Posició actual del robot
    private static String direccio = "NORD"; // NORD, EST, SUD, OEST
    private static boolean seguintParet;// Flag per saber si el robot esta seguint una paret o no
    private static Random rand = new Random();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear finestra de visualització
            JFrame frame = new JFrame("Robot Perímetre");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            // Crear l'escenari i el robot
            robot = new Robot();
            fila = rand.nextInt(Main.FILES - 2) + 1; // Genera un número entre 1 y Main.FILES - 2
            columna = rand.nextInt(Main.COLUMNES - 2) + 1; // Genera un número entre 1 y Main.COLUMNES - 2
            escenari = new Escenari();
            String novaDireccio = paretsAlCostat();
            if (paretsDiagonals() || novaDireccio != "") {
                seguintParet = true;
                direccio = novaDireccio;
            } else {
                seguintParet = false;
            }
            timer = new Timer(Main.VELOCITAT, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moureRobot();
                }
            });
            escenari.afegirComponent(robot, fila, columna);// Colocar el robot al centre de
                                                           // l'escenari

            // Crear el menú y controlar el moviment del robot
            Menu menu = new Menu(frame.getWidth(), robot);

            // Agregar el menú y l'escenari a la finestra
            frame.add(menu.getMenuPanel(), BorderLayout.NORTH);
            frame.add(escenari, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public static void iniciarMovimentPerimetre() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public static void aturarMovimentPerimetre() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    private static void moureRobot() {
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

        escenari.afegirComponent(robot, fila, columna);
    }

    private static boolean paretsDiagonals() {
        return escenari.getEsParet(fila - 1, columna + 1) || escenari.getEsParet(fila + 1, columna + 1)
                || escenari.getEsParet(fila + 1, columna - 1) || escenari.getEsParet(fila - 1, columna - 1);
    }

    private static String paretsAlCostat() {
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

}
