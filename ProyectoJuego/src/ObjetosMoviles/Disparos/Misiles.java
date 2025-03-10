/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles.Disparos;

import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Misiles extends Disparos {

    private Vectores jugadorP;
    private Vectores direccion;

    public Misiles(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo) {
        super(textura, posicion, velocidad, maxVel, angulo, ventanapartida, enemigo);
        direccion = new Vectores(0, 1);
        this.angulo = angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad = velocidad.MultiplicarVector(maxVel);
        this.enemigo = enemigo;

    }

    @Override
    public void actualizar(float dt) {
        /*tomamos la ubicacion del jugador y despues preguntamos si choca con
        el escudo*/
        jugadorP = ventanapartida.getJugador().CentroImagen();
        choqueEscudo();
        
        /*creamos el vector force que representa la fuersa ejercida, si el 
        jugador no esta reapareciendo la fuersa buscara al jugador, sino 
        buscara u nuevo vector*/
        Vectores force;
        if (!ventanapartida.getJugador().isAparecer()) {

            force = SeekForce(jugadorP);
        } else {
            force = SeekForce(new Vectores());
        }
        //dividimos la fuersa por su masa y sumamos la fuerz a la velocidad
        force = force.MultiplicarVector(1 / Constantes.MasaMisil);

        velocidad = velocidad.SumaVectores(force);
        
        /*si la magnitud de la velocidad es mayor a la velocidad limite*/
        if (velocidad.Manitud() > Constantes.Velocidad_Mic) {
            velocidad = velocidad.velocidadlimite(Constantes.Velocidad_Mic);
        }
        /*le sumamos la posicion la velocidad y
        sacamos el angulo a la velocidad y le sumamos 90 grados debido a que 
        la cabeza de la imagen esta en la parte superior de la imagen*/
        posicion = posicion.SumaVectores(velocidad);
        angulo = velocidad.getAngulo() + Math.PI / 2;
        ColisionaCon();
    }

    @Override
    public void Destruir() {
        ventanapartida.Explotar(posicion);
        super.Destruir();
    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(posicion.getX() - imgancho / 2, posicion.getY());
        //le sumamos el ancho de la imagen al angulo para que asi siga rotando al rededor de su centro
        at.rotate(angulo, imgancho / 2, 0);

        g2d.drawImage(textura, at, null);
    }

}
