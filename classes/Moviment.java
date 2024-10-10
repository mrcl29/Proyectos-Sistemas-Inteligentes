public class Moviment {
    private static final String ORDRE_MOVIMET[] = { "NORD", "EST", "SUD", "OEST" };
    private int temps;

    private Robot robot;
    private Escenari escenari;

    public Moviment(int temps, Robot robot, Escenari escenari) {
        this.temps = temps;
        this.robot = robot;
        this.escenari = escenari;
    }

    public void iniciMoviment() {
        while (true) {
            try {
                Thread.sleep(temps);
            } catch (InterruptedException e) {
                // Manejar la excepción
                e.printStackTrace();
            }

            
        }
    }

}
