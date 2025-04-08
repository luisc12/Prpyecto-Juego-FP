/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Matematicas.Vectores;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Animacion {
    //numero de fotogramas
    private BufferedImage[] frames;
    //velocidad con que cambia el fotograma
    private int velocidad;
    //el indice del fotigrama actual
    private int indice;
    //nos indica si se ejecuta la animacion
    private boolean ejecutando;
    //posiion de la animacion
    private Vectores posicion;
    //tiempo actual
    private long tiempo;

    public BufferedImage[] getFrames() {
        return frames;
    }
    public Animacion(BufferedImage[] frames, int velocidad, Vectores posicion) {
        this.frames = frames;
        this.velocidad = velocidad;
        this.posicion = posicion;
        indice=0;
        ejecutando=true;
        
        
    }
    public void actualizar(float dt){
        tiempo+=dt;
        
        /*si el tiempo transcurrido es mayor a la velocidad entonces cambia de
        a la siiguiente imagen*/
        if (tiempo>velocidad) {
            tiempo=0;
            indice++;
            //si el indice pasa el tamaño de los frames se para la animacion
            if (indice>=frames.length) {
                //
                ejecutando=false;
                indice=0;
            }
        }
    }

    public boolean isEjecutando() {
        return ejecutando;
    }

    public Vectores getPosicion() {
        return posicion;
    }
    //toma el fotograma actual en el arreglo de fotogramas
    public BufferedImage getFrameActual(){
        return frames[indice];
    }

    
    
    
}
