/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;
import java.time.LocalTime;


public class DTOLevantamiento {
    int idLevantamiento;
    LocalDate fechaLevantamiento;
    LocalTime horarioMontaje;
    LocalTime horarioDesmontaje;
    String estadoLevantamiento;
    String observaciones;

    public DTOLevantamiento(){}

    public int getIdLevatamiento() {
        return idLevantamiento;
    }

    public void setIdLevatamiento(int idLevatamiento) {
        this.idLevantamiento = idLevatamiento;
    }

    public LocalDate getFechaLevantamiento() {
        return fechaLevantamiento;
    }

    public void setFechaLevantamiento(LocalDate fechaLevantamiento) {
        this.fechaLevantamiento = fechaLevantamiento;
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

    public String getEstadoLevantamiento() {
        return estadoLevantamiento;
    }

    public void setEstadoLevantamiento(String estadoLevantamiento) {
        this.estadoLevantamiento = estadoLevantamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "DTOLevantamiento{" + "idLevatamiento=" + idLevantamiento + ", fechaLevantamiento=" + fechaLevantamiento + ", horarioMontaje=" + horarioMontaje + ", horarioDesmontaje=" + horarioDesmontaje + ", estadoLevantamiento=" + estadoLevantamiento + ", observaciones=" + observaciones + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.idLevantamiento;
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
        final DTOLevantamiento other = (DTOLevantamiento) obj;
        return this.idLevantamiento == other.idLevantamiento;
    }
    
    
    
    
}
