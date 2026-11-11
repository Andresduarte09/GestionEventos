/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.util.List;

public interface ISubsistemaPropuesta {

    // Consultas (GestorPropuesta)
    List<DTOServicio> consultarServiciosDisponibles();

    List<DTOEquipo> obtenerEquiposDisponibles();

    List<DTOPersonal> obtenerPersonalDisponible();

    // Operaciones de la fachada
    void seleccionarServicios(int idServicio, boolean seleccionado);

    void seleccionarEquipos(int idEquipo, int cantidad);

    void seleccionarPersonal(int idPersonal, int cantidad);

    boolean verificarSeleccionEquipo();

    boolean verificarSeleccionPersonal();

    /**
     * @throws IllegalArgumentException si un horario no tiene formato HH:mm
     */
    void enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios);
}
