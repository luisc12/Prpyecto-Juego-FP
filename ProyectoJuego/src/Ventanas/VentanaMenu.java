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
public class VentanaMenu extends Ventana{
    ArrayList<Boton> botones;

    public VentanaMenu() {
        botones=new ArrayList<Boton>();
        
        botones.add(
                new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2-Externos.bGris.getHeight(),
                Constantes.Comenzar,
                new Accion() {
            @Override
            public void hacerAccion() {
               Ventana.cambiarVentana(new VentanaPartida());
            }
        }));
        
         botones.add(new Boton(Externos.bGris,
                Externos.bVerde,
                Constantes.ancho/2-Externos.bGris.getWidth()/2,
                Constantes.alto/2+Externos.bGris.getHeight()/2,
                Constantes.Salir,
                new Accion() {
            @Override
            public void hacerAccion() {
               System.exit(0);
            }
        }));
    }

    
    @Override
    public void actualizar() {
        for (Boton b : botones) {
            b.actualizar();
        }
    }

    @Override
    public void dibujar(Graphics g) {
         for (Boton b : botones) {
            b.dibujar(g);
        }}
    
}
