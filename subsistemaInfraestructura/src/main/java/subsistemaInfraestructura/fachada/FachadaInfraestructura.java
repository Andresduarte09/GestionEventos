package subsistemaInfraestructura.fachada;

import dto.DTOEquipo;
import java.util.List;
import subsistemaInfraestructura.BO.InfraestructuraBO;
import subsistemaInfraestructura.cliente.ProveedorExternoHTTP;
import subsistemaInfraestructura.control.GestorInfraestructura;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;
import subsistemaInfraestructura.interfaces.ISubsistemaInfraestructura;

public class FachadaInfraestructura implements ISubsistemaInfraestructura {
    private final GestorInfraestructura gestor;

    public FachadaInfraestructura(String urlBase) {
        gestor = new GestorInfraestructura(new InfraestructuraBO(new ProveedorExternoHTTP(urlBase)));
    }

    // Instancia compartida por los subsistemas de negocio.
    private static class Instancia {
        private static final ISubsistemaInfraestructura PROVEEDOR =
                new FachadaInfraestructura(System.getenv().getOrDefault(
                        "PROVEEDOR_URL", "https://proveedor-externo.onrender.com"));
    }

    public static ISubsistemaInfraestructura getInstance() {
        return Instancia.PROVEEDOR;
    }

    @Override public List<DTOEquipo> consultarEquipos() { return gestor.consultarEquipos(); }
    @Override public List<DTOEquipoProveedor> consultarCatalogoEquipos() { return gestor.consultarCatalogoEquipos(); }
    @Override public DTOEquipoProveedor consultarEquipo(int id) { return gestor.consultarEquipo(id); }
    @Override public List<DTOEquipoProveedor> buscarEquipos(String nombre) { return gestor.buscarEquipos(nombre); }
    @Override public List<DTORecursoProveedor> consultarRecursos() { return gestor.consultarRecursos(); }
    @Override public DTORecursoProveedor consultarRecurso(int id) { return gestor.consultarRecurso(id); }
    @Override public DTORecursoProveedor consultarRecursoPorNombre(String nombre) { return gestor.consultarRecursoPorNombre(nombre); }
    @Override public float consultarTarifaEquipo(int id) { return gestor.consultarTarifaEquipo(id); }
    @Override public boolean verificarDisponibilidad(int id, int cantidad) { return gestor.verificarDisponibilidad(id, cantidad); }
}
