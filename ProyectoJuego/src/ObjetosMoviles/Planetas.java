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
public class Planetas extends ObjetosMovibles{
private Vectores direccion;
    public Planetas(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
         direccion = new Vectores(0, 1);
    }

    @Override
    public void actualizar(float dt) {
        Vectores PosicionJ=new Vectores(ventanapartida.getJugador().CentroImagen());
      
      int jugadorDistancia=(int)PosicionJ.RestaVectores(CentroImagen()).Manitud();
      
        if (jugadorDistancia<Constantes.DistanciaEscudo/2+imgancho/2) {
            if (ventanapartida.getJugador().isEscudoActivo()) {
                Vectores fuerzaHuida=fuerzaHuida();
                velocidad=velocidad.SumaVectores(fuerzaHuida);
                
            }
        }
        
        if (velocidad.Manitud()>=this.maxVel) {
            Vectores velocidadInvertida=new Vectores(-velocidad.getX(),-velocidad.getY());
            velocidad=velocidad.SumaVectores(velocidadInvertida.NormalizarVector().MultiplicarVector(0.01f));
            
        }
        velocidad=velocidad.velocidadlimite(Constantes.MaxVelocidadMeteor);
        
        posicion=posicion.SumaVectores(velocidad);
        
        if (posicion.getX() > Constantes.ancho) {
            posicion.setX(-imgancho);
        }
        if (posicion.getY() > Constantes.alto) {
            posicion.setY(-imgalto);
        }

        if (posicion.getX() < -imgancho) {
            posicion.setX(Constantes.ancho);
        }
        if (posicion.getY() < -imgalto) {
            posicion.setY(Constantes.alto);
        }
        //le damos la propiedad de que rote
        angulo += Constantes.anguloBase/2;
        /* angulo=-Math.PI/2;
       direccion = posicion.calcularDireccion(angulo);
        velocidad = direccion.velocidadlimite(Constantes.MaxVelEnemigo1);
        posicion = posicion.SumaVectores(velocidad);*/
        
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
