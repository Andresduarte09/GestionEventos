/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andres
 */
public class DTORecurso {
    int idRecurso;
    String nombre;
    String descripcion;
    int capacidad;
    float precioReferencia;
    String unidadCapacidad;
    String tipoRecurso;
    String unidadMedida;
    String estadoEnCatalogo;

    public DTORecurso(int idRecurso, String nombre, String descripcion, int capacidad, float precioReferencia, String unidadCapacidad, String tipoRecurso, String unidadMedida, String estadoEnCatalogo) {
        this.idRecurso = idRecurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
        this.precioReferencia = precioReferencia;
        this.unidadCapacidad = unidadCapacidad;
        this.tipoRecurso = tipoRecurso;
        this.unidadMedida = unidadMedida;
        this.estadoEnCatalogo = estadoEnCatalogo;
    }

    public DTORecurso() {
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public float getPrecioReferencia() {
        return precioReferencia;
    }

    public void setPrecioReferencia(float precioReferencia) {
        this.precioReferencia = precioReferencia;
    }

    public String getUnidadCapacidad() {
        return unidadCapacidad;
    }

    public void setUnidadCapacidad(String unidadCapacidad) {
        this.unidadCapacidad = unidadCapacidad;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getEstadoEnCatalogo() {
        return estadoEnCatalogo;
    }

    public void setEstadoEnCatalogo(String estadoEnCatalogo) {
        this.estadoEnCatalogo = estadoEnCatalogo;
    }
    
    
}
