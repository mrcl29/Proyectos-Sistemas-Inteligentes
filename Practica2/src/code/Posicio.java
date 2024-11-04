package code;

import java.util.Objects;

public class Posicio {
    public int fila;
    public int columna;
    public int g;
    public int h;
    public Posicio pare;

    public Posicio(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    Posicio(int fila, int columna, int g, int h, Posicio pare) {
        this.fila = fila;
        this.columna = columna;
        this.g = g;
        this.h = h;
        this.pare = pare;
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

    int f() {
        return g + h;
    }
}
