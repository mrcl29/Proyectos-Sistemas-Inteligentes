import java.util.HashSet;
import java.util.Set;

public class Tablero {

    // Representación de los estados posibles de cada casilla usando un conjunto de
    // estados
    enum Estado {
        OK, HEDOR, BRISA, POSIBLE_MONSTRUO, POSIBLE_PRECIPICIO, MONSTRUO, PRECIPICIO
    }

    // Dirección para las casillas adyacentes (arriba, abajo, izquierda, derecha)
    static final int[][] DIRECCIONES = {
            { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
    };

    // Tablero de casillas
    Set<Estado>[][] tablero;

    // Constructor
    public Tablero(int filas, int columnas) {
        tablero = new Set[filas][columnas];
        // Inicializamos todas las casillas con un conjunto vacío
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = new HashSet<>();
            }
        }
    }

    // Método para obtener el estado de una casilla
    public Set<Estado> getEstado(int fila, int columna) {
        return tablero[fila][columna];
    }

    // Método para establecer el estado de una casilla
    public void setEstado(int fila, int columna, Estado estado) {
        if (!tablero[fila][columna].contains(estado)) {
            tablero[fila][columna].add(estado);
        }
    }

    // Verifica si una casilla está dentro de los límites del tablero
    private boolean estaEnTablero(int fila, int columna) {
        return fila >= 0 && fila < tablero.length && columna >= 0 && columna < tablero[0].length;
    }

    // Método para comprobar las casillas adyacentes
    private void procesarAdyacentes(int fila, int columna) {
        Set<Estado> estado = getEstado(fila, columna);

        if (estado.contains(Estado.HEDOR)) {
            // Si la casilla tiene hedor, alguna adyacente debe ser un monstruo
            for (int[] dir : DIRECCIONES) {
                int nuevaFila = fila + dir[0];
                int nuevaColumna = columna + dir[1];
                if (estaEnTablero(nuevaFila, nuevaColumna)
                        && !getEstado(nuevaFila, nuevaColumna).contains(Estado.MONSTRUO)) {
                    setEstado(nuevaFila, nuevaColumna, Estado.POSIBLE_MONSTRUO);
                }
            }
        } else if (estado.contains(Estado.BRISA)) {
            // Si la casilla tiene brisa, alguna adyacente debe ser un precipicio
            for (int[] dir : DIRECCIONES) {
                int nuevaFila = fila + dir[0];
                int nuevaColumna = columna + dir[1];
                if (estaEnTablero(nuevaFila, nuevaColumna)
                        && !getEstado(nuevaFila, nuevaColumna).contains(Estado.PRECIPICIO)) {
                    setEstado(nuevaFila, nuevaColumna, Estado.POSIBLE_PRECIPICIO);
                }
            }
        }
    }

    // Método principal para deducir los monstruos y precipicios
    public void deducir() {
        boolean cambios;

        // Realizamos iteraciones hasta que no haya más cambios
        do {
            cambios = false;

            // Iterar sobre todas las casillas
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    Set<Estado> estado = getEstado(i, j);

                    // Si la casilla es OK, eliminamos cualquier posibilidad de monstruo o
                    // precipicio
                    if (estado.contains(Estado.OK)) {
                        if (estado.contains(Estado.POSIBLE_MONSTRUO)) {
                            estado.remove(Estado.POSIBLE_MONSTRUO);
                            cambios = true;
                        }
                        if (estado.contains(Estado.POSIBLE_PRECIPICIO)) {
                            estado.remove(Estado.POSIBLE_PRECIPICIO);
                            cambios = true;
                        }
                    }

                    // Procesamos casillas con información que puede deducir algo
                    if (estado.contains(Estado.HEDOR) || estado.contains(Estado.BRISA)) {
                        procesarAdyacentes(i, j);
                    }

                    // Si una casilla es posible monstruo y ninguna adyacente es brisa o precipicio,
                    // debemos convertirla en monstruo
                    if (estado.contains(Estado.POSIBLE_MONSTRUO)) {
                        boolean hayBrisa = false;
                        boolean hayPrecipicio = false;
                        for (int[] dir : DIRECCIONES) {
                            int nuevaFila = i + dir[0];
                            int nuevaColumna = j + dir[1];
                            if (estaEnTablero(nuevaFila, nuevaColumna)) {
                                Set<Estado> adyacente = getEstado(nuevaFila, nuevaColumna);
                                if (adyacente.contains(Estado.PRECIPICIO)) {
                                    hayPrecipicio = true;
                                }
                                if (adyacente.contains(Estado.BRISA)) {
                                    hayBrisa = true;
                                }
                            }
                        }
                        if (!hayBrisa && !hayPrecipicio) {
                            // Si no hay brisa ni precipicio en las casillas adyacentes, podemos deducir que
                            // es un monstruo
                            if (!estado.contains(Estado.MONSTRUO)) {
                                setEstado(i, j, Estado.MONSTRUO);
                                cambios = true;
                            }
                        }
                    }

                    // Similar para las posibles casillas de precipicio
                    if (estado.contains(Estado.POSIBLE_PRECIPICIO)) {
                        boolean hayHedor = false;
                        boolean hayMonstruo = false;
                        for (int[] dir : DIRECCIONES) {
                            int nuevaFila = i + dir[0];
                            int nuevaColumna = j + dir[1];
                            if (estaEnTablero(nuevaFila, nuevaColumna)) {
                                Set<Estado> adyacente = getEstado(nuevaFila, nuevaColumna);
                                if (adyacente.contains(Estado.MONSTRUO)) {
                                    hayMonstruo = true;
                                }
                                if (adyacente.contains(Estado.HEDOR)) {
                                    hayHedor = true;
                                }
                            }
                        }
                        if (!hayHedor && !hayMonstruo) {
                            // Si no hay hedor ni monstruos en las adyacentes, podemos deducir que es un
                            // precipicio
                            if (!estado.contains(Estado.PRECIPICIO)) {
                                setEstado(i, j, Estado.PRECIPICIO);
                                cambios = true;
                            }
                        }
                    }
                }
            }
        } while (cambios); // Continuar iterando hasta que no haya cambios
    }

    // Método para mostrar el estado del tablero
    public void mostrarTablero() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Crear el tablero con 5 filas y 5 columnas
        Tablero t = new Tablero(5, 5);

        // Establecer algunos valores de ejemplo
        t.setEstado(0, 0, Estado.HEDOR);
        t.setEstado(0, 1, Estado.OK); // Casilla OK
        t.setEstado(1, 0, Estado.OK); // Casilla OK
        t.setEstado(1, 1, Estado.BRISA);
        t.setEstado(2, 2, Estado.POSIBLE_MONSTRUO);
        t.setEstado(3, 3, Estado.POSIBLE_PRECIPICIO);

        // Mostrar el tablero antes de deducir
        System.out.println("Tablero antes de deducir:");
        t.mostrarTablero();

        // Deducir los monstruos y precipicios
        t.deducir();

        // Mostrar el tablero después de deducir
        System.out.println("Tablero después de deducir:");
        t.mostrarTablero();
    }
}
