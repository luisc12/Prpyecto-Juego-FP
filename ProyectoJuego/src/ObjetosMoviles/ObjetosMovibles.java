/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import ObjetosMoviles.Disparos.Lacer;
import ObjetosMoviles.Disparos.Disparos;
import ObjetosMoviles.Enemigos.Enemigos;
import Graficos.Externos;
import Graficos.Sonido;
import Matematicas.Vectores;
import Ventanas.VentanaControl;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public abstract class ObjetosMovibles {//extends ObjetosDelJuego

    protected BufferedImage textura;
    protected Vectores posicion;
    protected Vectores velocidad;
    //nos ayudara a rotar cual quier objeto que se mueva
    protected AffineTransform at;
    protected double angulo;
    //velocidad maxima
    protected double maxVel;
    protected int imgancho, imgalto;
    protected VentanaPartida ventanapartida;
protected VentanaControl ventanaControl;
    private Sonido explosion;

    private boolean muerte;

    public int getImgancho() {
        return imgancho;
    }

    public void setImgancho(int imgancho) {
        this.imgancho = imgancho;
    }

    public int getImgalto() {
        return imgalto;
    }

    public void setImgalto(int imgalto) {
        this.imgalto = imgalto;
    }

    public ObjetosMovibles(BufferedImage textura, Vectores posicion,
            Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        this.textura = textura;
        this.posicion = posicion;
        this.velocidad = velocidad;
        this.maxVel = maxVel;
        this.ventanapartida = ventanapartida;
        angulo = 0;
        imgancho = textura.getWidth();
        imgalto = textura.getHeight();
        explosion = new Sonido(Externos.Sonidoexplosion);
        explosion.cambiarVolumen(-9.0f);
        muerte = false;

    }

    public ObjetosMovibles(BufferedImage textura, Vectores posicion,
            Vectores velocidad, double maxVel, VentanaControl ventanaControl) {
        this.textura = textura;
        this.posicion = posicion;
        this.velocidad = velocidad;
        this.maxVel = maxVel;
        this.ventanaControl = ventanaControl;
        angulo = 0;
        imgancho = textura.getWidth();
        imgalto = textura.getHeight();
        explosion = new Sonido(Externos.Sonidoexplosion);
        explosion.cambiarVolumen(-9.0f);
        muerte = false;

    }
    public abstract void actualizar(float dt);

    public abstract void dibujar(Graphics g);

    public Vectores getPosicion() {
        return posicion;
    }

    public void setPosicion(Vectores posicion) {
        this.posicion = posicion;
        
    }
 
    protected void ColisionaCon() {
        //pedimos la lista de objetos moviles de la ventanapartida y hacemos un for
        ArrayList<ObjetosMovibles> objetosmovibles = ventanapartida.getObjetosmoviles();

        for (int i = 0; i < objetosmovibles.size(); i++) {
            ObjetosMovibles o = objetosmovibles.get(i);
            //si el objeto es el mismo que llama al metodo continua con el for
            if (o.equals(this)) {
                continue;
            }
            //para cañcular la distacia hay que restar los centros de las imagenes y sacar su mgnitud
            double distancia = o.CentroImagen().RestaVectores(CentroImagen()).Magnitud();

            //se pregunta si la distanciaes menor al la suma del ancho de los objetos
            //a demas de si estan muertos para que no alla colisiones fantasma
            if (distancia < o.imgancho / 2 + imgancho / 2 && objetosmovibles.contains(this)
                    && !o.muerte && !muerte) {
                ColisionObjetos(o, this);
            }
        }
    }

    protected void ColisionObjetos(ObjetosMovibles a, ObjetosMovibles b) {

        Jugador j = null;
        boolean enemigos = false;
        if (a instanceof Disparos) {
            if (((Disparos) a).isEnemigo()) {
                enemigos = true;
            }
        } else if (b instanceof Disparos) {
            if (((Disparos) b).isEnemigo()) {
                enemigos = true;
            }
        }
        if (a instanceof Jugador) {
            j = (Jugador) a;
        } else if (b instanceof Jugador) {
            j = (Jugador) b;
        }
        if (j != null && !enemigos && (a instanceof Disparos || b instanceof Disparos)) {
            return;
        }
        if (j != null && j.isAparecer()) {
            return;
        }
        if (a instanceof Meteoros && b instanceof Meteoros) {
            return;
        }
        //-----------------------------------------

        if (a instanceof Meteoros && b instanceof Lacer && enemigos || b instanceof Meteoros && b instanceof Lacer && enemigos) {
            return;
        }
        //si son ambos objetos enemigos no colisionan
        if (a instanceof Enemigos && b instanceof Enemigos || b instanceof Enemigos && a instanceof Enemigos) {
            return;
        }
        //los enemigos no colisionan con otros enemigos
        if (a instanceof Enemigos && b instanceof Meteoros || b instanceof Enemigos && a instanceof Meteoros) {
            return;
        }
        if (a instanceof Enemigos && b instanceof Disparos && enemigos || b instanceof Enemigos && a instanceof Disparos && enemigos) {
            return;
        }
        if (a instanceof Planetas && b instanceof Enemigos || b instanceof Planetas && a instanceof Enemigos
                || a instanceof Planetas && b instanceof Disparos || b instanceof Planetas && a instanceof Disparos) {
            return;
        }
        //-------------------------------------
        if (!(a instanceof PowerUp || b instanceof PowerUp)) {
            if (j != null) {
                j.setGanarPuntos(false);
            }
            a.Destruir();
            b.Destruir();
            return;
        }
        if (j != null) {
            if (a instanceof Jugador) {
                ((PowerUp) b).EjecutarAccion();
                b.Destruir();

            } else if (b instanceof Jugador) {
                ((PowerUp) a).EjecutarAccion();
                a.Destruir();
            }

        }
    }

    protected void choqueEscudo() {
        //posicion del jugador 
        Vectores PosicionJ = new Vectores(ventanapartida.getJugador().CentroImagen());
        //distacia del jugador con respecto al objeto movible
        int jugadorDistancia = (int) PosicionJ.RestaVectores(CentroImagen()).Magnitud();
        /*si la distancia entre el jugador es menor que la mitad del ancho + la 
        Constante de la distancia del escudo y ademas el escudo es ta activo,
        el objeto se destruye*/
        if (jugadorDistancia < Constantes.DistanciaEscudo / 2 + imgancho / 2) {
            if (ventanapartida.getJugador().isEscudoActivo()) {
                Destruir();
            }
        }
    }

    public Vectores FleeForce() {
        Vectores velocidadDeseada = ventanapartida.getJugador().
                CentroImagen().RestaVectores(CentroImagen());
        velocidadDeseada = (velocidadDeseada.
                velocidadlimite(Constantes.MaxVelocidadMeteor));
        Vectores v = new Vectores(velocidad);
        /*es el opuesto de la SeekForce por lo que se saca restando la
        velocidad del objeto con velocidadDeseada*/
        return v.RestaVectores(velocidadDeseada);
    }

    protected Vectores SeekForce(Vectores objetivo) {
        //velocidad deseada vector desde el UFO hacia el objetivo
        Vectores velocidadDeseada = objetivo.RestaVectores(CentroImagen());
        //tenemos que ajustar esa fuersa a la velocidad maxima del Ufo
        velocidadDeseada = velocidadDeseada.velocidadlimite(maxVel);
        //
        return velocidadDeseada.RestaVectores(velocidad);
    }

    protected Vectores PursuingForce(Vectores vjp) {
        Vectores posicionJ = vjp.RestaVectores(posicion);
        double distancia = posicionJ.Magnitud();
        // Estimar el tiempo de intercepción
        double prediccion = distancia / maxVel;
        // Calcular la posición futura del jugador
        Vectores futuraPosicion = vjp.SumaVectores(ventanapartida.getJugador()
                .JugadorgetVelocidad().MultiplicarVector(prediccion));
        // Aplicar Seek hacia la posición futura
        Vectores force = futuraPosicion.RestaVectores(posicion)
                .velocidadlimite(1);

        return force;
    }

    public void Destruir() {
        muerte = true;
        /*si el objeto no es un laser ni un powerUp se destruye y reproduce 
        el sonido de explosion*/
        if (!(this instanceof Lacer) && !(this instanceof PowerUp)) {
            explosion.play();
            /*si el objeto no es un jugador, se tomara un numer al azar si ese 
            numero es 4 se crea un powerUP pero primero volvemos su velocidad 
            igual a 0 y le sumamos esa velocidad a la posicion*/
            if (!(this instanceof Jugador)&&ventanapartida.getJugador().isGanarPuntos()) {
                int probabilidad = (int) (Math.random() * 5);
                if (probabilidad == 4) {

                    Vectores aceleracion = (velocidad.MultiplicarVector(-1).velocidadlimite(0));
                    velocidad = velocidad.SumaVectores(aceleracion);

                    velocidad = velocidad.velocidadlimite(maxVel);
                    posicion = posicion.SumaVectores(velocidad);
                    ventanapartida.spawnPowerUp(posicion);
                }
            }
        }
    }
protected void LimitarPantalla(){
       if (posicion.getX() > Constantes.ancho) {
            posicion.setX(0);

        }
        if (posicion.getY() > Constantes.alto) {
            posicion.setY(0);
        }
        if (posicion.getX() < 0) {
            posicion.setX(Constantes.ancho);
        }
        if (posicion.getY() < 0) {
            posicion.setY(Constantes.alto);
        }
}
    public Vectores CentroImagen() {
        return new Vectores(posicion.getX() + imgancho / 2, posicion.getY() + imgalto / 2);
    }

    public boolean isMuerte() {
        return muerte;
    }

}
 /*
  protected Vectores PursuingForce(){
      //  Vectores FuturePosicion=jugador.CentroImagen().SumaVectores(jugador.JugadorgetVelocidad().MultiplicarVector(tiempo));
      // Calcular distancia al jugador
      Vectores vjp=jugador.getPosicion();
      Vectores vjv=jugador.JugadorgetVelocidad();
      Vectores posicionJ=vjp.RestaVectores(posicion);
      
      double distancia=posicionJ.Magnitud();
      
        // Estimar el tiempo de intercepción
      double prediccion=distancia/maxVel;
      
       // Calcular la posición futura del jugador
      Vectores futuraPosicion = vjp.SumaVectores(jugador.JugadorgetVelocidad().MultiplicarVector(prediccion));
      // Aplicar Seek hacia la posición futura
      Vectores force = futuraPosicion.RestaVectores(posicion).NormalizarVector().MultiplicarVector(1);
      
      return force ;
    }*/
