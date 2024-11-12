/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.RatonEntrada2;
import Graficos.Externos;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author luis
 */
public class VentanaMenu extends Ventana{
    ArrayList<Boton> botones;

    public VentanaMenu() {
        botones=new ArrayList<Boton>();
       
        botones.add(
                new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2-Externos.bGris.getHeight()*2,
                Constantes.Comenzar,
                new Accion() {
            @Override
            public void hacerAccion() {
               Ventana.cambiarVentana(new VentanaPartida());
            }
        }));
        
         botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2+Externos.bGris.getHeight()*2,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
               System.exit(0);
            }
        }));
    /*       JButton boton2=new JButton();
        boton2.setBounds(Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2, 
                Externos.bGris.getWidth()+200, 
                Externos.bGris.getHeight());
       ImageIcon clicAqui=new ImageIcon("button_Verde.png");
       ImageIcon clicAqui2=new ImageIcon("button_Gris.png");
        boton2.setIcon(new ImageIcon(clicAqui.getImage().getScaledInstance(boton2.getWidth(), boton2.getHeight(), Image.SCALE_SMOOTH)));
        boton2.addMouseListener(new RatonEntrada2(boton2, clicAqui2, clicAqui, () -> {
         Ventana.cambiarVentana(new VentanaPuntaje());}));*/
        
        
        
      botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2,
                Constantes.MejoresPuntajes,
                new Accion() {
            @Override
            public void hacerAccion() {
               Ventana.cambiarVentana(new VentanaPuntaje());
            }
        }));
     //this.add(boton2,0);
     this.setVisible(true);
    }

    
    @Override
    public void actualizar() {
        for (Boton b : botones) {
            b.actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
    }
}
