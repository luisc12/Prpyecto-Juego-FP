/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.Teclado;
import Graficos.Animacion;
import Graficos.Externos;
import static Graficos.Externos.Ufo;
import Graficos.Sonido;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Cronometro;
import ObjetosMoviles.Jugador;
import ObjetosMoviles.Mensaje;
import ObjetosMoviles.Meteoros;
import ObjetosMoviles.ObjetosMovibles;
import ObjetosMoviles.Tamaños;
import ObjetosMoviles.Ufo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class VentanaPartida extends Ventana{
    VentanaPausa ventanaPausa;
public static final Vectores PosicionInicial = 
        new Vectores(Constantes.ancho/2 - Externos.player.getWidth()/2,
			Constantes.alto/2 - Externos.player.getHeight()/2);
	
    private Jugador jugador;
    private VentanaPausa pausa;
  

//creamos un arreglo de tipo objetomovibles quese encargara de actualizar y dibujar todos los objetosmovibles
    private ArrayList<ObjetosMovibles> objetosmoviles = new ArrayList<ObjetosMovibles>();
    //creamos un arayList de animacion
    private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
    //
    private ArrayList<Mensaje>mensajes= new ArrayList<Mensaje>();;

   

    private int puntos = 0;
    private int vidas=3;

    //numero de meteoros por partida
    private int meteoros;
    private int entregas=1;
    
    private Sonido musicaFondo;
    private Cronometro TGameOver;
    private boolean finJuego;
    
    private Cronometro aparecerUfo;

    public VentanaPartida() {
        //jugador
        jugador = new Jugador(Externos.player,
                new Vectores(Constantes.ancho/2-Externos.player.getWidth()/2,
                        Constantes.alto/2-Externos.player.getHeight()/2),
                new Vectores(0, 0), 7, this);
        
        TGameOver=new Cronometro();
        finJuego=false;
        
        objetosmoviles.add(jugador);

        meteoros = 1;
        empezarEntrega();
        //musica de fondo
        musicaFondo=new Sonido(Externos.MusicaFondo);
        musicaFondo.MusicaFondo();
        musicaFondo.cambiarVolumen(-10.0f);
        
        aparecerUfo=new Cronometro();
        aparecerUfo.Empezar(Constantes.TiempoAparecerUfo);
        System.out.println("cantidad "+Externos.cantidad);
        
        
    }

    public void SumarPuntos(int valor,Vectores posicion) {
        puntos += valor;
        
        mensajes.add(new Mensaje(
        "+"+valor+" Puntos",
        posicion,
        Color.WHITE,
        false,
        true, 
        Externos.Mfuente
        ));
    }

    public void DividirMeteoro(Meteoros m) {
        Tamaños tamaños = m.getTamaños();
        BufferedImage[] texturas = tamaños.texturas;

        Tamaños nuevoTamaño = null;

        switch (tamaños) {
            case GRANDE:
                nuevoTamaño = Tamaños.MEDIO;
                break;

            case MEDIO:
                nuevoTamaño = Tamaños.PEQUEÑO;
                break;

            case PEQUEÑO:
                nuevoTamaño = Tamaños.ENANO;
                break;
            default:
                return;
        }

        for (int i = 0; i < tamaños.division; i++) {
            objetosmoviles.add(
                    new Meteoros(texturas[(int) (Math.random() * texturas.length)],
                            m.getPosicion(),
                            //su direcion sera de con direcion sea un vector al azar entre 0 360 (PI*2)
                            new Vectores(0, 1).calcularDireccion(Math.random() * Math.PI * 2),
                            Constantes.Velocidad_Meteo * Math.random() + 1,
                            this,
                            nuevoTamaño));
        }
    }

    private void empezarEntrega() {
        double x, y;
        
mensajes.add(new Mensaje(
        " Encargo "+entregas,
        new Vectores(Constantes.ancho/2, Constantes.alto/2),
        Color.GREEN,
        true,
        true, 
        Externos.Gfuente
        ));
		
        for (int i = 0; i < meteoros; i++) {
            //pregunto si x es par si es verdadero digo que x es igual a un numero aleatorio entre cero y el ancho de la ventana sino me devuelve 0        
            x = 1 % 2 == 0 ? Math.random() * Constantes.ancho : 0;
            //si y es par sera cero 
            y = 1 % 2 == 0 ? 0 : Math.random() * Constantes.alto;
            //la imagen sera una imagen grande elegido al azar entre cero y el tamaño del array grandes
            BufferedImage textura = Externos.grades[(int) (Math.random() * Externos.grades.length)];

            objetosmoviles.add(new Meteoros(textura,
                    new Vectores(x, y),
                    //su direcion sera de con direcion sea un vector al azar entre 0 360 (PI*2)
                    new Vectores(0, 1).calcularDireccion(Math.random() * Math.PI * 2),
                    Constantes.Velocidad_Meteo * Math.random() + 1,
                    this,
                    Tamaños.GRANDE));

        }
         meteoros++;
       
        entregas++;
    }

    public void Explotar(Vectores posicion) {
        explosiones.add(new Animacion(Externos.explosion,
                50,
                //le restamos a la posicion la mitad del ancho y la altura de la imagen para que asi la animacion este en el centro
                posicion.RestaVectores(new Vectores(Externos.explosion[0].getWidth() / 2, Externos.explosion[0].getHeight() / 2))));
    }

    private void spawnUfo() {

        int randio = (int) (Math.random() * 2);

        double x = randio == 0 ? (Math.random() * Constantes.ancho) : 0;
        double y = randio == 0 ? 0 : (Math.random() * Constantes.alto);

        ArrayList<Vectores> caminos = new ArrayList<Vectores>();

        double posX, posY;
        //sector superior izquierdo

        posX = Math.random() * Constantes.ancho / 2;
        posY = Math.random() * Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector superior derecho
        posX = Math.random() * (Constantes.ancho / 2) + Constantes.ancho / 2;
        posY = Math.random() * Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector inferior izquierdo
        posX = Math.random() * Constantes.ancho / 2;
        posY = Math.random() * (Constantes.alto / 2) + Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));
        //sector inferior derecho
        posX = Math.random() * (Constantes.ancho / 2) + Constantes.ancho / 2;
        posY = Math.random() * (Constantes.alto / 2) + Constantes.alto / 2;
        caminos.add(new Vectores(posX, posY));

        objetosmoviles.add(new Ufo(Externos.Ufo[(int) (Math.random() * 2)],
                new Vectores(x, y),
                new Vectores(),
                Constantes.MaxVelUfo,
                this,
                caminos));

    }
 private void esperarSiPausado() {
        while (ventanaPausa.isPausar()) {
            synchronized (ventanaPausa.pauseLock) {
                try {
                    ventanaPausa.pauseLock.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

        }
    }
    @Override
    public void actualizar() {
        if (Teclado.pausa) {
             System.out.println("oprimer");
             VentanaPausa ventanaPausa=new VentanaPausa();
            Thread hiloCarga=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("esperando");
                 esperarSiPausado();
                 System.out.println("ver");
               /* while (!ventanaPausa.isPausar()) {
                    
                }*/
            }
        });
            hiloCarga.start();
            System.out.println("pausa");
            esperarSiPausado();
             
             System.out.println("return");
             //return;
             
        }
            
       
        //actualisamos los objetos moviles
        for (int i = 0; i < objetosmoviles.size(); i++) {
            ObjetosMovibles ob=objetosmoviles.get(i);
            ob.actualizar();
            //si esta muerto lo borra y se le resta a la i debido a que al borrar
            //un objeto todos los de su derecha avansan un paso a la izquierda
            //y el ultimo puesto de la derecha a hora basio lo elimina
            if (ob.isMuerte()) {
                objetosmoviles.remove(i);
                i--;
            }
            
        }
        /*  for (ObjetosMovibles obj : objetosmoviles) {
            obj.actualizar();
        }*/

        for (int i = 0; i < explosiones.size(); i++) {
            Animacion animacion = explosiones.get(i);
            animacion.actualizar();
            //si la animacion no esta ejecutando la elimino
            if (!animacion.isEjecutando()) {
                explosiones.remove(i);
            }
        }
        if (finJuego&&!TGameOver.isEjecutando()) {
            Ventana.cambiarVentana(new VentanaMenu());
        }
        if (!aparecerUfo.isEjecutando()) {
            aparecerUfo.Empezar(Constantes.TiempoAparecerUfo);
            spawnUfo();
        }
        TGameOver.actualizar();
        aparecerUfo.actualizar();
        
        //en este for si no hay ningun meteoro continuara a la linea de empezarEntrega
        for (int i = 0; i < objetosmoviles.size(); i++) {
            if (objetosmoviles.get(i) instanceof Meteoros) {
                return;
            }
        }

        empezarEntrega();
 
    }

    @Override
    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //mejora la vista del los objetos
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        
         for (int i = 0; i < mensajes.size(); i++) {
            mensajes.get(i).dibujar(g2d);
             if (mensajes.get(i).isMuerte()) {
                 mensajes.remove(i);
             }
        }
        for (int i = 0; i < objetosmoviles.size(); i++) {
            objetosmoviles.get(i).dibujar(g);
        }

        for (int i = 0; i < explosiones.size(); i++) {
            Animacion animacion = explosiones.get(i);

            g2d.drawImage(animacion.getFrameActual(), (int) animacion.getPosicion().getX(),
                    (int) animacion.getPosicion().getY(), null);

        }
        DibujarPuntuacion(g);
        DibujarVidas(g);
       
    }

    private void DibujarPuntuacion(Graphics g) {
        Vectores pos = new Vectores(850, 25);

        String PuntajeText = Integer.toString(puntos);

        for (int i = 0; i < PuntajeText.length(); i++) {
            g.drawImage(Externos.numeros[Integer.parseInt(PuntajeText.substring(i, i + 1))],
                    (int) pos.getX(), (int) pos.getY(), null);

            pos.setX(pos.getX() + 20);
        }
    }

    private void DibujarVidas(Graphics g) {
        
        if (vidas<1) {
            return; 
        }
        Vectores posV = new Vectores(25, 25);

        g.drawImage(Externos.vida, (int) posV.getX(), (int) posV.getY(), null);

        g.drawImage(Externos.numeros[10], (int) posV.getX() + 40, 
                (int) posV.getY() + 5, null);
        //// método Integer.toString devuelve 
        //la representación de cadena del argumento es decir 
        String vidasText=Integer.toString(vidas);
        
        Vectores pos=new Vectores(posV.getX(), posV.getY());
        
        for (int i = 0; i < vidasText.length(); i++) {
            
            int numero=Integer.parseInt(vidasText.substring(i, i + 1));
            
            if (numero<=0) {
                break;
            }
            g.drawImage(Externos.numeros[numero], (int)pos.getX()+ 60,
                   (int) pos.getY()+3, null);
            pos.setX(pos.getX() + 20);
        }
    }

    public ArrayList<ObjetosMovibles> getObjetosmoviles() {
        return objetosmoviles;
    }

    public void setObjetosmoviles(ArrayList<ObjetosMovibles> objetosmoviles) {
        this.objetosmoviles = objetosmoviles;
    }
 public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }
    public Jugador getJugador() {
        return jugador;
    }
    public boolean RestarVidas(){
        vidas--;
        return vidas>0;
    }
    public void gameOver(){
        
        this.mensajes.add(new Mensaje(
                
                "Game Over",
                PosicionInicial,
                Color.RED,
                true,
                true,
                Externos.Mfuente));
        TGameOver.Empezar(Constantes.TiempoFinal);
        finJuego=true;
        
    }

}
