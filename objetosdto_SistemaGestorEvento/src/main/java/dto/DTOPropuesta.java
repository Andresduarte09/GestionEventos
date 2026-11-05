/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andres
 */
public class DTOPropuesta {
    int idPropuesta;
    int idEvento;
    String folio;
    String horarioMontaje;
    String horarioDesmontaje;
    String comentarios;
    String estadoFolio;

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getHorarioMontaje() {
        return horarioMontaje;
    }

    public void setHorarioMontaje(String horarioMontaje) {
        this.horarioMontaje = horarioMontaje;
    }

    public String getHorarioDesmontaje() {
        return horarioDesmontaje;
    }

    public void setHorarioDesmontaje(String horarioDesmontaje) {
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
