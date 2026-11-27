/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.fachada;

import dto.DTOCotizacion;
import negocioCotizacion.gestor.GestorCotizacion;
import negocioCotizacion.interfaces.ISubsistemaCotizacion;
import dto.DTOVersionCotizacion;
import negocioPropuesta.control.GestorPropuesta;
import dto.DTODetalleCotizacion;
import java.util.List;

/**
 *
 * @author lui
 */
public class FachadaCotizacion implements ISubsistemaCotizacion {

    private final GestorCotizacion gestor;

    public FachadaCotizacion() {
        this.gestor = new GestorCotizacion();
    }

    public FachadaCotizacion(GestorPropuesta gestorPropuesta) {
        this.gestor = new GestorCotizacion(gestorPropuesta);
    }

    @Override
    public DTOVersionCotizacion consultarUltimaVersion(int idCotizacion) {
        return gestor.consultarUltimaVersion(idCotizacion);
    }

    @Override
    public DTOCotizacion calcularCotizacion(int idPropuesta) {
        return gestor.calcularCotizacion(idPropuesta);
    }

    @Override
    public DTOVersionCotizacion actualizarDescuentoVersion(int idVersion, float porcentaje) {
        return gestor.actualizarDescuentoVersion(idVersion, porcentaje);
    }

    @Override
    public List<DTODetalleCotizacion> consultarDetallesVersion(int idVersion) {
        return gestor.consultarDetallesVersion(idVersion);
    }

}
