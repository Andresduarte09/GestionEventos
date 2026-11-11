/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioCotizacion.interfaces;
import dto.DTOCotizacion;

/**
 *
 * @author lui
 */
public interface ICotizacionBO {
    
    DTOCotizacion calcularCotizacion(int idPropuesta);
}
