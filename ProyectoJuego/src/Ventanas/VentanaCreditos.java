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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    int posicion;

    public VentanaCreditos(ProyectoJuego p) throws ParserConfigurationException, SAXException, IOException {
        super(p);
        posicion = 0;
        System.out.println(" dentro");
        botones = new ArrayList<>();
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
                int totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
                if (numPagina < totalPaginas && numPagina > 1) {
                    numPagina--;
                    try {
                        Ventana.cambiarVentana(new VentanaCreditos(p));
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Página actual: " + numPagina);
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
                    numPagina++;
                    System.out.println("Página actual: " + numPagina);
                    try {
                        Ventana.cambiarVentana(new VentanaCreditos(p));
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaCreditos.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
           repaint();
    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
        int linea = 100; // Posición vertical inicial
        String tema = ""; // Para comparar si es el mismo tema
        /*  Vectores posTema= new Vectores(Constantes.ancho / 2, 100);
          Vectores posObjeto = new Vectores(Constantes.ancho / 2 - 600, 200);
          Vectores posCreador= new Vectores(Constantes.ancho / 2- 200, 200);
          Vectores posLicencia= new Vectores(Constantes.ancho / 2+400, 200);
        Vectores posModificacion = new Vectores(Constantes.ancho / 2 + 600, 200);*/
        
        int totalPaginas = (int) Math.ceil((double) listaDatos.size() / tamPagina);
        if (numPagina > totalPaginas) {
            numPagina = totalPaginas;
        }
        if (numPagina < 1) {
            numPagina = 1;
        }
        System.out.println("Mostrando página: " + numPagina + " de " + totalPaginas);
        List<Creditos> credito = paginaList(listaDatos, numPagina, tamPagina);
        System.out.println("Elementos en esta página: " + credito.size());

        // System.out.println( "Página " + pageNumber + ": " + paginatedResult);
     /*   for (Creditos c : credito) {

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
                    new Vectores(Constantes.ancho / 2 - 600, linea),
                    false,
                    Color.WHITE,
                    Externos.Pixeloid);

            Texto.DibujarTexto(g,
                    c.getCreador(),
                    new Vectores(Constantes.ancho / 2 - 200, linea),
                    false,
                    Color.ORANGE,
                    Externos.Pixeloid);

            Texto.DibujarTexto(g,
                    c.getLicencia(),
                    new Vectores(Constantes.ancho / 2 + 100, linea),
                    false,
                    Color.GREEN,
                    Externos.Pixeloid);
            Texto.DibujarTexto(g,
                    c.getModificacion(),
                    new Vectores(Constantes.ancho / 2 + 400, linea),
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
     linea = 100;
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
}
    }

    public static List<Creditos> paginaList(ArrayList<Creditos> listaDatos, int numPagina, int tamPagina) {
        int desplazamiento = (numPagina - 1) * tamPagina; //ùnto de partida

        if (desplazamiento >= listaDatos.size() || desplazamiento < 0) {
            return Collections.emptyList(); // Devuelve una lista vacía si el número de página está fuera del rango
        }
        int piePagina = Math.min(desplazamiento + tamPagina, listaDatos.size()); //punto final del sub conjunto de datos

        return listaDatos.subList(desplazamiento, piePagina); //se utiliza para extraer los elementos entre los índices calculados.
    }
}
