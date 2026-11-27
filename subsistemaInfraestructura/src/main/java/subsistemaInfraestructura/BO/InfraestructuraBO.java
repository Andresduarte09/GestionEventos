package subsistemaInfraestructura.BO;

import java.util.List;
import java.util.Objects;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;
import subsistemaInfraestructura.interfaces.IInfraestructuraBO;
import subsistemaInfraestructura.interfaces.IProveedorExterno;

public class InfraestructuraBO implements IInfraestructuraBO {
    private final IProveedorExterno proveedor;

    public InfraestructuraBO(IProveedorExterno proveedor) {
        this.proveedor = Objects.requireNonNull(proveedor);
    }

    @Override
    public List<DTOEquipoProveedor> consultarEquipos() {
        return proveedor.consultarEquipos();
    }

    @Override
    public List<DTORecursoProveedor> consultarRecursos() {
        return proveedor.consultarRecursos();
    }
}
