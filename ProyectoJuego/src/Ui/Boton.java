/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import Entrada.Raton;
import Graficos.Externos;
import Graficos.Sonido;
import Graficos.Texto;
import Matematicas.Vectores;
import java.awt.Color;
import java.awt.Font;
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
    private Color cEncendido;
    private Color cApagado;
    private Sonido click;
    private Font fuente;


    public Boton(BufferedImage ratonfuera, BufferedImage ratonSobre,
            int x, int y, String texto, Color cEncendido,Color cApagado,Font fuente, Accion accion) {
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        cuadroDelimitador = new Rectangle(x, y, ratonSobre.getWidth(), ratonSobre.getHeight());
        this.texto = texto;
        this.accion = accion;
        this.cEncendido = cEncendido;
        this.cApagado=cApagado;
        this.fuente=fuente;
        click= new Sonido(Externos.clickBoton);
          click.cambiarVolumen(-10.0f);
    }

    public Boton(BufferedImage ratonfuera, BufferedImage ratonSobre,
            int x, int y, String texto, Accion accion) {
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        cuadroDelimitador = new Rectangle(x, y, ratonSobre.getWidth(), ratonSobre.getHeight());
        this.texto = texto;
        this.accion = accion;
        cEncendido = Color.BLACK;
        cApagado=Color.BLACK;
         click= new Sonido(Externos.DisparoUfo);
          click.cambiarVolumen(-10.0f);
           fuente=  Externos.Pixeloid;
    }
   

    public Boton(BufferedImage ratonfuera, BufferedImage ratonSobre,
            int x, int y, String texto, Color cEncendido,Color cApagado, Accion accion) {
        this.ratonfuera = ratonfuera;
        this.ratonSobre = ratonSobre;
        cuadroDelimitador = new Rectangle(x, y, ratonSobre.getWidth(), ratonSobre.getHeight());
        this.texto = texto;
        this.accion = accion;
        this.cEncendido = cEncendido;
        this.cApagado=cApagado;
        click= new Sonido(Externos.clickBoton);
          click.cambiarVolumen(-10.0f);
        fuente=  Externos.Pixeloid;
    }

    public void actualizar() {
        //este metodo retorna verdadero silas coordenadas especificasdas estan dentro
        //del cuadro delimitador
        if (cuadroDelimitador.contains(Raton.x, Raton.y)) {
            ratonDentro = true;
        } else {
            ratonDentro = false;
        }
        if (ratonDentro && Raton.salidaRaton) {
            click.play();
            accion.hacerAccion();
        }

    }

    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //mejora la vista del los objetos
        Color c;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (ratonDentro) {

            g2d.drawImage(ratonSobre, cuadroDelimitador.x, cuadroDelimitador.y, null);
 c=cEncendido;
   
        } else {

            g2d.drawImage(ratonfuera, cuadroDelimitador.x, cuadroDelimitador.y, null);
c=cApagado;
        }
        Texto.DibujarTexto(g2d,
                texto,
                new Vectores(cuadroDelimitador.x + cuadroDelimitador.getWidth() / 2,
                        cuadroDelimitador.y + cuadroDelimitador.getHeight()),
                true,
                c,
                fuente);

    }

    public boolean isRatonDentro() {
        return ratonDentro;
    }

    public void setRatonDentro(boolean ratonDentro) {
        this.ratonDentro = ratonDentro;
    }

}