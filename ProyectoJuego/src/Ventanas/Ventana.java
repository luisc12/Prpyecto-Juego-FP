/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author luis
 */
public abstract class Ventana extends JFrame{//extends JFrame
    private static Ventana ventanaActual=null;

    public static Ventana getVentanaActual() {
        return ventanaActual;
    }
    public static void cambiarVentana(Ventana v){
        ventanaActual=v;
    }
    
    public abstract void actualizar(float dt);
    public  abstract void dibujar(Graphics g);
}
