/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.Teclado;
import Graficos.Externos;
import static Graficos.Externos.panelAncho;
import static Graficos.Externos.panelAnchoT;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Jugador;
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
public class VentanaControl extends Ventana {

    BufferedImage imagen;

    //------------fecha derecha-------
    BufferedImage derechaGris, derechaVerde, izquierdaGris, izquierdaVerde, arribaGris, arribaVerde, abajoGris, abajoVerde;
    double angulo;
    ArrayList<Boton> botones;
    Boton arriba, abajo, derecha, izquierda, espacio, atras, comenzar;
    boolean existe;
    Vectores posicion;

    Jugador j;

    public VentanaControl(ProyectoJuego p) {
         super(p);
        botones = new ArrayList<Boton>();

        imagen = Externos.jugadores[0];

        angulo = 0;

        posicion = new Vectores((Constantes.ancho / 2 + 100) + 100, (Constantes.alto / 4 - 50) + 100);

        //-----------Jugador
        j = new Jugador(imagen, posicion, new Vectores(0, 0), 7, null);

        //-------------------
        int tamaño = 100;
        //------------fecha derecha-------
        BufferedImage derechaGris = Externos.cambiarTamaño(Externos.flechaGrisD, tamaño);

        BufferedImage derechaVerde = Externos.cambiarTamaño(Externos.flechaVerdeD, tamaño);

        //------------flecha izquierda------
        izquierdaGris = Externos.cambiarTamaño(Externos.flechaGrisI, tamaño);

        izquierdaVerde = Externos.cambiarTamaño(Externos.flechaVerdeI, tamaño);

        //------------flecha Arriba----------
        arribaGris = Externos.cambiarTamaño(Externos.flechaGrisA, tamaño);

        arribaVerde = Externos.cambiarTamaño(Externos.flechaVerdeA, tamaño);

        //------------flecha Arriba----------
        abajoGris = Externos.cambiarTamaño(Externos.flechaGrisB, tamaño);

        abajoVerde = Externos.cambiarTamaño(Externos.flechaVerdeB, tamaño);

        arriba = new Boton(arribaGris,
                arribaVerde,
                Constantes.ancho / 3 - arribaGris.getWidth() * 2,
                Constantes.alto / 3 - arribaGris.getHeight(),
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        abajo = new Boton(abajoGris,
                abajoVerde,
                Constantes.ancho / 3 - arribaGris.getWidth() * 2,
                Constantes.alto / 3,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        izquierda = new Boton(izquierdaGris,
                izquierdaVerde,
                Constantes.ancho / 3 - izquierdaGris.getWidth() * 3,
                Constantes.alto / 3,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        derecha = new Boton(derechaGris,
                derechaVerde,
                Constantes.ancho / 3 - derechaVerde.getWidth(),
                Constantes.alto / 3,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        
        BufferedImage botonDesactivado = Externos.cambiarTamaño2(Externos.bDesactivado, 192, 64);
        
        espacio = new Boton(botonDesactivado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - (Constantes.botonActivo.getWidth() * 2 + 25),
                Constantes.alto - Constantes.botonActivo.getHeight() * 4,
                "ESPACIO",Color.WHITE,Color.RED, new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        atras = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.botonActivo.getHeight(),
                Constantes.alto - Constantes.botonActivo.getHeight() * 2,
                Constantes.Atras,Externos.cEncendido,Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p));
            }
        });
        comenzar = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 + Constantes.botonActivo.getWidth()*3,
                Constantes.alto - Constantes.botonActivo.getHeight() * 2,
                Constantes.Comenzar,Externos.cEncendido,Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {

                try {
                    Ventana.cambiarVentana(new VentanaSkins(p));
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(VentanaControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(VentanaControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        botones.add(comenzar);
        botones.add(arriba);
        botones.add(abajo);
        botones.add(izquierda);
        botones.add(derecha);
        botones.add(espacio);
        botones.add(atras);

    }

    @Override
    public void actualizar(float dt) {
        atras.actualizar();
        comenzar.actualizar();
        if (Teclado.arriba) {
            arriba.setRatonDentro(true);

        } else if (Teclado.abajo) {
            abajo.setRatonDentro(true);

        } else if (Teclado.derecha) {
            derecha.setRatonDentro(true);
            angulo += Constantes.anguloBase;
        } else if (Teclado.izquierda) {
            izquierda.setRatonDentro(true);
            angulo -= Constantes.anguloBase;
        } else if (Teclado.disparar) {
            espacio.setRatonDentro(true);
        } else {
            arriba.setRatonDentro(false);
            abajo.setRatonDentro(false);
            derecha.setRatonDentro(false);
            izquierda.setRatonDentro(false);
            espacio.setRatonDentro(false);
        }

        j.actualizar(dt);

        double anchuraRectanguloMinimo = Constantes.ancho / 2 + 100;
        double anchuraRectanguloMaximo = (Constantes.ancho / 2 + 100) + 200;

        double alturaRectanguloMinimo = Constantes.alto / 4 - 50;
        double alturaRectanguloMaximo = (Constantes.alto / 4 - 50) + 200;

        if (j.getPosicion().getX() < anchuraRectanguloMinimo) {

            posicion.setX(anchuraRectanguloMaximo - imagen.getWidth());
            posicion.setY(j.getPosicion().getY());
            j.setPosicion(posicion);

        } else if (j.getPosicion().getX() > anchuraRectanguloMaximo - imagen.getWidth()) {

            posicion.setX(anchuraRectanguloMinimo);
            posicion.setY(j.getPosicion().getY());
            j.setPosicion(posicion);

        } else if (j.getPosicion().getY() < alturaRectanguloMinimo) {

            posicion.setX(j.getPosicion().getX());
            posicion.setY(alturaRectanguloMaximo - imagen.getHeight());
            j.setPosicion(posicion);

        } else if (j.getPosicion().getY() > alturaRectanguloMaximo - imagen.getHeight()) {
            posicion.setX(j.getPosicion().getX());
            posicion.setY(alturaRectanguloMinimo);
            j.setPosicion(posicion);
        }
        /*     if (j.getDireccion().Manitud() >= this.maxVel) {
            Vectores velocidadInvertida = new Vectores(-getDireccion().getX(), -velocidad.getY());
            velocidad = velocidad.SumaVectores(velocidadInvertida.velocidadlimite(0.01f));

        }
        velocidad = velocidad.velocidadlimite(Constantes.Velocidad_lac);
        
        posicion = posicion.SumaVectores(velocidad);*/

    }

    @Override
    public void dibujar(Graphics g) {
         Graphics2D g2d = (Graphics2D) g;
          BufferedImage imagenEscalada = Externos.cambiarTamaño2(Externos.panelAncho, Constantes.ancho-50,  Constantes.alto-50); // Ancho: 200, Alto: 300
            AffineTransform at = AffineTransform.getTranslateInstance(
                    Constantes.ancho / 2-imagenEscalada.getWidth()/2,
                   0);
             g2d.drawImage(imagenEscalada, at, null);
        for (Boton b : botones) {
            b.dibujar(g);
        }

        g.setColor(Color.DARK_GRAY);
        g.drawRect(Constantes.ancho / 2 + 100, Constantes.alto / 4 - 50, 200, 200);
        g.fillRect(Constantes.ancho / 2 + 100, Constantes.alto / 4 - 50, 200, 200);
        j.dibujar(g);
        Texto.DibujarTexto(g,
                "Controles",
                new Vectores(Constantes.ancho / 2, 100),
                true,
                Color.yellow,
                Externos.Gfuente);
        Texto.DibujarTexto(g,
                "Flechas del Teclado",
                new Vectores(Constantes.ancho / 3 - arribaGris.getWidth() * 2+150,
                        Constantes.alto / 3+100 - arribaGris.getHeight()),
                false,
                Color.white,
                Externos.Mfuente);
          Texto.DibujarTexto(g,
                "Disparar",
                new Vectores(  Constantes.ancho / 2 - (Externos.bGris.getWidth() * 2+25 )+300,
                Constantes.alto - Externos.bGris.getHeight() * 5+100),
                false,
                Color.white,
                Externos.Mfuente);

        
        /*   AffineTransform   at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
        //punto de rotacion : optenemos el ancho y lo dividimos entre 2        
        at.rotate(angulo, imagen.getWidth() / 2, imagen.getHeight() / 2);
        g2d.drawImage(imagen, at, null);*/
    }

}
