package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Agent extends Variables {
    private int fila;
    private int columna;
    private ArrayList<Posicio> perill;
    private ArrayList<Posicio> visitades;
    private Stack<Posicio> cami;
    private int direccioActual;
    private boolean teTresor;

    // Constructor de l'Agent
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

    // Mètode principal per moure l'agent
    public void moviment() {
        Posicio novaPosicio = trobarNovaPosicioValida();
        if (novaPosicio != null && !teTresor) {
            moureACasella(novaPosicio.fila, novaPosicio.columna);
        } else {
            tornarEnrere();
        }
    }

    // Cerca una nova posició vàlida per moure's
    private Posicio trobarNovaPosicioValida() {
        for (int i = 0; i < DIRECCIO.length; i++) {
            int index = (direccioActual + i) % DIRECCIO.length;
            int novaFila = fila + MOVIMIENTS.get(DIRECCIO[index])[0];
            int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[index])[1];
            Posicio novaPosicio = new Posicio(novaFila, novaColumna);
            if (!foraDelEscenari(novaFila, novaColumna) && !perill.contains(novaPosicio)
                    && !visitades.contains(novaPosicio)) {
                direccioActual = index;
                return novaPosicio;
            }
        }
        return null;
    }

    // Mou l'agent a una nova casella
    private void moureACasella(int novaFila, int novaColumna) {
        String estatCasella = matriuEscenari[novaFila][novaColumna].getEstatCasella();
        if (estatCasella.equals(BUID) || estatCasella.equals(SORTIDA)) {
            realitzarMoviment(novaFila, novaColumna);
        } else if (estatCasella.equals(TRESOR)) {
            realitzarMoviment(novaFila, novaColumna);
            teTresor = true;
        } else if (estatCasella.equals(MONSTRE) || estatCasella.equals(PRECIPICI)) {
            perill.add(new Posicio(novaFila, novaColumna));
            direccioActual = (direccioActual + 1) % DIRECCIO.length;
        }
    }

    // Gestiona el retorn de l'agent
    private void tornarEnrere() {
        if (!cami.isEmpty() && teTresor) {
            List<Posicio> caminoRetorno = calcularCamiTornada();
            if (caminoRetorno != null && !caminoRetorno.isEmpty()) {
                Posicio siguientePaso = caminoRetorno.get(0);
                moureACasella(siguientePaso.fila, siguientePaso.columna);
                if (FILA_INICI == siguientePaso.fila && COLUMNA_INICI == siguientePaso.columna) {
                    nTresors = 0;
                    JOptionPane.showMessageDialog(null, "Tresor trobat!!!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
                    Main.reinici();
                }
            }
        } else if (!cami.isEmpty()) {
            Posicio anteriorPosicio = cami.peek();
            int passosFila = anteriorPosicio.fila - fila;
            int passosColumna = anteriorPosicio.columna - columna;
            // Moure una casella a la vegada
            if (passosFila != 0) {
                realitzarMoviment(fila + Integer.signum(passosFila), columna);
            } else if (passosColumna != 0) {
                realitzarMoviment(fila, columna + Integer.signum(passosColumna));
            }
            System.out.println("Tornant enrere a: " + fila + ", " + columna);
            cami.pop(); // Eliminar la posició actual
        } else if (!teTresor) {
            moureACasellaVisitada();
        }
    }

    // Mou l'agent a una casella ja visitada
    private void moureACasellaVisitada() {
        for (int i = 0; i < DIRECCIO.length; i++) {
            int novaFila = fila + MOVIMIENTS.get(DIRECCIO[i])[0];
            int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[i])[1];
            if (!foraDelEscenari(novaFila, novaColumna) && !perill.contains(new Posicio(novaFila, novaColumna))) {
                realitzarMoviment(novaFila, novaColumna);
                direccioActual = i;
                System.out.println("Movent-se a una casella ja visitada: " + novaFila + ", " + novaColumna);
                return;
            }
        }
        System.out.println("L'agent està completament bloquejat i no pot moure's.");
    }

    // Realitza el moviment físic de l'agent
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

    // Comprova si una posició està fora de l'escenari
    public boolean foraDelEscenari(int novaFila, int novaColumna) {
        return novaFila < 0 || novaFila >= tamanyEscenari || novaColumna < 0 || novaColumna >= tamanyEscenari;
    }

    // Getters
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean teTresor() {
        return teTresor;
    }

    // Calcula el camí de tornada utilitzant l'algorisme A*
    private List<Posicio> calcularCamiTornada() {
        boolean[][] tablero = new boolean[matriuEscenari.length][matriuEscenari[0].length];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = (visitades.contains(new Posicio(i, j)) && !perill.contains(new Posicio(i, j)));
            }
        }
        List<Posicio> camino = trobarcami(tablero, fila, columna, FILA_INICI, COLUMNA_INICI);
        if (camino != null && !camino.isEmpty()) {
            camino.remove(0); // Removemos la posición actual
        }
        return camino;
    }

    // Constants per a l'algorisme A*
    private static final int[] DX = { -1, 1, 0, 0 };
    private static final int[] DY = { 0, 0, -1, 1 };

    // Implementació de l'algorisme A*
    private List<Posicio> trobarcami(boolean[][] tauler, int filaInicial, int columnaInicial, int filaSortida,
            int columneSortida) {
        int files = tauler.length;
        int columnes = tauler[0].length;
        PriorityQueue<Posicio> oberts = new PriorityQueue<>(Comparator.comparingInt(Posicio::f));
        boolean[][] tancats = new boolean[files][columnes];

        Posicio inici = new Posicio(filaInicial, columnaInicial, 0,
                manhattan(filaInicial, columnaInicial, filaSortida, columneSortida), null);
        oberts.offer(inici);

        while (!oberts.isEmpty()) {
            Posicio actual = oberts.poll();
            if (actual.fila == filaSortida && actual.columna == columneSortida) {
                return reconstruirCami(actual);
            }
            tancats[actual.fila][actual.columna] = true;

            for (int i = 0; i < 4; i++) {
                int novaFila = actual.fila + DX[i];
                int novaColumna = actual.columna + DY[i];

                if (novaFila < 0 || novaFila >= files || novaColumna < 0 || novaColumna >= columnes ||
                        tancats[novaFila][novaColumna] || !tauler[novaFila][novaColumna]) {
                    continue;
                }

                int nouG = actual.g + 1;
                int nouH = manhattan(novaFila, novaColumna, filaSortida, columneSortida);
                Posicio vei = new Posicio(novaFila, novaColumna, nouG, nouH, actual);

                if (!oberts.contains(vei) || nouG < vei.g) {
                    oberts.offer(vei);
                }
            }
        }
        return null;
    }

    // Calcula la distància de Manhattan entre dos punts
    private static int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // Reconstrueix el camí trobat per l'algorisme A*
    private static List<Posicio> reconstruirCami(Posicio fi) {
        List<Posicio> cami = new ArrayList<>();
        Posicio actual = fi;
        while (actual != null) {
            cami.add(0, actual);
            actual = actual.pare;
        }
        return cami;
    }
}
