/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Matematicas;

import Entrada.Teclado;

/**
 *
 * @author luis
 */
public class Vectores {

    private double x, y;

    public Vectores(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vectores(Vectores v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vectores() {
        x = 0;
        y = 0;
    }

    //le pasamos un vector para sumarlo con los de la posicion anterior para mover el objeto
    public Vectores SumaVectores(Vectores v) {
        return new Vectores(x + v.getX(), y + v.getY());
    }

    public Vectores RestaVectores(Vectores v) {
        return new Vectores(x - v.getX(), y - v.getY());
    }
 

    //le pasamos la cnstante de aceleracion
    public Vectores MultiplicarVector(double valor) {

        return new Vectores(x * valor, y * valor);

    }

    //limitamos la velocidad
    public Vectores velocidadlimite(double valor) {
        //normalizamos y luego multiplicamos para que asi no pierda su direccion
        if (Manitud() > valor) {
            return this.NormalizarVector().MultiplicarVector(valor);
        }
        return this;
    }

    //hacer que el vector tenga magnitud igual a uno
    public Vectores NormalizarVector() {
        double magnitud = Manitud();
        return new Vectores(x / magnitud, y / magnitud);
    }

    //sacamos la magnitud
    public double Manitud() {
        return Math.sqrt((x * x) + (y * y));
    }

    public Vectores calcularDireccion(double angulo) {
        //para sacar su direccion abra que modificar sus componestes X y Y con el angulo recibido
        double magnitud = Manitud();
        
        x = Math.cos(angulo) * magnitud;
        y = Math.sin(angulo) * magnitud;
        return new Vectores(x, y);

    }

    public double getAngulo() {
        return Math.asin(y / Manitud());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
