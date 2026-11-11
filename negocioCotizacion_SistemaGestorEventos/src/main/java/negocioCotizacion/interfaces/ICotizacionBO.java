/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioCotizacion.interfaces;

import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOVersionCotizacion;

/**
 *
 * @author lui
 */
public interface ICotizacionBO {

    DTOCotizacion guardar(DTOCotizacion cotizacion);

    DTOCotizacion consultar(int idCotizacion);

    void actualizar(DTOCotizacion cotizacion);

    DTOVersionCotizacion guardarVersion(DTOVersionCotizacion version);

    DTOVersionCotizacion consultarVersion(int idVersion);

    DTODetalleCotizacion guardarDetalle(DTODetalleCotizacion detalle);
}
