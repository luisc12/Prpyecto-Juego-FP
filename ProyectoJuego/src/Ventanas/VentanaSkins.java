/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import EntradaSalida.DatosPuntaje;
import EntradaSalida.XMLParser;
import Graficos.Externos;
import Graficos.Naves;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author luis
 */
public class VentanaSkins extends Ventana {

    public BufferedImage apariencia;
    ArrayList<Boton> botones;
    private int mejorpuntaje;
    private int index = 0;
    private int indice = 0;
    private Naves[] aux;
    private boolean activar;
    long permitir;

    public VentanaSkins(String nombre) throws ParserConfigurationException, SAXException, IOException {
        botones = new ArrayList<Boton>();

        Boton atras = new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 - Externos.bGris.getWidth() * 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu());
            }
        });
        botones.add(atras);

        Boton comenzar = new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 + Externos.bGris.getWidth(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Comenzar, new Accion() {
            @Override
            public void hacerAccion() {

                Ventana.cambiarVentana(new VentanaPartida(nombre, aux[indice].textura));
            }
        });
        botones.add(comenzar);

        BufferedImage flechaderechaV = Externos.cambiarTamaño(Externos.flechaVerdeD, 150);
        BufferedImage flechaderechaG = Externos.cambiarTamaño(Externos.flechaGrisD, 150);
        BufferedImage flechaizquierdaV = Externos.cambiarTamaño(Externos.flechaVerdeI, 150);
        BufferedImage flechaizquierdaG = Externos.cambiarTamaño(Externos.flechaGrisI, 150);

        Boton derecha = new Boton(flechaderechaG,
                flechaderechaV,
                Constantes.ancho - flechaderechaG.getWidth() * 2,
                Constantes.alto / 2 - flechaderechaG.getHeight()/2,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                if (indice + 1 < index) {

                    indice += 1;
                } else {
                    indice = 0;
                }

            }
        });

        botones.add(derecha);

        Boton izquierda = new Boton(flechaizquierdaG,
                flechaizquierdaV,
                Externos.flechaGrisI.getWidth() * 2,
                Constantes.alto / 2 - flechaizquierdaG.getHeight()/2,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                if (indice > 1) {
                    indice -= 1;
                } else {
                    indice = index - 1;
                }
            }
        });
        botones.add(izquierda);
        ArrayList<DatosPuntaje> listaDatos = XMLParser.LeerFichero();
        for (DatosPuntaje l : listaDatos) {
            if (l.getPuntaje() > mejorpuntaje) {
                mejorpuntaje = l.getPuntaje();
            }
        }
        System.out.println(mejorpuntaje);
        Naves[] naves = Naves.values();

        int i = 0;
        for (int j = 0; j < naves.length; j++) {
            if (mejorpuntaje >= naves[j].puntaje) {
                i++;
            }
            System.out.println(naves[j].name() + " " + naves[j].puntaje);
        }
        index = i;
        System.out.println("index:" + index);
        aux = new Naves[index];
        for (i = 0; i <= naves.length; i++) {
            if (i < index) {
                aux[i] = naves[i];
                System.out.println(i);
            }

        }
        System.out.println("i: " + i);
        for (int j = 0; j < aux.length; j++) {
            System.out.println(aux[j].name() + " " + aux[j].puntaje);
        }

    }

    @Override
    public void actualizar(float dt) {

        permitir += dt;
        if (permitir > 100) {
            for (Boton b : botones) {

                b.actualizar();
            }
            permitir = 0;
        }

    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
        Graphics2D g2d = (Graphics2D) g;

        Texto.DibujarTexto(g,
                "Seleccione la Skin",
                new Vectores(Constantes.ancho / 2 - aux[indice].nombre.length(), 100),
                true,
                Color.YELLOW,
                Externos.Gfuente);

        Texto.DibujarTexto(g,
                aux[indice].nombre,
                new Vectores(Constantes.ancho / 2 - aux[indice].nombre.length(), Constantes.alto / 1.2),
                true,
                Color.MAGENTA,
                Externos.Gfuente);

        BufferedImage muestra = Externos.cambiarTamaño(aux[indice].textura, 200);
        Vectores PosicionInicial
                = new Vectores(Constantes.ancho / 2 - muestra.getWidth() / 2,
                        Constantes.alto / 2 - muestra.getHeight() / 2);

        AffineTransform at = AffineTransform.getTranslateInstance(PosicionInicial.getX(),
                PosicionInicial.getY());

        /*    public static final Vectores PosicionInicial
            = new Vectores(Constantes.ancho / 2 - Externos.jugador.getWidth() / 2,
                    Constantes.alto / 2 - Externos.jugador.getHeight() / 2);*/
        at.rotate(0, muestra.getWidth() / 2, muestra.getWidth() / 2);
        g2d.drawImage(muestra, at, null);
    }

}
