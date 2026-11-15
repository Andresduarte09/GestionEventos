/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOPropuesta;
import dto.DTOServicio;
import infraestructura.ProveedorExterno;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import negocioPropuesta.interfaces.IPropuestaBO;

public class PropuestaBO implements IPropuestaBO {

    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;

    private final List<DTOPropuesta> propuestas =
            new ArrayList<>();

    private final Map<Integer, DTOInventario> seleccionesPorEvento =
            new HashMap<>();

    private static PropuestaBO instancia;

    private PropuestaBO(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
    }

    public static synchronized PropuestaBO getInstance(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        if (instancia == null) {
            instancia = new PropuestaBO(
                    almacen,
                    proveedorExterno
            );
        }

        return instancia;
    }

    private boolean existeEvento(int idEvento) {

        for (var evento : almacen.getEventos()) {
            if (evento.getIdEvento() == idEvento) {
                return true;
            }
        }

        return false;
    }

    @Override
    public synchronized void registrarSeleccionEvento(
            int idEvento,
            DTOInventario seleccion) {

        if (idEvento <= 0 || !existeEvento(idEvento)) {
            throw new IllegalArgumentException(
                    "El evento no existe: " + idEvento
            );
        }

        if (seleccion == null) {
            throw new IllegalArgumentException(
                    "La seleccion no puede ser null."
            );
        }

        seleccionesPorEvento.put(
                idEvento,
                copiarInventario(seleccion)
        );
    }

    @Override
    public synchronized DTOInventario consultarSeleccion(
            int idEvento) {

        return copiarInventario(
                seleccionesPorEvento.get(idEvento)
        );
    }

    @Override
    public synchronized DTOPropuesta guardar(
            DTOPropuesta propuesta) {

        if (propuesta == null) {
            return null;
        }

        if (!existeEvento(propuesta.getIdEvento())) {
            throw new IllegalArgumentException(
                    "La propuesta debe tener un evento registrado."
            );
        }

        if (propuesta.getInventario() == null) {
            throw new IllegalArgumentException(
                    "La propuesta debe incluir su inventario."
            );
        }

        if (propuesta.getIdPropuesta() <= 0) {

            int nuevoId = 1;

            for (DTOPropuesta p : propuestas) {
                if (p.getIdPropuesta() >= nuevoId) {
                    nuevoId = p.getIdPropuesta() + 1;
                }
            }

            propuesta.setIdPropuesta(nuevoId);

        } else {

            propuestas.removeIf(
                    p -> p.getIdPropuesta()
                    == propuesta.getIdPropuesta()
            );
        }

        propuestas.add(copiarPropuesta(propuesta));

        return copiarPropuesta(propuesta);
    }

    @Override
    public synchronized DTOPropuesta consultar(int idPropuesta) {

        for (DTOPropuesta p : propuestas) {

            if (p.getIdPropuesta() == idPropuesta) {
                return copiarPropuesta(p);
            }
        }

        return null;
    }

    @Override
    public synchronized DTOInventario consultarRecursosPropuesta(
            int idPropuesta) {

        DTOPropuesta propuesta = consultar(idPropuesta);

        return propuesta == null
                ? null
                : propuesta.getInventario();
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
                copiarInventario(origen.getInventario())
        );

        return copia;
    }

    private DTOInventario copiarInventario(DTOInventario origen) {

        if (origen == null) {
            return null;
        }

        DTOInventario copia = new DTOInventario();

        copia.setDisponibilidad(origen.getDisponibilidad());

        if (origen.getServicios() != null) {

            for (DTOServicio s : origen.getServicios()) {

                copia.getServicios().add(
                        new DTOServicio(
                                s.getIdServicio(),
                                s.getNombre(),
                                s.getDescripcion(),
                                s.isSeleccionado(),
                                s.isEsAdicional(),
                                s.getObservaciones()
                        )
                );
            }
        }

        if (origen.getEquipos() != null) {

            for (DTOEquipo e : origen.getEquipos()) {

                copia.getEquipos().add(
                        new DTOEquipo(
                                e.getIdEquipo(),
                                e.getNombre(),
                                e.getDisponibilidad(),
                                e.getCantidadSeleccionada()
                        )
                );
            }
        }

        if (origen.getPersonal() != null) {

            for (DTOPersonal p : origen.getPersonal()) {

                copia.getPersonal().add(
                        new DTOPersonal(
                                p.getIdEspecialidad(),
                                p.getEspecialidad(),
                                p.getCantidad(),
                                p.getHorasMontaje(),
                                p.getHorasOperacion(),
                                p.getHorasDesmontaje()
                        )
                );
            }
        }

        return copia;
    }

    @Override
    public void actualizarSeleccion(DTOInventario cambios) {

        if (cambios == null) {
            return;
        }

        if (cambios.getServicios() != null) {

            for (DTOServicio cambio : cambios.getServicios()) {

                for (DTOServicio s : almacen.getServicios()) {

                    if (s.getIdServicio() == cambio.getIdServicio()) {

                        s.setSeleccionado(cambio.isSeleccionado());
                        break;
                    }
                }
            }
        }

        if (cambios.getEquipos() != null) {

            for (DTOEquipo cambio : cambios.getEquipos()) {

                for (DTOEquipo e : todosLosEquipos()) {

                    if (e.getIdEquipo() == cambio.getIdEquipo()) {

                        e.setCantidadSeleccionada(
                                cambio.getCantidadSeleccionada()
                        );

                        break;
                    }
                }
            }
        }

        if (cambios.getPersonal() != null) {

            for (DTOPersonal cambio : cambios.getPersonal()) {

                for (DTOPersonal p : almacen.getPersonal()) {

                    if (p.getIdEspecialidad()
                            == cambio.getIdEspecialidad()) {

                        p.setCantidad(cambio.getCantidad());
                        break;
                    }
                }
            }
        }
    }

    private List<DTOEquipo> todosLosEquipos() {

        List<DTOEquipo> todos =
                new ArrayList<>(almacen.getEquipos());

        todos.addAll(proveedorExterno.getEquipos());

        return todos;
    }
}