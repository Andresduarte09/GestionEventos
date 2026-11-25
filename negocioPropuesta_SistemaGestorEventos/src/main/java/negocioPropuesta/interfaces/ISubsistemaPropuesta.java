/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOPropuesta;
import dto.DTOServicio;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface ISubsistemaPropuesta {

    void seleccionarServicios(
            int idServicio, boolean seleccionado);

    boolean seleccionarEquipos(
            int idEquipo, int cantidad);

    boolean seleccionarPersonal(
            int idPersonal, int cantidad);

    boolean verificarSeleccionServicios();

    boolean verificarSeleccionEquipo();

    boolean verificarSeleccionPersonal();

    boolean validarCantidadEquipo(
            int idEquipo, int cantidad);

    DTOPropuesta enviarPropuestaHaciaCotizacion(
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios);

    // Operaciones de integración con presentación y F4.

    List<DTOServicio> consultarServiciosDisponibles();

    List<DTOEquipo> obtenerEquiposDisponibles();

    List<DTOPersonal> obtenerPersonalDisponible();

    List<DTOEquipo> consultarEquiposPropios();

    List<DTOEquipo> consultarEquiposExternos();

    List<DTOInventario> consultarInventariosDisponibles();

    List<DTOEquipo> buscarEquiposExternos(String texto);

    DTOInventario obtenerResumenSeleccion(int idEvento);

    DTOPropuesta consultarPropuesta(int idPropuesta);

    DTOInventario consultarRecursosPropuesta(int idPropuesta);

    DTOPropuesta actualizarPropuesta(DTOInventario cambios);
}