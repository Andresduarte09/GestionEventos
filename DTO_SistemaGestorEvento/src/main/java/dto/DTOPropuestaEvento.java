/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.time.LocalTime;

/**
 *
 * @author Andres
 */
public class DTOPropuestaEvento {
    int folio;
    LocalTime horarioMontaje;
    LocalTime horarioDesmontaje;
    String comentarios;
    String estadoFolio;  

    public DTOPropuestaEvento(int folio, LocalTime horarioMontaje, LocalTime horarioDesmontaje, String comentarios, String estadoFolio) {
        this.folio = folio;
        this.horarioMontaje = horarioMontaje;
        this.horarioDesmontaje = horarioDesmontaje;
        this.comentarios = comentarios;
        this.estadoFolio = estadoFolio;
    }

    public DTOPropuestaEvento() {
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public LocalTime getHorarioMontaje() {
        return horarioMontaje;
    }

    public void setHorarioMontaje(LocalTime horarioMontaje) {
        this.horarioMontaje = horarioMontaje;
    }

    public LocalTime getHorarioDesmontaje() {
        return horarioDesmontaje;
    }

    public void setHorarioDesmontaje(LocalTime horarioDesmontaje) {
        this.horarioDesmontaje = horarioDesmontaje;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstadoFolio() {
        return estadoFolio;
    }

    public void setEstadoFolio(String estadoFolio) {
        this.estadoFolio = estadoFolio;
    }
    
    
}
