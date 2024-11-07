/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Matematicas.Vectores;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author luis
 */
public class Texto {

    public static void DibujarTexto(Graphics g, String texto, Vectores pos,
            boolean centro, Color color, Font fuente) {
        g.setColor(color);
        g.setFont(fuente);
        Vectores posicion=new Vectores(pos.getX(), pos.getY());
        
        if (centro) {
            //encontrar el punto medio del texto
            FontMetrics fm=g.getFontMetrics();
            //la posicion de x - la mitad del ancho del texto
            posicion.setX(posicion.getX()-fm.stringWidth(texto)/2);
            //la posicion de y - la mitad el tamaño de la fuente
            posicion.setY(posicion.getY()-fm.getHeight()/2);
        }
          g.drawString(texto, (int)posicion.getX(), (int)posicion.getY());

    }
}
