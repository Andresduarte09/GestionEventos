/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.fachada;

import dto.DTOOrdenServicio;
import java.util.List;
import negocioOrden.gestor.GestorOrden;
import negocioOrden.interfaces.ISubsistemaOrden;

public class FachadaOrden implements ISubsistemaOrden {

    private final GestorOrden gestorOrden;

    public FachadaOrden() {
        this.gestorOrden = new GestorOrden();
    }

    @Override
    public void establecerContextoOrden(int idEvento, int idCotizacion) {
        gestorOrden.establecerContextoOrden(idEvento, idCotizacion);
    }

    @Override
    public List<String> obtenerServiciosCotizados() {
        return gestorOrden.obtenerServiciosCotizados();
    }

    @Override
    public List<String> obtenerRecursosCotizados(int idCotizacion) {
        return gestorOrden.obtenerRecursosCotizados(idCotizacion);
    }

    @Override
    public boolean registrarDecisionCotizacion(String tipoDecision, List<String> elementosSeleccionados) {
        return gestorOrden.registrarDecisionCotizacion(tipoDecision, elementosSeleccionados);
    }

    @Override
    public DTOOrdenServicio procesarDecisionYOrden(
            int idEvento,
            int idCotizacion,
            String tipoDecision,
            String observaciones,
            List<String> elementosSeleccionados) {
        return gestorOrden.procesarDecisionYOrden(
                idEvento,
                idCotizacion,
                tipoDecision,
                observaciones,
                elementosSeleccionados
        );
    }

    @Override
    public DTOOrdenServicio consultarOrdenPorId(int idOrden) {
        return gestorOrden.consultarOrdenPorId(idOrden);
    }

    @Override
    public List<DTOOrdenServicio> consultarTodasOrdenes() {
        return gestorOrden.consultarTodasOrdenes();
    }
}