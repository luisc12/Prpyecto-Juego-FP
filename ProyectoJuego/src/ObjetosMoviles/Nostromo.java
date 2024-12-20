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
public class Nostromo extends Enemigos {

    private Vectores direccion;
    private int indice;
    private int vida;

    private boolean continuar;
    private Vectores jugadorP;

    private Vectores ultimaposicion;
    //private Cronometro fuego;
    long fuego;

    private Sonido Sdisparar;

    public Nostromo(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);

        direccion = new Vectores(0, 1);
        indice = 0;
        continuar = true;
        fuego = 0;
        Sdisparar = new Sonido(Externos.DisparoUfo);
        vida = 100;
    }

    /*
    private Vectores SeguirCamino() {
        nodoActual = camino.get(indice);

        double distanciaAlNodo = nodoActual.RestaVectores(CentroImagen()).Manitud();

        if (distanciaAlNodo < Constantes.RadiusNodo) {
            indice++;
            if (indice >= camino.size()) {
                continuar = false;
            }
        }
        return PursuingForce(nodoActual,3);
    }
    /*
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
        return SeekForce(FuturePosicion);
    }
     */
    @Override
    public void actualizar(float dt) {

        fuego += dt;

        jugadorP = ventanapartida.getJugador().CentroImagen().RestaVectores(CentroImagen());

        continuar = false;
        ultimaposicion = jugadorP;
        angulo = jugadorP.NormalizarVector().getAngulo();
        direccion = jugadorP.calcularDireccion(jugadorP.getAngulo());

        //System.out.println(this.angulo + "------" + Math.atan2(direccion.getY(), direccion.getX()));
        //velocidad.velocidadlimite(Constantes.MaxVelUfo);
        velocidad = direccion.velocidadlimite(Constantes.MaxVelEnemigo1);
        posicion = posicion.SumaVectores(velocidad);

        if (fuego > Constantes.TDisparoEnemigo1) {

            Laser laser = new Laser(Externos.blueLaser,
                    CentroImagen(),
                    jugadorP,
                    Constantes.Velocidad_lac,
                    angulo + Math.PI / 2,
                    ventanapartida, true, 0);
            ventanapartida.getObjetosmoviles().add(0, laser);

            fuego = 0;
            Sdisparar.play();
        }

        if (posicion.getX() > Constantes.ancho) {
            posicion.setX(0);
            continuar = true;
        }
        if (posicion.getY() > Constantes.alto) {
            posicion.setY(0);
            continuar = true;
        }

        if (posicion.getX() < 0) {
            posicion.setX(Constantes.ancho);
            continuar = true;
        }
        if (posicion.getY() < 0) {
            posicion.setY(Constantes.alto);
            continuar = true;
        }
        ColisonaCon();

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
