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
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author luis
 */
public class VentanaMenu extends Ventana {

    ArrayList<Boton> botones;
Color c;
long cambio;
boolean ver;
    public VentanaMenu() {
        botones = new ArrayList<Boton>();

        botones.add(
                new Boton(Externos.bGris,
                        Externos.bVerde,
                        Constantes.ancho / 2 - Externos.bGris.getWidth() / 2,
                        Constantes.alto / 2 - Externos.bGris.getHeight() * 2,
                        Constantes.Comenzar,
                        new Accion() {
                    @Override
                    public void hacerAccion() {
                       
                       
                            //Ventana.cambiarVentana(new VentanaControl());
                            Ventana.cambiarVentana(new VentanaControl());
                        
                    }
                }));

        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 - Externos.bGris.getWidth() / 2,
                Constantes.alto / 2 + Externos.bGris.getHeight() * 2,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
                System.exit(0);
            }
        }));
        BufferedImage scaledgris = Externos.cambiarancho(Externos.bGris, 100);

        BufferedImage scaledverde = Externos.cambiarancho(Externos.bVerde, 100);
        
        botones.add(new Boton(scaledgris,
                scaledverde ,
                Constantes.ancho / 2 - scaledgris.getWidth() / 2,
                Constantes.alto / 2,
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
        cambio+=dt;
        for (Boton b : botones) {
            b.actualizar();
        }
        if (cambio>200) {
            if (ver) {
                c=Color.WHITE;
            }else{
               c=Color.MAGENTA; 
            }
        }
    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
         Texto.DibujarTexto(g,
                "Entrega Espacial",
                new Vectores(Constantes.ancho / 2, 100),
                true,
                c,
                Externos.Gfuente);
    }
}
