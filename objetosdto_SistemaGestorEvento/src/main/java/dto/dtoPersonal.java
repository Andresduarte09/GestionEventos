/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andre
 */

public class DTOPersonal {

    private int idEspecialidad;
    private String especialidad;
    private int cantidad;
    private int horasMontaje;
    private int horasOperacion;
    private int horasDesmontaje;

    public DTOPersonal() {
    }

    public DTOPersonal(int idEspecialidad, String especialidad, int cantidad,
            int horasMontaje, int horasOperacion, int horasDesmontaje) {
        this.idEspecialidad = idEspecialidad;
        this.especialidad = especialidad;
        this.cantidad = cantidad;
        this.horasMontaje = horasMontaje;
        this.horasOperacion = horasOperacion;
        this.horasDesmontaje = horasDesmontaje;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getHorasMontaje() {
        return horasMontaje;
    }

    public void setHorasMontaje(int horasMontaje) {
        this.horasMontaje = horasMontaje;
    }

    public int getHorasOperacion() {
        return horasOperacion;
    }

    public void setHorasOperacion(int horasOperacion) {
        this.horasOperacion = horasOperacion;
    }

    public int getHorasDesmontaje() {
        return horasDesmontaje;
    }

    public void setHorasDesmontaje(int horasDesmontaje) {
        this.horasDesmontaje = horasDesmontaje;
    }
}
