/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntradaSalida;

/**
 *
 * @author luis
 */
public class Creditos {
    private String tema;
    private String objeto;
    private String creador;
    private String licencia;
    private String modificacion;

    public Creditos(String tema, String objeto, String creador, String licencia, String modificacion) {
        this.tema = tema;
        this.objeto = objeto;
        this.creador = creador;
        this.licencia = licencia;
        this.modificacion = modificacion;
    }

    public Creditos() {
    }
    
    
    
}
