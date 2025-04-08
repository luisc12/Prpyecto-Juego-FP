/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles.Disparos;

import Graficos.Externos;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ventanas.VentanaControl;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Lacer extends Disparos{

    public boolean cambiarLacer=false;

    public Lacer(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo) {
        super(textura, posicion, velocidad, maxVel, angulo, ventanapartida, enemigo);
        cambiarLacer=false;
    }

    public Lacer(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, double angulo, VentanaControl ventanaControl, boolean enemigo) {
        super(textura, posicion, velocidad, maxVel, angulo, ventanaControl, enemigo);
        cambiarLacer=false;
    }

    @Override
    public void actualizar(float dt) {
        /*funciona igual que el metodo choqueEscudo() solo que en vez de 
        destruir usamos la fuerza huida*/
        if (ventanapartida!=null) {
             Vectores PosicionJ = new Vectores(ventanapartida.getJugador()
                .CentroImagen());
        
        int jugadorDistancia = (int) PosicionJ.RestaVectores(CentroImagen())
                .Magnitud();
        if (enemigo && ventanapartida.getJugador().isEscudoActivo() && 
             jugadorDistancia < Constantes.DistanciaEscudo / 2 + imgancho/2) {
          //si el lacer choca con el escudo aplicamos la fuerza huida 
            cambiarLacer=true;
            Vectores fuerzaHuida = FleeForce();
            //le sumamos la velocidad  nuestra fuerza huida
            velocidad = velocidad.SumaVectores(fuerzaHuida);
            /*al salir cmbiara el color del lacer y ya no dañara al jugador si 
            no a los enemigos*/
        }else if (jugadorDistancia> Constantes.DistanciaEscudo / 2 + imgancho/2
                &&cambiarLacer) {
                enemigo=false;
            textura=Externos.greenLaser;
        }
        }
       
        if (velocidad.Magnitud() >= this.maxVel) {
            Vectores velocidadInvertida = new Vectores(-velocidad.getX(),
                    -velocidad.getY());
            velocidad = velocidad.SumaVectores(velocidadInvertida.
                    velocidadlimite(0.01f));
        }
        //limitamos la velocidad
        velocidad = velocidad.velocidadlimite(Constantes.Velocidad_lac);        
        posicion = posicion.SumaVectores(velocidad);
    /*el angulo se sacara de la velocidad `90 grados debido a que la iamgen 
        del lacer mira hacia arriba
    */
            angulo=velocidad.getAngulo()+Math.PI/2;
    //al salir de la pantalla se destruye
        if (ventanapartida!=null) {
             if (posicion.getX() < 0 || posicion.getX() > Constantes.ancho
                || posicion.getY() < 0 || posicion.getY() > Constantes.alto) {
            Destruir();
             
        }
             ColisionaCon();
        }
       
       
    }

    @Override
    public void dibujar(Graphics g) {
        //dibujamos el laser
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(posicion.getX() - imgancho / 2, posicion.getY());
        //le sumamos el ancho de la imagen al angulo para que asi siga rotando al rededor de su centro
        at.rotate(angulo, imgancho / 2, 0);

        g2d.drawImage(textura, at, null);

    }

   
}
