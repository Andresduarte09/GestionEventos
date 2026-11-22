/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.gestor;

/**
 *
 * @author Andres
 */
import java.util.List;
import negocioOrden.interfaces.IDecisionClienteBO;
import negocioOrden.interfaces.IOrdenServicioBO;

public class GestorOrden {

    private final IOrdenServicioBO ordenBO;
    private final IDecisionClienteBO decisionBO;

    private int idEventoActual = -1;
    private int idCotizacionActual = -1;

    public GestorOrden(IOrdenServicioBO ordenBO, IDecisionClienteBO decisionBO) {
        this.ordenBO = ordenBO;
        this.decisionBO = decisionBO;
    }

    public void establecerContextoOrden(int idEvento, int idCotizacion) {
        this.idEventoActual = idEvento;
        this.idCotizacionActual = idCotizacion;
    }

    public List<String> obtenerServiciosCotizados() {
        return ordenBO.obtenerServiciosCotizados();
    }

    public boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados) {
        if (decision == null || decision.isBlank()) {
            return false;
        }

        if ("Aceptación total".equals(decision)) {
            decisionBO.registrarDecision(decision, "El cliente aceptó todos los servicios cotizados.");
            ordenBO.registrarCotizacionEnAlmacen();
            ordenBO.generarOrdenServicio(idEventoActual, idCotizacionActual, null);
            return true;
        }

        if ("Aceptación parcial".equals(decision)) {
            if (serviciosSeleccionados == null || serviciosSeleccionados.isEmpty()) {
                return false;
            }
            String observaciones = "Servicios aceptados: " + String.join(", ", serviciosSeleccionados);
            decisionBO.registrarDecision(decision, observaciones);
            ordenBO.registrarCotizacionEnAlmacen();
            ordenBO.generarOrdenServicio(idEventoActual, idCotizacionActual, serviciosSeleccionados);
            return true;
        }

        if ("Solicitud de modificación".equals(decision) || "Solicitud de modificacion".equals(decision)) {
            return true;
        }

        if ("Negación total".equals(decision)) {
            decisionBO.registrarDecision(decision, "El cliente rechazó todos los servicios.");
            ordenBO.registrarCotizacionEnAlmacen();
            return true;
        }

        return false;
    }
}