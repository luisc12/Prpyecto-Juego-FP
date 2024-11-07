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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public abstract class ObjetosMovibles extends ObjetosDelJuego{

    protected Vectores velocidad;
    //nos ayudara a rotar cual quier objeto que se mueva
    protected AffineTransform at;
    protected double angulo;
    //velocidad maxima
    protected double maxVel;
    protected int imgancho, imgalto;
    protected VentanaPartida ventanapartida;
   
    private Sonido explosion;
    
    private boolean muerte;
    
    public ObjetosMovibles(BufferedImage textura, Vectores posicion, Vectores velocidad,double maxVel,VentanaPartida ventanapartida) {
        super(textura, posicion);
        this.velocidad=velocidad;
        this.maxVel=maxVel;
        this.ventanapartida=ventanapartida;
        angulo=0;
        imgancho=textura.getWidth();
        imgalto=textura.getHeight();
        explosion=new Sonido(Externos.Sonidoexplosion);
        
        muerte=false;
       
        
    }
    protected void ColisonaCon(){
        //pedimos la lista de objetos moviles de la ventanapartida y hacemos un for
        ArrayList<ObjetosMovibles> objetosmovibles=ventanapartida.getObjetosmoviles();
        for (int i = 0; i < objetosmovibles.size(); i++) {
            ObjetosMovibles o=objetosmovibles.get(i);
            //si el objeto es el mismo que llama al metodo continua con el for
            if (o.equals(this)) {
                continue;
            }
            //para cañcular la distacia hay que restar los centros de las imagenes y sacar su mgnitud
            double distancia=o.CentroImagen().RestaVectores(CentroImagen()).Manitud();
            
            //se pregunta si la distanciaes menor al la suma del ancho de los objetos
            //a demas de si estan muertos para que no alla colisiones fantasma
            if (distancia<o.imgancho/2+imgancho/2&&objetosmovibles.contains(this)
                    &&!o.muerte&&!muerte) {
               ColisionObjetos(o,this);
            }
        }
    }
    protected void ColisionObjetos(ObjetosMovibles a, ObjetosMovibles b){
        
        if ((a instanceof Jugador &&((Jugador)a).isAparecer())||
                (b instanceof Jugador &&((Jugador)b).isAparecer())) {
            return;
        }
        
        if (!(a instanceof Meteoros&&b instanceof Meteoros)) {
            ventanapartida.Explotar(CentroImagen());
            a.Destruir();
            b.Destruir();
        }
    }
    protected void Destruir(){
        muerte=true;
       // ventanapartida.getObjetosmoviles().remove(this);
        if (!(this instanceof Laser)) {
            explosion.play();
        }
    }
 protected Vectores CentroImagen(){
        return new Vectores(posicion.getX()+imgancho/2, posicion.getY()+imgalto/2);
    }

    public boolean isMuerte() {
        return muerte;
    }
   
   
}
