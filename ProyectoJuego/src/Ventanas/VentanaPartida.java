/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Entrada.Teclado;
import EntradaSalida.DatosPuntaje;
import EntradaSalida.XMLParser;
import Graficos.Animacion;
import Graficos.Externos;
import Graficos.Sonido;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import ObjetosMoviles.Enemigos.Nostromo;
import ObjetosMoviles.Jugador;
import ObjetosMoviles.Mensaje;
import ObjetosMoviles.Meteoros;
import ObjetosMoviles.ObjetosMovibles;
import ObjetosMoviles.Planetas;
import ObjetosMoviles.PowerUp;
import ObjetosMoviles.Tamaños;
import ObjetosMoviles.TiposPowerUP;
import ObjetosMoviles.Enemigos.Ufo;
import ObjetosMoviles.Enemigos.Venator;
import Ui.Accion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class VentanaPartida extends Ventana {

    VentanaPausa ventanaPausa;
    public static final Vectores PosicionInicial
            = new Vectores(Constantes.ancho / 2 - Externos.jugadores[0].getWidth() / 2,
                    Constantes.alto / 2 - Externos.jugadores[0].getWidth() / 2);

    private Jugador jugador;

//creamos un arreglo de tipo objetomovibles quese encargara de actualizar y dibujar todos los objetosmovibles
    private ArrayList<ObjetosMovibles> objetosmoviles = new ArrayList<ObjetosMovibles>();
    //creamos un arayList de animacion
    private ArrayList<Animacion> explosiones = new ArrayList<Animacion>();
    double angulo = 0;
    //
    private ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

    private int puntos = 0;
    private int vidas = 3;

    //numero de meteoros por partida
    private int meteoros;
    private int entregas = 1;

    private Sonido musicaFondo, sumarP;
    //private Cronometro TGameOver;
    private long TGameOver;
    private boolean finJuego;
    private boolean AparecePlaneta;
    public String nombre;

    //private Cronometro aparecerUfo;
    private long pausa;
    private long aparecerUfo;
    private long aparecerPowerUP;
    private BufferedImage apariencia;

    public VentanaPartida(String nombre, BufferedImage apariencia, ProyectoJuego p) {
        super(p);
        this.nombre = nombre;
        this.apariencia = apariencia;
        //jugador
        jugador = new Jugador(apariencia,
                new Vectores(Constantes.ancho / 2 - apariencia.getWidth() / 2,
                        Constantes.alto / 2 - apariencia.getHeight() / 2),
                new Vectores(0, 0), 7, this);

        finJuego = false;

        objetosmoviles.add(jugador);

        meteoros = 1;
        empezarEntrega();
        //musica de fondo
        musicaFondo = new Sonido(Externos.MusicaFondo);
        musicaFondo.MusicaFondo();
        musicaFondo.cambiarVolumen(-10.0f);

        sumarP = new Sonido(Externos.sodidoMasPuntos);
        sumarP.cambiarVolumen(-10.0f);

        TGameOver = 0;
        aparecerUfo = 0;
        aparecerPowerUP = 0;
        pausa = 500;
    }

    public VentanaPartida(ProyectoJuego p) {
        super(p);
    }

    public void SumarPuntos(int valor, Vectores posicion) {
        if (jugador.isGanarPuntos()) {

            puntos += valor;
            Color c = Color.WHITE;

            String texto = "+" + valor + " Puntos";

            if (jugador.isDoblePuntajeActivo()) {
                c = Color.GREEN;
                valor = valor * 2;
                texto = "+" + valor * 2 + " Puntos";
            }
            puntos += valor;
            sumarP.play();
            mensajes.add(new Mensaje(
                    texto,
                    posicion,
                    c,
                    false,
                    true,
                    Externos.Mfuente));
        } else {
            jugador.setGanarPuntos(true);
        }
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
                " Encargo " + entregas,
                new Vectores(Constantes.ancho / 2, Constantes.alto / 2),
                Color.GREEN,
                true,
                false,
                Externos.Gfuente));

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

        explosiones.add(new Animacion(Externos.explosion2,
                25,
                //le restamos a la posicion la mitad del ancho y la altura de la imagen para que asi la animacion este en el centro
                posicion.RestaVectores(new Vectores(Externos.explosion2[0].getWidth() / 2, Externos.explosion2[0].getHeight() / 2))));
    }

    private void spawnEnemigo() {
        //int probabilidad = (int) (Math.random() * 4 + 1);
        int probabilidad = 4;
        int randio = (int) (Math.random() * 2);

        double x = randio == 0 ? (Math.random() * Constantes.ancho) : 0;
        double y = randio == 0 ? 0 : (Math.random() * Constantes.alto);

        if (probabilidad == 3 || probabilidad == 4) {
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
            if (probabilidad == 3) {
                objetosmoviles.add(new Ufo(Externos.Ufo[(int) (Math.random() * 2)],
                        new Vectores(x, y),
                        new Vectores(),
                        Constantes.MaxVelUfo,
                        this,
                        caminos));
            } else {
                objetosmoviles.add(new Venator(Externos.venator,
                        new Vectores(x, y),
                        new Vectores(),
                        Constantes.MaxVelVen,
                        this,
                        caminos));
            }

        } else if (probabilidad == 2) {
            objetosmoviles.add(new Nostromo(Externos.nostromo,
                    new Vectores(x, y),
                    new Vectores(),
                    Constantes.MaxVelUfo,
                    this));
        }

        /*  */
    }

    /*
    private void spanwNostromo() {
        int randio = (int) (Math.random() * 2);

        double x = randio == 0 ? (Math.random() * Constantes.ancho) : 0;
        double y = randio == 0 ? 0 : (Math.random() * Constantes.alto);

        //  ArrayList<Vectores> caminos = new ArrayList<Vectores>();
      
    }*/
//aqui espera al ibjeto pausa lock
/*
    private void esperarSiPausado() {
        System.out.println("p11");
        while (!ventanaPausa.isPausar()) {
            System.out.println("p12");
            System.out.println("entrando en synchronized (ventanaPausa.pauseLock");
            synchronized (ventanaPausa.pauseLock) {
                System.out.println("pausado en el metodo");
                try {
                    ventanaPausa.pauseLock.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

        }
    }
     */
    public void spawnPowerUp(Vectores posicion) {

        int index = (int) (Math.random() * (TiposPowerUP.values().length));

        TiposPowerUP tp = TiposPowerUP.values()[index];

        final String texto = tp.texto;
        Accion accion = null;

        switch (tp) {
            case VIDA:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        vidas++;
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.blue,
                                false,
                                false,
                                Externos.Mfuente));
                    }
                };

                break;
            case ESCUDO:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        jugador.setEscudoActivo();
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.CYAN,
                                false,
                                false,
                                Externos.Mfuente));
                    }
                };
                break;
            case PUNTOSX2:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        jugador.setDoblePuntajeActivo();
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.green,
                                false,
                                false,
                                Externos.Mfuente));
                    }
                };
                break;
            case FUEGO_RAPIDO:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        jugador.setFuegoRapidoActivo();
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.ORANGE,
                                false,
                                false,
                                Externos.Mfuente));

                    }
                };
                break;
            case GRAN_PUNTUACION:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        puntos += 1000;
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.MAGENTA,
                                false,
                                false,
                                Externos.Mfuente));
                    }
                };
                break;
            case DOBLE_GUN:
                accion = new Accion() {
                    @Override
                    public void hacerAccion() {
                        jugador.setDobleGunActivo();
                        mensajes.add(new Mensaje(texto,
                                posicion,
                                Color.lightGray,
                                false,
                                false,
                                Externos.Mfuente));
                    }
                };
                break;

            default:
                throw new AssertionError();
        }
        this.objetosmoviles.add(new PowerUp(tp.textura,
                posicion,
                this,
                accion, tp.orbe));
    }
//aqui en actualizar al oprimir la tecla "P"

    @Override
    public void actualizar(float dt) {

        if (Teclado.pausa) {

            if (p.isPausa()) {
                p.continuar();
            } else {
                dt = 0;
                p.pausar();
            }
            p.setPausa(!p.isPausa());
        }
        //  angulo += Constantes.anguloBase / 2;
        pausa += dt;
        if (finJuego) {
            TGameOver += dt;

        }
        if (Teclado.salir) {
            gameOver();
        }
        aparecerUfo += dt;
        if (!p.isPausa()) {

            //actualisamos los objetos moviles
            for (int i = 0; i < objetosmoviles.size(); i++) {
                ObjetosMovibles ob = objetosmoviles.get(i);
                ob.actualizar(dt);
                //si esta muerto lo borra y se le resta a la i debido a que al borrar
                //un objeto todos los de su derecha avansan un paso a la izquierda
                //y el ultimo puesto de la derecha a hora basio lo elimina
                if (ob.isMuerte()) {
                    objetosmoviles.remove(i);
                    i--;
                }

            }

            for (int i = 0; i < explosiones.size(); i++) {
                Animacion animacion = explosiones.get(i);
                animacion.actualizar(dt);
                //si la animacion no esta ejecutando la elimino
                if (!animacion.isEjecutando()) {
                    explosiones.remove(i);
                }
            }
        }
        if (TGameOver > Constantes.TiempoFinal) {
            //boolean yagrabado = false;
            try {
                ArrayList<DatosPuntaje> listaDatos = XMLParser.LeerFichero();

                listaDatos.add(new DatosPuntaje(nombre, puntos));

                XMLParser.escribirFichero(listaDatos);

            } catch (ParserConfigurationException ex) {
                Logger.getLogger(VentanaPartida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(VentanaPartida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPartida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(VentanaPartida.class.getName()).log(Level.SEVERE, null, ex);
            }

            Ventana.cambiarVentana(new VentanaMenu(p));
        }

        if (aparecerUfo > Constantes.TiempoAparecerUfo) {
            spawnEnemigo();
            aparecerUfo = 0;
        }

        //en este for si no hay ningun meteoro continuara a la linea de empezarEntrega
        for (int i = 0; i < objetosmoviles.size(); i++) {

            if (!(objetosmoviles.get(i) instanceof Jugador) && !(objetosmoviles.get(i) instanceof Meteoros)) {
                if (objetosmoviles.get(i).getPosicion().getX() < -objetosmoviles.get(i).getImgancho() || objetosmoviles.get(i).getPosicion().getX() > Constantes.ancho + objetosmoviles.get(i).getImgancho()
                        || objetosmoviles.get(i).getPosicion().getY() < -objetosmoviles.get(i).getImgalto() || objetosmoviles.get(i).getPosicion().getY() > Constantes.alto + objetosmoviles.get(i).getImgalto()) {
                    if (!(objetosmoviles.get(i) instanceof Planetas)) {
                        objetosmoviles.get(i).Destruir();
                    }
                }
            }
            if (objetosmoviles.get(i) instanceof Meteoros) {
                return;
            }
        }

        if (TGameOver > Constantes.TiempoFinal) {

        }

        empezarEntrega();
    }

    @Override
    public void dibujar(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //mejora la vista del los objetos
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (p.isPausa()) {
            Texto.DibujarTexto(g,
                    "Seleccione la Skin",
                    new Vectores(Constantes.ancho / 2 - apariencia.getWidth() / 2,
                            Constantes.alto / 2 - apariencia.getHeight() / 2),
                    true,
                    Color.YELLOW,
                    Externos.Gfuente);
        }

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
            AffineTransform atexplosion = AffineTransform.getTranslateInstance(
                    animacion.getPosicion().getX(),
                    animacion.getPosicion().getY());
            atexplosion.rotate(angulo, animacion.getFrameActual().getWidth() / 2, animacion.getFrameActual().getHeight() / 2);

            g2d.drawImage(animacion.getFrameActual(), atexplosion, null);

        }
        DibujarPuntuacion(g);
        DibujarVidas(g);

    }

    private void DibujarPuntuacion(Graphics g) {
        Vectores pos = new Vectores(1050, 25);

        String PuntajeText = Integer.toString(puntos);

        for (int i = 0; i < PuntajeText.length(); i++) {
            g.drawImage(Externos.cambiarTamaño(Externos.numeros[Integer.parseInt(PuntajeText.substring(i, i + 1))], 40, 40),
                    (int) pos.getX(), (int) pos.getY(), null);

            pos.setX(pos.getX() + 50);
        }
    }

    private void DibujarVidas(Graphics g) {

        if (vidas < 1) {
            return;
        }
        Vectores posV = new Vectores(25, 25);

        BufferedImage vida = Externos.cambiarTamaño(apariencia, 50, 50);
        g.drawImage(vida, (int) posV.getX(), (int) posV.getY(), null);

        g.drawImage(Externos.cambiarTamaño(Externos.numeros[10], 40, 40), (int) posV.getX() + 70,
                (int) posV.getY() + 5, null);
        //// método Integer.toString devuelve 
        //la representación de cadena del argumento es decir 
        String vidasText = Integer.toString(vidas);

        Vectores pos = new Vectores(posV.getX(), posV.getY());

        for (int i = 0; i < vidasText.length(); i++) {

            int numero = Integer.parseInt(vidasText.substring(i, i + 1));

            if (numero <= 0) {
                break;
            }
            g.drawImage(Externos.cambiarTamaño(Externos.numeros[numero], 40, 40), (int) pos.getX() + 120,
                    (int) pos.getY() + 3, null);
            pos.setX(pos.getX() + +50);
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

    public boolean RestarVidas(Vectores posicion) {

        vidas--;

        Mensaje perdida = new Mensaje("-1 VIDA",
                posicion,
                Color.RED,
                false,
                false,
                Externos.Mfuente);
        mensajes.add(perdida);

        return vidas > 0;
    }

    public void gameOver() {

        this.mensajes.add(new Mensaje(
                "Game Over",
                PosicionInicial,
                Color.RED,
                true,
                true,
                Externos.Mfuente));
        musicaFondo.parar();
        finJuego = true;

    }
}
