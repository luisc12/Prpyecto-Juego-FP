/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author luis
 */
public class Constantes {
    //-----frame dimensiones-----------------

    public static final int ancho = 1000, alto = 600;

    //----propiedades del jugador-------------------
    //tiempo de disparo //FIRERATE
    public static final int TDisparo = 300;
    //constante de aceleracion;
    public static final double ACC = 0.2;
    //angulo base//DELTAANGLE 
    public static final double anguloBase = 0.1;
    //maxima velocidad del jugador//PLAYER_MAX_VEL
    public static final double Jugador_Max_Vel = 0.7;
    public static final long TiempoParpadeo = 200;//milisegundos
    public static final long TiempoAparecerJugador = 3000;//3 segundos
    public static final long TiempoFinal = 3000;//GAME_OVER_TIME

    //----propiedades del laser-------------------
    //velocidad del laser //LASER_VEL
    public static final double Velocidad_lac = 15.0;

    //------propiedades meteoros-------
    public static final double Velocidad_Meteo = 2.0;

    public static final int PuntosMeteoros = 20;

    public static final double MaxVelocidadMeteor = 6.0;//METEOR_MAX_VEL

    public static final int DistanciaEscudo = 150;//SHIELD_DISTANCE

    //------propiedades Ufo----
    public static final int RadiusNodo = 160;//Node_radius
    public static final double MasaUfo = 60;//UFO_MASS
    public static final int MaxVelUfo = 3;//UFO_MAX_VEL
    public static final long TDisparoUfo = 1000;//UFO_FIRE_RATE
    public static double RangoAnguloUfo = Math.PI / 2;//UFO_ANGLE_RANGE 
    public static final int PuntosUfo = 40;//UFO_SCORE 
    public static final long TiempoAparecerUfo = 10000;//UFO_SPAWN_RATE

    //------propiedades Enemigo 1----
    public static final double MasaEnemigo1 = 50;//UFO_MASS
    public static final int MaxVelEnemigo1 = 2;//UFO_MAX_VEL
    public static final long TDisparoEnemigo1 = 2000;//UFO_FIRE_RATE
    //------propiedades Botones----
    public static final String Comenzar = "Comenzar";

    public static final String Salir = "Salir";

    //-------propiedades de Cargar----------------------
    public static final int BarraCargaAncho = 500;//LOADING_BAR_WIDTH
    public static final int BarraCargaAlto = 50;//LOADING_BAR_HEIGHT 

    //-------propiedades Puntos----------------
    public static final String Atras = "Atras";
    public static final String MejoresPuntajes = "MEJORES PUNTAJES";

    public static final String Nombres = "NOMBRE";
    public static final String Puntos = "PUNTOS";
    public static final String fecha = "FECHA";

    //ubicar la carpeta en mis documentos o en el sistema operativo linix en la carpeta Home
    public static final String ubicacion = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Entrega_Espacial\\RECORS.XML"; // data.xml if you use XMLParser
public static final String ubicacioncreditos = "../entregaespacial.xml";
    // This variables are required to use XMLParser
    public static final String JUGADOR = "JUGADOR";
    public static final String JUGADORES = "JUGADORES";
    //------------Creditos-----------
    public static final String Tema = "TEMA";
    public static final String OBJETO = "OBJETO";
    public static final String CREADOR = "CREADOR";
    public static final String LICENCIA = "LICENCIA";
    public static final String MODIFICACION = "MODIFICACION";
    public static final String CREDITOS= "CREDITOS";
    public static final String CREDITO= "CREDITO";
    //-------propiedades Power Up----------------
    public static final long DuracionPU = 10000;//POWER_UP_DURATION
    public static final long TiempoAparecerPower = 8000;//POWER_UP_SPAWN_TIME

    public static final long TiempoEscudo = 12000;//SHIELD_TIME
    public static final long TiempoDoblePuntaje = 10000;//DOUBLE_SCORE_TIME
    public static final long TiempoFuegoRapido = 14000;//FAST_FIRE_TIME
    public static final long TiempoDobleGun = 12000;//DOUBLE_GUN_TIME

    public static final int GranPuntuacion = 1000;//SCORE_STACK 

}
