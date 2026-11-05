/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.control;

import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.util.List;

/**
 *
 * @author Andres
 */
public class ControlPropuesta {
     private DTOInventario seleccionActual; 

    public List<DTOServicio> solicitarServicios() {
        return null;
    }

    public List<DTOEquipo> solicitarEquipos() {
        return null;
    }

    public List<DTOPersonal> solicitarPersonal() {
        return null;
    }

    public boolean verificarSeleccion(DTOInventario seleccion) {
        return false;
    }

    public DTOInventario obtenerResumen(int idEvento) {
        return null;
    }

    public void solicitarPropuesta(String folio, String horarioMontaje, String horarioDesmontaje, String comentarios) {
        // Vacío
    }

    public void actualizarPropuesta(DTOInventario cambios) {
        // Vacío
    }
}
