/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author luise
 */
public class LocalEvento {
    int idLocalEvento;
    String tipoLocal;
    String dimensionesLugar;
    String dimencionesEspacios;
    String condicionesAcceso;
    String restriccionesLocal;
    String necesidadesDetectadas;

    public LocalEvento(){}
    
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

    public String getDimencionesEspacios() {
        return dimencionesEspacios;
    }

    public void setDimencionesEspacios(String dimencionesEspacios) {
        this.dimencionesEspacios = dimencionesEspacios;
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

    @Override
    public String toString() {
        return "LocalEvento{" + "idLocalEvento=" + idLocalEvento + ", tipoLocal=" + tipoLocal + ", dimensionesLugar=" + dimensionesLugar + ", dimencionesEspacios=" + dimencionesEspacios + ", condicionesAcceso=" + condicionesAcceso + ", restriccionesLocal=" + restriccionesLocal + ", necesidadesDetectadas=" + necesidadesDetectadas + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idLocalEvento;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LocalEvento other = (LocalEvento) obj;
        return this.idLocalEvento == other.idLocalEvento;
    }
    
    
    
}
