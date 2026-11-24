/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioOrden.interfaces;

import dto.DTOOrdenServicio;
import java.util.List;

public interface ISubsistemaOrden {

    void establecerContextoOrden(int idEvento, int idCotizacion);

    List<String> obtenerServiciosCotizados();

    List<String> obtenerRecursosCotizados(int idCotizacion);

    boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados);

    DTOOrdenServicio procesarDecisionYOrden(
            int idEvento,
            int idCotizacion,
            String tipoDecision,
            String observaciones,
            List<String> elementosSeleccionados);

    DTOOrdenServicio consultarOrdenPorId(int idOrden);

    List<DTOOrdenServicio> consultarTodasOrdenes();
}
