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
    private float valorC;//alpha
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
        
        this.texto=texto;
        this.posicion = posicion;
        this.color = color;
        this.centro = centro;
        this.desbanecer = desbanecer;
        this.fuente=fuente;
        this.muerte=false;
        
        if (desbanecer) {
            valorC=1;
        }else{
            valorC=0;
        }
    }
    public void dibujar(Graphics2D g2d){
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,valorC));
        
        Texto.DibujarTexto(g2d, texto, posicion, centro, color, fuente);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        
        posicion.setY(posicion.getY()-1);
        if (desbanecer) {
            valorC-=velocidadDesbanecimiento;
        }else{
            valorC+=velocidadDesbanecimiento;
        }
        
        if (desbanecer&&valorC<0) {
            muerte=true;
        } else if (!desbanecer&&valorC>1) {
            desbanecer=true;
            valorC=1;
        } 
        /*if (desbanecer&&valorC<0||!desbanecer&&valorC>1) {
            ventanaPartida.getMensajes().remove(this);
        }*/
    }

    public boolean isMuerte() {
        return muerte;
    }
    
}
