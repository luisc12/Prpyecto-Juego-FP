/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import Ui.Accion;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class PowerUp extends ObjetosMovibles{

    private long duracion;
    private Accion accion;
    private Sonido activarPowerUP;
    private BufferedImage tipoTextura;
    private BufferedImage orbe;
    
    public PowerUp(BufferedImage textura, Vectores posicion,VentanaPartida ventanapartida,Accion accion) {
        super(textura, posicion, new Vectores(), 0, ventanapartida);
        this.accion=accion;
        this.tipoTextura=textura;
        duracion=0;
        activarPowerUP=new Sonido(Externos.PowerUP);
        orbe=Externos.orbe;
    }
    
    public PowerUp(BufferedImage textura, Vectores posicion,VentanaPartida ventanapartida,Accion accion,BufferedImage orbe) {
        super(textura, posicion, new Vectores(), 0, ventanapartida);
        this.accion=accion;
        this.tipoTextura=textura;
        this.orbe=orbe;
        duracion=0;
        activarPowerUP=new Sonido(Externos.PowerUP);
        
    }
    public void EjecutarAccion() {
       accion.hacerAccion();
       activarPowerUP.play();
    }


    @Override
    public void actualizar(float dt) {
      angulo+=0.1;
      duracion+=dt;
        if (duracion>Constantes.DuracionPU) {
            this.Destruir();
        }
        ColisonaCon();
    }

    @Override
    public void dibujar(Graphics g) {
       Graphics2D g2d=(Graphics2D) g;
       
       at=AffineTransform.getTranslateInstance(
               posicion.getX()+orbe.getWidth()/2- tipoTextura.getWidth()/2,
               posicion.getY()+orbe.getHeight()/2-tipoTextura.getHeight()/2);
       
       at.rotate(angulo,tipoTextura.getWidth()/2,tipoTextura.getHeight()/2);
       
       
       //AffineTransform at2=AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
      // at2.rotate(angulo,orbe.getWidth()/2,orbe.getHeight()/2);
       g.drawImage(orbe,(int)posicion.getX(),(int)posicion.getY(),null);
      // g.drawImage(orbe, at2, null);
       
       g2d.drawImage(tipoTextura, at, null);
       
       
    }

   
   
    
}
