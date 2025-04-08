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

  //  VentanaPausa ventanaPausa;
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
        /*si el jugador esta ganando puntos sumamos los puntos obtenidos al
        destruir meteoros y enemigos y mostramos un mensaje en la posicion del
        objeto destruido*/
        if (jugador.isGanarPuntos()) {

            /*si el jugador tiene el poder del doblePuntaje entonces el color 
            de las letras del mensaje sera verde en vez de blanco, ademas
            de ganar el doble de puntos. y por ultimo metemos nuestro mensaje 
            en nuestro ArrayList de Mensajes*/
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
            /*pregunto si x es par si es verdadero digo que x es igual a un 
            numero aleatorio entre cero y el ancho de la ventana sino
            me devuelve 0*/       
            x = 1 % 2 == 0 ? Math.random() * Constantes.ancho : 0;
            //si y es par sera cero 
            y = 1 % 2 == 0 ? 0 : Math.random() * Constantes.alto;
            /*la imagen sera una imagen grande elegido al azar entre cero y el 
            tamaño del array grandes*/
            BufferedImage textura = Externos.grades[(int) (Math.random() * 
                    Externos.grades.length)];

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
        //se seleciona un nuemro entre 1 y 4 al azar
      int probabilidad = (int) (Math.random() * 4 + 1);
       
        //se seleciona un numero entre cero y dos
        int randio = (int) (Math.random() * 2);
 /*si X es par se seleciona un numero al azar entre 0 y el ancho de la ventana
        sino sera 0, en caso del eje Y es el caso contrario si es par sera 0 y
        sino se elige un numero al azar entre 0 y la altura de la ventana*/
        double x = randio == 0 ? (Math.random() * Constantes.ancho) : 0;
        double y = randio == 0 ? 0 : (Math.random() * Constantes.alto);
/*si la probabilidad es 3 0 cuatro creamos cuatro vectores para
        nuestro ArayList de vectores*/
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
            /*si la probabilidad es 3  se crea un objeto ufo, con la 
            apariencia al azar*/
            if (probabilidad == 3) {
              objetosmoviles.add(new Ufo(Externos.Ufo[(int) (Math.random() * 2)],
                        new Vectores(x, y), new Vectores(),Constantes.MaxVelUfo,
                        this,
                        caminos));
            } else {
                //sino un objeto Venator
                objetosmoviles.add(new Venator(Externos.venator,
                        new Vectores(x, y),new Vectores(),Constantes.MaxVelVen,
                        this,
                        caminos));
            }
//si la probabilidad resulto ser 2 entonces creamos nuestro objeto Nostromo 
        } else if (probabilidad == 2) {
            objetosmoviles.add(new Nostromo(Externos.nostromo,new Vectores(x, y),
                    new Vectores(),
                    Constantes.MaxVelUfo,
                    this));
        }
        /* si no es ninguna de las anteriores no se creea ningun objeto */
    }

    public void spawnPowerUp(Vectores posicion) {
    /*se selecciona un numero al azar entre sero y el nuemro de objetos de 
        nuestro Enum*/ 
        int index = (int) (Math.random() * (TiposPowerUP.values().length));
     //se abre el objeto de TiposPowerUP que correponde con esa identificacion
        TiposPowerUP tp = TiposPowerUP.values()[index];

        //sacamos texto de nuestro tp que seria el mensaje a mostrar
        final String texto = tp.texto;
        Accion accion = null;
 /*usamos un switch que dependiendo del tipo de poder sus 
        acciones seran diferentes */
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
        //despues metemos nuestro ppwer un en nuestro ArrayList de objetos
        this.objetosmoviles.add(new PowerUp(tp.textura,
                posicion,
                this,
                accion, tp.orbe));
    }
//aqui en actualizar al oprimir la tecla "P"

    @Override
    public void actualizar(float dt) {
        
        //  angulo += Constantes.anguloBase / 2;
        pausa += dt;
      //si el jugador se quedo sin vidas comiensa el 
        if (finJuego) {
            TGameOver += dt;
        }
        if (Teclado.salir) {
            gameOver();
        }
        aparecerUfo += dt;

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
//animaciones
            for (int i = 0; i < explosiones.size(); i++) {
                Animacion animacion = explosiones.get(i);
                animacion.actualizar(dt);
                //si la animacion no esta ejecutando la elimino
                if (!animacion.isEjecutando()) {
                    explosiones.remove(i);
                }
            }
        
        if (TGameOver > Constantes.TiempoFinal) {
            try {
                /*leo los puntajes del fichero XML y lo meto en un ArrayList
                despues introdusco */
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
            Ventana.cambiarVentana(new VentanaMenu(p,true));
        }
        if (aparecerUfo > Constantes.TiempoAparecerUfo/2) {
            spawnEnemigo();
            aparecerUfo = 0;
        }
        //en este for si no hay ningun meteoro continuara a la linea de empezarEntrega
        for (int i = 0; i < objetosmoviles.size(); i++) {
/*si el objeto np es un objeto de la clase jugador ni es un meteoro*/
            if (!(objetosmoviles.get(i) instanceof Jugador) && 
                    !(objetosmoviles.get(i) instanceof Meteoros)) {
                /*si la posision del objeto sale por alguno de los lados de
                la ventana se destruye*/
                if (objetosmoviles.get(i).getPosicion().getX() 
                        < -objetosmoviles.get(i).getImgancho() || 
                        objetosmoviles.get(i).getPosicion().getX() >
                        Constantes.ancho + objetosmoviles.get(i).getImgancho()
                        || objetosmoviles.get(i).getPosicion().getY() 
                        < -objetosmoviles.get(i).getImgalto() || 
                        objetosmoviles.get(i).getPosicion().getY() >
                        Constantes.alto + objetosmoviles.get(i).getImgalto()) {
                    
                    if (!(objetosmoviles.get(i) instanceof Planetas)) {
                        objetosmoviles.get(i).Destruir();
                    }
                }
            }
            //si hay un objeto meteoro sale del for para no terminar la entrega
            if (objetosmoviles.get(i) instanceof Meteoros) {
                return;
            }
        }
        //empesamos una nueva entrega
        empezarEntrega();
    }
 /*   if (p.isPausa()) {
            Texto.DibujarTexto(g,
                    "Seleccione la Skin",
                    new Vectores(Constantes.ancho / 2 - apariencia.getWidth() / 2,
                            Constantes.alto / 2 - apariencia.getHeight() / 2),
                    true,
                    Color.YELLOW,
                    Externos.Gfuente);
        }*/
    @Override
    public void dibujar(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //mejora la vista del los objetos
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
   
       //ArrayList de mensajes qe se muestran en pantalla
        for (int i = 0; i < mensajes.size(); i++) {
            mensajes.get(i).dibujar(g2d);
            if (mensajes.get(i).isMuerte()) {
                mensajes.remove(i);
            }
        }
        //ArrayList de objetos que se muestran en pantalla
        for (int i = 0; i < objetosmoviles.size(); i++) {
            objetosmoviles.get(i).dibujar(g);
        }
//ArrayList de Explosiones que se muestran en pantalla
        for (int i = 0; i < explosiones.size(); i++) {
            Animacion animacion = explosiones.get(i);
            AffineTransform atexplosion = AffineTransform.getTranslateInstance(
                    animacion.getPosicion().getX(),
                    animacion.getPosicion().getY());
            atexplosion.rotate(angulo, animacion.getFrameActual().getWidth() / 2,
                    animacion.getFrameActual().getHeight() / 2);

            g2d.drawImage(animacion.getFrameActual(), atexplosion, null);

        }
        DibujarPuntuacion(g);
        DibujarVidas(g);

    }

    private void DibujarPuntuacion(Graphics g) {
        //posicion de puntuacion
        Vectores pos = new Vectores(1050, 25);
         /*metodo Integer.toString devuelve la representación de cadena del 
        argumento es decir  convierte el
        número de vidas en un String para poder recorrerlo dígito por digito.*/
        String PuntajeText = Integer.toString(puntos);

        for (int i = 0; i < PuntajeText.length(); i++) {
            //Se dibuja la imagen que corresponde al numero de la posicion
            g.drawImage(Externos.cambiarTamaño(Externos.numeros[
                    Integer.parseInt(PuntajeText.substring(i, i + 1))], 40, 40),
                    (int) pos.getX(), (int) pos.getY(), null);
   // Se mueve la posición del eje X para el siguiente numero
            pos.setX(pos.getX() + 50);
        }
    }

    private void DibujarVidas(Graphics g) {
        //si las vidas son menor que 1 regresan al principio
        if (vidas < 1) {
            return;
        }
        Vectores posV = new Vectores(25, 25);
        //dibujamos la skins de nuestro jugador
        BufferedImage vida = Externos.cambiarTamaño(apariencia, 50, 50);
        g.drawImage(vida, (int) posV.getX(), (int) posV.getY(), null);

        //dibujamos la X para saber el numero de vidas
        g.drawImage(Externos.cambiarTamaño(Externos.numeros[10], 40, 40),
                (int) posV.getX() + 70,(int) posV.getY() + 5, null);
        /*metodo Integer.toString devuelve la representación de cadena del 
        argumento es decir  convierte el
        número de vidas en un String para poder recorrerlo dígito por digito.*/
        String vidasText = Integer.toString(vidas);
        //la posicion donde estara el numero de vidas
        Vectores pos = new Vectores(posV.getX(), posV.getY());
    // Se recorre cada digito del numero de vidas para dibujarlo individualmente
        for (int i = 0; i < vidasText.length(); i++) {
            //sacamos el numero actual del estring
            int numero = Integer.parseInt(vidasText.substring(i, i + 1));
            if (numero <= 0) {
                break;
            }
         //Se dibuja la imagen que corresponde al numero de la posicion
            g.drawImage(Externos.cambiarTamaño(Externos.numeros[numero], 40, 40),
                    (int) pos.getX() + 120,
                    (int) pos.getY() + 3, null);
            // Se mueve la posición del eje X para el siguiente numero
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
       //resta 1 a las vidas cuando el jugador muere
        vidas--;
     /*salta un mensaje de perdida de vidas y agrea el mensaje al ArrayList
        de mensajes, despues retorna las vidas actuales si son mayor a 0 */
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
