/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
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

    private boolean continuar;

    //private Cronometro fuego;
    long fuego;
    
    private Sonido Sdisparar;

    public Enemigos(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel,
            VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.camino = camino;
        indice = 0;
        continuar = true;
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
    private Vectores PursuingForce(Vectores objetivo, int tiempo){
        Vectores FuturePosicion=posicion.SumaVectores(velocidad.MultiplicarVector(tiempo));
        return FuturePosicion;
    }

    
    public abstract void actualizar(float dt);

    @Override
    public void Destruir(){
        if (continuar) {
            
       
        ventanapartida.SumarPuntos(Constantes.PuntosUfo,posicion);
        ventanapartida.Explotar(posicion);
        super.Destruir();
         }
    }
    @Override
    public abstract void dibujar(Graphics g);

 

}
