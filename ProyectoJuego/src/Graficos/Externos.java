/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author luis
 */
public class Externos {

    // public static BufferedImage icono=Cargador.CargarImagen("graficos/icono.png");
    //barra de carga
    public static boolean cargado=false;
    public static float cantidad=0;
    public static float cantidadMax=46;
    //Skins
    public static BufferedImage player;

    //efectos
    public static BufferedImage propulsion;

    //laseres    
    public static BufferedImage blueLaser, redLaser, greenLaser;

    //meteoros
    public static BufferedImage[] grades = new BufferedImage[4];

    public static BufferedImage[] medianos = new BufferedImage[2];

    public static BufferedImage[] pequeños = new BufferedImage[2];

    public static BufferedImage[] enanos = new BufferedImage[2];
    //efectos

    public static BufferedImage[] explosion = new BufferedImage[9];
    //enemigos
    public static BufferedImage[] Ufo = new BufferedImage[2];

    //numeros
    public static BufferedImage[] numeros = new BufferedImage[11];

    public static BufferedImage vida;
    
    //fuentes
    public static Font Gfuente;
    public static Font Mfuente;
    
    //Sonidos
    public static Clip MusicaFondo,Sonidoexplosion,PerdidaJugador,DisparoJugador,DisparoUfo;
    
    //ui
    public static BufferedImage bVerde,bGris;

    public static void inicio() {
        player = CargarImagen("skins/Player2.png");

        propulsion = CargarImagen("efectos/fire05.png");
        
        //laseres

        blueLaser = CargarImagen("laseres/laserBlue01.png");

        redLaser = CargarImagen("laseres/laserRed01.png");

        greenLaser = CargarImagen("laseres/laserGreen11.png");
        
        //vida
        vida= CargarImagen("otros/playerLife2.png");
        
        //fuentes
        Gfuente=CargarFuente("fuentes/kenvector_future.ttf", 42);
        Mfuente=CargarFuente("fuentes/kenvector_future.ttf", 20);
        //meteoros

        for (int i = 0; i < grades.length; i++) {
            grades[i] = CargarImagen("meteoros/meteorBrown_big" + (i + 1) + ".png");
        }
        for (int i = 0; i < medianos.length; i++) {

            medianos[i] = CargarImagen("meteoros/meteorBrown_med" + (i + 1) + ".png");

        }
        for (int i = 0; i < pequeños.length; i++) {

            pequeños[i] = CargarImagen("meteoros/meteorBrown_small" + (i + 1) + ".png");

        }
        for (int i = 0; i < enanos.length; i++) {

            enanos[i] = CargarImagen("meteoros/meteorBrown_tiny" + (i + 1) + ".png");

        }
        for (int i = 0; i < explosion.length; i++) {

            explosion[i] = CargarImagen("explosion/" + i + ".png");

        }
        for (int i = 0; i < Ufo.length; i++) {

            Ufo[i] = CargarImagen("enemigos/ufo" + (i + 1) + ".png");

        }
        for (int i = 0; i < numeros.length; i++) {

            numeros[i] = CargarImagen("numeros/numeral" + i + ".png");
        }
        //sonido
        MusicaFondo=CargarMusica("sonidos/backgroundMusic.wav");
        Sonidoexplosion=CargarMusica("sonidos/explosion.wav");
        PerdidaJugador=CargarMusica("sonidos/playerLoose.wav");
        DisparoJugador=CargarMusica("sonidos/playerShoot.wav");
        DisparoUfo=CargarMusica("sonidos/ufoShoot.wav");
        
        //botones
        bGris=CargarImagen("ui/button_Gris.png");
        bVerde=CargarImagen("ui/button_Verde.png");
        
        cargado=true;
       
    }

    public static BufferedImage CargarImagen(String ruta) {

        try {
            cantidad++;
            return ImageIO.read(Externos.class.getResource(ruta));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font CargarFuente(String ruta, int tamaño) {
        try {
            cantidad++;
            return Font.createFont(Font.TRUETYPE_FONT, Externos.class.getResourceAsStream(ruta)).deriveFont(Font.PLAIN, tamaño);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Clip CargarMusica(String ruta) {
        try {
            cantidad++;
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Externos.class.getResource(ruta)));
            return clip;
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        return null;
    }
}