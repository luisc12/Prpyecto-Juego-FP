/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public enum Naves {
    
    JUGADOR1("cartero",Externos.jugadores[0],0),
    CRAB("CRAB",Externos.jugadores[1],350),
    MAC_1("MAC-1",Externos.jugadores[2],450),
    SPORTS_SHIP("SPORTS SHIP",Externos.jugadores[3],550),
     CRUSTER("CRUSTER",Externos.jugadores[4],650),
    MAC_2("MAC-2",Externos.jugadores[5],1000),
    SCORPION("SCORPION",Externos.jugadores[6],1500),
    MODELXHIP("MODELXHIP",Externos.jugadores[7],2000);
    public String nombre;
    public BufferedImage textura;
    public int puntaje;

    private Naves(String nombre, BufferedImage textura, int puntaje) {
        this.nombre = nombre;
        this.textura = textura;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public BufferedImage getTextura() {
        return textura;
    }

    public int getPuntaje() {
        return puntaje;
    }

}
