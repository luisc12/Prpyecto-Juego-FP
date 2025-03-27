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
    private VentanaMenu vm;
    BufferedImage imagenEscalada; // Ancho: 200, Alto: 300  

    public VentanaSkins(ProyectoJuego p) throws ParserConfigurationException, SAXException, IOException {
        super(p);
        botones = new ArrayList<Boton>();
        activar = false;
        indice = 0;
        
        imagenEscalada = Externos.cambiarTamaño(Externos.panelMenu,
                Constantes.ancho - 100, Constantes.alto - 100);
        vm.getMusicaFondo().parar();

        Boton atras = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 - Externos.bGris.getWidth() * 2,
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, Externos.cEncendido, Externos.cApagado, 
                new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu(p,false));
            }
        });
        botones.add(atras);
        Boton comenzar = new Boton(Constantes.botonApagado,
                Constantes.botonActivo,
                Constantes.ancho / 2 + Externos.bGris.getWidth(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Comenzar, Externos.cEncendido, Externos.cApagado,
                new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaPartida(nombre,
                        aux[indice].textura, p));
            }
        });
        botones.add(comenzar);
        BufferedImage flechaderechaV = Externos.cambiarTamaño(
                Externos.flechaVerdeD, 150, 150);
        BufferedImage flechaderechaG = Externos.cambiarTamaño(
                Externos.flechaGrisD, 150, 150);
        BufferedImage flechaizquierdaV = Externos.cambiarTamaño(
                Externos.flechaVerdeI, 150, 150);
        BufferedImage flechaizquierdaG = Externos.cambiarTamaño(
                Externos.flechaGrisI, 150, 150);
        Boton derecha = new Boton(flechaderechaG,
                flechaderechaV,
                Constantes.ancho - flechaderechaG.getWidth() * 2,
                Constantes.alto / 2 - flechaderechaG.getHeight() / 2,
                "", Externos.cApagado, Externos.cApagado, new Accion() {
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
                "", Externos.cApagado, Externos.cApagado, new Accion() {
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
        //buscamos cual es el mejor puntaje
        ArrayList<DatosPuntaje> listaDatos = XMLParser.LeerFichero();
        for (DatosPuntaje l : listaDatos) {
            if (l.getPuntaje() > mejorpuntaje) {
                mejorpuntaje = l.getPuntaje();
            }
        }
        //sacamos un Array de naves
        Naves[] naves = Naves.values();
        int i = 0;
        //verificamos cuantas Skins son desbloquedas usando la puntuacion maxima
        for (int j = 0; j < naves.length; j++) {
            if (mejorpuntaje >= naves[j].puntaje) {
                i++;
            }
           
        }
        index = i;
        //creamos un Array con la cantidad de naves desbloqueadas
        aux = new Naves[index];
        for (i = 0; i <= naves.length; i++) {
            if (i < index) {
                aux[i] = naves[i];
            }
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
        //creamos una etiqueta de Texto
        JLabel etiqueta = new JLabel();
        //establecemos el texto de la etiqueta
        etiqueta.setText("Ingrese su nombre:");
        etiqueta.setBounds(50, 10, 300, 50);
        etiqueta.setBackground(Color.DARK_GRAY);
        etiqueta.setForeground(Color.GREEN);

        etiqueta.setFont(Externos.Pixeloid);/*establecemos la fuente del texto 
        0 es normal, 1 es negrita, 2 es cursiba y 3 es negrita y cursiva*/
        panel.add(etiqueta);

        // Crear campo de texto
        textField = new JTextField();
        textField.setBounds(350, 20, 200, 30);
        textField.setFont(Externos.Mfuente);
        textField.setText("Jugador 1");
        panel.add(textField);

        // Acción para capturar el texto cuando se presiona Enter
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                //no permite que se introdusca un nombre con mas de 9 caracteres
                if (textField.getText().length() == 9) {
                    e.consume();
                }
            }
        });
        
        /*defino el ancho y la altura de las imagenes de los botones */
        int ancho = 192;
        int alto = 64;
        BufferedImage botonDesactivado = Externos.cambiarTamaño(Externos.
                bDesactivado, ancho, alto);
        BufferedImage botonactivado = Externos.cambiarTamaño(Externos.bActivo,
                ancho, alto);
        
        JButton boton1 = new JButton("Aceptar");
        //Imagen por defecto
        ImageIcon normalIcon = new ImageIcon(botonDesactivado);
        //Imagen al pasar el mouse
        ImageIcon hoverIcon = new ImageIcon(botonactivado);
        boton1.setBounds(20, 200, botonDesactivado.getWidth(),
                botonDesactivado.getHeight());
        boton1.setHorizontalTextPosition(JButton.CENTER);
        boton1.setVerticalTextPosition(JButton.CENTER);
        //establecemos el encendido del boton
        boton1.setEnabled(true);
        //Establecemos el color de la letra de nuestro botón
        boton1.setForeground(Externos.cApagado);
        boton1.setIcon(normalIcon);
        //Establecemos la fuente de la letra del botton
        boton1.setFont(Externos.Pixeloid);
        panel.add(boton1);
        boton1.addMouseListener(new MouseAdapter() {
//cambiamos la aparencia del boton segun su evento
            @Override
            public void mousePressed(MouseEvent e) {
                //boton 1 es el boton izquierdo del raton
                /*al precionar activar se vuelve true y permite actualizar 
                los botones*/
                if (e.getButton() == MouseEvent.BUTTON1) {
                    boton1.setForeground(Externos.cEncendido);
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
                    boton1.setForeground(Externos.cEncendido);
                } else {
                    boton1.setIcon(normalIcon);
                    boton1.setForeground(Externos.cApagado);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
               //al estar en sima del boton
                boton1.setIcon(hoverIcon);
                boton1.setForeground(Externos.cEncendido);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton1.setIcon(normalIcon); // Volver a la imagen normal
                boton1.setForeground(Externos.cApagado);
            }

        });
        
        panel.setBackground(Color.BLACK);
        Pnom.add(panel);

        Pnom.setIconImage(Externos.getIconImage());
        Pnom.setVisible(true);
        //poner que la ventana este siempre visible
        Pnom.setAlwaysOnTop(true);
    }

    @Override
    public void actualizar(float dt) {
        /*permitir es el contador de tiempo que evita que al cambiar de Skins
        de la vuelta completa al ArrayList en lugar de avanzar o
        regresar 1 Skin, siempre que se alla serrado la ventana Nombre*/
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
        //dibujamos el panel
        g.drawImage(imagenEscalada, Constantes.ancho / 2 - 
                imagenEscalada.getWidth() / 2, WIDTH, null);
//dibujamos los botones
        for (Boton b : botones) {
            b.dibujar(g);
        }
        //dibujamos los textos
        Texto.DibujarTexto(g,
                "Seleccione la Skin",
                new Vectores(Constantes.ancho / 2 - aux[indice]
                        .nombre.length(), 100),
                true,
                Externos.cEncendido,
                Externos.Gfuente);
        
        Texto.DibujarTexto(g,
                aux[indice].nombre,
                new Vectores(Constantes.ancho / 2 - aux[indice]
                        .nombre.length(), Constantes.alto / 1.2),
                true,
                Externos.cEncendido,
                Externos.Gfuente);
        BufferedImage muestra = Externos.cambiarTamaño(aux[indice]
                .textura, 200, 200);
        
        //dibujamos la apariencia del jugador actual
        Vectores PosicionInicial
                = new Vectores(Constantes.ancho / 2 - muestra.getWidth() / 2,
                        Constantes.alto / 2 - muestra.getHeight() / 2);
        
        AffineTransform at = AffineTransform.getTranslateInstance(
                PosicionInicial.getX(), PosicionInicial.getY());

        at.rotate(0, muestra.getWidth() / 2, muestra.getWidth() / 2);
        g2d.drawImage(muestra, at, null);
    }
}
