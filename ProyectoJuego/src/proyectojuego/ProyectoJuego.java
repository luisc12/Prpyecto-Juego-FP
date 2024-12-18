/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectojuego;

import Ventanas.VentanaPausa;
import Entrada.RatonEntrada;
import Entrada.Teclado;
import Graficos.Externos;
import ObjetosMoviles.Constantes;
import Ventanas.Ventana;
import Ventanas.VentanaCarga;
import Ventanas.VentanaMenu;
import Ventanas.VentanaPartida;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author luis
 */

public class ProyectoJuego extends JFrame implements Runnable {

    public JPanel panel;

    private Thread hilo;
    //canvas es un lienso en el que se dibuja y atrapa los eventos del teclado, ratony accion
    private  Canvas canvas;
    private boolean ejecutando = false;
 JLayeredPane layeredPane;
    private BufferStrategy bs;
    private Graphics g;
VentanaPausa p;
    private final int FPS = 60;//fotogramas por segundo

    //un segundo en nanosegundos dividido por FPS
    private double objT = 1000000000 / FPS;
    //tiempo transcurrido
    private double TTrans = 0;
    //promedio de FPS
    private int PROFPS = FPS;


    
    private Teclado teclado;
    private RatonEntrada raton;

    public ProyectoJuego() {
        setTitle("Amenaza Espacial");
        setSize(Constantes.ancho, Constantes.alto);
        setMinimumSize(new Dimension(200, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        

        canvas = new Canvas();
        teclado = new Teclado();
        raton=new RatonEntrada();

        canvas.setPreferredSize(new Dimension(Constantes.ancho, Constantes.alto));
        canvas.setMaximumSize(new Dimension(Constantes.ancho, Constantes.alto));
        canvas.setMinimumSize(new Dimension(Constantes.ancho, Constantes.alto));
        setFocusable(true);

        add(canvas);
        canvas.addKeyListener(teclado);
        canvas.addMouseListener(raton);
        //con este nos aseguramos que resiva los eventos de los botones cuando se mueva
        canvas.addMouseMotionListener(raton);
        // canvas.setBounds(alto, alto, WIDTH, HEIGHT);
  //
       
        
        
        //
        Externos.getIconImage();
        // Ingresarusuario();
//boolean v=ventanan.isEjecutando();
        setVisible(true);
    }
    

    //icono del JFrame
  /*  @Override
    public Image getIconImage() {
        Image retValue;
        retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Graficos/otros/icono2.png"));

        return retValue;
    }*/

    public static void main(String[] args) {
        new ProyectoJuego().start();

    }

    public void Ingresarusuario() {
        //ventana nombre
        JFrame Pnom = new JFrame("Ingresar usuario");
        Pnom.setIconImage(getIconImage());
        Pnom.setSize(300, 200);
        Pnom.setResizable(true);
        Pnom.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);//desactivar el diseño 

        Pnom.add(panel);//agregamos el panel a la ventana

        //etiqueta
        JLabel etiqueta = new JLabel();//creamos una etiqueta de Texto
        etiqueta.setText("Nombre del Jugador");//establecemos el texto de la etiqueta
        etiqueta.setBounds(20, 10, 180, 30);
        etiqueta.setHorizontalAlignment(SwingConstants.LEFT);//establecemos la aniliacion del texto
        etiqueta.setVisible(true);
        panel.add(etiqueta);

        //caja de texto
        JTextField cajaTexto = new JTextField();
        cajaTexto.setBounds(170, 10, 80, 30);
        cajaTexto.setText("Jugador 1");
        cajaTexto.setVisible(true);
        panel.add(cajaTexto);

        //caja de boton
        JButton boton1 = new JButton();
        //JButton boton1=new JButton("Click");
        boton1.setText("Aceptar");//establecemos un texto al boton
        boton1.setBounds(20, 100, 80, 30);
        boton1.setEnabled(true);//establecemos el encendido del boton
        boton1.setMnemonic('a');//Establecemos alt+ letra
        boton1.setForeground(Color.BLACK);//Establecemos el color de la letra de nuestro botón
        //boton1.setFont(new Font("arial",, 20));//Establecemos la fuente de la letra del botton
        panel.add(boton1);

        Pnom.setVisible(true);

    }


    private void actualizar(float dt) {

        //this.setVisibles(ventanan.isEjecutando());
        teclado.actualizar();
        Ventana.getVentanaActual().actualizar(dt);

    }

    private void dibujar() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
           canvas.createBufferStrategy(3);//el numero de buffer que utiliza un canvas

            return;
        }

        g = bs.getDrawGraphics();

        //-----------------------
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constantes.ancho, Constantes.alto);

       // ventanaPartida.dibujar(g);
       Ventana.getVentanaActual().dibujar(g);

       g.setColor(Color.WHITE);
       
        g.drawString("" + PROFPS, 10, 20);

        //---------------------
        g.dispose();
        bs.show();

    }

    private void inicio() {
        
        Thread hiloCarga=new Thread(new Runnable() {
            @Override
            public void run() {
                Externos.inicio();
            }
        });
        
        
        
        Ventana.cambiarVentana(new VentanaCarga(hiloCarga));

    }
 private void pausar() {
        
        Thread hiloCarga=new Thread(new Runnable() {
            @Override
            public void run() {
                while(p.isPausar()){
                    
                }
            }
        });
        
        
        
        Ventana.cambiarVentana(new VentanaPausa(hiloCarga));

    }
    @Override
    public void run() {

        long ahora = 0;
        long TPasado = System.nanoTime();//hora actual del sistema en nano segundos
        int frames = 0;
        long tiempo = 0;

        inicio();

        // usamos un ciclo while que actualizara todos los objetos del juego
        while (ejecutando) {
          
            ahora = System.nanoTime();
            TTrans += (ahora - TPasado) / objT;
            tiempo += (ahora - TPasado);
            TPasado = ahora;
            if (TTrans >= 1) {
                //paso el tiempo entre fotogramas multiplicando TTrans por objT y luego convertirlo en milisegundos
                actualizar((float)(TTrans*objT*0.000001f));
                dibujar();
                TTrans--;
                frames++;

            }
            if (tiempo >= 1000000000) {
                PROFPS = frames;
                frames = 0;
                tiempo = 0;
               
            }

        }
        stop();
    }

    private void start() {

        hilo = new Thread(this);
        hilo.start();
        ejecutando = true;

    }


    private void stop() {
        try {
            hilo.join();

            ejecutando = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
