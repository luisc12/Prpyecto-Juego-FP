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
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author luis
 */
public class VentanaSkins extends Ventana {

    ArrayList<Boton> botones;
    private int mejorpuntaje;
    private int index = 0;
    private int indice=0;
private Naves[] aux;
    public VentanaSkins(String nombre) throws ParserConfigurationException, SAXException, IOException {
        botones = new ArrayList<Boton>();
        botones.add(
                new Boton(Externos.bGris,
                        Externos.bVerde,
                        Externos.bGris.getHeight() * 2,
                        Constantes.alto - Externos.bVerde.getHeight() * 2,
                        Constantes.Atras, new Accion() {
                    @Override
                    public void hacerAccion() {
                        Ventana.cambiarVentana(new VentanaMenu());
                    }
                }));

        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho + 20 - Externos.bGris.getWidth() * 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Comenzar, new Accion() {
            @Override
            public void hacerAccion() {
                //Ventana.cambiarVentana(new VentanaPartida(nombre));
            }
        }));

        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho + 20 - Externos.bGris.getWidth() * 2,
                Constantes.alto / 2 - Externos.bVerde.getHeight(),
                "->", new Accion() {
            @Override
            public void hacerAccion() {
                if (indice<=index) {
                   indice+=1; 
                }else{
                    indice=0;
                }
                
            }
        }));
        botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Externos.bGris.getHeight() * 2,
                Constantes.alto / 2 - Externos.bVerde.getHeight(),
                "<-", new Accion() {
            @Override
            public void hacerAccion() {
                  if (indice>1) {
                   indice-=1; 
                }else   {
                      indice=index;
                  }
            }
        }));

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
            System.out.println(naves[j].name()+" "+naves[j].puntaje);
        }
        index=i;
        System.out.println("index:" +index);
        aux = new Naves[index];
         for (i = 0; i <= naves.length; i++) {
             if (i<index) {
                  aux[i]=naves[i];
             System.out.println("entro");
             }
             
        }
          System.out.println("i: "+i);
         for (int j = 0; j <aux.length; j++) {
             System.out.println(aux[j].name()+" "+aux[j].puntaje);
        }
        
    }

    @Override
    public void actualizar(float dt) {
        for (Boton b : botones) {
            b.actualizar();
        }

    }

    @Override
    public void dibujar(Graphics g) {
        for (Boton b : botones) {
            b.dibujar(g);
        }
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getTranslateInstance(Ventanas.VentanaPartida.PosicionInicial.getX(),
                Ventanas.VentanaPartida.PosicionInicial.getX());
        at.rotate(0, aux[indice].textura.getWidth() / 2, aux[indice].textura.getWidth()/ 2);
g2d.drawImage(aux[indice].textura, at, null);
    }

}
