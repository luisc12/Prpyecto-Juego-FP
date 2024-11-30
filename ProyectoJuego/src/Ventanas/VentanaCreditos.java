/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Graficos.Externos;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author luis
 */
public class VentanaCreditos extends Ventana{
ArrayList<Boton> botones;

    public VentanaCreditos() {
        botones=new ArrayList<>();
        botones.add( new Boton(Externos.bGris,
                Externos.bVerde,
                Externos.bGris.getHeight(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu());
            }}));
        
    }

    @Override
    public void actualizar(float dt) {
        for (Boton b : botones) {
            b.actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
          for (Boton b : botones) {
            b.dibujar(g);
        }
          
    }
    
}
