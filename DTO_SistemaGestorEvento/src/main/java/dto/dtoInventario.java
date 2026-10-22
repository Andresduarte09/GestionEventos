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

public class dtoInventario {

    private List<dtoEquipo> equipos = new ArrayList<>();
    private List<dtoPersonal> personal = new ArrayList<>();
    private List<dtoServicio> servicios = new ArrayList<>();

    public dtoInventario() {
    }

    public dtoInventario(List<dtoEquipo> equipos, List<dtoPersonal> personal, List<dtoServicio> servicios) {
        this.equipos = equipos;
        this.personal = personal;
        this.servicios = servicios;
    }

    public List<dtoEquipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<dtoEquipo> equipos) {
        this.equipos = equipos;
    }

    public List<dtoPersonal> getPersonal() {
        return personal;
    }

    public void setPersonal(List<dtoPersonal> personal) {
        this.personal = personal;
    }

    public List<dtoServicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<dtoServicio> servicios) {
        this.servicios = servicios;
    }
}
