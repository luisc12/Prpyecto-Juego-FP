/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Jugador;
import ObjetosMoviles.ObjetosMovibles;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public abstract class Enemigos extends ObjetosMovibles {
 private ArrayList<Vectores> camino;
    private Vectores nodoActual;
    private int indice;
    private int vida;

Jugador jugador=ventanapartida.getJugador();
    //private Cronometro fuego;
    long fuego;
    
    private Sonido Sdisparar;

    public Enemigos(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel,
            VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.camino = camino;
        indice = 0;
        fuego=0;
        Sdisparar=new Sonido(Externos.DisparoUfo);
        vida=100;
    }


    private Vectores SeekForce(Vectores objetivo) {
        //velocidad deseada vector desde el UFO hacia el objetivo
        Vectores velocidadDeseada = objetivo.RestaVectores(CentroImagen());
        //tenemos que ajustar esa fuersa a la velocidad maxima del Ufo
        velocidadDeseada = velocidadDeseada.NormalizarVector().MultiplicarVector(maxVel);
        //
        return velocidadDeseada.RestaVectores(velocidad);
    }
    protected Vectores PursuingForce(){
      //  Vectores FuturePosicion=jugador.CentroImagen().SumaVectores(jugador.JugadorgetVelocidad().MultiplicarVector(tiempo));
      // Calcular distancia al jugador
      Vectores vjp=jugador.getPosicion();
      Vectores vjv=jugador.JugadorgetVelocidad();
      Vectores posicionJ=vjp.RestaVectores(posicion);
      
      double distancia=posicionJ.Manitud();
      
        // Estimar el tiempo de intercepción
      double prediccion=distancia/maxVel;
      
       // Calcular la posición futura del jugador
      Vectores futuraPosicion = vjp.SumaVectores(jugador.JugadorgetVelocidad().MultiplicarVector(prediccion));
      // Aplicar Seek hacia la posición futura
      Vectores force = futuraPosicion.RestaVectores(posicion).NormalizarVector().MultiplicarVector(0.5);
      
      return force ;
    }

    protected double jugadorSurdo( Vectores jugadorP){
         if (jugadorP.getX() < 0) {
                angulo = -angulo + Math.PI;
                return angulo;
            }
        return angulo;
    }
    public abstract void actualizar(float dt);

    @Override
    public void Destruir(){
       
        ventanapartida.SumarPuntos(Constantes.PuntosUfo,posicion);
        ventanapartida.Explotar(posicion);
        super.Destruir();
         
    }
    
    @Override
    public abstract void dibujar(Graphics g);

 

}
