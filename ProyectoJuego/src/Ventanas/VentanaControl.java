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
    BufferedImage derechaGris, derechaVerde, izquierdaGris, izquierdaVerde,
            arribaGris, arribaVerde, abajoGris, abajoVerde;
    double angulo;
    ArrayList<Boton> botones;
    Boton arriba, abajo, derecha, izquierda, espacio, escape, atras, comenzar;
    boolean existe;
    Vectores posicion;
    private ArrayList<ObjetosMovibles> objetosmoviles
            = new ArrayList<ObjetosMovibles>();
    Jugador j;

    public VentanaControl(ProyectoJuego p) {
        super(p);
        botones = new ArrayList<Boton>();

        imagen = Externos.jugadores[0];

        angulo = 0;

        posicion = new Vectores((Constantes.ancho / 2 + 100) + 100,
                (Constantes.alto / 4 - 50) + 100);

        //-----------Jugador
        j = new Jugador(imagen, posicion, new Vectores(0, 0), 7, this);
        objetosmoviles.add(j);
        //-------------------
        int tamaño = 100;
        //------------fecha derecha-------
        derechaGris = Externos.cambiarTamaño(Externos.flechaGrisD,
                tamaño, tamaño);

        derechaVerde = Externos.cambiarTamaño(Externos.flechaVerdeD,
                tamaño, tamaño);

        //------------flecha izquierda------
        izquierdaGris = Externos.cambiarTamaño(Externos.flechaGrisI,
                tamaño, tamaño);

        izquierdaVerde = Externos.cambiarTamaño(Externos.flechaVerdeI,
                tamaño, tamaño);

        //------------flecha Arriba----------
        arribaGris = Externos.cambiarTamaño(Externos.flechaGrisA,
                tamaño, tamaño);

        arribaVerde = Externos.cambiarTamaño(Externos.flechaVerdeA,
                tamaño, tamaño);

        //------------flecha Abajo----------
        abajoGris = Externos.cambiarTamaño(Externos.flechaGrisB,
                tamaño, tamaño);

        abajoVerde = Externos.cambiarTamaño(Externos.flechaVerdeB,
                tamaño, tamaño);

        arriba = new Boton(arribaGris,
                arribaVerde,
                Constantes.ancho / 3 - arribaGris.getWidth() * 2,
                Constantes.alto / 3 - arribaGris.getHeight(),
                "", Externos.cApagado, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });
        abajo = new Boton(abajoGris,
                abajoVerde,
                Constantes.ancho / 3 - arribaGris.getWidth() * 2,
                Constantes.alto / 3,
                "", Externos.cApagado, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });
        izquierda = new Boton(izquierdaGris,
                izquierdaVerde,
                Constantes.ancho / 3 - izquierdaGris.getWidth() * 3,
                Constantes.alto / 3,
                "", Externos.cApagado, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });
        derecha = new Boton(derechaGris,
                derechaVerde,
                Constantes.ancho / 3 - derechaVerde.getWidth(),
                Constantes.alto / 3,
                "", Externos.cApagado, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });

        BufferedImage botonDesactivado = Externos.cambiarTamaño(
                Externos.bDesactivado, 192, 64);

        espacio = new Boton(botonDesactivado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - (Constantes.botonActivo.getWidth() * 3),
                Constantes.alto - Constantes.botonActivo.getHeight() * 6,
                "ESPACIO", Color.WHITE, Color.RED, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });
        BufferedImage botonZDes = Externos.cambiarTamaño(
                Externos.bInactivo, 64, 64);
        BufferedImage botonZAct = Externos.cambiarTamaño(
                Externos.bActivo, 64, 64);

        escape = new Boton(botonZDes,
                botonZAct,
                Constantes.ancho / 2 - (Constantes.botonActivo.getWidth()),
                Constantes.alto - Constantes.botonActivo.getHeight() * 6,
                "Z", Color.WHITE, Color.RED, Externos.GPixeloid, new Accion() {
            @Override
            public void hacerAccion() {
            }
        });
        atras = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.botonActivo.getHeight(),
                Constantes.alto - Constantes.botonActivo.getHeight() * 2,
                Constantes.Atras, Externos.cEncendido,
                Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p));
            }
        });

        comenzar = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 + Constantes.botonActivo.getWidth() * 2,
                Constantes.alto - Constantes.botonActivo.getHeight() * 2,
                Constantes.Comenzar, Externos.cEncendido, Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {

                try {
                    Ventana.cambiarVentana(new VentanaSkins(p));
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(VentanaControl.class.getName())
                            .log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(VentanaControl.class.getName())
                            .log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaControl.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        botones.add(comenzar);
        botones.add(arriba);
        botones.add(abajo);
        botones.add(izquierda);
        botones.add(derecha);
        botones.add(espacio);
        botones.add(escape);
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
        } else if (Teclado.izquierda) {
            izquierda.setRatonDentro(true);
        } /*else if (Teclado.disparar) {
            espacio.setRatonDentro(true);
        }*/ else if (Teclado.salir) {
            escape.setRatonDentro(true);
        } else {
            arriba.setRatonDentro(false);
            abajo.setRatonDentro(false);
            derecha.setRatonDentro(false);
            izquierda.setRatonDentro(false);
            espacio.setRatonDentro(false);
            escape.setRatonDentro(false);
        }

        //j.actualizar(dt);
        for (int i = 0; i < objetosmoviles.size(); i++) {
            ObjetosMovibles ob = objetosmoviles.get(i);
            ob.actualizar(dt);
            //si esta muerto lo borra y se le resta a la i debido a que al borrar
            //un objeto todos los de su derecha avansan un paso a la izquierda
            //y el ultimo puesto de la derecha a hora basio lo elimina
            if (ob.isMuerte()) {
                objetosmoviles.remove(i);
                i--;
            }

        }

    }

    public void limiteControl(ObjetosMovibles o) {
        int tamaño = 400;
        double anchuraRectanguloMinimo = Constantes.ancho / 2 + 100;
        double anchuraRectanguloMaximo = (Constantes.ancho / 2 + 100) + tamaño;

        double alturaRectanguloMinimo = Constantes.alto / 4 - 50;
        double alturaRectanguloMaximo = (Constantes.alto / 4 - 50) + tamaño;
        if (o instanceof Jugador) {
            if (o.getPosicion().getX() < anchuraRectanguloMinimo) {

                posicion.setX(anchuraRectanguloMaximo - imagen.getWidth());
                posicion.setY(o.getPosicion().getY());
                o.setPosicion(posicion);

            } else if (o.getPosicion().getX() > anchuraRectanguloMaximo
                    - imagen.getWidth()) {

                posicion.setX(anchuraRectanguloMinimo);
                posicion.setY(o.getPosicion().getY());
                o.setPosicion(posicion);

            } else if (o.getPosicion().getY() < alturaRectanguloMinimo) {

                posicion.setX(o.getPosicion().getX());
                posicion.setY(alturaRectanguloMaximo - imagen.getHeight());
                o.setPosicion(posicion);

            } else if (o.getPosicion().getY() > alturaRectanguloMaximo
                    - imagen.getHeight()) {
                posicion.setX(o.getPosicion().getX());
                posicion.setY(alturaRectanguloMinimo);
                o.setPosicion(posicion);
            }
        } else {
            if (o.getPosicion().getX() < anchuraRectanguloMinimo) {

              o.isMuerte();

            } else if (j.getPosicion().getX() > anchuraRectanguloMaximo
                    - imagen.getWidth()) {

               o.isMuerte();

            } else if (j.getPosicion().getY() < alturaRectanguloMinimo) {

                o.isMuerte();

            } else if (j.getPosicion().getY() > alturaRectanguloMaximo
                    - imagen.getHeight()) {
               o.isMuerte();
            }
        }

    }

    public ArrayList<ObjetosMovibles> getObjetosmoviles() {
        return objetosmoviles;
    }
    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage imagenEscalada = Externos.cambiarTamaño(Externos.panelAncho,
                Constantes.ancho - 50, Constantes.alto - 50);
        AffineTransform at = AffineTransform.getTranslateInstance(
                Constantes.ancho / 2 - imagenEscalada.getWidth() / 2,
                0);
        g2d.drawImage(imagenEscalada, at, null);
        for (Boton b : botones) {
            b.dibujar(g);
        }
      
        int tamaño = 400;
        g.setColor(Color.BLACK);
        g.drawRect(Constantes.ancho / 2 + 100, Constantes.alto / 4 - 50, tamaño,
                tamaño);
        g.fillRect(Constantes.ancho / 2 + 100, Constantes.alto / 4 - 50, tamaño,
                tamaño);
       
        //  j.dibujar(g);
       for (int i = 0; i < objetosmoviles.size(); i++) {
            objetosmoviles.get(i).dibujar(g);
        }
        Texto.DibujarTexto(g,
                "Controles",
                new Vectores(Constantes.ancho / 2, 100),
                true,
                Externos.cEncendido,
                Externos.Gfuente);
        Texto.DibujarTexto(g,
                "Flechas del Teclado",
                new Vectores(Constantes.ancho / 3 - (arribaGris.getWidth() + 50),
                        Constantes.alto / 3 + arribaGris.getWidth() + 40),
                true,
                Color.white,
                Externos.Pixeloid);
        Texto.DibujarTexto(g,
                "Movimiento",
                new Vectores(Constantes.ancho / 3 - (arribaGris.getWidth() + 50),
                        Constantes.ancho / 3 - (arribaGris.getWidth() * 3)),
                true,
                Color.white,
                Externos.Mfuente);
        Texto.DibujarTexto(g,
                "Disparar",
                new Vectores(Constantes.ancho / 2 - (Constantes.botonActivo.
                        getWidth() * 3 - 100),
                        Constantes.alto - (Externos.bGris.getHeight() * 6)),
                true,
                Externos.cEncendido,
                Externos.Mfuente);
        Texto.DibujarTexto(g,
                "Salir",
                new Vectores(Constantes.ancho / 2 - (Constantes.botonActivo.
                        getWidth()),
                        Constantes.alto - (Externos.bGris.getHeight() * 6)),
                false,
                Externos.cEncendido,
                Externos.Mfuente);

    }

}
