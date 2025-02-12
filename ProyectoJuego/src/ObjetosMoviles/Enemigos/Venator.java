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
import ObjetosMoviles.Misiles;
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
public class Venator extends Enemigos {

    private ArrayList<Vectores> camino;
    private Vectores nodoActual;
    private int indice;
    private int vida;

    private boolean continuar;

    public Venator(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida,ArrayList<Vectores> camino) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.camino = camino;
        Sdisparar = new Sonido(Externos.DisparoUfo);
        continuar = true;
     
        
    }

    private Vectores SeguirCamino() {
        nodoActual = camino.get(indice);

        double distanciaAlNodo = nodoActual.RestaVectores(CentroImagen()).Manitud();

        if (distanciaAlNodo < Constantes.RadiusNodo) {
            indice++;
            if (indice >= camino.size()) {
                continuar = false;
            }
        }
        return SeekForce(nodoActual);
    }

    @Override
    public void actualizar(float dt) {
        fuego += dt;
        Vectores siguindo;
        if (continuar) {
            siguindo = SeguirCamino();
        } else {
            siguindo = new Vectores();
        }
 siguindo = siguindo.MultiplicarVector(1 / Constantes.MasaVen);

        velocidad = velocidad.SumaVectores(siguindo);

        velocidad = velocidad.velocidadlimite(maxVel);

        posicion = posicion.SumaVectores(velocidad);
angulo=posicion.getAngulo();
angulo=jugadorSurdo(siguindo);

        if (fuego>Constantes.TDisparoVen/2) {
          //   Vectores posicionJ = ventanapartida.getJugador().CentroImagen().RestaVectores(CentroImagen());
          //    posicionJ = posicionJ.NormalizarVector();
            
            
            Misiles misil=new Misiles(Externos.blueMisil,
                    CentroImagen(),
                    velocidad.NormalizarVector(),
                    Constantes.Velocidad_Mic,
                    angulo,
                    ventanapartida,true,0);
            ventanapartida.getObjetosmoviles().add(0, misil);
             fuego = 0;

            Sdisparar.play();
        }
          if (Sdisparar.getFramePsition() > 8500) {
            Sdisparar.parar();
        }
        angulo += 0.05;

        ColisonaCon();
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
