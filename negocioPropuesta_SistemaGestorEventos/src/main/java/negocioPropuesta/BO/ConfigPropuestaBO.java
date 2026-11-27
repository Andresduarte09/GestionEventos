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

}