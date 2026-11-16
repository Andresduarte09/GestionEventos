/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOInventario;
import dto.DTOPropuesta;

public interface ISubsistemaPropuesta {

    void establecerEventoActual(int idEvento);

    void seleccionarServicios(int idServicio, boolean seleccionado);

    boolean seleccionarEquipos(int idEquipo, int cantidad);

    boolean seleccionarPersonal(int idPersonal, int cantidad);

    boolean verificarSeleccionServicios();

    boolean verificarSeleccionEquipo();

    boolean verificarSeleccionPersonal();

    boolean validarCantidadEquipo(int idEquipo, int cantidad);
    
    DTOPropuesta consultarPropuesta(int idPropuesta);

    DTOInventario consultarRecursosPropuesta(int idPropuesta);

    DTOInventario obtenerResumenSeleccion(int idEvento);

    DTOPropuesta enviarPropuestaHaciaCotizacion(
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios
    );
}