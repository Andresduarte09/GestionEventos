/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author luise
 */
public class DTOLocalEvento {
    int idLocalEvento;
    String tipoLocal;
    String dimensionesLugar;
    String dimensionesEspacios;
    String condicionesAcceso;
    String restriccionesLocal;
    String necesidadesDetectadas;

    public DTOLocalEvento(){}

    public DTOLocalEvento(int idLocalEvento, String tipoLocal, String dimensionesLugar, String dimensionesEspacios, String condicionesAcceso, String restriccionesLocal, String necesidadesDetectadas) {
        this.idLocalEvento = idLocalEvento;
        this.tipoLocal = tipoLocal;
        this.dimensionesLugar = dimensionesLugar;
        this.dimensionesEspacios = dimensionesEspacios;
        this.condicionesAcceso = condicionesAcceso;
        this.restriccionesLocal = restriccionesLocal;
        this.necesidadesDetectadas = necesidadesDetectadas;
    }
    
    
    public int getIdLocalEvento() {
        return idLocalEvento;
    }

    public void setIdLocalEvento(int idLocalEvento) {
        this.idLocalEvento = idLocalEvento;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getDimensionesLugar() {
        return dimensionesLugar;
    }

    public void setDimensionesLugar(String dimensionesLugar) {
        this.dimensionesLugar = dimensionesLugar;
    }

    public String getDimensionesEspacios() {
        return dimensionesEspacios;
    }

    public void setDimensionesEspacios(String dimencionesEspacios) {
        this.dimensionesEspacios = dimencionesEspacios;
    }

    public String getCondicionesAcceso() {
        return condicionesAcceso;
    }

    public void setCondicionesAcceso(String condicionesAcceso) {
        this.condicionesAcceso = condicionesAcceso;
    }

    public String getRestriccionesLocal() {
        return restriccionesLocal;
    }

    public void setRestriccionesLocal(String restriccionesLocal) {
        this.restriccionesLocal = restriccionesLocal;
    }

    public String getNecesidadesDetectadas() {
        return necesidadesDetectadas;
    }

    public void setNecesidadesDetectadas(String necesidadesDetectadas) {
        this.necesidadesDetectadas = necesidadesDetectadas;
    }
}
