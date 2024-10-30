package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Agent extends Variables {
    private int fila;
    private int columna;

    private Map<String, ArrayList<Posicio>> mapa = new HashMap<>();

    private String[] direccio = { "NORD", "EST", "SUD", "OEST" };// NORD, EST, SUD, OEST
    private int direccio_idx = 0;

    private boolean teTresor = false;

    public Agent(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        ArrayList<Posicio> entrada = new ArrayList<>();
        entrada.add(new Posicio(fila, columna));
        mapa.put("ENTRADA", entrada);
        mapa.put("VISITADA", new ArrayList<>());
        mapa.put("MONSTRE", new ArrayList<>());
        mapa.put("PRECIPICI", new ArrayList<>());
        matriuEscenari[fila][columna].setEstatCasella(AGENT);
    }

    public void moviment() {
        int novaFila = fila + MOVIMIENTS.get(direccio[direccio_idx % direccio.length])[0];
        int novaColumna = columna + MOVIMIENTS.get(direccio[direccio_idx % direccio.length])[1];
        Posicio pos_aux = new Posicio(novaFila, novaColumna);
        while (mapa.get(MONSTRE).contains(pos_aux) || mapa.get(PRECIPICI).contains(pos_aux)
                || foraDelEscenari(novaFila, novaColumna)) {
            direccio_idx++;
            novaFila = fila + MOVIMIENTS.get(direccio[direccio_idx % direccio.length])[0];
            novaColumna = columna + MOVIMIENTS.get(direccio[direccio_idx % direccio.length])[1];
            pos_aux = new Posicio(novaFila, novaColumna);
        }
        if (!mapa.get("VISITADA").contains(pos_aux) || senseOpcio()) {
            String estatCasella = matriuEscenari[novaFila][novaColumna].getEstatCasella();
            if (estatCasella == BUID) {
                movimentCasella(novaFila, novaColumna);
            } else if (estatCasella == MONSTRE) {
                afegirCasellaNoVisitable(MONSTRE, novaFila, novaColumna);
            } else if (estatCasella == PRECIPICI) {
                afegirCasellaNoVisitable(PRECIPICI, novaFila, novaColumna);
            } else if (estatCasella == TRESOR) {
                movimentCasella(novaFila, novaColumna);
                teTresor = true;
            }
        } else {
            direccio_idx++;
        }
    }

    public void afegirCasellaNoVisitable(String cosaTrobada, int fila, int columna) {
        mapa.get(cosaTrobada).add(new Posicio(fila, columna));
        direccio_idx++;
    }

    public void movimentCasella(int novaFila, int novaColumna) {
        matriuEscenari[fila][columna].setEstatCasella(BUID);
        mapa.get("VISITADA").add(new Posicio(fila, columna));
        fila = novaFila;
        columna = novaColumna;
        matriuEscenari[fila][columna].setEstatCasella(AGENT);
    }

    public boolean foraDelEscenari(int fila, int columna) {
        return fila < 0 || fila >= tamanyEscenari || columna < 0 || columna >= tamanyEscenari;
    }

    public boolean senseOpcio() {
        for (int i = 0; i < 4; i++) {
            int novaFila = fila + MOVIMIENTS.get(direccio[i % direccio.length])[0];
            int novaColumna = columna + MOVIMIENTS.get(direccio[i % direccio.length])[1];
            Posicio pos_aux = new Posicio(novaFila, novaColumna);
            if (!mapa.get("VISITADA").contains(pos_aux) && !foraDelEscenari(novaFila, novaColumna)) {
                return false;
            }
        }
        return true;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
