/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import ObjetosMoviles.Disparos.Lacer;
import Entrada.Teclado;
import Graficos.Animacion;
import Graficos.Externos;
import static Graficos.Externos.purpuraLaser;
import Graficos.Sonido;
import Matematicas.Vectores;
import Ventanas.VentanaControl;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
public class Jugador extends ObjetosMovibles {

    private Vectores direccion;
    //cambio de velocidad con respecto al tiempo
    private Vectores aceleracion;
    private boolean aparecer, visible;

    // private Cronometro TAparecer, Tparpadeo;
    private long fuego, TAparecer, Tparpadeo, TEscudo, TDoblePuntaje, TFuegoRapido, TDobleGun;

    private boolean avansado = false;

    //limitar la velocidad de disparo
    // private long tiempo,TPasado;
    //private Cronometro fuego;
    //sonido
    private Sonido Sdisparar, SPerdida, SGun;

    private boolean escudoActivo, doblePuntajeActivo, fuegoRapidoActivo, dobleGunActivo, ganarPuntos;

    private Animacion efectoEscudo;

    private long velocidadFuego;

    public Vectores getDireccion() {
        return direccion;
    }

    public Jugador(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);

        direccion = new Vectores(0, 1);
        aceleracion = new Vectores();
        // tiempo=0;
        ///TPasado=System.currentTimeMillis();
        fuego = 0;
        TAparecer = 0;
        Tparpadeo = 0;
        TEscudo = 0;
        TFuegoRapido = 0;
        TDobleGun = 0;

        Sdisparar = new Sonido(Externos.DisparoJugador);
        Sdisparar.cambiarVolumen(-11.0f);
        SGun = new Sonido(Externos.disparoGun);
        SPerdida = new Sonido(Externos.PerdidaJugador);

        efectoEscudo = new Animacion(Externos.efectoEscudo, 80, null);

        visible = true;
        ganarPuntos = true;
    }

    public Jugador(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaControl ventanaControl) {
        super(textura, posicion, velocidad, maxVel, ventanaControl);

        direccion = new Vectores(0, 1);
        aceleracion = new Vectores();
        // tiempo=0;
        ///TPasado=System.currentTimeMillis();
        fuego = 0;
        TAparecer = 0;
        Tparpadeo = 0;
        TEscudo = 0;
        TFuegoRapido = 0;
        TDobleGun = 0;

        Sdisparar = new Sonido(Externos.DisparoJugador);
        Sdisparar.cambiarVolumen(-11.0f);
        SPerdida = new Sonido(Externos.PerdidaJugador);

        efectoEscudo = new Animacion(Externos.efectoEscudo, 80, null);

        visible = true;
        ganarPuntos = true;
    }

    public boolean isGanarPuntos() {
        return ganarPuntos;
    }

    public void setGanarPuntos(boolean ganarPuntos) {
        this.ganarPuntos = ganarPuntos;
    }

    @Override
    public void actualizar(float dt) {

        fuego += dt;
        //------------duracion de los poderes al ser activados
        if (escudoActivo) {
            TEscudo += dt;
        }
        if (doblePuntajeActivo) {
            TDoblePuntaje += dt;
        }
        if (fuegoRapidoActivo) {
            velocidadFuego = Constantes.TDisparo / 2;
            TFuegoRapido += dt;
        } else {
            velocidadFuego = Constantes.TDisparo;
        }

        if (dobleGunActivo) {
            TDobleGun += dt;
        }
        //-----------------parar los efectos
        if (TEscudo > Constantes.TiempoEscudo*6 ) {
            escudoActivo = false;
            TEscudo = 0;

        }
        if (TDoblePuntaje > Constantes.TiempoDoblePuntaje) {
            doblePuntajeActivo = false;
            TDoblePuntaje = 0;
        }
        if (TFuegoRapido > Constantes.TiempoFuegoRapido) {
            fuegoRapidoActivo = false;
            TFuegoRapido = 0;
        }
        if (TDobleGun > Constantes.TiempoDobleGun) {
            dobleGunActivo = false;
            TDobleGun = 0;
        }
//aparecer despues de perder una vida
        if (aparecer) {
            Tparpadeo += dt;
            TAparecer += dt;
            //si es verdadero "visible pasa a false y viseversa obteniendo el efecto parpadeo
            if (Tparpadeo > Constantes.TiempoParpadeo) {
                visible = !visible;
                Tparpadeo = 0;
            }
            if (TAparecer > Constantes.TiempoAparecerJugador) {
                aparecer = false;
                visible = true;
            }
        }
        //-----------disparar
        if (Teclado.disparar && fuego > velocidadFuego & !aparecer) {
            if (dobleGunActivo) {
                Vectores gunDerecho = CentroImagen();
                Vectores gunIzquierdo = CentroImagen();

                Vectores temp = new Vectores(direccion);
                temp.NormalizarVector();
                temp = temp.calcularDireccion(angulo - 1.3f);
                temp = temp.MultiplicarVector(imgancho);
                gunIzquierdo = gunIzquierdo.SumaVectores(temp);

                temp = temp.calcularDireccion(angulo - 1.9f);
                gunDerecho = gunDerecho.SumaVectores(temp);

                Lacer d = new Lacer(Externos.gunLaser, gunDerecho,
                        direccion, Constantes.Velocidad_lac, angulo,
                        ventanapartida, false);

                Lacer i = new Lacer(Externos.gunLaser, gunIzquierdo,
                        direccion, Constantes.Velocidad_lac, angulo,
                        ventanapartida, false);
                //el cero antes de introducir un nuevo laser significa que el laser se va a agregar primero y no al final  
                ventanapartida.getObjetosmoviles().add(0, d);
                ventanapartida.getObjetosmoviles().add(0, i);
                SGun.play();
                 if (SGun.getFramePsition() > 8500) {
            SGun.parar();
        }
            } else {
                if (ventanapartida != null) {
                    ventanapartida.getObjetosmoviles().add(0, new Lacer(
                            Externos.greenLaser,
                            CentroImagen().SumaVectores(direccion.MultiplicarVector(imgancho)),
                            direccion, Constantes.Velocidad_lac,
                            angulo, ventanapartida, false));
                } else {

                    Lacer l = new Lacer(
                            Externos.greenLaser,
                            CentroImagen().SumaVectores(direccion.MultiplicarVector(imgancho)),
                            direccion, Constantes.Velocidad_lac, angulo,
                            ventanaControl, false);
                    ventanaControl.getObjetosmoviles().add(0, l);
                    ArrayList<ObjetosMovibles> obj = ventanaControl.getObjetosmoviles();
                    for (int i = 0; i < obj.size(); i++) {
                        ObjetosMovibles o = obj.get(i);
                        if (o instanceof Jugador) {
                            System.out.println("objeto jugador");
                        } else {
                            System.out.println("objeto laser");
                        }

                    }

                }
                Sdisparar.play();
                if (Sdisparar.getFramePsition() > 8500) {
                    Sdisparar.parar();
                }
            }
            fuego = 0;
        }
        if (Sdisparar.getFramePsition() > 8500) {
            Sdisparar.parar();
        }

        if (Teclado.derecha) {
            angulo += Constantes.anguloBase;
        }
        if (Teclado.izquierda) {
            angulo -= Constantes.anguloBase;
        }
        if (Teclado.arriba) {
            aceleracion = direccion.MultiplicarVector(Constantes.ACC);
            avansado = true;
        } else if (Teclado.abajo) {
            aceleracion = direccion.MultiplicarVector(-Constantes.ACC);
            avansado = false;
        } else {
            /*igualar la aceleracion a un vector opuesto a la velocidad pero 
            con magnitud igual a la constante aceleracion*/
            if (velocidad.Magnitud() != 0) {
                aceleracion = (velocidad.MultiplicarVector(-1).NormalizarVector())
                        .MultiplicarVector(Constantes.ACC / 2);
                avansado = false;
            }
        }

        //la aceleracion representara el cambio de velocidad con respecto al tiempo
        velocidad = velocidad.SumaVectores(aceleracion);
        velocidad = velocidad.velocidadlimite(maxVel);
        //para que la nave comiense de frete restamos 90 grados es decir PI
        //ya que java trabaja en radianes
        direccion = direccion.calcularDireccion(angulo - Math.PI / 2);

        /*la velocidad es representada como el cambio de posicion con respecto
        al tiempo por lo tanto cada fotograma le sumariamos el vetor velocidad 
        al vector posicion*/
        posicion = posicion.SumaVectores(velocidad);

        if (escudoActivo) {
            efectoEscudo.actualizar(dt);
        }
        //evitamos que el jugador salga de la pantalla
        if (ventanapartida != null) {

            LimitarPantalla();
            ColisionaCon();
        }

    }

    public boolean isEscudoActivo() {
        return escudoActivo;
    }

    public void setEscudoActivo() {
        if (escudoActivo) {
            TEscudo = 0;
        }
        escudoActivo = true;
    }

    public boolean isDoblePuntajeActivo() {
        return doblePuntajeActivo;
    }

    public void setDoblePuntajeActivo() {
        if (doblePuntajeActivo) {
            TDoblePuntaje = 0;

        }
        doblePuntajeActivo = true;
    }

    public boolean isFuegoRapidoActivo() {
        return fuegoRapidoActivo;
    }

    public void setFuegoRapidoActivo() {
        if (fuegoRapidoActivo) {
            TFuegoRapido = 0;
        }
        fuegoRapidoActivo = true;
    }

    public boolean isDobleGunActivo() {
        return dobleGunActivo;
    }

    public void setDobleGunActivo() {
        if (dobleGunActivo) {
            TDobleGun = 0;
        }
        this.dobleGunActivo = true;
    }

    @Override
    public void Destruir() {

        aparecer = true;
        ventanapartida.Explotar(posicion);
        TAparecer = 0;
        SPerdida.play();
        if (!ventanapartida.RestarVidas(posicion)) {
            ventanapartida.gameOver();
            super.Destruir();
        }
        ReiniciarValor();
    }

    private void ReiniciarValor() {
        angulo = 0;
        velocidad = new Vectores();
        posicion = VentanaPartida.PosicionInicial;
    }

    @Override
    public void dibujar(Graphics g) {
        if (!visible) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        //-----------------Propulsion----------
        AffineTransform at1 = AffineTransform.getTranslateInstance(posicion.getX() + imgancho / 2 + 5,
                posicion.getY() + imgalto / 2 + 10);
        //dibujamos a partir de la exquina superior izquierda
        AffineTransform at2 = AffineTransform.getTranslateInstance(posicion.getX() + 5,
                posicion.getY() + imgalto / 2 + 10);
        at1.rotate(angulo, -5, -10);
        //lo trasla damos al medio por eso dividimos x entre 2
        at2.rotate(angulo, imgancho / 2 - 5, -10);
        if (avansado) {
            g2d.drawImage(Externos.propulsion, at1, null);
            g2d.drawImage(Externos.propulsion, at2, null);
        }
        //----------------Escudo----------------
        if (escudoActivo) {
            
            BufferedImage frameActual = efectoEscudo.getFrameActual();
            AffineTransform at3 = AffineTransform.getTranslateInstance(
                    posicion.getX() - frameActual.getWidth() / 2 + imgancho / 2,
                    posicion.getY() - frameActual.getHeight() / 2 + imgalto / 2);
            at3.rotate(angulo, frameActual.getWidth() / 2, frameActual.getHeight() / 2);
            g2d.drawImage(efectoEscudo.getFrameActual(), at3, null);
        }
        //-----------------jugador y Guns---------------
        at = AffineTransform.getTranslateInstance(posicion.getX(), posicion.getY());
        //punto de rotacion : optenemos el ancho y lo dividimos entre 2        
        at.rotate(angulo, imgancho / 2, imgalto / 2);
        if (dobleGunActivo) {
            AffineTransform atGunI = AffineTransform.getTranslateInstance(posicion.getX() + imgancho / 2 + 5,
                    posicion.getY() + imgalto / 2 - 5);
            AffineTransform atGunD = AffineTransform.getTranslateInstance(posicion.getX() + 1,
                    posicion.getY() + imgalto / 2 - 5);
            atGunI.rotate(angulo + Math.PI, -5, 5);
            atGunD.rotate(angulo + Math.PI, imgancho / 2 - 1, 5);
            g2d.drawImage(Externos.gun, atGunD, null);
            g2d.drawImage(Externos.gun, atGunI, null);
        }
        g2d.drawImage(textura, at, null);
    }

    public boolean isAparecer() {
        return aparecer;
    }

    public Vectores JugadorgetVelocidad() {
        return velocidad;
    }

}
