/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.BO;

import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;
import negocioPropuesta.interfaces.IPropuestaBO;
import dto.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre
 */
public class PropuestaBO implements IPropuestaBO {

    private static PropuestaBO instancia;

    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;
    private final List<DTOPropuesta> propuestas;

    private PropuestaBO(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
        this.propuestas = new ArrayList<>();
    }

    public static synchronized PropuestaBO getInstance(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        if (instancia == null) {
            instancia = new PropuestaBO(
                    almacen, proveedorExterno);
        }

        return instancia;
    }

    @Override
    public synchronized DTOPropuesta guardar(
            DTOPropuesta propuesta) {

        if (propuesta == null) {
            return null;
        }

        if (!existeEvento(propuesta.getIdEvento())) {
            throw new IllegalArgumentException(
                    "El evento no existe.");
        }

        if (propuesta.getInventario() == null) {
            throw new IllegalArgumentException(
                    "La propuesta no tiene inventario.");
        }

        if (propuesta.getIdPropuesta() <= 0) {

            int id = 1;

            for (DTOPropuesta p : propuestas) {

                if (p.getIdPropuesta() >= id) {
                    id = p.getIdPropuesta() + 1;
                }
            }

            propuesta.setIdPropuesta(id);
        }

        if (propuesta.getFolio() == null
                || propuesta.getFolio().isBlank()) {

            propuesta.setFolio(
                    "PROP-" + propuesta.getIdPropuesta());
        }

        propuestas.removeIf(p ->
                p.getIdPropuesta() == propuesta.getIdPropuesta());

        propuestas.add(copiarPropuesta(propuesta));

        return copiarPropuesta(propuesta);
    }

    @Override
    public synchronized DTOPropuesta consultar(int idPropuesta) {

        for (DTOPropuesta propuesta : propuestas) {

            if (propuesta.getIdPropuesta() == idPropuesta) {

                return copiarPropuesta(propuesta);
            }
        }

        return null;
    }

    @Override
    public DTOInventario consultarSeleccion(int idEvento) {

        if (!existeEvento(idEvento)) {

            throw new IllegalArgumentException(
                    "El evento no existe.");
        }

        DTOInventario seleccion = new DTOInventario();

        for (DTOServicio servicio : almacen.getServicios()) {

            if (servicio.isSeleccionado()) {
                seleccion.getServicios().add(servicio);
            }
        }

        for (DTOEquipo equipo : consultarTodosLosEquipos()) {

            if (equipo.getCantidadSeleccionada() > 0) {
                seleccion.getEquipos().add(equipo);
            }
        }

        for (DTOPersonal personal : almacen.getPersonal()) {

            if (personal.getCantidad() > 0) {
                seleccion.getPersonal().add(personal);
            }
        }

        return copiarInventario(seleccion);
    }

    @Override
    public void actualizarSeleccion(DTOInventario cambios) {

        if (cambios == null) {
            return;
        }

        if (cambios.getServicios() != null) {

            for (DTOServicio cambio : cambios.getServicios()) {

                for (DTOServicio servicio : almacen.getServicios()) {

                    if (servicio.getIdServicio()
                            == cambio.getIdServicio()) {

                        servicio.setSeleccionado(
                                cambio.isSeleccionado());

                        break;
                    }
                }
            }
        }

        if (cambios.getEquipos() != null) {

            for (DTOEquipo cambio : cambios.getEquipos()) {

                for (DTOEquipo equipo : consultarTodosLosEquipos()) {

                    if (equipo.getIdEquipo()
                            == cambio.getIdEquipo()) {

                        equipo.setCantidadSeleccionada(
                                cambio.getCantidadSeleccionada());

                        break;
                    }
                }
            }
        }

        if (cambios.getPersonal() != null) {

            for (DTOPersonal cambio : cambios.getPersonal()) {

                for (DTOPersonal personal : almacen.getPersonal()) {

                    if (personal.getIdEspecialidad()
                            == cambio.getIdEspecialidad()) {

                        personal.setCantidad(
                                cambio.getCantidad());

                        break;
                    }
                }
            }
        }
    }

    // Métodos privados auxiliares.

    private boolean existeEvento(int idEvento) {

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() == idEvento) {
                return true;
            }
        }

        return false;
    }

    private List<DTOEquipo> consultarTodosLosEquipos() {

        List<DTOEquipo> equipos =
                new ArrayList<>(almacen.getEquipos());

        equipos.addAll(proveedorExterno.getEquipos());

        return equipos;
    }

    private DTOPropuesta copiarPropuesta(DTOPropuesta origen) {

        DTOPropuesta copia = new DTOPropuesta();

        copia.setIdPropuesta(origen.getIdPropuesta());
        copia.setIdEvento(origen.getIdEvento());
        copia.setFolio(origen.getFolio());
        copia.setHorarioMontaje(origen.getHorarioMontaje());
        copia.setHorarioDesmontaje(origen.getHorarioDesmontaje());
        copia.setComentarios(origen.getComentarios());
        copia.setEstadoFolio(origen.getEstadoFolio());

        copia.setInventario(
                copiarInventario(origen.getInventario()));

        return copia;
    }

    private DTOInventario copiarInventario(DTOInventario origen) {

        if (origen == null) {
            return null;
        }

        DTOInventario copia = new DTOInventario();

        copia.setDisponibilidad(origen.getDisponibilidad());

        if (origen.getServicios() != null) {

            for (DTOServicio servicio : origen.getServicios()) {

                copia.getServicios().add(new DTOServicio(
                        servicio.getIdServicio(),
                        servicio.getNombre(),
                        servicio.getDescripcion(),
                        servicio.isSeleccionado(),
                        servicio.isEsAdicional(),
                        servicio.getObservaciones()
                ));
            }
        }

        if (origen.getEquipos() != null) {

            for (DTOEquipo equipo : origen.getEquipos()) {

                copia.getEquipos().add(new DTOEquipo(
                        equipo.getIdEquipo(),
                        equipo.getNombre(),
                        equipo.getDisponibilidad(),
                        equipo.getCantidadSeleccionada()
                ));
            }
        }

        if (origen.getPersonal() != null) {

            for (DTOPersonal personal : origen.getPersonal()) {

                copia.getPersonal().add(new DTOPersonal(
                        personal.getIdEspecialidad(),
                        personal.getEspecialidad(),
                        personal.getCantidad(),
                        personal.getHorasMontaje(),
                        personal.getHorasOperacion(),
                        personal.getHorasDesmontaje()
                ));
            }
        }

        return copia;
    }
}