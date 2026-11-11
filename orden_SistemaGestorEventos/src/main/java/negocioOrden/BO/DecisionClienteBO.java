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
import dto.DTODecisionCliente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import negocioOrden.interfaces.IDecisionClienteBO;

public class DecisionClienteBO implements IDecisionClienteBO {

    private final AlmacenDatos almacen;

    public DecisionClienteBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public void guardarDecisionCliente(String decision, String observaciones) {
        int nuevoID = 1;

        for (DTODecisionCliente d : almacen.getDecisionesCliente()) {
            if (d.getIdDecision() >= nuevoID) {
                nuevoID = d.getIdDecision() + 1;
            }
        }

        DTODecisionCliente nuevaDecision = new DTODecisionCliente(nuevoID, LocalDate.now(), decision, observaciones);
        almacen.registrarDecisionCliente(nuevaDecision);
    }

    @Override
    public DTODecisionCliente consultarDecisionPorId(int idDecision) {
        for (DTODecisionCliente d : almacen.getDecisionesCliente()) {
            if (d.getIdDecision() == idDecision) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<DTODecisionCliente> consultarHistorialDecisiones() {
        return new ArrayList<>(almacen.getDecisionesCliente());
    }
}