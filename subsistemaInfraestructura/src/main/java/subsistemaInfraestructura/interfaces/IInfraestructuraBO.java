package subsistemaInfraestructura.interfaces;
import java.util.List;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;

public interface IInfraestructuraBO {
    List<DTOEquipoProveedor> consultarEquipos();
    List<DTORecursoProveedor> consultarRecursos();
}
