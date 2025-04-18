/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.Graphics;
import javax.swing.JFrame;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public abstract class Ventana extends JFrame {

    private static Ventana ventanaActual = null;
    ProyectoJuego p;

    public Ventana(ProyectoJuego p) {
        this.p = p;
    }
    //mostrar la ventana actual

    public static Ventana getVentanaActual() {
        return ventanaActual;
    }
    
    // cambiar la ventana por otro objeto de la clase ventana
    public static void cambiarVentana(Ventana v) {
        ventanaActual = v;
    }

    public abstract void actualizar(float dt);

    public abstract void dibujar(Graphics g);
}
