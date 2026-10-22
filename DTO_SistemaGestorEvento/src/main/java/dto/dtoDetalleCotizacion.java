package dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lui
 */
public class dtoDetalleCotizacion {
    private int ID;
    private LocalTime horarioFases;
    List<dtoInventario> inventario = new ArrayList<>();
    private int cantidad;
    private float importe;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalTime getHorarioFases() {
        return horarioFases;
    }

    public void setHorarioFases(LocalTime horarioFases) {
        this.horarioFases = horarioFases;
    }

    public List<dtoInventario> getInventario() {
        return inventario;
    }

    public void setInventario(List<dtoInventario> inventario) {
        this.inventario = inventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }
    
    
}
