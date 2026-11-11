/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.interfaces;

/**
 *
 * @author Andres
 */
import dto.DTODecisionCliente;
import java.util.List;

public interface IDecisionClienteBO {
    void guardarDecisionCliente(String decision, String observaciones);
    DTODecisionCliente consultarDecisionPorId(int idDecision);
    List<DTODecisionCliente> consultarHistorialDecisiones();
}
