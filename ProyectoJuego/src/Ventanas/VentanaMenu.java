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
import ObjetosMoviles.ObjetosMovibles;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    boolean ver,musica;
    BufferedImage panel;
    private static Sonido musicaFondo;
    public static Sonido getMusicaFondo() {
        return musicaFondo;
    }
 
    public static void setMusicaFondo(Sonido musicaFondo) {
        VentanaMenu.musicaFondo = musicaFondo;
    }

    public VentanaMenu(ProyectoJuego p,boolean musica) {
        super(p);
         ver = false;
        panel = Externos.cambiarTamaño(Externos.panelMenu,
                Constantes.ancho / 2, Constantes.alto - 200); 
        if (musica ) {
            musicaFondo = new Sonido(Externos.menuMusica);
            musicaFondo.MusicaFondo();
            musicaFondo.cambiarVolumen(-10.0f);
        }

        botones = new ArrayList<Boton>();

        botones.add(
                new Boton(Constantes.botonApagado,
                        Constantes.botonActivo,
                        Constantes.ancho / 2
                        - Constantes.botonApagado.getWidth() / 2,
                        Constantes.alto / 2
                        - Constantes.botonApagado.getHeight() * 4,
                        Constantes.Comenzar, Externos.cEncendido,
                        Externos.cApagado,
                        new Accion() {
                    @Override
                    public void hacerAccion() {
                        Ventana.cambiarVentana(new VentanaControl(p));

                    }
                }));

        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - Constantes.botonApagado.getWidth() / 2,
                Constantes.alto / 2,
                Constantes.Salir, Externos.cEncendido, Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {

                System.exit(0);

            }
        }));
        int ancho = 300;
        int alto = 64;
        BufferedImage scaledgris = Externos.cambiarTamaño(Externos.bInactivo,
                ancho,alto);

        BufferedImage scaledverde = Externos.cambiarTamaño(Externos.bActivo,
                ancho,alto);

        botones.add(new Boton(scaledgris,
                scaledverde,
                Constantes.ancho / 2 - scaledgris.getWidth() / 2,
                Constantes.alto / 2 - Externos.bGris.getHeight() * 2,
                Constantes.MejoresPuntajes, Externos.cEncendido,
                Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaPuntaje(p));
            }
        }));
        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - Constantes.botonApagado.getWidth() / 2,
                Constantes.alto / 2 + Constantes.botonApagado.getHeight() * 2,
                "Creditos", Externos.cEncendido, Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                try {
                    Ventana.cambiarVentana(new VentanaCreditos(p));
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).
                            log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).
                            log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaMenu.class.getName()).
                            log(Level.SEVERE, null, ex);
                }} }));

       
    }

    @Override
    public void actualizar(float dt) {
        cambio += dt;
        for (Boton b : botones) {
            b.actualizar();
        }
        //cambia el color del texto del titulo al pasar un tiempo
        if (cambio > 700) {
            if (ver) {
                c = Externos.cApagado;
                ver = false;
            } else {
                c = Externos.cEncendido;
                ver = true;
            }
        }
    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
       
        AffineTransform at = AffineTransform.getTranslateInstance(
                Constantes.ancho / 2 - panel.getWidth() / 2,
                0);
        g2d.drawImage(panel, at, null);

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
