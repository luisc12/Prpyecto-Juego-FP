/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;

import ObjetosMoviles.Constantes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class VentanaCarga extends Ventana{

    private Thread cargarHilo;
    private Font fuente;
    
    public VentanaCarga(Thread cargarHilo) {
        this.cargarHilo=cargarHilo;
        this.cargarHilo.start();
        fuente=Externos.CargarFuente("fuentes/kenvector_future.ttf", 38);
    }
    
    

    @Override
    public void actualizar(float dt) {
        if (Externos.cargado) {
            Ventana.cambiarVentana(new VentanaMenu());
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
                Color.BLUE,
                Constantes.ancho/2+Constantes.BarraCargaAncho/2,
                Constantes.alto/2+Constantes.BarraCargaAlto/2,
                Color.GREEN);
        
        Graphics2D g2d=(Graphics2D)g;
        
        g2d.setPaint(gp);
        
        float porcentaje=Externos.cantidad/Externos.cantidadMax;
        //aqui reyeno
        g2d.fillRect(
                Constantes.ancho/2-Constantes.BarraCargaAncho/2,
                Constantes.alto/2-Constantes.BarraCargaAlto/2,
                (int)(Constantes.BarraCargaAncho*porcentaje),
                Constantes.BarraCargaAlto);
        //aqui dibujo el marco
        g2d.drawRect(
                Constantes.ancho/2-Constantes.BarraCargaAncho/2,
                Constantes.alto/2-Constantes.BarraCargaAlto/2,
                Constantes.BarraCargaAncho,
                Constantes.BarraCargaAlto);
        
        Texto.DibujarTexto(
                g2d,
                "PREPARANDO ENTREGA",
                new Vectores(Constantes.ancho/2, Constantes.alto/2 -50),
                true,
                Color.white,
                fuente);
        
         Texto.DibujarTexto(
                g2d,
                "CARGANDO..",
                new Vectores(Constantes.ancho/2, Constantes.alto/2 +50),
                true,
                Color.white,
                fuente);
    }
    
}
