/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioPropuesta.BO;

import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.util.ArrayList;
import java.util.List;
import negocioPropuesta.interfaces.IConfigPropuestaBO;
import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;

/**
 * Consulta de recursos y manejo de la selección para configurar la propuesta.
 */
public class ConfigPropuestaBO implements IConfigPropuestaBO {

    //datos internos (almacen) y equipos del proveedor externo
    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;

    private static ConfigPropuestaBO instancia;

    private ConfigPropuestaBO(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
    }

    //Singleton: se crea una sola vez; las llamadas posteriores reutilizan la misma instancia
    public static synchronized ConfigPropuestaBO getInstance(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        if (instancia == null) {
            instancia = new ConfigPropuestaBO(almacen, proveedorExterno);
        }
        return instancia;
    }

    @Override
    public List<DTOServicio> consultarServicios() {
        return new ArrayList<>(almacen.getServicios());
    }

    @Override
    public List<DTOEquipo> consultarEquipos() {
        return new ArrayList<>(almacen.getEquipos());
    }

    @Override
    public List<DTOPersonal> consultarPersonal() {
        return new ArrayList<>(almacen.getPersonal());
    }

    //equipos propios + equipos del proveedor externo
    private List<DTOEquipo> todosLosEquipos() {
        List<DTOEquipo> todos = consultarEquipos();
        todos.addAll(proveedorExterno.getEquipos());
        return todos;
    }

    @Override
    public void seleccionarServicio(int idServicio, boolean seleccionado) {
        for (DTOServicio s : almacen.getServicios()) {
            if (s.getIdServicio() == idServicio) {
                s.setSeleccionado(seleccionado);
                return;
            }
        }
    }

    @Override
    public void seleccionarEquipo(int idEquipo, int cantidad) {
        for (DTOEquipo e : todosLosEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                e.setCantidadSeleccionada(cantidad);
                return;
            }
        }
    }

    @Override
    public void seleccionarPersonal(int idEspecialidad, int cantidad) {
        for (DTOPersonal p : almacen.getPersonal()) {
            if (p.getIdEspecialidad() == idEspecialidad) {
                p.setCantidad(cantidad);
                return;
            }
        }
    }

    @Override
    public boolean haySeleccionEquipos() {
        for (DTOEquipo e : todosLosEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean haySeleccionPersonal() {
        for (DTOPersonal p : consultarPersonal()) {
            if (p.getCantidad() > 0) {
                return true;
            }
        }
        return false;
    }
}
