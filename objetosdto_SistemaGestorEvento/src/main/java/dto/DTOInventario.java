/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */

public class DTOInventario {

    private List<DTOEquipo> equipos = new ArrayList<>();
    private List<DTOPersonal> personal = new ArrayList<>();
    private List<DTOServicio> servicios = new ArrayList<>();
    private int disponibilidad; 

    public DTOInventario() {
    }

    public DTOInventario(List<DTOEquipo> equipos, List<DTOPersonal> personal, List<DTOServicio> servicios, int disponibilidad) {
        this.equipos = equipos;
        this.personal = personal;
        this.servicios = servicios;
        this.disponibilidad = disponibilidad;
    }

    public List<DTOEquipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<DTOEquipo> equipos) {
        this.equipos = equipos;
    }

    public List<DTOPersonal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<DTOPersonal> personal) {
        this.personal = personal;
    }

    public List<DTOServicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<DTOServicio> servicios) {
        this.servicios = servicios;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
    
    
}
