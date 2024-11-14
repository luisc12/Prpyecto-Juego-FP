/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaSalida;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author luis
 */
public class DatosPuntaje {
    private String nombre;
    private String fecha;
    private int puntaje;

    public DatosPuntaje(String nombre,  int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        Date fechas=new Date(System.currentTimeMillis());
       SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
       fecha=format.format(fechas);
    }
    public DatosPuntaje(String nombre,  int puntaje,String fecha) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.fecha=fecha;
    }

    public DatosPuntaje() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "DatosPuntaje{" + "nombre=" + nombre + ", fecha=" + fecha + ", puntaje=" + puntaje + '}';
    }
    
}
