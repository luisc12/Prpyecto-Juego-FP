/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Entrada.Teclado;
import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import Ventanas.VentanaPartida;
import com.sun.webkit.dom.KeyboardEventImpl;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class Jugador extends ObjetosMovibles {

    private Vectores direccion;
    //cambio de velocidad con respecto al tiempo
    private Vectores aceleracion;
    private boolean aparecer, visible;

    private Cronometro TAparecer, Tparpadeo;

    private boolean avansado = false;

    //limitar la velocidad de disparo
    // private long tiempo,TPasado;
    private Cronometro fuego;
    
    //sonido
    private Sonido Sdisparar,SPerdida;

    public Jugador(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);

        direccion = new Vectores(0, 1);
        aceleracion = new Vectores();
        // tiempo=0;
        ///TPasado=System.currentTimeMillis();
        fuego = new Cronometro();
        TAparecer = new Cronometro();
        Tparpadeo = new Cronometro();
        Sdisparar=new Sonido(Externos.DisparoJugador);
        SPerdida=new Sonido(Externos.PerdidaJugador);

    }

    @Override
    public void actualizar() {

        //tiempo+=System.currentTimeMillis()-TPasado;
        // TPasado=System.currentTimeMillis();
        if (!TAparecer.isEjecutando()) {
            aparecer = false;
            visible = true;
        }
        if (aparecer) {
            if (!Tparpadeo.isEjecutando()) {
                Tparpadeo.Empezar(Constantes.TiempParpadeo);
                //si es verdadero "visible pasa a false y viseversa obteniendo el efecto parpadeo
                visible = !visible;
            }
        }
        if (Teclado.disparar && !fuego.isEjecutando() && !aparecer) {
            //el cero antes de introducir un nuevo laser significa que el laser se va a agregar primero y no al final
            ventanapartida.getObjetosmoviles().add(0, new Laser(Externos.greenLaser,
                    CentroImagen().SumaVectores(direccion.MultiplicarVector(imgancho)),
                    direccion,
                    10,
                    angulo,
                    ventanapartida));
            //tiempo=0;
            fuego.Empezar(Constantes.TDisparo);
            Sdisparar.play();
        }
        
        if (Sdisparar.getFramePsition()>8500) {
            Sdisparar.parar();
        }
        
        
        if (Teclado.derecha) {
            angulo += Constantes.anguloBase;
        }
        if (Teclado.izquierda) {
            angulo -= Constantes.anguloBase;
        }
        if (Teclado.arriba) {

            aceleracion = direccion.MultiplicarVector(Constantes.ACC);
            avansado = true;
        } else if (Teclado.abajo) {
            aceleracion = direccion.MultiplicarVector(-Constantes.ACC);
            avansado = false;
        } else {
            //igualar la aceleracion a un vector opuesto a la velocidad pero con magnitud igual a la constante aceleracion
            if (velocidad.Manitud() != 0) {
                aceleracion = (velocidad.MultiplicarVector(-1).NormalizarVector()).MultiplicarVector(Constantes.ACC / 2);
                avansado = false;
            }
        }
        /*   if (Teclado.abajo) {
            aceleracion=direccion.MultiplicarVector(-ACC);
        }*/

        //la aceleracion representara el cambio de velocidad con respecto al tiempo
        velocidad = velocidad.SumaVectores(aceleracion);

        velocidad = velocidad.velocidadlimite(maxVel);

        //para que la nave comiense de frete restamos 90 grados es decir PI ya que java trabaja en radianes
        direccion = direccion.calcularDireccion(angulo - Math.PI / 2);

        /*la velocidad es representada como el cambio de posicion con respecto al tiempo 
       por lo tanto cada fotograma le sumariamos el vetor velocidad al vector posicion*/
        posicion = posicion.SumaVectores(velocidad);

        //evitamos que el jugador salga de la pantalla
        if (posicion.getX() > Constantes.ancho) {
            posicion.setX(0);
        }
        if (posicion.getY() > Constantes.alto) {
            posicion.setY(0);
        }

        if (posicion.getX() < 0) {
            posicion.setX(Constantes.ancho);
        }
        if (posicion.getY() < 0) {
            posicion.setY(Constantes.alto);
        }
        fuego.actualizar();
        TAparecer.actualizar();
        Tparpadeo.actualizar();
        ColisonaCon();
    }

    @Override
    public void Destruir() {
        
        aparecer = true;
        TAparecer.Empezar(Constantes.TiempAparecerJugador);
        SPerdida.play();
        if (!ventanapartida.RestarVidas()) {
            ventanapartida.gameOver();
            super.Destruir();
        }
 ReiniciarValor();
 //ventanapartida.RestarVidas();
    }

    private void ReiniciarValor() {
angulo=0;
velocidad=new Vectores();

posicion=VentanaPartida.PosicionInicial;
    }

    @Override
    public void dibujar(Graphics g) {
        //dibujamos el jugador en su posicion con observador null
        //  g.drawImage(textura, (int)posicion.getX(), (int)posicion.getY(), null); 

        if (!visible) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at1 = AffineTransform.getTranslateInstance(posicion.getX() + imgancho / 2 + 5,
                posicion.getY() + imgalto / 2 + 10);
        //divujamos a partir de la exquina superior izquierda
        AffineTransform at2 = AffineTransform.getTranslateInstance(posicion.getX() + 5,
                posicion.getY() + imgalto / 2 + 10);

        at1.rotate(angulo, -5, -10);
        //lo trasla damos al medio por eso dividimos x entre 2
        at2.rotate(angulo, imgancho / 2 - 5, -10);

        if (avansado) {
            g2d.drawImage(Externos.propulsion, at1, null);
            g2d.drawImage(Externos.propulsion, at2, null);
        }

        at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
        //punto de rotacion : optenemos el ancho y lo dividimos entre 2        
        at.rotate(angulo, imgancho / 2, imgalto / 2);

        g2d.drawImage(textura, at, null);
    }

    public boolean isAparecer() {
        return aparecer;
    }

}
