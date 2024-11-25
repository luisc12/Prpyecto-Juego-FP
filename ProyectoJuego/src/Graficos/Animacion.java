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
    
    private long tiempo;

    public Animacion(BufferedImage[] frames, int velocidad, Vectores posicion) {
        this.frames = frames;
        this.velocidad = velocidad;
        this.posicion = posicion;
        indice=0;
        ejecutando=true;
        
        
    }
    public void actualizar(float dt){
        tiempo+=dt;
        
        
        if (tiempo>velocidad) {
            tiempo=0;
            indice++;
            if (indice>=frames.length) {
                
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
