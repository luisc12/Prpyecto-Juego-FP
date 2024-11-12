/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Entrada.RatonEntrada;
import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Boton {

    private BufferedImage ratonfuera;
    private BufferedImage ratonSobre;
    private boolean ratonDentro;
    //para detectar la colision del boton con el raton usamos un onjeto de Tipo
    //Rectangulo
    private Rectangle cuadroDelimitador;
    private String texto;
    private Accion accion;
    int valor;

    public Boton(BufferedImage ratonfuera, BufferedImage ratonSobre,
            int x, int y, String texto, Accion accion) {
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        cuadroDelimitador = new Rectangle(x, y, ratonSobre.getWidth(), ratonSobre.getHeight());
        this.texto = texto;
        this.accion = accion;
        valor = 0;
    }

    public Boton(BufferedImage ratonfuera, BufferedImage ratonSobre,
            int x, int y, String texto, Accion accion, int valor) {
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        cuadroDelimitador = new Rectangle(x, y, ratonSobre.getWidth() + valor, ratonSobre.getHeight());
        this.texto = texto;
        this.accion = accion;
        this.valor = valor;
    }

    public Rectangle getCuadroDelimitador() {
        return cuadroDelimitador;
    }

    /*
    public Rectangle sumaranchura(Rectangle cuadroDelimitador,int valor) {
        cuadroDelimitador.contains(cuadroDelimitador.x, cuadroDelimitador.y, cuadroDelimitador.getWidth()+20, cuadroDelimitador.getHeight());
        return cuadroDelimitador;
        
    }*/
    public void actualizar() {
        //este metodo retorna verdadero silas coordenadas especificasdas estan dentro
        //del cuadro delimitador
        if (cuadroDelimitador.contains(RatonEntrada.x, RatonEntrada.y)) {
            ratonDentro = true;
        } else {
            ratonDentro = false;
        }
        if (ratonDentro && RatonEntrada.salidaRaton) {
            accion.hacerAccion();
        }

    }

    /*   public void sumaranchura(BufferedImage imagen, int valor) {

    }*/
    public void dibujar(Graphics g) {
         Graphics2D g2d = (Graphics2D) g;
        if (ratonDentro) {
            if (valor > 0) {

                //  sumaranchura(ratonSobre, valor);
                int ancho = ratonSobre.getWidth();
                int alto=ratonSobre.getHeight();
                int escalarancho=ratonSobre.getWidth()*valor;
                int escalaralto=ratonSobre.getHeight();
                BufferedImage cambio = new BufferedImage(escalarancho, escalaralto,
                        ratonSobre.getType());

              //  Graphics2D g2 = cambio.createGraphics();
                
                g2d .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2d .drawImage(ratonSobre,
                        cuadroDelimitador.x,
                        cuadroDelimitador.y,
                        escalarancho,
                        escalaralto,
                        cuadroDelimitador.x,
                        cuadroDelimitador.y,
                        ancho,
                        alto,
                        null);
                

            } else {
                g2d .drawImage(ratonSobre, cuadroDelimitador.x, cuadroDelimitador.y, null);

            }

        } else {
            if (valor < 0) {

                //  sumaranchura(ratonSobre, valor);
                int ancho = ratonSobre.getWidth() + valor;
                BufferedImage cambio = new BufferedImage(ancho, ratonSobre.getHeight(),
                        ratonSobre.getType());

               // Graphics2D g2 = cambio.createGraphics();
                g2d .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2d .drawImage(ratonSobre,
                        cuadroDelimitador.x,
                        cuadroDelimitador.y,
                        ancho,
                        ratonSobre.getHeight(),
                        cuadroDelimitador.x,
                        cuadroDelimitador.y,
                        ratonSobre.getWidth(),
                        ratonSobre.getHeight(),
                        null);
//g2.dispose();
            } else {
                g2d .drawImage(ratonfuera, cuadroDelimitador.x, cuadroDelimitador.y, null);

            }
        }
        Texto.DibujarTexto(g2d,
                texto,
                new Vectores(cuadroDelimitador.x + cuadroDelimitador.getWidth() / 2, cuadroDelimitador.y + cuadroDelimitador.getHeight()),
                true,
                Color.BLACK,
                Externos.Mfuente);

    }

}
