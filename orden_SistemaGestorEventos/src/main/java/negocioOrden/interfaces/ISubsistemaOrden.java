/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioOrden.interfaces;

import negocioOrden.BO.OrdenServicioBO;

/**
 *
 * @author Andres
 */
import java.util.List;

public interface ISubsistemaOrden {
    boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados);
}
