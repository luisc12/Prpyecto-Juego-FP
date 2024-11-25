/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
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
public class Meteoros extends ObjetosMovibles {

    private Tamaños tamaños;
    public Meteoros(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida,Tamaños tamaños) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        this.tamaños=tamaños;
        //escalonamos el vector velocidad
        this.velocidad=velocidad.MultiplicarVector(maxVel);
        
    }

    @Override
    public void actualizar(float dt) {
      //  posicion = posicion.SumaVectores(velocidad);
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

    }
    
    public Vectores fuerzaHuida(){
        Vectores velocidadDeseada=ventanapartida.getJugador().CentroImagen();
        velocidadDeseada=(velocidadDeseada.NormalizarVector()).MultiplicarVector(Constantes.MaxVelocidadMeteor);
        Vectores v=new Vectores(velocidad);
        return v.RestaVectores(velocidadDeseada);
    }
    @Override
    public void Destruir(){
    ventanapartida.DividirMeteoro(this);
    ventanapartida.Explotar(posicion);
    ventanapartida.SumarPuntos(Constantes.PuntosMeteoros,posicion);
    
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

    public Tamaños getTamaños() {
        return tamaños;
    }

}
