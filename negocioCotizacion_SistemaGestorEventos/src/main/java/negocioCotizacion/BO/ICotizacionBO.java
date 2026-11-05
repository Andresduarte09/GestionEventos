/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioCotizacion.BO;

import dto.DTOCotizacion;

/**
 *
 * @author Andres
 */
public interface ICotizacionBO {
    public DTOCotizacion guardar(DTOCotizacion cotizacion);
    
    public DTOCotizacion consultar(int idCotizacion);
    
    public void actualizar(DTOCotizacion cotizacion);
    
    public DTOCotizacion guardarVersion(DTOCotizacion version);
    
    public DTOCotizacion consultarVersion(int idVersion);
    
    public DTOCotizacion guardarDetalle(DTOCotizacion detalle);
}
