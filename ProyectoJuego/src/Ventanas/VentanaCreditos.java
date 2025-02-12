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
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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
private long tiempoAnterior;

    public VentanaCreditos(ProyectoJuego p) throws ParserConfigurationException, SAXException, IOException {
        super(p);
          tiempoAnterior = System.nanoTime();
        
        posicion = 0;
        
        botones = new ArrayList<Boton>();
        numPagina = 1;
        tamPagina = 5;
        totalPaginas=0;
        cambio=0;

        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonApagado,
                Externos.bGris.getHeight(),
                Constantes.alto - Constantes.botonApagado.getHeight() * 2,
                Constantes.Atras,Externos.cEncendido,Externos.cApagado, new Accion() {
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
        for (Boton b : botones) {
            b.actualizar();
        }
        totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
        System.out.println("dt: " + dt + ", cambio: " + cambio);
         cambio+=dt;
          long ahora = System.nanoTime();
    long diferencia = (ahora - tiempoAnterior) / 1_000_000; // Convertir a milisegundos

    if (diferencia > Constantes.TCambioPag/2) {
        if (numPagina < totalPaginas) {
            numPagina++;
            repaint();
            System.out.println("Página actual: " + numPagina);
        }
        tiempoAnterior = ahora; // Reiniciar el tiempo de referencia
    }
}
       

    @Override
    public void dibujar(Graphics g) {
          Graphics2D g2d = (Graphics2D) g;
          BufferedImage imagenEscalada = Externos.cambiarTamaño2(Externos.panelAncho, Constantes.ancho,  Constantes.alto); // Ancho: 200, Alto: 300
            AffineTransform at = AffineTransform.getTranslateInstance(
                    Constantes.ancho / 2-imagenEscalada.getWidth()/2,
                   0);
             g2d.drawImage(imagenEscalada, at, null);
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
                   "|"+ c.getObjeto(),
                    new Vectores(Constantes.ancho / 2 - 680, linea),
                    false,
                    Color.WHITE,
                    Externos.creditos);

            Texto.DibujarTexto(g,
                    "|"+c.getCreador(),
                    new Vectores(Constantes.ancho / 2 - 340, linea),
                    false,
                    Color.ORANGE,
                    Externos.creditos);

            Texto.DibujarTexto(g,
                    "|"+c.getLicencia(),
                    new Vectores(Constantes.ancho / 2 + 50, linea),
                    false,
                    Color.GREEN,
                    Externos.creditos);
            Texto.DibujarTexto(g,
                    "|"+c.getModificacion(),
                    new Vectores(Constantes.ancho / 2 + 240, linea),
                    false,
                    Color.blue,
                    Externos.creditos);

           
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
