package code;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Variables extends Constants {
    public static Escenari escenari;
    public static Agent agent;

    public static int tamanyEscenari;

    public static Casella[][] matriuEscenari;

    public static String colocar;
    public static int nMonstres;
    public static int nTresors;

    public static Timer timer;

    public static void novaVelocitat(int velocitat) {
        timer = new Timer(velocitat, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agent.moviment();
            }
        });
    }

}
