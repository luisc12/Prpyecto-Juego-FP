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
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Disparos.Lacer;
import ObjetosMoviles.Disparos.Lacer;
import ObjetosMoviles.Disparos.Lacer;
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
public class Nostromo extends Enemigos {

    private Vectores direccion;
    private int indice;
    private int vida;
    private Vectores jugadorP, posicionAnteriro;
    long fuego;

    private Sonido Sdisparar;

    public Nostromo(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);

        direccion = new Vectores(0, 1);
        indice = 0;
        // continuar = true;
        fuego = 0;
        Sdisparar = new Sonido(Externos.disparoNostromo);
        Sdisparar.cambiarVolumen(-10.0f);
        vida = 100;
        posicionAnteriro=new Vectores();
    }

    @Override
    public void actualizar(float dt) {

        fuego += dt;
        choqueEscudo();
        //-----------Movimiento--------------------
         Vectores force;
         /*si el jugador no acaba de reaparecer ejecutamos la fuerza de 
         persecucion con respecto a la distancia entre el jugador y Nostromo
         sino perseguira un nuevo vector*/
        if (!ventanapartida.getJugador().isAparecer()) {
            //sacamos la la distancia del jugador con respecto a Notromo
         jugadorP = new Vectores(ventanapartida.getJugador().CentroImagen()
                 .RestaVectores(CentroImagen()));
        
         }else{
            jugadorP=new Vectores();
        }
         //sacamos la fuerza de persecucion 
         force = PursuingForce(jugadorP);
         if (force.Manitud() > Constantes.maxforceNos) {
            force = force.NormalizarVector().MultiplicarVector(Constantes.maxforceNos);
        }
         
        force = force.MultiplicarVector(1 / (Constantes.MasaNos/2));
        velocidad = velocidad.SumaVectores(force);
        velocidad = velocidad.velocidadlimite(maxVel);
        posicion = posicion.SumaVectores(velocidad);
        angulo = jugadorP.NormalizarVector().getAngulo();
        
          //-----------Disparo--------------------
        if (fuego > Constantes.TDisparoNos) {
            jugadorP = jugadorP.NormalizarVector();

            // Obtenemos el ángulo correcto usando el método corregido getAngulo()
            double anguloActual = jugadorP.getAngulo();

            // Introduce variación en el ángulo de disparo
            anguloActual += Math.random() * Constantes.RangoAnguloUfo
                    - (Constantes.RangoAnguloUfo / 2);

            /* Ajustamos la dirección del disparo usando la función
            calcularDireccion() de la clase Vectores*/
            jugadorP = jugadorP.calcularDireccion(anguloActual);

            Lacer laser = new Lacer(
                    Externos.redLaser,
                    CentroImagen(),
                    jugadorP, 
                    Constantes.Velocidad_lacNostromo,
                    anguloActual + Math.PI / 2,
                    ventanapartida, true);

            ventanapartida.getObjetosmoviles().add(0, laser);

            fuego = 0;
            Sdisparar.play();
        }
        if (Sdisparar.getFramePsition() > 100000) {
            Sdisparar.parar();
        }
     LimitarPantalla();
        ColisionaCon();
    }

    @Override
    public void Destruir() {

        ventanapartida.SumarPuntos(Constantes.PuntosUfo, posicion);
        ventanapartida.Explotar(posicion);
        super.Destruir();

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
