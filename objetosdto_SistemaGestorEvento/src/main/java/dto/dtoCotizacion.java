package dto;


import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lui
 */
public class DTOCotizacion {
    private int ID;
    private LocalDate fecha;
    private String estadoGeneral;
    private String reglaDescuento;
    private final int porcentajeMaximoDescuento = 10;
    
    public DTOCotizacion() {
    }

    public DTOCotizacion(int ID, LocalDate fecha, String estadoGeneral) {
        this.ID = ID;
        this.fecha = fecha;
        this.estadoGeneral = estadoGeneral;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(String estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }    

    public String getReglaDescuento() {
        return reglaDescuento;
    }

    public void setReglaDescuento(String reglaDescuento) {
        this.reglaDescuento = reglaDescuento;
    }
    
}
