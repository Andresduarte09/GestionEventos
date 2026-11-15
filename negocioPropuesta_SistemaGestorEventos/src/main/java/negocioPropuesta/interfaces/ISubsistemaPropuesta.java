/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOInventario;
import dto.DTOPropuesta;

public interface ISubsistemaPropuesta {

    void establecerEventoActual(int idEvento);

    void seleccionarServicios(
            int idServicio,
            boolean seleccionado
    );

    void seleccionarEquipos(int idEquipo, int cantidad);

    void seleccionarPersonal(int idPersonal, int cantidad);

    boolean verificarSeleccionEquipo();

    boolean verificarSeleccionPersonal();

    DTOPropuesta enviarPropuestaHaciaCotizacion(
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios
    );

    DTOPropuesta consultarPropuesta(int idPropuesta);

    DTOInventario consultarRecursosPropuesta(int idPropuesta);
}