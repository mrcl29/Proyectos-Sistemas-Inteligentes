package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Agent extends Variables {
    private int fila;
    private int columna;
    private Map<String, ArrayList<Posicio>> mapa = new HashMap<>();

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

    public void Moviment() {
        if (fila > 0) {// NORD
            String estatCasella = matriuEscenari[fila - 1][columna].getEstatCasella();
            if (estatCasella == BUID) {

            } else if (estatCasella == MONSTRE) {

            } else if (estatCasella == PRECIPICI) {

            } else if (estatCasella == TRESOR) {

            }

        } else if (columna < tamanyEscenari - 1) {// EST

        } else if (fila < tamanyEscenari - 1) {// SUD

        } else if (columna > 1) {// OEST

        }
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
