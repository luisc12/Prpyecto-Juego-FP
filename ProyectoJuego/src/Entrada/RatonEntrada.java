/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entrada;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author luis
 */
public class RatonEntrada extends MouseAdapter{

    public static int x,y;
    public static boolean salidaRaton;
   

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
            salidaRaton=true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         if (e.getButton()==MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
            salidaRaton=false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //mantendra la posicion del raton almacenada en estas variables
       x=e.getX();
       y=e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       x=e.getX();
       y=e.getY();
    }
    
}
