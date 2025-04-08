/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles.Enemigos;

import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Jugador;
import ObjetosMoviles.Jugador;
import ObjetosMoviles.ObjetosMovibles;
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

    private int indice;
    private boolean continuar;
    Jugador jugador = ventanapartida.getJugador();
    long fuego;

    Sonido Sdisparar;

    public Enemigos(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel,VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        indice = 0;
        fuego = 0;
        Sdisparar = new Sonido(Externos.DisparoUfo);

        continuar = true;
    }

    protected Vectores SeguirCamino(ArrayList<Vectores> camino) {
        /* primero tomamos nuestro nodo actual de nuestro Array de vectores y 
        calculamos la distancia de nuestro enemigo */
        Vectores nodoActual = camino.get(indice);
        
        /*calculamos la distancia de nuestro enemigo hacia ese punto restando 
        nuestro nodo actual por nuestra posicion y sacamos su magnitud*/
        double distanciaAlNodo = nodoActual.RestaVectores(CentroImagen()).Magnitud();

        /*preguntamos si nuestra constante RadiusNodo es mayor que la distancia 
        que hay entre nuestro enemigo y el nodo si lo es pasamos al siguiente nodo
        si no hay otro nodo el ufo seguira avanzando en la misma direccion en ese momento*/
        if (distanciaAlNodo < Constantes.RadiusNodo) {
            indice++;
            if (indice >= camino.size()) {
                continuar = false;
            }
        }
        return SeekForce(nodoActual);
    }

    @Override
    public abstract void actualizar(float dt);

    @Override
    public void Destruir() {

        ventanapartida.SumarPuntos(Constantes.PuntosUfo, posicion);
        ventanapartida.Explotar(posicion);
        super.Destruir();

    }

    @Override
    public abstract void dibujar(Graphics g);

    public boolean isContinuar() {
        return continuar;
    }
}
/*
    protected double jugadorSurdo( Vectores jugadorP){
         if (jugadorP.getX() < 0) {
                angulo = -angulo + Math.PI;
                return angulo;
            }
        return angulo;
    }*/
