/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import EntradaSalida.Creditos;
import EntradaSalida.XMLParserCreditos;
import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import proyectojuego.ProyectoJuego;
import static Ventanas.VentanaCreditos.paginaList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis
 */
public class VentanaCreditos extends Ventana {

    ArrayList<Boton> botones;
    ArrayList<Creditos> listaDatos;
    int numPagina;
    int tamPagina;
    int totalPaginas;
    int posicion;
    long cambio;

    public VentanaCreditos(ProyectoJuego p) throws ParserConfigurationException, SAXException, IOException {
        super(p);
        posicion = 0;
        
        botones = new ArrayList<Boton>();
        numPagina = 1;
        tamPagina = 5;
        totalPaginas=0;
        cambio=0;

        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Externos.bGris.getHeight(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p));
            }
        }));

        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 - Externos.bGris.getWidth() * 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                "anterior", new Accion() {
            @Override
            public void hacerAccion() {
                if (numPagina > 1) {
                    numPagina--; // Reducimos la página sin crear una nueva ventana
                    repaint(); // Redibujamos la pantalla
                    System.out.println("Página actual: " + numPagina);
                    cambio=0;
                }

            }
        }));
        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 - Externos.bGris.getWidth() / 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                "Siguiente", new Accion() {
            @Override
            public void hacerAccion() {
                int totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
                if (numPagina < totalPaginas) {
                    numPagina++; // Aumentamos la página sin crear una nueva ventana
                    repaint(); // Redibujamos la pantalla
                    System.out.println("Página actual: " + numPagina);
                    cambio=0;
                }
            }
        }));

        System.out.println(" ver");
        listaDatos = XMLParserCreditos.LeerFichero();
        for (int i = 0; i < listaDatos.size(); i++) {
            System.out.println(listaDatos.get(i).toString());
        }
        System.out.println("salir");
    }

    @Override
    public void actualizar(float dt) {
        totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
         cambio+=dt;
        for (Boton b : botones) {
            b.actualizar();
        }
        if (cambio>Constantes.TCambioPag) {
             if (numPagina < totalPaginas) {
                    numPagina++; // Aumentamos la página sin crear una nueva ventana
                    repaint(); // Redibujamos la pantalla
                    System.out.println("Página actual: " + numPagina);
                    cambio=0;
                }
        }
        repaint();
    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
        int linea = 100; // Posición vertical inicial
        String tema = ""; // Para comparar si es el mismo tema

        

        // Asegurar que numPagina no sea mayor que totalPaginas ni menor que 1
        if (numPagina > totalPaginas) {
            numPagina = totalPaginas;
        }
        if (numPagina < 1) {
            numPagina = 1;
        }
        System.out.println("Mostrando página: " + numPagina + " de " + totalPaginas);

        List<Creditos> credito = paginaList(listaDatos, numPagina, tamPagina);
        System.out.println("Elementos en esta página: " + credito.size());
        linea = 90;
        // System.out.println( "Página " + pageNumber + ": " + paginatedResult);
        for (Creditos c : credito) {

            if (!tema.equalsIgnoreCase(c.getTema())) {

                Texto.DibujarTexto(g,
                        c.getTema(),
                        new Vectores(Constantes.ancho / 2, linea),
                        true,
                        Color.yellow,
                        Externos.Gfuente);
                tema = c.getTema();
                linea += 50;
            }

            Texto.DibujarTexto(g,
                    c.getObjeto(),
                    new Vectores(Constantes.ancho / 2 - 750, linea),
                    false,
                    Color.WHITE,
                    Externos.Pixeloid);

            Texto.DibujarTexto(g,
                    c.getCreador(),
                    new Vectores(Constantes.ancho / 2 - 400, linea),
                    false,
                    Color.ORANGE,
                    Externos.Pixeloid);

            Texto.DibujarTexto(g,
                    c.getLicencia(),
                    new Vectores(Constantes.ancho / 2 + 50, linea),
                    false,
                    Color.GREEN,
                    Externos.Pixeloid);
            Texto.DibujarTexto(g,
                    c.getModificacion(),
                    new Vectores(Constantes.ancho / 2 + 240, linea),
                    false,
                    Color.blue,
                    Externos.Pixeloid);

            /*  posTema.setY(posTema.getY() + posicion);
            posObjeto.setY(posObjeto.getY() + 40);
            posCreador.setY(posCreador.getY() + 40);
            posLicencia.setY(posLicencia.getY() + 40);
            posModificacion.setY(posModificacion.getY() + 40);
            posicion+=40;
         //   linea += 40;
        }*/
 /*
        for (Creditos c : credito) {
            g.setColor(Color.YELLOW);
            g.drawString("Tema: " + c.getTema(), 50, linea);
            g.setColor(Color.WHITE);
            g.drawString("Objeto: " + c.getObjeto(), 50, linea + 20);
            g.setColor(Color.ORANGE);
            g.drawString("Creador: " + c.getCreador(), 50, linea + 40);
            g.setColor(Color.GREEN);
            g.drawString("Licencia: " + c.getLicencia(), 50, linea + 60);
            g.setColor(Color.BLUE);
            g.drawString("Modificación: " + c.getModificacion(), 50, linea + 80);

            linea += 120; // Espaciado entre créditos
        }*/
            linea += 120;
        }
    }

    public static List<Creditos> paginaList(ArrayList<Creditos> listaDatos, int numPagina, int tamPagina) {
        int desplazamiento = (numPagina - 1) * tamPagina; //ùnto de partida

        // Asegurar que no haya un desplazamiento fuera del rango
        if (desplazamiento >= listaDatos.size()) {
            return Collections.emptyList();
        }

        int piePagina = Math.min(desplazamiento + tamPagina, listaDatos.size());
        return listaDatos.subList(desplazamiento, piePagina);
    }

}
