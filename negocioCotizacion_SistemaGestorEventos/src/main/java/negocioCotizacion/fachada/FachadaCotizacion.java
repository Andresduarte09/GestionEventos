/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.fachada;

import dto.DTOCotizacion;
import negocioCotizacion.gestor.GestorCotizacion;
import negocioCotizacion.interfaces.ISubsistemaCotizacion;
import dto.DTOVersionCotizacion;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;

/**
 *
 * @author Andres
 */
public class FachadaCotizacion implements ISubsistemaCotizacion {

    private final GestorCotizacion gestor;

    public FachadaCotizacion() {
        this.gestor = new GestorCotizacion();
    }

    public FachadaCotizacion(ISubsistemaPropuesta subsistemaPropuesta) {
        this.gestor = new GestorCotizacion(subsistemaPropuesta);
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

}
