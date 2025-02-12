/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Graficos.Externos;
import Graficos.Sonido;
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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class VentanaMenu extends Ventana {

    ArrayList<Boton> botones;
    Color c;
    long cambio;
    boolean ver;
BufferedImage panel;
 private Sonido musicaFondo;
    public VentanaMenu(ProyectoJuego p) {
        super(p);
    musicaFondo=new Sonido(Externos.MusicaFondo);
    musicaFondo.MusicaFondo();
        botones = new ArrayList<Boton>();

        botones.add(
                new Boton( Constantes.botonApagado,
                        Constantes.botonActivo,
                        Constantes.ancho / 2 -  Constantes.botonApagado.getWidth() / 2,
                        Constantes.alto / 2 -  Constantes.botonApagado.getHeight() * 4,
                        Constantes.Comenzar,Externos.cEncendido,Externos.cApagado,
                        new Accion() {
                    @Override
                    public void hacerAccion() {
musicaFondo.parar();
                        //Ventana.cambiarVentana(new VentanaControl());
                        Ventana.cambiarVentana(new VentanaControl(p));

                    }
                }));

        botones.add(new Boton( Constantes.botonApagado,
                        Constantes.botonActivo,
                Constantes.ancho / 2 -  Constantes.botonApagado.getWidth() / 2,
                Constantes.alto / 2 ,
                Constantes.Salir,Externos.cEncendido,Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                
                    musicaFondo.parar();
                    System.exit(0);
                    
            }
        }));

        BufferedImage scaledgris =  Externos.cambiarTamaño2(Externos.bInactivo,300, 64);

        BufferedImage scaledverde = Externos.cambiarTamaño2(Externos.bActivo,300,  64);
        
System.out.println(scaledverde.getWidth()+" "+scaledverde.getHeight());
        botones.add(new Boton(scaledgris,
                scaledverde,
                Constantes.ancho / 2 - scaledgris.getWidth() / 2,
                Constantes.alto / 2 - Externos.bGris.getHeight() * 2,
                Constantes.MejoresPuntajes,Externos.cEncendido,Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
               // musicaFondo.parar();
                Ventana.cambiarVentana(new VentanaPuntaje(p));
            }
        }));
        botones.add(new Boton( Constantes.botonApagado,
                        Constantes.botonActivo,
                Constantes.ancho / 2 - Constantes.botonApagado.getWidth() / 2,
                Constantes.alto / 2 + Constantes.botonApagado.getHeight() * 2,
                "Creditos",Externos.cEncendido,Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                try {
                    // System.exit(0);
                    musicaFondo.parar();
                    Ventana.cambiarVentana(new VentanaCreditos(p));
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        
      /*  int randio = (int) (Math.random() * 2);

        double x = randio == 0 ? (Math.random() * Constantes.ancho) : 0;
        double y = randio == 0 ? 0 : (Math.random() * Constantes.alto);

        ArrayList<Vectores> caminos = new ArrayList<Vectores>();

        double posX, posY;
        //sector superior izquierdo

        posX = Math.random() * Constantes.ancho / 2;
        posY = Math.random() * Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector superior derecho
        posX = Math.random() * (Constantes.ancho / 2) + Constantes.ancho / 2;
        posY = Math.random() * Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector inferior izquierdo
        posX = Math.random() * Constantes.ancho / 2;
        posY = Math.random() * (Constantes.alto / 2) + Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector inferior derecho
        posX = Math.random() * (Constantes.ancho / 2) + Constantes.ancho / 2;
        posY = Math.random() * (Constantes.alto / 2) + Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));*/
        
        ver=false;
        c = Color.MAGENTA;
        panel=Externos.panelMenu;
    }

    @Override
    public void actualizar(float dt) {
        cambio += dt;
        for (Boton b : botones) {
            b.actualizar();
        }
        if (cambio > 5000) {
            if (ver) {
                c = Externos.cApagado;
                ver=false;
            } else {
                c = Externos.cEncendido;
                ver=true;
            }
        }
    }

    @Override
    public void dibujar(Graphics g) {
          Graphics2D g2d = (Graphics2D) g;
            BufferedImage imagenEscalada = Externos.cambiarTamaño2(Externos.panelMenu, Constantes.ancho/2,  Constantes.alto-200); // Ancho: 200, Alto: 300
            AffineTransform at = AffineTransform.getTranslateInstance(
                    Constantes.ancho / 2-imagenEscalada.getWidth()/2,
                    0);
             g2d.drawImage(imagenEscalada, at, null);
            
       //  g.setColor(Color.getHSBColor(37, 137, 41));
  Texto.DibujarTexto(g,
                "Entrega Espacial",
                new Vectores(Constantes.ancho / 2, 100),
                true,
                c,
                Externos.Gfuente);
        for (Boton b : botones) {
            b.dibujar(g);
        }
    }
}
