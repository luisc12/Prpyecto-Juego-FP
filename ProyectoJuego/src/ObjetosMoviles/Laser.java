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
public class Laser extends ObjetosMovibles{

    //le pasamos el angulo del jugador 
    public Laser(BufferedImage textura, Vectores posicion, Vectores velocidad,
            double maxVel,double angulo, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel,ventanapartida);
        this.angulo=angulo;
        //la velocidad seria la direcion multipicado por la velocidad maxima
        this.velocidad=velocidad.MultiplicarVector(maxVel);
    }

    @Override
    public void actualizar() {
        //a la posicion le sumamos la velocidad
        posicion=posicion.SumaVectores(velocidad);
        if (posicion.getX()<0||posicion.getX()>Constantes.ancho||
                posicion.getY()<0||posicion.getY()>Constantes.alto) {
            Destruir();
        }
        ColisonaCon();
    }

    @Override
    public void dibujar(Graphics g) {
        //dibujamos el laser
        Graphics2D g2d=(Graphics2D)g;
        
        at=AffineTransform.getTranslateInstance(posicion.getX()-imgancho/2, posicion.getY());
        //le sumamos el ancho de la imagen al angulo para que asi siga rotando al rededor de su centro
        at.rotate(angulo,imgancho/2,0);
        
        g2d.drawImage(textura, at, null);
        
    }
    @Override
     public Vectores CentroImagen(){
        return new Vectores(posicion.getX()+imgancho/2, posicion.getY()+imgancho/2);
    }
}
