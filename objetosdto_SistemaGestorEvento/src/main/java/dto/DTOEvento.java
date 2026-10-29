/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DTOEvento {

    int idEvento;
    String tipoEvento;
    LocalDate fechaEvento; 
    String ubicacion;
    LocalDate fechaRegistro;
    int numAsistentes;
    String necesidadesIniciales;
    LocalTime horaInicio;
    LocalTime horaFin;
    String observaciones;
    String estadoAtencion;

    public DTOEvento() {
    }
 
    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getNumAsistentes() {
        return numAsistentes;
    }

    public void setNumAsistentes(int numAsistentes) {
        this.numAsistentes = numAsistentes;
    }

    public String getNecesidadesIniciales() {
        return necesidadesIniciales;
    }

    public void setNecesidadesIniciales(String necesidadesIniciales) {
        this.necesidadesIniciales = necesidadesIniciales;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoAtencion() {
        return estadoAtencion;
    }

    public void setEstadoAtencion(String estadoAtencion) {
        this.estadoAtencion = estadoAtencion;
    }
    
    //para guardar los detalles de los recursos, se utiliza el DTODetalleSolicitud
    //se pone en DTOEvento porq es el nucleo de las operaciones
    private List<DTODetalleSolicitud> detallesSolicitud = new ArrayList<>();

    public List<DTODetalleSolicitud> getDetallesSolicitud() {
        return detallesSolicitud;
    }

    public void setDetallesSolicitud(List<DTODetalleSolicitud> detallesSolicitud) {
        this.detallesSolicitud = detallesSolicitud;
    }    
    
    @Override
    public String toString() {
        return "DTOEvento{" + "idEvento=" + idEvento + ", tipoEvento=" + tipoEvento + ", fechaEvento=" + fechaEvento + ", ubicacion=" + ubicacion + ", fechaRegistro=" + fechaRegistro + ", numAsistentes=" + numAsistentes + ", necesidadesIniciales=" + necesidadesIniciales + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", observaciones=" + observaciones + ", estadoAtencion=" + estadoAtencion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idEvento;
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
        final DTOEvento other = (DTOEvento) obj;
        return this.idEvento == other.idEvento;
    }
    
    
}
