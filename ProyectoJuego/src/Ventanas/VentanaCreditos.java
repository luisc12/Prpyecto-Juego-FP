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
      BufferedImage imagenEscalada;

    public VentanaCreditos(ProyectoJuego p) throws ParserConfigurationException,
            SAXException, IOException {
        super(p);
         imagenEscalada  = Externos.cambiarTamaño(
                Externos.panelAncho, Constantes.ancho, Constantes.alto);
        tiempoAnterior = System.nanoTime();

        posicion = 0;

        botones = new ArrayList<Boton>();
        numPagina = 1;
        tamPagina = 5;
        totalPaginas = 0;
        cambio = 0;

        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Externos.bGris.getHeight(),
                Constantes.alto - Constantes.botonApagado.getHeight() * 2,
                Constantes.Atras, Externos.cEncendido, Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p));
            }
        }));

        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - Constantes.botonApagado.getWidth(),
                Constantes.alto - Constantes.botonApagado.getHeight() * 2,
                "<-", Externos.cEncendido, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                if (cambio > 10000) {
                    if (numPagina > 1) {
                        // Reducimos la página sin crear una nueva ventana
                        numPagina--;
                        // Redibujamos la ventana
                        repaint();

                    }
                    cambio = 0;
                }
            }
        }));

        botones.add(new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2,
                Constantes.alto - Constantes.botonApagado.getHeight() * 2,
                "->", Externos.cEncendido, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                if (cambio > 10000) {
                    int totalPaginas = (int) Math.ceil((double) listaDatos.size()
                            / tamPagina);
                    if (numPagina < totalPaginas) {
                        // Aumentamos la página sin crear una nueva ventana
                        numPagina++;
                        repaint();
                    }
                    cambio = 0;
                }
            }
        }));
        listaDatos = XMLParserCreditos.LeerFichero();
    }

    @Override
    public void actualizar(float dt) {
        cambio += dt;

        for (Boton b : botones) {
            b.actualizar();
        }

        //Math.ceil redondea el numero hacia el entero mayor mas cercano
        totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
        //usamos System.nanoTime(); para saber el tiempo actual
        long ahora = System.nanoTime();
        /*sacamos la diferencia entre el tiempo anterior y el tiempo actual y lo
        dividimos entre 1.000.000 para pasarlo a milisegundos*/
        // Convertir a milisegundos
        long diferencia = (ahora - tiempoAnterior) / 1000000;

        /*si la diferencia es mayor que nuestra coostante y siempre que que el
        numero de total de paginas sea mayor al numero de la pagina actual se
        cambiara a la pagina siguiente, de no ser asi vuelve a la primera 
        pagina*/
 /*nos aseguramos de que alla pasado el tiempo necsario para cambiar de
        pagina*/
        if (diferencia > Constantes.TCambioPag) {
            if (numPagina < totalPaginas) {
                numPagina++;
                repaint();
            } else {
                numPagina = 0;
                repaint();
            }
            tiempoAnterior = ahora; // Reiniciar el tiempo de referencia

        }
    }

    @Override
    public void dibujar(Graphics g) {  
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at = AffineTransform.getTranslateInstance(
                Constantes.ancho / 2 - imagenEscalada.getWidth() / 2,
                -20);
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
//mostramos los creditos en la pantalla
        List<Creditos> credito = paginaList(listaDatos, numPagina, tamPagina);
        linea = 90;
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
                    "|" + c.getObjeto(),
                    new Vectores(Constantes.ancho / 2 - 680, linea),
                    false,
                    Color.WHITE,
                    Externos.creditos);
            Texto.DibujarTexto(g,
                    "|" + c.getCreador(),
                    new Vectores(Constantes.ancho / 2 - 340, linea),
                    false,
                    Color.ORANGE,
                    Externos.creditos);
            Texto.DibujarTexto(g,
                    "|" + c.getLicencia(),
                    new Vectores(Constantes.ancho / 2 + 50, linea),
                    false,
                    Color.GREEN,
                    Externos.creditos);
            Texto.DibujarTexto(g,
                    "|" + c.getModificacion(),
                    new Vectores(Constantes.ancho / 2 + 240, linea),
                    false,
                    Color.blue,
                    Externos.creditos);

            linea += 120;
        }
    }

    public static List<Creditos> paginaList(ArrayList<Creditos> listaDatos,
            int numPagina, int tamPagina) {
        /* el desplazamiento indica dónde comenzar a obtener datos. Se calcula 
        en función de la cantidad de elementos que hay en las páginas 
        anteriores.*/
        int desplazamiento = (numPagina - 1) * tamPagina;

        // Asegurar que no haya un desplazamiento fuera del rango
        if (desplazamiento >= listaDatos.size()) {
        // Devuelve una lista vacía si el número de página está fuera del rango
            return Collections.emptyList();
        }
        /*Math.min() es un metodo que devuelve un numero con el valor mas bajo, 
        el pie de pagina es el punto final del subconjunto de datos,
        asegurándose de que no exceda el tamaño total de la lista.*/
        
        int piePagina = Math.min(desplazamiento + tamPagina, listaDatos.size());
        //devuelve la parte de la lista que corresponde a la página.
        return listaDatos.subList(desplazamiento, piePagina);
    }

}
