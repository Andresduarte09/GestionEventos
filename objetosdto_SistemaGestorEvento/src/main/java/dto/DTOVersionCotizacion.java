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
public class DTOVersionCotizacion {
    private int ID;
    private int numero;


    private LocalDate fechaEmision;
    private String estado;
    private String observaciones;
    private float descuento;
    private float subtotal;
    private final int IVA = 16;
    private float total;
    
        public DTOVersionCotizacion() {
    }

    public DTOVersionCotizacion(int ID, int numero, LocalDate fechaEmision, String estado, String observaciones, float descuento, float subtotal, float total) {
        this.ID = ID;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.observaciones = observaciones;
        this.descuento = descuento;
        this.subtotal = subtotal;
        this.total = total;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
        
    
}
