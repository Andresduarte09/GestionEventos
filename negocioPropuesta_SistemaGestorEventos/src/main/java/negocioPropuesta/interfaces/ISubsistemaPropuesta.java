/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.interfaces;

import dto.DTOPropuesta;

public interface ISubsistemaPropuesta {

    void seleccionarServicios(int idServicio, boolean seleccionado);

    void seleccionarEquipos(int idEquipo, int cantidad);

    void seleccionarPersonal(int idPersonal, int cantidad);

    boolean verificarSeleccionEquipo();

    boolean verificarSeleccionPersonal();

    /**
     * @throws IllegalArgumentException si un horario no tiene formato HH:mm
     */
    DTOPropuesta enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios);
}