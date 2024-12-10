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
    
    ESCUDO("ESCUDO",Externos.escudo),
    VIDA("1+ VIDA",Externos.vida),
    PUNTOSX2("PUNTOS x2",Externos.doblePuntuacion),
    FUEGO_RAPIDO("FUEGO RAPIDO",Externos.fuegoRapido),
    GRAN_PUNTUACION("+1000 PUNTOS",Externos.estrella),
    DOBLE_GUN("DOBLE GUN",Externos.dobleGun);
    
    
    public BufferedImage textura2;
    public String texto;
    public BufferedImage textura;
    public int i;

    private TiposPowerUP(String texto, BufferedImage textura) {
        this.texto = texto;
        this.textura = textura;
    }
    
}
