/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Matematicas.Vectores;
import Ventanas.VentanaPartida;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public class Planetas extends ObjetosMovibles{

    public Planetas(BufferedImage textura, Vectores posicion, Vectores velocidad, double maxVel, VentanaPartida ventanapartida) {
        super(textura, posicion, velocidad, maxVel, ventanapartida);
        
    }

    @Override
    public void actualizar(float dt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dibujar(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
