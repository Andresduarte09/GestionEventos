/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Ale
 */
public class DTOLocalEvento {
    int idLocalEvento;
    String nombreLocal;
    String tipoLocal;
    String dimensionesLugar;
    String distribucionEspacios;
    String condicionesAcceso;
    String restriccionesLocal;
    String necesidadesDetectadas;
    int capacidad;
    float precioLocal;
    String imagen;

    public DTOLocalEvento(){
    }

    public DTOLocalEvento(int idLocalEvento, String nombreLocal, String tipoLocal, String dimensionesLugar, 
            String dimensionesEspacios, String condicionesAcceso, String restriccionesLocal,
            String necesidadesDetectadas, int capacidad, float precioLocal) {
        this.idLocalEvento = idLocalEvento;
        this.nombreLocal = nombreLocal;
        this.tipoLocal = tipoLocal;
        this.dimensionesLugar = dimensionesLugar;
        this.distribucionEspacios = dimensionesEspacios;
        this.condicionesAcceso = condicionesAcceso;
        this.restriccionesLocal = restriccionesLocal;
        this.necesidadesDetectadas = necesidadesDetectadas;
        this.capacidad = capacidad;
        this.precioLocal = precioLocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
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

    public String getDistribucionEspacios() {
        return distribucionEspacios;
    }

    public void setDistribucionEspacios(String dimencionesEspacios) {
        this.distribucionEspacios = dimencionesEspacios;
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

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public float getPrecioLocal() {
        return precioLocal;
    }

    public void setPrecioLocal(float precioLocal) {
        this.precioLocal = precioLocal;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
  
}
