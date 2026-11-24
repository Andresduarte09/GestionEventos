/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.gestor;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOOrdenServicio;
import java.util.List;
import negocioOrden.BO.DecisionClienteBO;
import negocioOrden.BO.OrdenServicioBO;
import negocioOrden.interfaces.IOrdenServicioBO;

public class GestorOrden {

    private final AlmacenDatos almacen;
    private final IOrdenServicioBO ordenServicioBO;
    private final DecisionClienteBO decisionClienteBO;
    private int idEventoContexto;
    private int idCotizacionContexto;

    public GestorOrden() {
        this.almacen = AlmacenDatos.getInstance();
        this.ordenServicioBO = new OrdenServicioBO(this.almacen);
        this.decisionClienteBO = new DecisionClienteBO(this.almacen);
    }

    public void establecerContextoOrden(int idEvento, int idCotizacion) {
        this.idEventoContexto = idEvento;
        this.idCotizacionContexto = idCotizacion;
    }

    public List<String> obtenerServiciosCotizados() {
        int idCotizacion = idCotizacionContexto > 0 ? idCotizacionContexto : resolverIdCotizacionActual();
        return ordenServicioBO.obtenerRecursosCotizados(idCotizacion);
    }

    public List<String> obtenerRecursosCotizados(int idCotizacion) {
        return ordenServicioBO.obtenerRecursosCotizados(idCotizacion);
    }

    public boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados) {
        int idCotizacion = idCotizacionContexto > 0 ? idCotizacionContexto : resolverIdCotizacionActual();
        int idEvento = idEventoContexto > 0 ? idEventoContexto : resolverIdEventoActual(idCotizacion);
        DTOOrdenServicio orden = procesarDecisionYOrden(idEvento, idCotizacion, decision, "", serviciosSeleccionados);
        return orden != null
                || "Negación total".equalsIgnoreCase(decision)
                || "Solicitud de modificacion".equalsIgnoreCase(decision);
    }

    public DTOOrdenServicio procesarDecisionYOrden(
            int idEvento,
            int idCotizacion,
            String tipoDecision,
            String observaciones,
            List<String> elementosSeleccionados) {

        decisionClienteBO.registrarDecision(String.valueOf(idCotizacion), tipoDecision, observaciones);

        boolean generaOrden = "Aceptación total".equalsIgnoreCase(tipoDecision)
                || "Aceptación parcial".equalsIgnoreCase(tipoDecision)
                || "Aceptada".equalsIgnoreCase(tipoDecision);

        if (!generaOrden) {
            return null;
        }

        return ordenServicioBO.generarOrdenServicio(idEvento, idCotizacion, elementosSeleccionados);
    }

    public DTOOrdenServicio consultarOrdenPorId(int idOrden) {
        return ordenServicioBO.consultarOrdenPorId(idOrden);
    }

    public List<DTOOrdenServicio> consultarTodasOrdenes() {
        return ordenServicioBO.consultarTodasOrdenes();
    }

    private int resolverIdCotizacionActual() {
        if (almacen.getCotizaciones() == null || almacen.getCotizaciones().isEmpty()) {
            return 0;
        }
        return almacen.getCotizaciones().get(almacen.getCotizaciones().size() - 1).getID();
    }

    private int resolverIdEventoActual(int idCotizacion) {
        if (almacen.getEventos() == null || almacen.getEventos().isEmpty()) {
            return 0;
        }
        for (int i = almacen.getEventos().size() - 1; i >= 0; i--) {
            if (almacen.getEventos().get(i) != null && almacen.getEventos().get(i).getIdEvento() > 0) {
                return almacen.getEventos().get(i).getIdEvento();
            }
        }
        return 0;
    }
}