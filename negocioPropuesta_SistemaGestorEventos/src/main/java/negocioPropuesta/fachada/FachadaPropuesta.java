package negocioPropuesta.fachada;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import infraestructura.ProveedorExterno;
import java.util.List;
import negocioPropuesta.BO.ConfigPropuestaBO;
import negocioPropuesta.control.ControlPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;

/**
 * Punto de entrada al subsistema de propuesta.
 */
public class FachadaPropuesta implements ISubsistemaPropuesta {

    private final ControlPropuesta control;

    public FachadaPropuesta(AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        this.control = new ControlPropuesta(ConfigPropuestaBO.getInstance(almacen, proveedorExterno));
    }

    @Override
    public List<DTOServicio> consultarServiciosDisponibles() {
        return control.consultarServiciosDisponibles();
    }

    @Override
    public List<DTOEquipo> obtenerEquiposDisponibles() {
        return control.obtenerEquiposDisponibles();
    }

    @Override
    public List<DTOPersonal> obtenerPersonalDisponible() {
        return control.obtenerPersonalDisponible();
    }

    @Override
    public void seleccionarServicios(int idServicio, boolean seleccionado) {
        control.seleccionarServicios(idServicio, seleccionado);
    }

    @Override
    public void seleccionarEquipos(int idEquipo, int cantidad) {
        control.seleccionarEquipos(idEquipo, cantidad);
    }

    @Override
    public void seleccionarPersonal(int idPersonal, int cantidad) {
        control.seleccionarPersonal(idPersonal, cantidad);
    }

    @Override
    public boolean verificarSeleccionEquipo() {
        return control.verificarSeleccionEquipo();
    }

    @Override
    public boolean verificarSeleccionPersonal() {
        return control.verificarSeleccionPersonal();
    }

    @Override
    public void enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios) {
        control.enviarPropuestaHaciaCotizacion(horarioMontaje, horarioDesmontaje, comentarios);
    }
}