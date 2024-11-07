/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.RatonEntrada;
import Entrada.Teclado;
import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Mensaje;
import Ui.Accion;
import Ui.Boton;
import static Ventanas.VentanaPartida.PosicionInicial;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author luis
 */
public class VentanaPausa extends Ventana {

    final Object pauseLock = new Object();
    private volatile boolean pausar = false;
    private boolean ver;
//JButton botonReanudar;
    ArrayList<Boton> botones ;
    private Thread PausaHilo;
    private int Pancho = 350, Palto = 250;
    public JPanel panel;
    private Canvas canvas;

    private Teclado teclado;
    private RatonEntrada raton;

    public VentanaPausa() {
        
        /* this.PausaHilo=PausaHilo;
       
        this.PausaHilo.start();*/

        System.out.println("Ventana de pausa");
        JFrame Pausa = new JFrame("Ventana de pausa");

        Pausa.setSize(Pancho, Palto);
        Pausa.setResizable(true);
        Pausa.setLocationRelativeTo(null);
        Pausa.setMinimumSize(new Dimension(200, 200));
        Pausa.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Pausa.setResizable(false);
        Pausa.setLocationRelativeTo(null);
        
         canvas = new Canvas();
        teclado = new Teclado();
        raton = new RatonEntrada();

        canvas.setPreferredSize(new Dimension(Pancho, Palto));
        canvas.setMaximumSize(new Dimension(Pancho, Palto));
        canvas.setMinimumSize(new Dimension(Pancho, Palto));
        Pausa.setFocusable(true);

        Pausa.add(canvas);
        canvas.addKeyListener(teclado);
        canvas.addMouseListener(raton);
        //con este nos aseguramos que resiva los eventos de los botones cuando se mueva
        canvas.addMouseMotionListener(raton);
        // canvas.setBounds(alto, alto, WIDTH, HEIGHT);
  System.out.println("visible");
       
        //no se me muestra mi boton personalizado a pesar que si se muestra bien en la ventana menu
        //panel = new JPanel();
        //panel.setLayout(null);//desactivar el diseño 

        //etiqueta
        /* JLabel etiqueta = new JLabel();//creamos una etiqueta de Texto
        etiqueta.setText("Click para continuar");//establecemos el texto de la etiqueta
        etiqueta.setBounds(20, 10, 180, 30);
        etiqueta.setHorizontalAlignment(SwingConstants.LEFT);//establecemos la aniliacion del texto
        etiqueta.setVisible(true);*/
        
       

        //panel.add(etiqueta);
        //caja de boton
       /* JButton botonReanudar = new JButton();
        botonReanudar.setText("Reanudar");//establecemos un texto al boton
        botonReanudar.setBounds(20, 100, 100, 30);
        botonReanudar.setEnabled(true);//establecemos el encendido del boton
        botonReanudar.setMnemonic('a');//Establecemos alt+ letra
        botonReanudar.setForeground(Color.BLACK);//Establecemos el color de la letra de nuestro botón
        botonReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pausa.dispose();

                synchronized (pauseLock) {

                    Reanudar();
                    pauseLock.notifyAll();
                }
            }
        });*/

        //panel.add(botonReanudar);
        // Pausa.add(panel);//agregamos el panel a la ventana
       
        System.out.println("botones");
        botones = new ArrayList<Boton>();
           botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                20,
                100,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("reanudar");
                pausar=true;
                Reanudar();
            }
        }));
         
        Pausa.setVisible(true);    
      
    }

    private void Reanudar() {
        synchronized (pauseLock) {
            pausar = true;
            pauseLock.notifyAll();
        }
    }

    public boolean isPausar() {
        return pausar;
    }

    @Override
    public void actualizar() {
        System.out.println("actualizar");
        for (Boton b : botones) {
            b.actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
         Texto.DibujarTexto(
                g2d,
                "PREPARANDO ENTREGA",
                new Vectores(Pancho/2, Palto/2 -100),
                true,
                Color.white,
                Externos.Mfuente);
         System.out.println("dibujar");
        for (Boton b : botones) {
            b.dibujar(g);
        }
    }

}
