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
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */

public class VentanaSkins extends Ventana {

    public BufferedImage apariencia;
    ArrayList<Boton> botones;
    private int mejorpuntaje;
    private int index = 0;
    private int indice = 0;
    private Naves[] aux;
    private boolean activar;
    long permitir;
    String nombre;
    ///ventana nombre
    private JTextField Input = new JTextField();
    public JPanel panel;
    private boolean ejecutando = true;
    private BufferStrategy bs;
    private JTextField textField;
BufferedImage imagenEscalada; // Ancho: 200, Alto: 300  
    public VentanaSkins(ProyectoJuego p) throws ParserConfigurationException, SAXException, IOException {
        super(p);
        botones = new ArrayList<Boton>();
        activar = false;
        indice=0;
        
       imagenEscalada = Externos.cambiarTamaño(Externos.panelMenu, Constantes.ancho / 2, Constantes.alto - 200); 
        Boton atras = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - Externos.bGris.getWidth() * 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, Externos.cEncendido, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p));
            }
        });
        botones.add(atras);
        Boton comenzar = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 + Externos.bGris.getWidth(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Comenzar, Externos.cEncendido, Externos.cApagado, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaPartida(nombre, aux[indice].textura, p));
            }
        });
        botones.add(comenzar);
        BufferedImage flechaderechaV = Externos.cambiarTamaño(Externos.flechaVerdeD, 150,150);
        BufferedImage flechaderechaG = Externos.cambiarTamaño(Externos.flechaGrisD, 150,150);
        BufferedImage flechaizquierdaV = Externos.cambiarTamaño(Externos.flechaVerdeI, 150,150);
        BufferedImage flechaizquierdaG = Externos.cambiarTamaño(Externos.flechaGrisI, 150,150);
        Boton derecha = new Boton(flechaderechaG,
                flechaderechaV,
                Constantes.ancho - flechaderechaG.getWidth() * 2,
                Constantes.alto / 2 - flechaderechaG.getHeight() / 2,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                if (indice + 1 < index) {
                    indice += 1;
                } else {
                    indice = 0;
                }
            }
        });
        botones.add(derecha);
        Boton izquierda = new Boton(flechaizquierdaG,
                flechaizquierdaV,
                Externos.flechaGrisI.getWidth() * 2,
                Constantes.alto / 2 - flechaizquierdaG.getHeight() / 2,
                "", new Accion() {
            @Override
            public void hacerAccion() {
                if (indice > 1) {
                    indice -= 1;
                } else {
                    indice = index - 1;
                }
            }
        });
        botones.add(izquierda);
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
            System.out.println(naves[j].name() + " " + naves[j].puntaje);
        }
        index = i;
        System.out.println("index:" + index);
        aux = new Naves[index];
        for (i = 0; i <= naves.length; i++) {
            if (i < index) {
                aux[i] = naves[i];
                System.out.println(i);
            }
        }
        System.out.println("i: " + i);
        for (int j = 0; j < aux.length; j++) {
            System.out.println(aux[j].name() + " " + aux[j].puntaje);
        }
        ////-------------ventana nombre------------
        JFrame Pnom = new JFrame("Ingresar usuario");
        Pnom.setSize(600, 300);
        Pnom.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Pnom.setLocationRelativeTo(null);
        Pnom.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);
        Pnom.add(panel);
        JLabel etiqueta = new JLabel();//creamos una etiqueta de Texto
        etiqueta.setText("Ingrese su nombre:");//establecemos el texto de la etiqueta
        etiqueta.setBounds(50, 10, 300, 50);
        etiqueta.setBackground(Color.DARK_GRAY);
        etiqueta.setForeground(Color.GREEN);

        etiqueta.setFont(Externos.Pixeloid);/*establecemos la fuente del texto 0 es normal, 
         1 es negrita, 2 es cursiba y 3 es negrita y cursiva*/
        panel.add(etiqueta);

// Crear campo de texto
        textField = new JTextField();
        textField.setBounds(350, 20, 200, 30);
        textField.setFont(Externos.Mfuente);
        textField.setText("Jugador 1");
        panel.add(textField);


        /* JFrame Pnom = new JFrame("Ingresar usuario");
        Pnom.setSize(400, 400);
        Pnom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pnom.setLocationRelativeTo(null);
       panel = new JPanel();
        panel = new JPanel();*/
        // Acción para capturar el texto cuando se presiona Enter
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (textField.getText().length() == 9) {
                    e.consume();
                }
            }
        });
        JButton boton1 = new JButton("Aceptar");
        ImageIcon normalIcon = new ImageIcon(Externos.bGris); // Imagen por defecto
        ImageIcon hoverIcon = new ImageIcon(Externos.bVerde);   // Imagen al pasar el mouse
        boton1.setBounds(20, 200, Externos.bGris.getWidth(), Externos.bGris.getHeight());
        boton1.setHorizontalTextPosition(JButton.CENTER);
        boton1.setVerticalTextPosition(JButton.CENTER);
        boton1.setEnabled(true);//establecemos el encendido del boton
        boton1.setMnemonic('a');//Establecemos alt+ letra
        boton1.setForeground(Color.BLACK);//Establecemos el color de la letra de nuestro botón
        boton1.setFont(Externos.Mfuente);//Establecemos la fuente de la letra del botton
        boton1.setFont(Externos.Pixeloid);//Establecemos la fuente de la letra del botton
        panel.add(boton1);
        boton1.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
                    //  boton1.setIcon(new ImageIcon(Externos.bVerde.getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_REPLICATE)));
                    boton1.setIcon(hoverIcon);
                    nombre = textField.getText();
                    System.out.println(nombre);
                    activar = true;
                    Pnom.setVisible(false);

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //verifica que el raton esta encima del boton
                if (boton1.getBounds().contains(e.getPoint())) {
                    boton1.setIcon(hoverIcon);
                } else {
                    boton1.setIcon(normalIcon);
                }
                /*if (e.getButton() == MouseEvent.BUTTON1) {//boton 1 es el boton izquierdodel raton
                    
                    boton1.setIcon(new ImageIcon(Externos.bGris.getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_REPLICATE)));
                }*/
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //mantendra la posicion del raton almacenada en estas variables
                boton1.setIcon(hoverIcon);
                // boton1.setIcon(new ImageIcon(Externos.bVerde.getScaledInstance(boton1.getWidth(), boton1.getHeight(), Image.SCALE_REPLICATE)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton1.setIcon(normalIcon); // Volver a la imagen normal
            }

        });

        panel.setBackground(Color.BLACK);
        Pnom.add(panel);
        panel.setBackground(Color.BLACK);
        Pnom.add(panel);

        Pnom.setIconImage(Externos.getIconImage());
        Pnom.setVisible(true);
        Pnom.setAlwaysOnTop(true);
    }

    @Override
    public void actualizar(float dt) {
        permitir += dt;
        if (activar) {
            if (permitir > 100) {
                for (Boton b : botones) {
                    b.actualizar();
                }
                permitir = 0;
            }
        }

    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
       /* BufferedImage imagenEscalada = Externos.cambiarTamaño(Externos.panelMenu, Constantes.ancho / 2, Constantes.alto - 200); // Ancho: 200, Alto: 300  

        AffineTransform ati = AffineTransform.getTranslateInstance(
                Constantes.ancho / 2 - imagenEscalada.getWidth() / 2,
                0);
        g2d.drawImage(imagenEscalada, ati, null);*/
        for (Boton b : botones) {
            b.dibujar(g);
        }
        Texto.DibujarTexto(g,
                "Seleccione la Skin",
                new Vectores(Constantes.ancho / 2 - aux[indice].nombre.length(), 100),
                true,
                Color.YELLOW,
                Externos.Gfuente);
        Texto.DibujarTexto(g,
                aux[indice].nombre,
                new Vectores(Constantes.ancho / 2 - aux[indice].nombre.length(), Constantes.alto / 1.2),
                true,
                Color.MAGENTA,
                Externos.Gfuente);
        BufferedImage muestra = Externos.cambiarTamaño(aux[indice].textura, 200,200);
        Vectores PosicionInicial
                = new Vectores(Constantes.ancho / 2 - muestra.getWidth() / 2,
                        Constantes.alto / 2 - muestra.getHeight() / 2);
        AffineTransform at = AffineTransform.getTranslateInstance(PosicionInicial.getX(),
                PosicionInicial.getY());
        /*    public static final Vectores PosicionInicial
            = new Vectores(Constantes.ancho / 2 - Externos.jugador.getWidth() / 2,
                    Constantes.alto / 2 - Externos.jugador.getHeight() / 2);*/
        at.rotate(0, muestra.getWidth() / 2, muestra.getWidth() / 2);
        g2d.drawImage(muestra, at, null);
    }
}