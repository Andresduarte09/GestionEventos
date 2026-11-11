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
import java.util.List;
import negocioPropuesta.interfaces.IPropuestaBO;

/**
 * Guarda y consulta la propuesta del evento y la selección de recursos
 * (servicios, equipos y personal) asociada a ella.
 */
public class PropuestaBO implements IPropuestaBO {

    private final AlmacenDatos almacen;
    private final ProveedorExterno proveedorExterno;

    //AlmacenDatos todavia no maneja propuestas, por eso se conservan aqui
    private final List<DTOPropuesta> propuestas = new ArrayList<>();

    private static PropuestaBO instancia;

    private PropuestaBO(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
    }

    //Singleton: se crea una sola vez; las llamadas posteriores reutilizan la misma instancia
    public static synchronized PropuestaBO getInstance(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        if (instancia == null) {
            instancia = new PropuestaBO(almacen, proveedorExterno);
        }
        return instancia;
    }

    //guarda la propuesta; si no trae id le asigna uno nuevo y si ya existe la reemplaza
    @Override
    public DTOPropuesta guardar(DTOPropuesta propuesta) {
        if (propuesta == null) {
            return null;
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
            propuestas.removeIf(p -> p.getIdPropuesta() == propuesta.getIdPropuesta());
        }

        propuestas.add(propuesta);
        return propuesta;
    }

    @Override
    public DTOPropuesta consultar(int idPropuesta) {
        for (DTOPropuesta p : propuestas) {
            if (p.getIdPropuesta() == idPropuesta) {
                return p;
            }
        }
        return null;
    }

    //arma un inventario solo con lo que esta seleccionado actualmente
    //la seleccion se maneja en memoria para un evento a la vez, por eso idEvento no filtra
    @Override
    public DTOInventario consultarSeleccion(int idEvento) {
        DTOInventario seleccion = new DTOInventario();

        for (DTOServicio s : almacen.getServicios()) {
            if (s.isSeleccionado()) {
                seleccion.getServicios().add(s);
            }
        }

        for (DTOEquipo e : todosLosEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                seleccion.getEquipos().add(e);
            }
        }

        for (DTOPersonal p : almacen.getPersonal()) {
            if (p.getCantidad() > 0) {
                seleccion.getPersonal().add(p);
            }
        }

        return seleccion;
    }

    //aplica los cambios recibidos sobre la seleccion actual
    @Override
    public void actualizarSeleccion(DTOInventario cambios) {
        if (cambios == null) {
            return;
        }

        for (DTOServicio cambio : cambios.getServicios()) {
            for (DTOServicio s : almacen.getServicios()) {
                if (s.getIdServicio() == cambio.getIdServicio()) {
                    s.setSeleccionado(cambio.isSeleccionado());
                    break;
                }
            }
        }

        for (DTOEquipo cambio : cambios.getEquipos()) {
            for (DTOEquipo e : todosLosEquipos()) {
                if (e.getIdEquipo() == cambio.getIdEquipo()) {
                    e.setCantidadSeleccionada(cambio.getCantidadSeleccionada());
                    break;
                }
            }
        }

        for (DTOPersonal cambio : cambios.getPersonal()) {
            for (DTOPersonal p : almacen.getPersonal()) {
                if (p.getIdEspecialidad() == cambio.getIdEspecialidad()) {
                    p.setCantidad(cambio.getCantidad());
                    break;
                }
            }
        }
    }

    private List<DTOEquipo> todosLosEquipos() {
        List<DTOEquipo> todos = new ArrayList<>(almacen.getEquipos());
        todos.addAll(proveedorExterno.getEquipos());
        return todos;
    }
}
