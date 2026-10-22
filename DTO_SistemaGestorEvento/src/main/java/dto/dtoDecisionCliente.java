/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author armen
 */
import java.time.LocalDate;
public class dtoDecisionCliente {
    private int idDecision;
    private LocalDate fecha;
    private String tipoDecision;
    private String observaciones;
    
    public dtoDecisionCliente(){}
    
    public dtoDecisionCliente(int idDecision, LocalDate fecha, String tipoDecision, String observaciones){
            this.idDecision = idDecision; 
            this.fecha= fecha;
            this.tipoDecision = tipoDecision;
            this.observaciones = observaciones;
    }

    public int getIdDecision() {
        return idDecision;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipoDecision() {
        return tipoDecision;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdDecision(int idDecision) {
        this.idDecision = idDecision;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipoDecision(String tipoDecision) {
        this.tipoDecision = tipoDecision;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
