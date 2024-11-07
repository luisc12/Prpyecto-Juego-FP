/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjetosMoviles;

import Graficos.Externos;
import java.awt.image.BufferedImage;

/**
 *
 * @author luis
 */
public enum Tamaños {
   GRANDE(2,Externos.medianos),MEDIO(2,Externos.pequeños),PEQUEÑO(2,Externos.enanos),ENANO(0,null);
   //division es la cantidad de meteoros que se va a dividir//quantity
   public int division;
   //las imagenes de los meteoros divididos
   public BufferedImage[] texturas;

    private Tamaños(int division, BufferedImage[] texturas) {
        this.division = division;
        this.texturas = texturas;
    }
   
   
   
}
