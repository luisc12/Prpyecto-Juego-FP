/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
import java.awt.image.BufferedImage;
/**
 *
 * @author luis
 */

public enum TiposPowerUP {
    
    ESCUDO("ESCUDO",Externos.escudo,Externos.orbe),
    VIDA("1+ VIDA",Externos.vida,Externos.orbVida),
    PUNTOSX2("PUNTOS x2",Externos.doblePuntuacion,Externos.orb2X),
    FUEGO_RAPIDO("FUEGO RAPIDO",Externos.fuegoRapido,Externos.orbFuego),
    GRAN_PUNTUACION("+1000 PUNTOS",Externos.estrella,Externos.orbPuntuacion),
    DOBLE_GUN("DOBLE GUN",Externos.dobleGun,Externos.orbGun);
    
    
    public BufferedImage orbe;
    public String texto;
    public BufferedImage textura;
    public int i;
/*
    private TiposPowerUP(String texto, BufferedImage textura) {
        this.texto = texto;
        this.textura = textura;
    }
    */
    private TiposPowerUP(String texto, BufferedImage textura,BufferedImage orbe) {
        this.texto = texto;
        this.textura = textura;
        this.orbe=orbe;
    }
}
