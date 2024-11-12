/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entrada;

import Ui.Accion;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author luis
 */
public class RatonEntrada2 extends MouseAdapter{
    
    public static int x,y;
    public static boolean salidaRaton;
   public  ImageIcon ratonfuera;
   public  ImageIcon ratonSobre;
public  JButton  boton;
public Accion accion;
    public RatonEntrada2(JButton boton,ImageIcon ratonfuera, ImageIcon ratonSobre,Accion accion) {
        this.boton=boton;
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        this.accion=accion;
    }

   

   
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
             boton.setIcon(new ImageIcon(ratonfuera.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), Image.SCALE_SMOOTH)));
             accion.hacerAccion();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         if (e.getButton()==MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
            boton.setIcon(new ImageIcon(ratonfuera.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), Image.SCALE_SMOOTH)));
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
