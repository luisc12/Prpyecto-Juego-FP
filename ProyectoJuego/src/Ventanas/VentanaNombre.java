/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.RatonEntrada;
import Entrada.Teclado;
import Graficos.Externos;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class VentanaNombre extends Ventana {

    private JTextField Input = new JTextField();
    public JPanel panel;
    // private Canvas canvas;
    private boolean ejecutando = true;
    private BufferStrategy bs;
    private JTextField textField;
    ArrayList<Boton> botones;
    private MyCanvas canvas;
    public boolean isEjecutando() {
        return ejecutando;
    }

    public void setEjecutando(boolean ejecutando) {
        this.ejecutando = ejecutando;
    }

    public VentanaNombre() {
         botones=new ArrayList<Boton>();
        /*
        panel = new JPanel();
        panel.setLayout(null);//desactivar el diseño 

        add(panel);//agregamos el panel a la ventana

        //etiqueta
        JLabel etiqueta = new JLabel();//creamos una etiqueta de Texto
        etiqueta.setText("Nombre del Jugador");//establecemos el texto de la etiqueta
        etiqueta.setBounds(20, 10, 180, 30);
        etiqueta.setHorizontalAlignment(SwingConstants.LEFT);//establecemos la aniliacion del texto
        etiqueta.setVisible(true);
        panel.add(etiqueta);
        add(etiqueta);

        //caja de texto
        JTextField cajaTexto = new JTextField();
        cajaTexto.setBounds(170, 10, 80, 30);
        cajaTexto.setText("Jugador 1");
        cajaTexto.setVisible(true);

        panel.add(cajaTexto);*/

        //caja de boton
       
        
       
        
        JFrame Pnom = new JFrame("Ingresar usuario");
        Pnom.setSize(400, 400);
        Pnom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pnom.setLocationRelativeTo(null);

        // Configurar el JLayeredPane para manejar capas
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 500));
        Pnom.add(layeredPane);

        // Inicializar el Canvas y agregarlo a la capa inferior
        canvas = new MyCanvas();
        canvas.setBounds(0, 0, 500, 500); // Posición y tamaño del Canvas
        layeredPane.add(canvas, JLayeredPane.DEFAULT_LAYER); // Añadir el Canvas en la capa por defecto

        // Crear el JTextField y agregarlo en una capa superior
        textField = new JTextField(15);
        textField.setBounds(150, 50, 200, 30); // Posicionamiento del JTextField sobre el Canvas
        textField.setFont(Externos.Mfuente);
        layeredPane.add(textField, JLayeredPane.PALETTE_LAYER); // Añadir el JTextField en una capa superior
        
        textField.setText("Jugador 1");

        // Acción para capturar el texto cuando se presiona Enter
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
            }
        });
         JButton boton1 = new JButton();
      
         boton1.setBounds(20, 300, Externos.bGris.getWidth(), Externos.bGris.getHeight());
        boton1.setEnabled(true);//establecemos el encendido del boton
        boton1.setMnemonic('a');//Establecemos alt+ letra
        boton1.setForeground(Color.BLACK);//Establecemos el color de la letra de nuestro botón
        boton1.setFont(Externos.Mfuente);//Establecemos la fuente de la letra del botton
        //panel.add(boton1);
        
        ImageIcon clicAqui=new ImageIcon(Externos.bGris);
        boton1.setIcon(clicAqui);
       
       // boton1.setIcon(new ImageIcon(clicAqui.getImage().getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_REPLICATE)));
         // boton1.setText("Aceptar");//establecemos un texto al boton
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               boton1.setIcon(new ImageIcon(Externos.bVerde.getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_REPLICATE)));
               Pnom.setVisible(false);
               String nombre=textField.getText();
               System.out.println(nombre);
                Ventana.cambiarVentana(new VentanaPartida(nombre));
                
               
            }
        });
        layeredPane.add(boton1,JLayeredPane.PALETTE_LAYER);

       // pack(); // Ajustar el tamaño del JFrame a sus componentes
 botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2+Externos.bGris.getHeight()*2,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaPartida(textField.getText()));
            }
        }));
 
 
        Pnom.setVisible(true);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
      //  g.setColor(Color.BLUE);
      //  g.fillRect(50, 50, 300, 300); // Dibuja un rectángulo como ejemplo
    }

    

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, Constantes.ancho,Constantes.alto );
        /* super.paint(g);
        Input.setBounds(200, 200, 10, 20);
        Input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        Input.setEditable(true);
        Input.setBackground(getBackground());
        Input.setForeground(getForeground());
        Input.printAll(g);*/
        //canvas.paintComponent(g);
         for (Boton b : botones) {
            b.dibujar(g);
        }
         //  bs = canvas.getBufferresd;
    }

    @Override
    public void actualizar(float dt) {
        for (Boton b : botones) {
            b.actualizar();
        }   }

}
