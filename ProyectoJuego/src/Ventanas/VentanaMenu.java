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
               Ventana.cambiarVentana(new VentanaNombre());
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
     
     
    }

    
    @Override
    public void actualizar(float dt) {
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
