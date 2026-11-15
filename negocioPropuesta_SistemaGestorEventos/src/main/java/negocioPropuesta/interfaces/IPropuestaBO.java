/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOInventario;
import dto.DTOPropuesta;

/**
 *
 * @author Andres
 */
public interface IPropuestaBO {

    DTOPropuesta guardar(DTOPropuesta propuesta);

    DTOPropuesta consultar(int idPropuesta);

    DTOInventario consultarRecursosPropuesta(int idPropuesta);

    DTOInventario consultarSeleccion(int idEvento);

    void registrarSeleccionEvento(int idEvento, DTOInventario seleccion);

    void actualizarSeleccion(DTOInventario cambios);
}