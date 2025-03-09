/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles.Disparos;

import Matematicas.Vectores;
import ObjetosMoviles.ObjetosMovibles;
import Ventanas.VentanaControl;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public abstract class Disparos extends ObjetosMovibles{
   public boolean enemigo;
    public Disparos(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        this.enemigo = enemigo;
    }

 public Disparos(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaControl ventanaControl, boolean enemigo) {
        super(textura, posicion, velocidad, maxVel, ventanaControl);
        this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        this.enemigo = enemigo;
    }

    public boolean isEnemigo() {
        return enemigo;
    }
    @Override
    public abstract void actualizar(float dt);

     @Override
    public abstract void dibujar(Graphics g);
/* @Override
    public Vectores CentroImagen() {
        return new Vectores(posicion.getX() + imgancho / 2, posicion.getY() + imgancho / 2);
    }*/
}
