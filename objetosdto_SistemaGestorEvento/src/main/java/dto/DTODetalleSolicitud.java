/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.util.Objects;

public class DTODetalleSolicitud {
    int detalleSolicitud;
    String CategoriaSolicitada;
    String descripcionSolicitud;
    String preferencias;
    int cantidadEstimada;
    String observaciones;

    public DTODetalleSolicitud() {}

    public int getDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(int detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
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
        return this.detalleSolicitud == other.detalleSolicitud;
    }

    @Override
    public String toString() {
        return "DTODetalleSolicitud{" + "detalleSolicitud=" + detalleSolicitud + ", CategoriaSolicitada=" + CategoriaSolicitada + ", descripcionSolicitud=" + descripcionSolicitud + ", preferencias=" + preferencias + ", cantidadEstimada=" + cantidadEstimada + ", observaciones=" + observaciones + '}';
    }

    
}
