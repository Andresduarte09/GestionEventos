/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.fachada;
import dto.DTOCotizacion;
import negocioCotizacion.gestor.GestorCotizacion;
import negocioCotizacion.interfaces.ISubsistemaCotizacion;
/**
 *
 * @author Andres
 */
public class FachadaCotizacion implements ISubsistemaCotizacion {
    
    private final GestorCotizacion gestor;
    
    public FachadaCotizacion(){
        this.gestor = new GestorCotizacion();
    }
    
    @Override
    public DTOCotizacion calcularCotizacion(int idPropuesta){
        return gestor.calcularCotizacion(idPropuesta);
    }    
}
