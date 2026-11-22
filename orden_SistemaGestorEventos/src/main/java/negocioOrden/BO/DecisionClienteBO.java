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
import negocioOrden.interfaces.IDecisionClienteBO;

public class DecisionClienteBO implements IDecisionClienteBO {

    private final AlmacenDatos almacen;

    public DecisionClienteBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public boolean registrarDecision(String decision, String observaciones) {
        if (decision == null || decision.isBlank()) {
            return false;
        }

        if (almacen != null && almacen.getCotizaciones() != null && !almacen.getCotizaciones().isEmpty()) {
            DTOCotizacion ultima = almacen.getCotizaciones().get(almacen.getCotizaciones().size() - 1);
            if (ultima != null) {
                if ("Aceptación total".equals(decision)) {
                    ultima.setEstadoGeneral("Aceptada");
                } else if ("Aceptación parcial".equals(decision)) {
                    ultima.setEstadoGeneral("Aceptada parcialmente");
                } else if ("Negación total".equals(decision)) {
                    ultima.setEstadoGeneral("Rechazada");
                }
            }
        }

        DTODecisionCliente dtoDecision = new DTODecisionCliente();
        dtoDecision.setFecha(LocalDate.now());
        dtoDecision.setTipoDecision(decision);
        dtoDecision.setObservaciones(observaciones == null ? "" : observaciones);

        if (almacen != null) {
            almacen.registrarDecisionCliente(dtoDecision);
        }

        return true;
    }
}