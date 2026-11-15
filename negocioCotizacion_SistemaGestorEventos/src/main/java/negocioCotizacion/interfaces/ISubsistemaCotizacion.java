/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioCotizacion.interfaces;
import dto.DTOCotizacion;
import dto.DTOVersionCotizacion;

/**
 *
 * @author lui
 */
public interface ISubsistemaCotizacion {   
    DTOCotizacion calcularCotizacion(int idPropuesta);
    
    DTOVersionCotizacion actualizarDescuentoVersion(int idVersion, float porcentaje);
    
}
