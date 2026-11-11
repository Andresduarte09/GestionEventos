package negocioPropuesta.fachada;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOPropuesta;
import dto.DTOServicio;
import infraestructura.ProveedorExterno;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import negocioPropuesta.BO.ConfigPropuestaBO;
import negocioPropuesta.BO.PropuestaBO;
import negocioPropuesta.control.GestorPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;

/**
 * Punto de entrada al subsistema de propuesta.
 */
public class FachadaPropuesta implements ISubsistemaPropuesta {

    private final GestorPropuesta gestor;

    public FachadaPropuesta(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        this.gestor = new GestorPropuesta(
                ConfigPropuestaBO.getInstance(almacen, proveedorExterno),
                PropuestaBO.getInstance(almacen, proveedorExterno));
    }

    @Override
    public void seleccionarServicios(int idServicio, boolean seleccionado) {
        for (DTOServicio s : gestor.solicitarServicios()) {
            if (s.getIdServicio() == idServicio) {
                s.setSeleccionado(seleccionado);
                return;
            }
        }
    }

    @Override
    public void seleccionarEquipos(int idEquipo, int cantidad) {
        for (DTOEquipo e : gestor.solicitarEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                e.setCantidadSeleccionada(cantidad);
                return;
            }
        }
    }

    @Override
    public void seleccionarPersonal(int idPersonal, int cantidad) {
        for (DTOPersonal p : gestor.solicitarPersonal()) {
            if (p.getIdEspecialidad() == idPersonal) {
                p.setCantidad(cantidad);
                return;
            }
        }
    }

    @Override
    public boolean verificarSeleccionEquipo() {
        for (DTOEquipo e : gestor.solicitarEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verificarSeleccionPersonal() {
        for (DTOPersonal p : gestor.solicitarPersonal()) {
            if (p.getCantidad() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DTOPropuesta enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios) {
        validarHorario(horarioMontaje);
        validarHorario(horarioDesmontaje);
        return gestor.solicitarPropuesta(null, horarioMontaje, horarioDesmontaje, comentarios);
    }

    //los horarios son opcionales pero si vienen deben ser HH:mm
    private void validarHorario(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return;
        }
        try {
            LocalTime.parse(texto.trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Los horarios deben tener el formato HH:mm, por ejemplo 08:30.", ex);
        }
    }
}