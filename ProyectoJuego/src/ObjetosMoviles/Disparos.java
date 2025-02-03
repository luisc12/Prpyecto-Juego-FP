/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Matematicas.Vectores;
import Ventanas.VentanaPartida;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public abstract class Disparos extends ObjetosMovibles{
       public boolean enemigo;
    public int daño;
    public Disparos(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo, int daño) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
         this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        this.enemigo = enemigo;
        this.daño = daño;
    }
    protected Vectores SeekForce(Vectores objetivo) {
        //velocidad deseada vector desde el UFO hacia el objetivo
        Vectores velocidadDeseada = objetivo.RestaVectores(CentroImagen());
        //tenemos que ajustar esa fuersa a la velocidad maxima del Ufo
        velocidadDeseada = velocidadDeseada.NormalizarVector().MultiplicarVector(maxVel);
        //
        return velocidadDeseada.RestaVectores(velocidad);
    }
    public int getDaño() {
        return daño;
    }

    public boolean isEnemigo() {
        return enemigo;
    }
}
