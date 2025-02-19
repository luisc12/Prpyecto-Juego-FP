/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles.Enemigos;

import ObjetosMoviles.Enemigos.Enemigos;
import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Disparos.Laser;
import ObjetosMoviles.Disparos.Laser;
import Ventanas.VentanaPartida;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

/**
 *
 * @author luis
 */

/*el enimigo se movera dividiendo la pantalla en cuatro partes y escogemos un unto al azar en
cada secciony esos puntos formaran un camino que el enemigo va a seguir y desde que aparesca, comensara a seguir
el camino seguira con la velocidad con la que valla hasta que salga por completo de la ventana

usando Seek behavior que genera una fuerza generado por 2 vectores la velosidad actual y la velocidad deseada
UFO hasta el node Seek force= desired velocity- current velocity (F=m*a) (a=F/m) V+=a)*/
public class Ufo extends Enemigos {

    private ArrayList<Vectores> camino;
    private Vectores nodoActual;
    
    private int vida;

    private boolean continuar;

    //private Cronometro fuego;
    private long fuego;
    private Sonido sonidoufo;
    
    private Sonido Sdisparar;
    public Ufo(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida, ArrayList<Vectores> camino) {    
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.camino = camino;
    
    continuar = true;
    fuego=0;
    Sdisparar=new Sonido(Externos.DisparoUfo);
    sonidoufo=new Sonido(Externos.Ufosonido);
    sonidoufo.cambiarVolumen(-10.0f);
    sonidoufo.play();
    vida=100;
     for (int i = 0; i < this.camino.size(); i++) {
            System.out.println(camino.get(i).getX()+" "+camino.get(i).getY());
        }
    }


    @Override
    public void actualizar(float dt) {
         
        fuego+=dt;
        Vectores siguindo;
        choqueEscudo();
      
        if (isContinuar()) {
            siguindo = SeguirCamino(camino);
        } else {
            siguindo = new Vectores();
        }

        siguindo = siguindo.MultiplicarVector(1 / Constantes.MasaUfo);

        velocidad = velocidad.SumaVectores(siguindo);

        velocidad = velocidad.velocidadlimite(maxVel);

        posicion = posicion.SumaVectores(velocidad);

        //disparar
        
        if (fuego>Constantes.TDisparoUfo) {
            //tomamos la posicion del centro del jugador y le restamos el centro y lo normalisamos para optener la distancia
            Vectores posicionJ = ventanapartida.getJugador().CentroImagen().RestaVectores(CentroImagen());

            posicionJ = posicionJ.NormalizarVector();
            //sacamos el angulo usando asin(y/M)
            double anguloActual = posicionJ.getAngulo();
            //pi es 180 grados
            anguloActual += Math.random() * Constantes.RangoAnguloUfo - Constantes.RangoAnguloUfo / 2;

            posicionJ=posicionJ.calcularDireccion(anguloActual);

            Laser laser = new Laser(Externos.purpuraLaser,
                    CentroImagen().SumaVectores(posicionJ.MultiplicarVector(imgancho)),
                    posicionJ,
                    Constantes.Velocidad_lac,
                    anguloActual+ Math.PI / 2,
                    ventanapartida,true);

            ventanapartida.getObjetosmoviles().add(0, laser);
            fuego=0;
            
            Sdisparar.play();
        }
         
        if (Sdisparar.getFramePsition()>8500) {
            Sdisparar.parar();
        }
        angulo += 0.05;
        
       

        ColisionaCon();
       
    }

    @Override
    public void Destruir(){
        //if (continuar) {
            
       
        ventanapartida.SumarPuntos(Constantes.PuntosUfo,posicion);
        ventanapartida.Explotar(posicion);
        sonidoufo.parar();
        super.Destruir();
         //}
    }
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
        //punto de rotacion : optenemos el ancho y lo dividimos entre 2        
        at.rotate(angulo, imgancho / 2, imgalto / 2);

        g2d.drawImage(textura, at, null);

    }


}
