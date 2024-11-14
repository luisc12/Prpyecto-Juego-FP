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
    public static final long TiempParpadeo = 200;//milisegundos
    public static final long TiempAparecerJugador = 3000;//3 segundos
    public static final long TiempoFinal = 3000;//GAME_OVER_TIME

    //----propiedades del laser-------------------
    //velocidad del laser //LASER_VEL
    public static final double Velocidad_lac = 15.0;

    //------propiedades meteoros-------
    public static final double Velocidad_Meteo = 2.0;

    public static final int PuntosMeteoros = 20;

    //------propiedades Ufo----
    public static final int RadiusNodo = 160;//Node_radius
    public static final double MasaUfo = 60;//UFO_MASS
    public static final int MaxVelUfo = 3;//UFO_MAX_VEL
    public static final long TDisparoUfo = 1000;//UFO_FIRE_RATE
    public static double RangoAnguloUfo = Math.PI / 2;//UFO_ANGLE_RANGE 
    public static final int PuntosUfo = 40;//UFO_SCORE 
    public static final long TiempoAparecerUfo = 10000;//UFO_SPAWN_RATE

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
        public static final String ubicacion=FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\Entrega_Espacial\\RECORS.XML"; // data.xml if you use XMLParser
	
	// This variables are required to use XMLParser
	
	public static final String JUGADOR = "JUGADOR";
	public static final String JUGADORES = "JUGADORES";

}
