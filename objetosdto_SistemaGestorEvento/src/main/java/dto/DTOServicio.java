/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andre
 */
public class DTOServicio {

    private int idServicio;
    private String nombre;
    private String descripcion;
    private boolean seleccionado;
    private boolean esAdicional;
    private String observaciones;

    public DTOServicio() {
    }

    public DTOServicio(int idServicio, String nombre, String descripcion,
            boolean seleccionado, boolean esAdicional, String observaciones) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.seleccionado = seleccionado;
        this.esAdicional = esAdicional;
        this.observaciones = observaciones;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isEsAdicional() {
        return esAdicional;
    }

    public void setEsAdicional(boolean esAdicional) {
        this.esAdicional = esAdicional;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
