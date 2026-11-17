/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioCotizacion.interfaces;

import dto.DTOCotizacion;
import dto.DTOVersionCotizacion;
import dto.DTODetalleCotizacion;
import java.util.List;

/**
 *
 * @author lui
 */
public interface ISubsistemaCotizacion {

    DTOCotizacion calcularCotizacion(int idPropuesta);

    DTOVersionCotizacion actualizarDescuentoVersion(int idVersion, float porcentaje);

    DTOVersionCotizacion consultarUltimaVersion(int idCotizacion);

    List<DTODetalleCotizacion> consultarDetallesVersion(int idVersion);

}
