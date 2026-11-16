/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTODetalleSolicitud;
import dto.DTOEquipo;
import dto.DTOEvento;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOServicio;
import infraestructura.ProveedorExterno;
import java.util.ArrayList;
import java.util.List;
import negocioPropuesta.interfaces.IConfigPropuestaBO;

public class ConfigPropuestaBO implements IConfigPropuestaBO {

    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;

    private static ConfigPropuestaBO instancia;

    private ConfigPropuestaBO(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {

        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
    }

    public static synchronized ConfigPropuestaBO getInstance(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        if (instancia == null) {
            instancia = new ConfigPropuestaBO(
                    almacen,
                    proveedorExterno
            );
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

        for (DTOEvento e : almacen.getEventos()) {

            if (e.getIdEvento() == idEvento) {

                List<DTODetalleSolicitud> detalles =
                        e.getDetallesSolicitud();

                return detalles == null
                        ? new ArrayList<>()
                        : new ArrayList<>(detalles);
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

        List<DTOEquipo> todos =
                new ArrayList<>(almacen.getEquipos());

        todos.addAll(proveedorExterno.getEquipos());

        return todos;
    }

    @Override
    public List<DTOPersonal> consultarPersonal() {
        return new ArrayList<>(almacen.getPersonal());
    }

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
    public boolean verificarDisponibilidad(
            DTOInventario seleccion) {

        if (seleccion == null) {
            return false;
        }

        List<DTOEquipo> existentes = consultarEquipos();

        for (DTOEquipo pedido : seleccion.getEquipos()) {

            boolean encontrado = false;

            for (DTOEquipo e : existentes) {

                if (e.getIdEquipo() == pedido.getIdEquipo()) {

                    encontrado = true;

                    if (pedido.getCantidadSeleccionada()
                            > e.getDisponibilidad()) {
                        return false;
                    }

                    break;
                }
            }

            if (!encontrado) {
                return false;
            }
        }

        return true;
    }
}