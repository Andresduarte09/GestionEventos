/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.gestor;

/**
 *
 * @author Andres
 */
import dto.DTOServicio;
import java.util.ArrayList;
import java.util.List;
import negocioOrden.interfaces.IDecisionClienteBO;
import negocioOrden.interfaces.IOrdenServicioBO;

public class GestorOrden {

    private int idVersionActual;
    private final IOrdenServicioBO IOrdenBO;
    private final IDecisionClienteBO IDecisionClienteBO;

    public GestorOrden(IOrdenServicioBO ordenBO, IDecisionClienteBO decisionClienteBO) {
        this.IOrdenBO = ordenBO;
        this.IDecisionClienteBO = decisionClienteBO;
    }

    public void guardarDecisionCliente(String decision, String observaciones) {
        IDecisionClienteBO.guardarDecisionCliente(decision, observaciones);
    }

    public void registrarCotizacionEnAlmacen() {
        IOrdenBO.registrarCotizacionEnAlmacen();
    }

    public void generarOrdenServicio() {
        IOrdenBO.generarOrdenServicio();
    }

    public boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados) {
        if (decision == null || decision.equals("Selecciona una decisión")) {
            return false;
        }

        if (decision.equals("Aceptación total")) {
            guardarDecisionCliente(decision, "El cliente aceptó todos los servicios cotizados.");
            registrarCotizacionEnAlmacen();
            generarOrdenServicio();
            return true;
        } else if (decision.equals("Aceptación parcial")) {
            if (serviciosSeleccionados == null || serviciosSeleccionados.isEmpty()) {
                return false;
            }
            guardarDecisionCliente(decision, "Servicios aceptados: " + String.join(", ", serviciosSeleccionados));
            registrarCotizacionEnAlmacen();
            generarOrdenServicio();
            return true;
        } else if (decision.equals("Solicitud de modificación")) {
            guardarDecisionCliente(decision, "El cliente solicitó modificaciones.");
            return true;
        } else if (decision.equals("Negación total")) {
            guardarDecisionCliente(decision, "El cliente rechazó todos los servicios.");
            registrarCotizacionEnAlmacen();
            return true;
        }

        return false;
    }

    public int getIdVersionActual() {
        return idVersionActual;
    }

    public void setIdVersionActual(int idVersionActual) {
        this.idVersionActual = idVersionActual;
    }
}