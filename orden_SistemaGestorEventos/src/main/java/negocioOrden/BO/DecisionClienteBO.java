/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.BO;


/**
 *
 * @author Andres
 */
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCotizacion;
import dto.DTODecisionCliente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DecisionClienteBO {

    private final AlmacenDatos almacen;

    public DecisionClienteBO(AlmacenDatos almacen) {
        if (almacen == null) {
            throw new IllegalArgumentException("AlmacenDatos no puede ser nulo.");
        }
        this.almacen = almacen;
    }

    public DTODecisionCliente registrarDecision(String idCotizacionTexto, String tipoDecision) {
        return registrarDecision(idCotizacionTexto, tipoDecision, "");
    }

    public DTODecisionCliente registrarDecision(String idCotizacionTexto, String tipoDecision, String observaciones) {
        int idCotizacion = parsearIdCotizacion(idCotizacionTexto);
        validarCotizacion(idCotizacion);

        int nuevoId = almacen.getDecisionesCliente() != null
                ? almacen.getDecisionesCliente().size() + 1
                : 1;

        DTODecisionCliente decision = new DTODecisionCliente(
                nuevoId,
                LocalDate.now(),
                tipoDecision,
                observaciones
        );

        almacen.registrarDecisionCliente(decision);
        actualizarEstadoCotizacion(idCotizacion, tipoDecision);
        return decision;
    }

    public List<DTODecisionCliente> consultarDecisiones() {
        if (almacen.getDecisionesCliente() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(almacen.getDecisionesCliente());
    }

    private int parsearIdCotizacion(String idCotizacionTexto) {
        if (idCotizacionTexto == null || idCotizacionTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de cotización no puede estar vacío.");
        }
        try {
            int id = Integer.parseInt(idCotizacionTexto.trim());
            if (id <= 0) {
                throw new IllegalArgumentException("El ID de cotización debe ser mayor que cero.");
            }
            return id;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El ID de cotización debe ser un número válido: " + idCotizacionTexto, ex);
        }
    }

    private void validarCotizacion(int idCotizacion) {
        if (almacen.getCotizaciones() == null) {
            throw new IllegalArgumentException("No hay cotizaciones registradas.");
        }
        for (int i = 0; i < almacen.getCotizaciones().size(); i++) {
            DTOCotizacion cotizacion = almacen.getCotizaciones().get(i);
            if (cotizacion != null && cotizacion.getID() == idCotizacion) {
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró la cotización con ID " + idCotizacion);
    }

    private void actualizarEstadoCotizacion(int idCotizacion, String tipoDecision) {
        if (tipoDecision == null || almacen.getCotizaciones() == null) {
            return;
        }
        for (int i = 0; i < almacen.getCotizaciones().size(); i++) {
            DTOCotizacion cotizacion = almacen.getCotizaciones().get(i);
            if (cotizacion != null && cotizacion.getID() == idCotizacion) {
                cotizacion.setEstadoGeneral(tipoDecision);
                return;
            }
        }
    }
}