/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import static Graficos.Externos.efectoEscudo2;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
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
    public static float cantidadMax=57;
    //Skins
    public static BufferedImage[] jugadores = new BufferedImage[8];
    
    public static BufferedImage jugadorDobleGun;

    //efectos
    public static BufferedImage propulsion;
    
    //public static BufferedImage[] efectoEscudo1 = new BufferedImage[3];
    public static BufferedImage[] efectoEscudo2 = new BufferedImage[12];
    //animacion

    //public static BufferedImage[] explosion = new BufferedImage[9];
    public static BufferedImage[] explosion2 = new BufferedImage[16];
    //laseres    
    public static BufferedImage blueLaser, redLaser, greenLaser;
 //Misiles    
    public static BufferedImage blueMisil, redMisil, greenMisil;
    //meteoros
    public static BufferedImage[] grades = new BufferedImage[4];

    public static BufferedImage[] medianos = new BufferedImage[2];

    public static BufferedImage[] pequeños = new BufferedImage[2];

    public static BufferedImage[] enanos = new BufferedImage[2];
    
    //enemigos
    public static BufferedImage[] Ufo = new BufferedImage[2];
    public static BufferedImage enemigo1;

    //numeros
    public static BufferedImage[] numeros = new BufferedImage[11];

    public static BufferedImage vida;
    
    //fuentes
    public static Font Gfuente;
    public static Font Mfuente;
    public static Font Pixeloid;
    //Sonidos
    public static Clip MusicaFondo,Sonidoexplosion,PerdidaJugador,DisparoJugador,DisparoUfo,PowerUP;
    public static Clip Ufosonido;
    //ui
    public static BufferedImage bVerde,bGris;
    public static BufferedImage flechaVerdeD,flechaVerdeI,flechaGrisD,flechaGrisI,
            flechaVerdeA,flechaVerdeB,flechaGrisA,flechaGrisB;
    //powerUP
    public static BufferedImage doblePuntuacion,dobleGun,fuegoRapido,escudo,escudov2,estrella;
    public static BufferedImage orbe,orbFuego,orb2X,orbGun,orbVida,orbPuntuacion;
    
    //Planetas
    public static BufferedImage[] planetas = new BufferedImage[4];

    public static void inicio() {
        
jugadorDobleGun=CargarImagen("skins/doubleGunPlayer2.png");
        propulsion = CargarImagen("efectos/fire05.png");
        
         for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = CargarImagen("skins/Player" + (i + 1) + ".png");
        }
        
        //laseres

        blueLaser = CargarImagen("laseres/laserBlue01.png");

        redLaser = CargarImagen("laseres/laserRed01.png");

        greenLaser = CargarImagen("laseres/laserGreen11.png");
        // Misiles
        blueMisil = CargarImagen("laseres/spaceMissilesBlue.png");

        redMisil = CargarImagen("laseres/spaceMissilesRed.png");

        greenMisil = CargarImagen("laseres/spaceMissilesGreen.png");
        
        //vida
        vida= CargarImagen("otros/playerLife2.png");
        
        //fuentes
        Gfuente=CargarFuente("fuentes/kenvector_future.ttf", 42);
        Mfuente=CargarFuente("fuentes/kenvector_future.ttf", 25);
        Pixeloid=CargarFuente("fuentes/PixeloidSans-Bold.ttf", 20);
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
        //exploxiones
      /*  for (int i = 0; i < explosion.length; i++) {

            explosion[i] = CargarImagen("explosion/" + i + ".png");

        }*/
        for (int i = 0; i < explosion2.length; i++){
            explosion2[i] = CargarImagen("explosion/square_explosion" + (i+1) + ".png");
        }
        //enemigos
        for (int i = 0; i < Ufo.length; i++) {

            Ufo[i] = CargarImagen("enemigos/ufo" + (i + 1) + ".png");

        }
        enemigo1=CargarImagen("enemigos/enemigo2.png");
        //ui
        for (int i = 0; i < numeros.length; i++) {

            numeros[i] = CargarImagen("numeros/numeral" + i + ".png");
        }
        //
      //  for(int i = 0; i < efectoEscudo1.length; i++){
        //    efectoEscudo1[i] = CargarImagen("efectos/shield" + (i + 1) +".png"); 
	 // }
        for (int i = 0; i<efectoEscudo2.length ; i++) {
            efectoEscudo2[i]=CargarImagen("efectos/shieldV2" + (i + 1)+".png");
        }
         for (int i = 0; i<planetas.length ; i++) {
            planetas[i]=CargarImagen("otros/Planeta" + (i + 1)+".png");
        }
        
        MusicaFondo=CargarMusica("sonidos/Track01V2.wav");
        //Sonidoexplosion=CargarMusica("sonidos/explosion.wav");
        Sonidoexplosion=CargarMusica("sonidos/explosion.wav");
        PerdidaJugador=CargarMusica("sonidos/playerLoose.wav");
        DisparoJugador=CargarMusica("sonidos/ShotgunShot001V2.wav");
        DisparoUfo=CargarMusica("sonidos/ufoShoot.wav");
        PowerUP=CargarMusica("sonidos/powerUp.wav");
        Ufosonido=CargarMusica("sonidos/GrapplingHook_Reel(Loop).wav");
        
        //botones
        bGris=CargarImagen("ui/button_Gris.png");
        bVerde=CargarImagen("ui/button_Verde.png");
        
        flechaGrisD=CargarImagen("ui/flecha_Gris_Derecha.png");
        flechaVerdeD=CargarImagen("ui/flecha_Verde_Derecha.png");
        flechaGrisI=CargarImagen("ui/flecha_Gris_Izquierda.png");
        flechaVerdeI=CargarImagen("ui/flecha_Verde_Izquierda.png");
        
        flechaGrisA=CargarImagen("ui/flecha_Gris_Arriba.png");
        flechaVerdeA=CargarImagen("ui/flecha_Verde_Arriba.png");
        flechaGrisB=CargarImagen("ui/flecha_Gris_Abajo.png");
        flechaVerdeB=CargarImagen("ui/flecha_Verde_Abajo.png");
        
        //Poderes
        orbe=CargarImagen("poderes/orb.png");
        orb2X=CargarImagen("poderes/orb2X.png");
        orbFuego=CargarImagen("poderes/orbFuego.png");
        orbGun=CargarImagen("poderes/orbGun.png");
        orbPuntuacion=CargarImagen("poderes/orbPuntuacion.png");
        orbVida=CargarImagen("poderes/orbVida.png");
        
        
        doblePuntuacion=CargarImagen("poderes/doubleScore.png");
        dobleGun=CargarImagen("poderes/doubleGun.png");
        fuegoRapido=CargarImagen("poderes/fastFire.png");
        estrella=CargarImagen("poderes/star.png");
        escudo=CargarImagen("poderes/shield.png");
        escudov2=CargarImagen("poderes/escudov2.png");
        //--------------------------------------------------------
        cargado=true;
        System.out.println(cantidad);
       
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
    public static  BufferedImage cambiarTamaño(BufferedImage img, int newHeight) {
    
    double scaleFactor = (double) newHeight / img.getHeight();
    int newWidth = (int) (img.getWidth() * scaleFactor);

    BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, img.getType());
    Graphics2D g2d = scaledImage.createGraphics();
    g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
    g2d.dispose();

    return scaledImage;
}
     public static  BufferedImage cambiarancho(BufferedImage img, int newHeight) {
    
    double scaleFactor = (double) newHeight / img.getHeight();
    int newWidth = (int) (img.getWidth() * scaleFactor);

    BufferedImage scaledImage = new BufferedImage(newWidth, img.getHeight(), img.getType());
    Graphics2D g2d = scaledImage.createGraphics();
    g2d.drawImage(img, 0, 0, newWidth, img.getHeight(), null);
    g2d.dispose();

    return scaledImage;
}
    public static Image getIconImage() {
        Image retValue;
        retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Graficos/otros/icono2.png"));

        return retValue;
    }
}
