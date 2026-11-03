/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Andres
 */
public class DTOOrdenServicio {
    private int idOrden;
    private int idEvento;
    private int idCotizacion;
    private LocalDate fechaGeneracion;
    private List<DTOServicio> serviciosAutorizados;

    public DTOOrdenServicio(int idOrden, int idEvento, int idCotizacion, LocalDate fechaGeneracion, List<DTOServicio> serviciosAutorizados) {
        this.idOrden = idOrden;
        this.idEvento = idEvento;
        this.idCotizacion = idCotizacion;
        this.fechaGeneracion = fechaGeneracion;
        this.serviciosAutorizados = serviciosAutorizados;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public List<DTOServicio> getServiciosAutorizados() {
        return serviciosAutorizados;
    }

    public void setServiciosAutorizados(List<DTOServicio> serviciosAutorizados) {
        this.serviciosAutorizados = serviciosAutorizados;
    }
    
    
}
