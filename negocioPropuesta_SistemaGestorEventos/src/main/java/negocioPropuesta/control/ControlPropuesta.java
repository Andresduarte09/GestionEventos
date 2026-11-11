/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.control;

import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import negocioPropuesta.interfaces.IConfigPropuestaBO;

/**
 * Gestor del subsistema de propuesta.
 */
public class ControlPropuesta {

    private final IConfigPropuestaBO configBO;

    private LocalTime horarioMontaje;
    private LocalTime horarioDesmontaje;
    private String comentarios = "";

    public ControlPropuesta(IConfigPropuestaBO configBO) {
        this.configBO = configBO;
    }

    public List<DTOServicio> consultarServiciosDisponibles() {
        return configBO.consultarServicios();
    }

    public List<DTOEquipo> obtenerEquiposDisponibles() {
        return configBO.consultarEquipos();
    }

    public List<DTOPersonal> obtenerPersonalDisponible() {
        return configBO.consultarPersonal();
    }

    //la seleccion se maneja aqui sobre los DTOs que entrega el BO
    public void seleccionarServicios(int idServicio, boolean seleccionado) {
        for (DTOServicio s : configBO.consultarServicios()) {
            if (s.getIdServicio() == idServicio) {
                s.setSeleccionado(seleccionado);
                return;
            }
        }
    }

    public void seleccionarEquipos(int idEquipo, int cantidad) {
        for (DTOEquipo e : configBO.consultarEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                e.setCantidadSeleccionada(cantidad);
                return;
            }
        }
    }

    public void seleccionarPersonal(int idPersonal, int cantidad) {
        for (DTOPersonal p : configBO.consultarPersonal()) {
            if (p.getIdEspecialidad() == idPersonal) {
                p.setCantidad(cantidad);
                return;
            }
        }
    }

    public boolean verificarSeleccionEquipo() {
        for (DTOEquipo e : configBO.consultarEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarSeleccionPersonal() {
        for (DTOPersonal p : configBO.consultarPersonal()) {
            if (p.getCantidad() > 0) {
                return true;
            }
        }
        return false;
    }

    public void enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios) {
        LocalTime montaje = parsearHorario(horarioMontaje);
        LocalTime desmontaje = parsearHorario(horarioDesmontaje);
        this.horarioMontaje = montaje;
        this.horarioDesmontaje = desmontaje;
        this.comentarios = comentarios == null ? "" : comentarios.trim();
    }

    private LocalTime parsearHorario(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalTime.parse(texto.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Los horarios deben tener el formato HH:mm, por ejemplo 08:30.", ex);
        }
    }
}
