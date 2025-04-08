/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author luis
 */
public class Sonido {
    private Clip clip;
    private FloatControl volumen;

    public Sonido(Clip clip) {
        this.clip = clip;
        volumen=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
       
    }
    public void play(){
        //le decimos que comiebse desde el principio el sonido
        clip.setFramePosition(0);
        clip.start();
    }
    public void MusicaFondo(){//loop
        //la musica de fondo comiensa desde el frame 0 y se pone en bucle
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void parar(){
        clip.stop();
    }
    public int getFramePsition(){
        return clip.getFramePosition();
    }
    public void cambiarVolumen(float valor){
        volumen.setValue(valor);
    }
}
