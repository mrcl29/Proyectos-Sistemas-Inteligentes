package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Agent extends Variables {
    private int fila;
    private int columna;
    private Stack<Posicio> cami;
    private int direccioActual;
    private boolean teTresor;

    // Constructor de l'Agent
    public Agent(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        teTresor = false;
        this.visitades = new HashMap<>();
        cami = new Stack<>();
        direccioActual = 0;
        matriuEscenari[fila][columna].setEstatCasella(AGENT);
        registrarVisita(fila, columna);
        cami.push(new Posicio(fila, columna));
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    ////////////////////////
    private Map<Posicio, Boolean[]> mapaPercepcions = new HashMap<>();// hedor, brisa, resplandor
    private Map<Posicio, Boolean[]> mapaPosibles = new HashMap<>();// posibleMosntre, posiblePrecipici
    private ArrayList<Posicio> mapaOk = new ArrayList<>();
    private Map<Posicio, Boolean[]> mapaPerills = new HashMap<>();// monstre, precipici
    private Map<Posicio, Integer> visitades;

    // Mètode principal per moure l'agent
    public void moviment() {
        if (teTresor) {
            tornarInici();
        } else {
            registrarOk(fila, columna);
            registrarVisita(fila, columna);
            Boolean[] percepcions = getPercepcions(fila, columna);
            registrarPercepcions(fila, columna, percepcions);
            actualitzarPossiblesPerills(percepcions);
            actualitzarBC();
            if (percepcions[2] == true) {
                // System.out
                // .println("Tresor trobat a fila " + String.valueOf(fila) + ", columna "
                // + String.valueOf(columna));
                teTresor = true;
            } else {
                Posicio novaPosicio = trobarNovaPosicioValida();
                if (novaPosicio != null) {
                    realitzarMoviment(novaPosicio.fila, novaPosicio.columna);
                } else {
                    tornarEnrere();
                }
            }
        }
    }

    // Realitza el moviment físic de l'agent
    private void realitzarMoviment(int novaFila, int novaColumna) {
        matriuEscenari[fila][columna].setEstatCasella(BUID);
        fila = novaFila;
        columna = novaColumna;
        if (matriuEscenari[fila][columna].getEstatCasella() == TRESOR) {
            matriuEscenari[fila][columna].setEstatCasella(TRESOR_AGENT);
        } else {
            matriuEscenari[fila][columna].setEstatCasella(AGENT);
        }
        // registrarVisita(fila, columna);
        cami.push(new Posicio(fila, columna));
    }

    private Posicio trobarNovaPosicioValida() {
        List<Posicio> posicionsSegures = new ArrayList<>();
        List<Posicio> posicionsRisc = new ArrayList<>();
        int minVisites = Integer.MAX_VALUE;

        for (int i = 0; i < DIRECCIO.length; i++) {
            int novaFila = fila + MOVIMIENTS.get(DIRECCIO[i])[0];
            int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[i])[1];
            Posicio novaPos = new Posicio(novaFila, novaColumna);

            if (!foraDelEscenari(novaFila, novaColumna) && mapaOk.contains(novaPos)) {
                int visites = visitades.getOrDefault(novaPos, 0);
                if (visites < minVisites) {
                    minVisites = visites;
                    posicionsSegures.add(novaPos);
                }
            } else if (mapaPosibles.containsKey(novaPos)) {
                posicionsRisc.add(novaPos);
            }
        }

        if (!posicionsSegures.isEmpty()) {
            posicionsSegures.sort(Comparator.comparingInt(pos -> visitades.getOrDefault(pos, 0)));
            return posicionsSegures.get(0);
        }

        return posicionsRisc.isEmpty() ? null : posicionsRisc.get(0);
    }

    private void registrarVisita(int fila, int columna) {
        Posicio pos = new Posicio(fila, columna);
        visitades.put(pos, visitades.getOrDefault(pos, 0) + 1);
    }

    private void registrarOk(int fila, int columna) {
        Posicio pos = new Posicio(fila, columna);
        Boolean[] aux = { false, false };
        mapaPosibles.put(pos, aux);
        mapaPerills.put(pos, aux);
        mapaOk.add(pos);
    }

    private void registrarPercepcions(int fila, int columna, Boolean[] perc) {
        Posicio pos = new Posicio(fila, columna);
        mapaPercepcions.put(pos, perc);
    }

    private Boolean[] getPercepcions(int fila, int columna) {
        boolean hedor = false;
        boolean brisa = false;
        boolean resplandor = false;

        for (int i = 0; i < DIRECCIO.length; i++) {
            int index = (direccioActual + i) % DIRECCIO.length;
            int novaFila = fila + MOVIMIENTS.get(DIRECCIO[index])[0];
            int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[index])[1];
            if (!foraDelEscenari(novaFila, novaColumna)) {
                String estatCasella = matriuEscenari[novaFila][novaColumna].getEstatCasella();
                if (estatCasella == MONSTRE) {
                    hedor = true;
                } else if (estatCasella == PRECIPICI) {
                    brisa = true;
                }
            }
        }

        String estatCasella = matriuEscenari[fila][columna].getEstatCasella();
        // System.out.println("Fila - Columna: " + String.valueOf(fila) + " - " +
        // String.valueOf(columna) + " /// Estat = "
        // + estatCasella);
        if (estatCasella == TRESOR || estatCasella == TRESOR_AGENT) {
            resplandor = true;
        }

        Boolean[] aux = { hedor, brisa, resplandor };
        return aux;
    }

    // Comprova si una posició està fora de l'escenari
    public boolean foraDelEscenari(int novaFila, int novaColumna) {
        return novaFila < 0 || novaFila >= tamanyEscenari || novaColumna < 0 || novaColumna >= tamanyEscenari;
    }

    private void actualitzarPossiblesPerills(Boolean[] perc) {
        for (int i = 0; i < DIRECCIO.length; i++) {
            int index = (direccioActual + i) % DIRECCIO.length;
            int novaFila = fila + MOVIMIENTS.get(DIRECCIO[index])[0];
            int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[index])[1];
            Posicio novaPos = new Posicio(novaFila, novaColumna);

            boolean foraEscenari = foraDelEscenari(novaFila, novaColumna);
            boolean esOk = (mapaOk.contains(novaPos));
            Boolean[] perills = mapaPerills.get(novaPos);
            boolean esPerill;
            if (perills != null) {
                esPerill = (perills[0] == true || perills[1] == true);
            } else {
                esPerill = false;
            }

            if (!foraEscenari && !esOk && !esPerill) {
                Boolean hedor = perc[0];
                Boolean brisa = perc[1];

                if (!hedor && !brisa) {
                    mapaOk.add(novaPos);
                }
                Boolean[] aux = { hedor, brisa };
                mapaPosibles.put(novaPos, aux);
            }
        }
    }

    private void actualitzarBC() {
        for (int i = 0; i < tamanyEscenari; i++) {
            for (int j = 0; j < tamanyEscenari; j++) {
                Posicio pos = new Posicio(i, j);
                Boolean[] perills = mapaPerills.get(pos);
                if (perills != null) {
                    if (!perills[0] && !perills[1]) {
                        if (mapaPercepcions.containsKey(pos)) {
                            Boolean[] perc = mapaPercepcions.get(pos);
                            if (perc[0]) {
                                registrarPerill(i, j, 0);
                            }
                            if (perc[1]) {
                                registrarPerill(i, j, 1);
                            }
                            if (perc[0] || perc[1]) {
                                break;
                            }
                        }
                        if (mapaPosibles.containsKey(pos)) {
                            Boolean[] posibles = mapaPosibles.get(pos);
                            boolean esOkm = false;
                            boolean esOkp = false;
                            if (posibles[0]) {
                                for (int t = 0; t < DIRECCIO.length; t++) {
                                    int index = (direccioActual + t) % DIRECCIO.length;
                                    int novaFila = i + MOVIMIENTS.get(DIRECCIO[index])[0];
                                    int novaColumna = j + MOVIMIENTS.get(DIRECCIO[index])[1];
                                    Posicio novaPos = new Posicio(novaFila, novaColumna);
                                    Boolean[] percAdj = mapaPercepcions.get(novaPos);
                                    if (!percAdj[0]) {
                                        esOkm = true;
                                        posibles[0] = false;
                                        mapaPosibles.put(pos, posibles);
                                    }
                                }
                            }
                            if (posibles[1]) {
                                for (int t = 0; t < DIRECCIO.length; t++) {
                                    int index = (direccioActual + t) % DIRECCIO.length;
                                    int novaFila = i + MOVIMIENTS.get(DIRECCIO[index])[0];
                                    int novaColumna = j + MOVIMIENTS.get(DIRECCIO[index])[1];
                                    Posicio novaPos = new Posicio(novaFila, novaColumna);
                                    Boolean[] percAdj = mapaPercepcions.get(novaPos);
                                    if (!percAdj[1]) {
                                        esOkp = true;
                                        posibles[1] = false;
                                        mapaPosibles.put(pos, posibles);
                                    }
                                }
                            }
                            if (esOkm && esOkp) {
                                mapaOk.add(pos);
                            }
                        }
                    }
                }
            }
        }
    }

    private void registrarPerill(int fila, int columna, int tipusPerill) {
        if (tipusPerill > -1 && tipusPerill < 2) {
            int filaRestant = -1;
            int columnaRestant = -1;
            int contadorCaselles = 0;
            for (int t = 0; t < DIRECCIO.length; t++) {
                int index = (direccioActual + t) % DIRECCIO.length;
                int novaFila = fila + MOVIMIENTS.get(DIRECCIO[index])[0];
                int novaColumna = columna + MOVIMIENTS.get(DIRECCIO[index])[1];
                Posicio novaPos = new Posicio(novaFila, novaColumna);

                Boolean[] perills = mapaPerills.get(novaPos);
                Boolean preci;
                if (perills != null) {
                    preci = perills[1 - tipusPerill];
                } else {
                    preci = false;
                }

                if (mapaOk.contains(novaPos) || foraDelEscenari(novaFila, novaColumna) || preci) {
                    contadorCaselles++;
                } else {
                    filaRestant = novaFila;
                    columnaRestant = novaColumna;
                }
            }
            if (contadorCaselles == 3) {
                Posicio posRestant = new Posicio(filaRestant, columnaRestant);
                Boolean[] aux = { false, false };
                if (mapaPerills.containsKey(posRestant)) {
                    aux = mapaPerills.get(posRestant);
                }
                aux[tipusPerill] = true;
                Boolean[] aux2 = { false, false };
                mapaPosibles.put(posRestant, aux2);
                mapaPerills.put(posRestant, aux);
            }
        }
    }

    // Mètode per tornar enrere a la casella anterior
    private void tornarEnrere() {
        if (!cami.isEmpty()) {
            // Elimina la posició actual de la pila
            // Posicio posicioActual = cami.pop();
            cami.pop();
            // Si la pila no està buida, torna a la posició anterior
            if (!cami.isEmpty()) {
                Posicio posicioAnterior = cami.peek();

                // Actualitza les coordenades de l'agent
                matriuEscenari[fila][columna].setEstatCasella(BUID); // Marca la casella actual com a buida
                fila = posicioAnterior.fila;
                columna = posicioAnterior.columna;
                matriuEscenari[fila][columna].setEstatCasella(AGENT); // Marca la nova posició amb l'agent
            }
        }
    }

    // Mètode per tornar a la casella inicial movent-se una casella a la vegada amb
    // l'algorisme A*
    private void tornarInici() {
        // Comprovar si ja estem a la casella inicial
        if (fila == FILA_INICI && columna == COLUMNA_INICI) {
            nTresors = 0;
            JOptionPane.showMessageDialog(null, "Tresor trobat!!!", "Èxit", JOptionPane.INFORMATION_MESSAGE);
            Main.reinici();
            cercaIniciada = false;
        }

        // Calcular el camí més curt utilitzant A*
        List<Posicio> cami = calcularCamiTornada();

        // Si hi ha un camí vàlid, moure's a la següent casella
        if (cami != null && !cami.isEmpty()) {
            Posicio seguent = cami.get(0); // Primera posició del camí
            realitzarMoviment(seguent.fila, seguent.columna); // Actualitzar la posició de l'agent
        } else {
            System.out.println("No s'ha trobat un camí de tornada a la casella inicial!");
        }
    }

    // Calcula el camí de tornada utilitzant l'algorisme A*
    private List<Posicio> calcularCamiTornada() {
        boolean[][] tablero = new boolean[matriuEscenari.length][matriuEscenari[0].length];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = (visitades.containsKey(new Posicio(i, j)));
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
