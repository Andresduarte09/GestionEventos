/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.fachada;

/**
 *
 * @author Andres
 */
import com.mycompany.persistencia.AlmacenDatos;
import java.util.List;
import negocioOrden.BO.DecisionClienteBO;
import negocioOrden.BO.OrdenServicioBO;
import negocioOrden.gestor.GestorOrden;
import negocioOrden.interfaces.ISubsistemaOrden;

public class FachadaOrden implements ISubsistemaOrden {

    private final GestorOrden gestor;

    public FachadaOrden(AlmacenDatos almacen) {
        this.gestor = new GestorOrden(
                new OrdenServicioBO(almacen),
                new DecisionClienteBO(almacen)
        );
    }

    @Override
    public boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados) {
        return gestor.registrarDecisionCotizacion(decision, serviciosSeleccionados);
    }
}