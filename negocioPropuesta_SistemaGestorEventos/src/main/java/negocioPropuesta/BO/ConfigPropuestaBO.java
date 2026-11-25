/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.BO;

import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;
import negocioPropuesta.interfaces.IConfigPropuestaBO;
import dto.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Andre
 */
public class ConfigPropuestaBO implements IConfigPropuestaBO {

    private static ConfigPropuestaBO instancia;

    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;

    private ConfigPropuestaBO(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
    }

    public static synchronized ConfigPropuestaBO getInstance(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        if (instancia == null) {

            instancia = new ConfigPropuestaBO(
                    almacen, proveedorExterno);
        }

        return instancia;
    }

    @Override
    public DTODetalleSolicitud guardarDetalleSolicitud(
            DTODetalleSolicitud detalle) {

        if (detalle == null) {
            return null;
        }

        almacen.getDetallesSolicitud().add(detalle);

        return detalle;
    }

    @Override
    public List<DTODetalleSolicitud> consultarDetallesPorEvento(
            int idEvento) {

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() == idEvento) {

                if (evento.getDetallesSolicitud() == null) {
                    return new ArrayList<>();
                }

                return new ArrayList<>(
                        evento.getDetallesSolicitud());
            }
        }

        return new ArrayList<>();
    }

    @Override
    public List<DTOServicio> consultarServicios() {

        return new ArrayList<>(almacen.getServicios());
    }

    @Override
    public List<DTOEquipo> consultarEquipos() {

        List<DTOEquipo> equipos =
                new ArrayList<>(almacen.getEquipos());

        equipos.addAll(proveedorExterno.getEquipos());

        return equipos;
    }

    @Override
    public List<DTOPersonal> consultarPersonal() {

        return new ArrayList<>(almacen.getPersonal());
    }

    @Override
    public boolean verificarDisponibilidad(
            DTOInventario seleccion) {

        if (seleccion == null) {
            return false;
        }

        List<DTOEquipo> equipos = consultarEquipos();

        for (DTOEquipo solicitado : seleccion.getEquipos()) {

            boolean disponible = false;

            for (DTOEquipo equipo : equipos) {

                if (equipo.getIdEquipo()
                        == solicitado.getIdEquipo()) {

                    disponible =
                            solicitado.getCantidadSeleccionada() >= 0
                            && solicitado.getCantidadSeleccionada()
                            <= equipo.getDisponibilidad();

                    break;
                }
            }

            if (!disponible) {
                return false;
            }
        }

        return true;
    }

    // Operaciones de integración requeridas por el gestor.

    @Override
    public List<DTOEquipo> consultarEquiposPropios() {

        return new ArrayList<>(almacen.getEquipos());
    }

    @Override
    public List<DTOEquipo> consultarEquiposExternos() {

        return new ArrayList<>(proveedorExterno.getEquipos());
    }

    @Override
    public List<DTOInventario> consultarInventariosDisponibles() {

        return new ArrayList<>(almacen.getInventario());
    }

    @Override
    public boolean verificarDisponibilidadLocal(
            int idEventoActual) {

        DTOEvento actual = null;

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() == idEventoActual) {

                actual = evento;
                break;
            }
        }

        if (actual == null
                || actual.getUbicacion() == null
                || actual.getUbicacion().isBlank()
                || actual.getFechaEvento() == null) {

            return false;
        }

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() != idEventoActual) {

                boolean mismaFecha = Objects.equals(
                        actual.getFechaEvento(),
                        evento.getFechaEvento());

                boolean mismaUbicacion =
                        actual.getUbicacion().equalsIgnoreCase(
                                evento.getUbicacion());

                boolean cancelado = "Cancelado".equalsIgnoreCase(
                        evento.getEstadoAtencion());

                boolean rechazado = "Rechazada".equalsIgnoreCase(
                        evento.getEstadoAtencion());

                if (mismaFecha && mismaUbicacion
                        && !cancelado && !rechazado) {

                    return false;
                }
            }
        }

        return true;
    }
}