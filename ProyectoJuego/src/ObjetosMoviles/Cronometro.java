/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

/**
 *
 * @author luis
 */
public class Cronometro {

    //un segundo en nanosegundos dividido por FPS//Delta
    private long TTrans ;
    //tiempo transcurrido//lastTime
    private long TPasado;
    
    private long tiempo;
    //running
private boolean ejecutando = false;



    public Cronometro() {
        TTrans=0;
        TPasado= System.currentTimeMillis();
        ejecutando=false;
    }
public void Empezar(long tiempo){
    ejecutando=true;
    this.tiempo=tiempo;
}
public void actualizar(){
    if (ejecutando) {
        TTrans +=System.currentTimeMillis()-TPasado;
        
    }
    if (TTrans>=tiempo) {
        ejecutando=false;
        TTrans=0;
    }
    TPasado=System.currentTimeMillis();
}

    public boolean isEjecutando() {
        return ejecutando;
    }



}
