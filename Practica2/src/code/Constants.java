package code;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Constants {
    public static final int ALTURA_MENU = 50;

    public static final String PATH = "Practica2/src/assets/";
    public static final ImageIcon MONSTRE_ICON = new ImageIcon(PATH + "monstre.png");
    public static final ImageIcon PRECIPICI_ICON = new ImageIcon(PATH + "precipici.png");
    public static final ImageIcon TRESOR_ICON = new ImageIcon(PATH + "tresor.png");
    public static final ImageIcon AGENT_ICON = new ImageIcon(PATH + "agent.png");
    public static final ImageIcon SORTIDA_ICON = new ImageIcon(PATH + "sortida.png");

    public static final String BUID = "BUID";
    public static final String MONSTRE = "MONSTRE";
    public static final String PRECIPICI = "PRECIPICI";
    public static final String TRESOR = "TRESOR";
    public static final String AGENT = "AGENT";
    public static final String SORTIDA = "SORTIDA";

    public static final int MONSTRES_TOTALS = -1;
    public static final int TRESORS_TOTALS = 1;

    public static final int FILA_INICI = 0;
    public static final int COLUMNA_INICI = 0;

    public static final String[] DIRECCIO = { "NORD", "EST", "SUD", "OEST" };

    public static final Map<String, int[]> MOVIMIENTS = new HashMap<>();
    static {
        MOVIMIENTS.put("NORD", new int[] { -1, 0 });
        MOVIMIENTS.put("SUD", new int[] { 1, 0 });
        MOVIMIENTS.put("EST", new int[] { 0, 1 });
        MOVIMIENTS.put("OEST", new int[] { 0, -1 });
    }
}
