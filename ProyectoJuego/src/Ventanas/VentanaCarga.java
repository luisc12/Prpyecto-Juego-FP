/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Graficos.Externos;
import static Graficos.Externos.CargarImagen;
import static Graficos.Externos.CargarMusica;
import Graficos.Sonido;
import Graficos.Texto;
import Matematicas.Vectores;

import ObjetosMoviles.Constantes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class VentanaCarga extends Ventana{

    private Thread cargarHilo;
    private Font fuente;
    BufferedImage imagenEscalada;
     private Sonido musicaFondo;
    public VentanaCarga(Thread cargarHilo,ProyectoJuego p) {
        super(p);
        this.cargarHilo=cargarHilo;
        this.cargarHilo.start();
        fuente=Externos.CargarFuente("fuentes/kenvector_future.ttf", 58);
        imagenEscalada = Externos.cambiarTamaño(CargarImagen("ui/Card X2.png"), Constantes.ancho -Constantes.ancho/5, Constantes.alto- Constantes.alto/4); 
      
         musicaFondo = new Sonido( Externos.CargarMusica("sonidos/TremLoadinglooplV2.wav") );
        musicaFondo.MusicaFondo();
       // musicaFondo.cambiarVolumen(-10.0f);
        musicaFondo.play();
    }
    
    

    @Override
    public void actualizar(float dt) {
        if (Externos.cargado) {
            musicaFondo.parar();
            Ventana.cambiarVentana(new VentanaMenu(p));
            try {
                cargarHilo.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(VentanaCarga.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void dibujar(Graphics g) {
        //usamos gradientPaint para estrapolar un color de un punto a otro punto
        GradientPaint gp=new GradientPaint(
                Constantes.ancho/2-Constantes.BarraCargaAncho/2,
                Constantes.alto/2-Constantes.BarraCargaAlto/2,
                Externos.cApagado,
                Constantes.ancho/2+Constantes.BarraCargaAncho/2,
                Constantes.alto/2+Constantes.BarraCargaAlto/2,
                Externos.cEncendido);
        
        Graphics2D g2d=(Graphics2D)g;
     
        AffineTransform ati = AffineTransform.getTranslateInstance(
                Constantes.ancho / 2 - imagenEscalada.getWidth() / 2,
                 Constantes.alto / 2 - imagenEscalada.getHeight() / 2);
        g2d.drawImage(imagenEscalada, ati, null);
        
        g2d.setPaint(gp);
        
        float porcentaje=Externos.cantidad/Externos.cantidadMax;
        int cuadx=Constantes.ancho/2-Constantes.BarraCargaAncho/2;
        int cuady=Constantes.alto/2+100;
        //aqui reyeno
        g2d.fillRect(
                 cuadx,cuady,
                (int)(Constantes.BarraCargaAncho*porcentaje),
                Constantes.BarraCargaAlto);
        //aqui dibujo el marco
        g2d.drawRect(
                cuadx,cuady,
                Constantes.BarraCargaAncho,
                Constantes.BarraCargaAlto);
        
        Texto.DibujarTexto(
                g2d,
                "PREPARANDO ENTREGA",
                new Vectores(Constantes.ancho/2, Constantes.alto/2 -100),
                true,
                Externos.cEncendido,
                fuente);
        
         Texto.DibujarTexto(
                g2d,
                "CARGANDO..",
                new Vectores(Constantes.ancho/2, Constantes.alto/2 +50),
                true,
                Externos.cEncendido,
                fuente);
    }
    
}
