/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entrada;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import proyectojuego.ProyectoJuego;

/**
 *
 * @author luis
 */
//activamos la entrada por teclado
public class Teclado implements KeyListener {

    //para agregar cualquier tecla usamos 256
    private boolean[] keys = new boolean[256];
    
    public static boolean arriba, derecha, izquierda, abajo, disparar,pausa,reanudar;

    public Teclado() {
        arriba = false;
        derecha = false;
        izquierda = false;
        abajo = false;
        disparar = false;
        pausa=false;
        reanudar=false;

    }
//

    public void actualizar() {
        arriba=keys[KeyEvent.VK_UP];
        derecha=keys[KeyEvent.VK_RIGHT];
        izquierda=keys[KeyEvent.VK_LEFT];
        abajo=keys[KeyEvent.VK_DOWN];
        disparar=keys[KeyEvent.VK_SPACE];
        pausa=keys[KeyEvent.VK_P];
        reanudar=keys[KeyEvent.VK_O];

    }
 // Método para resetear el estado de las teclas
    public void resetearTeclas() {
        arriba = false;
        abajo = false;
        izquierda = false;
        derecha = false;
    }

    // Método para resetear el estado de pausa
    public void resetPausa() {
        pausa = false;
    }
//al precionar
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
       
       
    }
//al soltar 

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
