/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Matematicas.Vectores;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public abstract class ObjetosDelJuego {
    
    protected BufferedImage textura;
    protected  Vectores posicion;

    public ObjetosDelJuego(BufferedImage textura, Vectores posicion) {
        this.textura = textura;
        this.posicion = posicion;
    }
    
    public abstract void actualizar();
    
    public abstract void dibujar(Graphics g) ;

    public Vectores getPosicion() {
        return posicion;
    }

    public void setPosicion(Vectores posicion) {
        this.posicion = posicion;
    }
    
    
    
    
}
