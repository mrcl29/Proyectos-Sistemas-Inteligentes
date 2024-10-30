package code;

import java.util.Objects;

public class Posicio {
    public int fila;
    public int columna;

    public Posicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Posicio posicio = (Posicio) o;
        return fila == posicio.fila && columna == posicio.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
