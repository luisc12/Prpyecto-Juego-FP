/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.Teclado;
import Graficos.Externos;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author luis
 */
public class VentanaPausa extends Ventana{
 final Object pauseLock = new Object();
 private volatile boolean pausar = false;
 private boolean ver;
//JButton botonReanudar;
 ArrayList<Boton> botones;
 private Thread PausaHilo;
 private int Pancho=300,Palto=200;
public JPanel panel;

    public VentanaPausa() {
      /* this.PausaHilo=PausaHilo;
       
        this.PausaHilo.start();*/
     
        System.out.println("Ventana de pausa");
         JFrame Pausa = new JFrame("Ventana de pausa");

        Pausa.setSize(Pancho,Palto);
        Pausa.setResizable(true);
        Pausa.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);//desactivar el diseño 

        //etiqueta
        JLabel etiqueta = new JLabel();//creamos una etiqueta de Texto
        etiqueta.setText("Click para continuar");//establecemos el texto de la etiqueta
        etiqueta.setBounds(20, 10, 180, 30);
        etiqueta.setHorizontalAlignment(SwingConstants.LEFT);//establecemos la aniliacion del texto
        etiqueta.setVisible(true);
        panel.add(etiqueta);

        //caja de boton
        JButton botonReanudar = new JButton();
        //JButton boton1=new JButton("Click");
        botonReanudar.setText("Reanudar");//establecemos un texto al boton
        botonReanudar.setBounds(20, 100, 100, 30);
        botonReanudar.setEnabled(true);//establecemos el encendido del boton
        botonReanudar.setMnemonic('a');//Establecemos alt+ letra
        botonReanudar.setForeground(Color.BLACK);//Establecemos el color de la letra de nuestro botón
        //boton1.setFont(new Font("arial",, 20));//Establecemos la fuente de la letra del botton
        botonReanudar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pausa.dispose();
                
                synchronized (pauseLock) {
                   
                      Reanudar();
                    pauseLock.notifyAll();
                }
            }
        });

        panel.add(botonReanudar);
        Pausa.add(panel);//agregamos el panel a la ventana

        System.out.println("botones");
 /*botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                20,
                100,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
                System.out.println("reanudar");
             //pausar=true;
             Reanudar();
            }
        }));*/
        System.out.println("visible");
 Pausa.setVisible(true);
    }

    
 private void Reanudar() { 
     synchronized (pauseLock) {
            pausar = true;
            pauseLock.notifyAll();
        }}

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
         for (Boton b : botones) {
            b.dibujar(g);
        }
    }

   
 
 
    }

   


    

