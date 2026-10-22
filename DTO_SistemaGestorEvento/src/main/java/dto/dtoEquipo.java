/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andre
 */
public class dtoEquipo {

    private int idEquipo;
    private String nombre;
    private int disponibilidad;
    private int cantidadSeleccionada;

    public dtoEquipo() {
    }

    public dtoEquipo(int idEquipo, String nombre, int disponibilidad, int cantidadSeleccionada) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
        this.cantidadSeleccionada = cantidadSeleccionada;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCantidadSeleccionada() {
        return cantidadSeleccionada;
    }

    public void setCantidadSeleccionada(int cantidadSeleccionada) {
        this.cantidadSeleccionada = cantidadSeleccionada;
    }
}
