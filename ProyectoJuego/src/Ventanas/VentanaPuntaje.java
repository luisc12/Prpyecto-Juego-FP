/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import EntradaSalida.DatosPuntaje;
import Graficos.Externos;
import Graficos.Texto;
import Matematicas.Vectores;
import ObjetosMoviles.Constantes;
import Ui.Accion;
import Ui.Boton;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author luis
 */
public class VentanaPuntaje extends Ventana {

    private Boton atras;
    //es una estructura de datos con solo una entrada ordenada de tal manera que el dato menor va a la cabeza
    //facilitando ordenar los valores
    private PriorityQueue<DatosPuntaje> datospuntajes;

    private Comparator<DatosPuntaje> comparador;
    private DatosPuntaje[] aux;

    public VentanaPuntaje() {
        atras = new Boton(Externos.bGris,
                Externos.bVerde,
                Externos.bGris.getHeight(),
                Constantes.alto - Externos.bVerde.getHeight() * 2,
                Constantes.Atras, new Accion() {
            @Override
            public void hacerAccion() {
                Ventana.cambiarVentana(new VentanaMenu());
            }
        });

        comparador = new Comparator<DatosPuntaje>() {
            @Override
            public int compare(DatosPuntaje d1, DatosPuntaje d2) {
                return d1.getPuntaje() < d2.getPuntaje() ? -1 : d1.getPuntaje() > d2.getPuntaje()
                        ? 1 : 0;
            }
        };
        datospuntajes=new PriorityQueue<DatosPuntaje>(10,comparador);
        //para acseder al segunda hay que remover el primer valor en el PriorityQueue por lo que abra que usar un iterador
        //
        datospuntajes.add(new DatosPuntaje("Juan", 1000));
        datospuntajes.add(new DatosPuntaje("Jonh", 100));
        datospuntajes.add(new DatosPuntaje("Clara",200));
    }

    @Override
    public void actualizar() {
        atras.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        atras.dibujar(g);
        //paso mi cola con prioridad a un arreglo normal
        aux=datospuntajes.toArray(new DatosPuntaje[datospuntajes.size()]);
        //para ordenarlo
        Arrays.sort(aux,comparador);

        Vectores posNombres=new Vectores(Constantes.ancho / 2 - 300, 100);
        Vectores posPuntajes=new Vectores(Constantes.ancho / 2, 100);
         Vectores posFechas=new Vectores(Constantes.ancho / 2 + 300, 100);
        
        Texto.DibujarTexto(g,
                "Nombre",
                posNombres,
                true,
                Color.yellow,
                Externos.Gfuente);
        
        Texto.DibujarTexto(g,
                Constantes.Puntos,
                posPuntajes,
                true,
                Color.yellow,
                Externos.Gfuente);

        Texto.DibujarTexto(g,
                Constantes.fecha,
                posFechas,
                true,
                Color.yellow,
                Externos.Gfuente);
        
        //sumamos 40 piseles
        posNombres.setY(posNombres.getY()+40);
        posPuntajes.setY(posPuntajes.getY()+40);
        posFechas.setY(posFechas.getY()+40);
        //iteramos el arreglo ausiliar al reves por que la cola con prioridad es de menor a mayor
        for (int i = aux.length-1; i>-1; i--) {
            DatosPuntaje d=aux[i];
            
             Texto.DibujarTexto(g,
                d.getNombre(),
                posNombres,
                true,
                Color.yellow,
                Externos.Mfuente);
             
             Texto.DibujarTexto(g,
                Integer.toString(d.getPuntaje()),
                posPuntajes,
                true,
                Color.WHITE,
                Externos.Mfuente);
             
            Texto.DibujarTexto(g,
                d.getFecha(),
                posFechas,
                true,
                Color.white,
                Externos.Mfuente);
            
              posNombres.setY(posNombres.getY()+40);
        posPuntajes.setY(posPuntajes.getY()+40);
        posFechas.setY(posFechas.getY()+40);
        }
    }

}