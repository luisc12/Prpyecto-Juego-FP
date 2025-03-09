/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Texto;
import Matematicas.Vectores;
import Ventanas.VentanaPartida;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author luis
 */
public class Mensaje {
    //valor de los colores que indica transparencia
    private float valorC;
    private String texto;
    private Vectores posicion;
    private Color color;
    private boolean centro;
    private boolean desbanecer;//fade
    //indica la velocidad de desbanecimiento
    private Font fuente;
    private final float velocidadDesbanecimiento=0.01f;
    private boolean muerte;

    public Mensaje(String texto, Vectores posicion,
            Color color, boolean centro, boolean desbanecer,Font fuente) {
        this.texto=texto;this.posicion = posicion;this.color = color;
        this.centro = centro;this.desbanecer = desbanecer;this.fuente=fuente;
        this.muerte=false;
        /* Si el mensaje debe desvanecerse, inicia con valorC 1 
       (completamente visible) Si no debe desvanecerse, inicia con valorC 0 
        (totalmente transparente)*/
        if (desbanecer) {
            valorC=1;
        }else{
            valorC=0;
        }
    }
    public void dibujar(Graphics2D g2d){
         // Aplica la transparencia al dibujo del mensaje
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                valorC));
        Texto.DibujarTexto(g2d, texto, posicion, centro, color, fuente);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        
        posicion.setY(posicion.getY()-1);
         // Si el mensaje debe desvanecerse, reduce su valorC
        if (desbanecer) {
            valorC-=velocidadDesbanecimiento;
        }else{
            valorC+=velocidadDesbanecimiento;
        }
        /* Si el mensaje se está desvaneciendo y su valorC  es menor que 0, 
        marca como muerto */
        if (desbanecer&&valorC<0) {
            muerte=true;
           /* Si el mensaje está apareciendo y valorC supera 1,
        se desbanece*/
        } else if (!desbanecer&&valorC>1) {
            desbanecer=true;
            valorC=1;
        } 
    }

    public boolean isMuerte() {
        return muerte;
    }
    
}
