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

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getModificacion() {
        return modificacion;
    }

    public void setModificacion(String modificacion) {
        this.modificacion = modificacion;
    }

    @Override
    public String toString() {
        return "Creditos{" + "tema=" + tema + ", objeto=" + objeto + ", creador=" + creador + ", licencia=" + licencia + ", modificacion=" + modificacion + '}';
    }
    
    
    
}
