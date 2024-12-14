/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.Teclado;
import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Jugador;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class VentanaControl extends Ventana {

    Jugador jugador;
    BufferedImage imagen;
    double angulo;
    ArrayList<Boton> botones;
    Boton arriba,abajo,derecha,izquierda,espacio;
    boolean existe;

    public VentanaControl() {
        botones = new ArrayList<Boton>();

        angulo = 0;
        int tamaño = 100;
        //------------fecha derecha-------
        BufferedImage derechaGris = Externos.cambiarTamaño(Externos.flechaGrisD, tamaño);

        BufferedImage derechaVerde = Externos.cambiarTamaño(Externos.flechaVerdeD, tamaño);
        
        //------------flecha izquierda------
        BufferedImage izquierdaGris = Externos.cambiarTamaño(Externos.flechaGrisI, tamaño);

        BufferedImage izquierdaVerde = Externos.cambiarTamaño(Externos.flechaVerdeI, tamaño);
        
        //------------flecha Arriba----------
          BufferedImage arribaGris = Externos.cambiarTamaño(Externos.flechaGrisA, tamaño);

        BufferedImage arribaVerde = Externos.cambiarTamaño(Externos.flechaVerdeA, tamaño);
        
        //------------flecha Arriba----------
          BufferedImage abajoGris = Externos.cambiarTamaño(Externos.flechaGrisB, tamaño);

        BufferedImage abajoVerde = Externos.cambiarTamaño(Externos.flechaVerdeB, tamaño);
        
        

        arriba=new Boton(arribaGris,
                arribaVerde,
                Constantes.ancho / 3 - arribaGris.getWidth()*2,
                Constantes.alto / 3 - arribaGris.getHeight() ,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        abajo=new Boton(abajoGris,
                abajoVerde,
                Constantes.ancho / 3 - arribaGris.getWidth()*2,
                Constantes.alto / 3  ,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        izquierda=new Boton(izquierdaGris,
                izquierdaVerde,
                Constantes.ancho / 3 - izquierdaGris.getWidth()*3,
                Constantes.alto / 2 - izquierdaGris.getHeight() ,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        derecha=new Boton(derechaGris,
                derechaVerde,
                Constantes.ancho / 3 - derechaVerde.getWidth(),
                Constantes.alto / 2 - derechaVerde.getHeight() ,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        espacio=new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho / 2 - (Externos.bGris.getWidth()*2+25),
                Constantes.alto  - Externos.bGris.getHeight() *4,
                "ESPACIO", new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("ver");
            }
        });
        
        
        botones.add(arriba);
        botones.add(abajo);
        botones.add(izquierda);
        botones.add(derecha);
        botones.add(espacio);
      

    }

    @Override
    public void actualizar(float dt) {
        if (Teclado.arriba) {
            arriba.setRatonDentro(true);
            
        }else if (Teclado.abajo) {
            abajo.setRatonDentro(true);
            
        }else if (Teclado.derecha) {
            derecha.setRatonDentro(true);
            angulo += Constantes.anguloBase;
        }else if (Teclado.izquierda) {
            izquierda.setRatonDentro(true);
             angulo -= Constantes.anguloBase;
        } else if (Teclado.disparar) {
            espacio.setRatonDentro(true);
        }
          else{
            arriba.setRatonDentro(false);
            abajo.setRatonDentro(false);
            derecha.setRatonDentro(false);
            izquierda.setRatonDentro(false);
            espacio.setRatonDentro(false);
        }
        
        
        
    }

    @Override
    public void dibujar(Graphics g) {
         for (Boton b : botones) {
            b.dibujar(g);
        }
         Texto.DibujarTexto(g,
                "Controles",
                new Vectores(Constantes.ancho / 2, 100),
                true,
                Color.yellow,
                Externos.Gfuente);
         
    }

}
