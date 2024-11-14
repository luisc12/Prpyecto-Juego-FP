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
import Ui.Accion;
import Ui.Boton;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
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
public class VentanaNombre extends Ventana {
     static Scanner sc = new Scanner(System.in);
 ArrayList<Boton> botones;
    private JTextField Input = new JTextField();
    //public JPanel panel;
   private boolean permitido=false;
   private String escribiendo;
  
    private Teclado teclado;
    private RatonEntrada raton;
    private BufferStrategy bs;
    private Graphics g;

    public VentanaNombre() {
botones=new ArrayList<Boton>();
        
        Input.setBounds(200, 200, 100, 50);
        Input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        Input.setEditable(true);

        Input.requestFocus();

       
        Input.setBackground(Color.darkGray);
        Input.setForeground(Color.white);
        

        Input.addKeyListener(teclado);
        Input.addMouseListener(raton);
        Input.setFont(Externos.Mfuente);
       
 botones.add(
                new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2-Externos.bGris.getHeight()/2,
                "ingresar",
                new Accion() {
            @Override
            public void hacerAccion() {
                if (permitido) {
                     Ventana.cambiarVentana(new VentanaPartida(Input.getText()));
                }
              
            }
        }));
        // Pnom.setVisible(true);
    }
public void dibujartexto(){
    
}
    @Override
    public void actualizar() {
        Input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
               escribiendo=String.valueOf(e.getKeyChar());
                if (e.equals(KeyEvent.VK_ENTER)) {
                    permitido=true;
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        if (Input.getText().length()>0) {
            permitido=true;
        }
        for (Boton b : botones) {
            b.actualizar();
        }

    }

    @Override
    public void dibujar(Graphics g) {
       // super.paint(g);
        Input.printAll(g);
        Vectores pos=new Vectores(200, 200);
       // g.drawString(escribiendo, 200, 200);
          /*   Texto.DibujarTexto(g,
                escribiendo,
                pos,
                false,
                Color.yellow,
                Externos.Gfuente);*/
      
        pos.setX(pos.getX()+20);
       
         for (Boton b : botones) {
            b.dibujar(g);
        }
    }

}
