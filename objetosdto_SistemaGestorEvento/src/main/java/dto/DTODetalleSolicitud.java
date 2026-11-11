/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Objects;

public class DTODetalleSolicitud {

    private int idVersion;
    int idDetalleSolicitud;
    int idRecurso;
    String CategoriaSolicitada;
    String descripcionSolicitud;
    String preferencias;
    int cantidadEstimada;
    String observaciones;
    private int idCotizacion;

    public DTODetalleSolicitud() {
    }

    public int getIdDetalleSolicitud() {
        return idDetalleSolicitud;
    }

    public void setIdDetalleSolicitud(int detalleSolicitud) {
        this.idDetalleSolicitud = detalleSolicitud;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getCategoriaSolicitada() {
        return CategoriaSolicitada;
    }

    public void setCategoriaSolicitada(String CategoriaSolicitada) {
        this.CategoriaSolicitada = CategoriaSolicitada;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public int getCantidadEstimada() {
        return cantidadEstimada;
    }

    public void setCantidadEstimada(int cantidadEstimada) {
        this.cantidadEstimada = cantidadEstimada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    @Override
    public int hashCode() {
        int hash = 7;
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
        final DTODetalleSolicitud other = (DTODetalleSolicitud) obj;
        return this.idDetalleSolicitud == other.idDetalleSolicitud;
    }

    @Override
    public String toString() {
        return "DTODetalleSolicitud{" + "detalleSolicitud=" + idDetalleSolicitud + ", "
                + "CategoriaSolicitada=" + CategoriaSolicitada + ", descripcionSolicitud="
                + descripcionSolicitud + ", preferencias=" + preferencias + ", cantidadEstimada="
                + cantidadEstimada + ", observaciones=" + observaciones + '}';
    }

    

}
