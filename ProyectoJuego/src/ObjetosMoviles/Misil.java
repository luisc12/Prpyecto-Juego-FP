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
public class Misil  extends Disparos  {
private Vectores jugadorP;
 private Vectores direccion;

    public Misil(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel, double angulo, VentanaPartida ventanapartida, boolean enemigo, int daño) {
        super(textura, posicion, velocidad, maxVel,angulo, ventanapartida, enemigo, daño);
        direccion = new Vectores(0, 1);
    }

    
    

    @Override
    public void actualizar(float dt) {
         

       // jugadorP = ventanapartida.getJugador().CentroImagen().RestaVectores(CentroImagen());

        Vectores force=SeekForce(posicion);
      //  angulo = jugadorP.NormalizarVector().getAngulo2();
      //  direccion = jugadorP.calcularDireccion(jugadorP.getAngulo());

        //System.out.println(this.angulo + "------" + Math.atan2(direccion.getY(), direccion.getX()));
        //velocidad.velocidadlimite(Constantes.MaxVelUfo);
        velocidad=velocidad.SumaVectores(force);
        
        if (velocidad.Manitud() > Constantes.Velocidad_Mic) {
              velocidad = velocidad.velocidadlimite(Constantes.Velocidad_Mic);
        }
      
        posicion = posicion.SumaVectores(velocidad);
        angulo=velocidad.getAngulo2();
        ColisonaCon();
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
