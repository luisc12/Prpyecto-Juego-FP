/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Matematicas.Vectores;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Laser extends Disparos{

   public boolean enemigo;
    public int daño;
    public boolean cambiardireccion=false;

    public Laser(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo, int daño) {
        super(textura, posicion, velocidad, maxVel, angulo, ventanapartida, enemigo, daño);
    }

    //le pasamos el angulo del jugador 
   /* public Laser(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        enemigo = false;
    }*/

   /* public Laser(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo, int daño) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        this.enemigo = enemigo;
        this.daño = daño;
    }*/

    public int getDaño() {
        return daño;
    }

    public boolean isEnemigo() {
        return enemigo;
    }

    @Override
    public void actualizar(float dt) {
        //a la posicion le sumamos la velocidad
        Vectores PosicionJ = new Vectores(ventanapartida.getJugador().CentroImagen());
        int jugadorDistancia = (int) PosicionJ.RestaVectores(CentroImagen()).Manitud();
        if (enemigo && ventanapartida.getJugador().isEscudoActivo() && jugadorDistancia < Constantes.DistanciaEscudo / 2 + imgancho / 2) {
cambiardireccion=true;
            Vectores fuerzaHuida = fuerzaHuida();
            velocidad = velocidad.SumaVectores(fuerzaHuida);

        }
        if (velocidad.Manitud() >= this.maxVel) {
            Vectores velocidadInvertida = new Vectores(-velocidad.getX(), -velocidad.getY());
            velocidad = velocidad.SumaVectores(velocidadInvertida.velocidadlimite(0.01f));

        }
        velocidad = velocidad.velocidadlimite(Constantes.Velocidad_lac);
        
        posicion = posicion.SumaVectores(velocidad);
        if (cambiardireccion) {
            angulo=posicion.NormalizarVector().getAngulo()+Math.PI/2;
        }
        
        if (posicion.getX() < 0 || posicion.getX() > Constantes.ancho
                || posicion.getY() < 0 || posicion.getY() > Constantes.alto) {
            Destruir();
        }
        ColisonaCon();
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

    @Override
    public Vectores CentroImagen() {
        return new Vectores(posicion.getX() + imgancho / 2, posicion.getY() + imgancho / 2);
    }
}