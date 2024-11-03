package code;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

public class Agent extends Variables {
    private int fila;
    private int columna;

    private ArrayList<Posicio> perill;
    private ArrayList<Posicio> visitades;
    private Stack<Posicio> cami;

    private String[] direccio = { "NORD", "EST", "SUD", "OEST" };
    private int direccioActual;

    private boolean teTresor;

    public Agent(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        teTresor = false;
        perill = new ArrayList<>();
        visitades = new ArrayList<>();
        cami = new Stack<>();
        direccioActual = 0;
        matriuEscenari[fila][columna].setEstatCasella(AGENT);
        visitades.add(new Posicio(fila, columna));
        cami.push(new Posicio(fila, columna));
    }

    public void moviment() {
        Posicio novaPosicio = trobarNovaPosicioValida();
        if (novaPosicio != null && !teTresor) {
            moureACasella(novaPosicio.getFila(), novaPosicio.getColumna());
        } else {
            tornarEnrere();
        }
    }

    private Posicio trobarNovaPosicioValida() {
        for (int i = 0; i < direccio.length; i++) {
            int index = (direccioActual + i) % direccio.length;
            int novaFila = fila + MOVIMIENTS.get(direccio[index])[0];
            int novaColumna = columna + MOVIMIENTS.get(direccio[index])[1];
            Posicio novaPosicio = new Posicio(novaFila, novaColumna);

            if (!foraDelEscenari(novaFila, novaColumna) && !perill.contains(novaPosicio)
                    && !visitades.contains(novaPosicio)) {
                direccioActual = index;
                return novaPosicio;
            }
        }
        return null;
    }

    private void moureACasella(int novaFila, int novaColumna) {
        String estatCasella = matriuEscenari[novaFila][novaColumna].getEstatCasella();

        if (estatCasella.equals(BUID) || estatCasella.equals(SORTIDA)) {
            realitzarMoviment(novaFila, novaColumna);
        } else if (estatCasella.equals(TRESOR)) {
            realitzarMoviment(novaFila, novaColumna);
            teTresor = true;
        } else if (estatCasella.equals(MONSTRE) || estatCasella.equals(PRECIPICI)) {
            perill.add(new Posicio(novaFila, novaColumna));
            direccioActual = (direccioActual + 1) % direccio.length;
        }
    }

    private void tornarEnrere() {
        if (!cami.isEmpty()) {

            Posicio anteriorPosicio = cami.peek();
            int passosFila = anteriorPosicio.getFila() - fila;
            int passosColumna = anteriorPosicio.getColumna() - columna;

            // Mover una casilla a la vez
            if (passosFila != 0) {
                realitzarMoviment(fila + Integer.signum(passosFila), columna);
            } else if (passosColumna != 0) {
                realitzarMoviment(fila, columna + Integer.signum(passosColumna));
            }

            System.out.println("Tornant enrere a: " + fila + ", " + columna);
            cami.pop(); // Eliminar la posición actual
        } else if (teTresor) {
            Variables.nTresors--;
            JOptionPane.showMessageDialog(null, "Tresor trobat!!!", "Èxit",
                    JOptionPane.INFORMATION_MESSAGE);
            Main.reinici();
        } else {
            moureACasellaVisitada();
        }
    }

    private void moureACasellaVisitada() {
        for (int i = 0; i < direccio.length; i++) {
            int novaFila = fila + MOVIMIENTS.get(direccio[i])[0];
            int novaColumna = columna + MOVIMIENTS.get(direccio[i])[1];

            if (!foraDelEscenari(novaFila, novaColumna) && !perill.contains(new Posicio(novaFila, novaColumna))) {
                realitzarMoviment(novaFila, novaColumna);
                direccioActual = i;
                System.out.println("Movent-se a una casella ja visitada: " + novaFila + ", " + novaColumna);
                return;
            }
        }
        System.out.println("L'agent està completament bloquejat i no pot moure's.");
    }

    private void realitzarMoviment(int novaFila, int novaColumna) {
        matriuEscenari[fila][columna].setEstatCasella(BUID);
        fila = novaFila;
        columna = novaColumna;
        matriuEscenari[fila][columna].setEstatCasella(AGENT);
        if (!visitades.contains(new Posicio(fila, columna))) {
            visitades.add(new Posicio(fila, columna));
        }
        cami.push(new Posicio(fila, columna));
    }

    public boolean foraDelEscenari(int novaFila, int novaColumna) {
        return novaFila < 0 || novaFila >= tamanyEscenari || novaColumna < 0 || novaColumna >= tamanyEscenari;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean teTresor() {
        return teTresor;
    }
}
