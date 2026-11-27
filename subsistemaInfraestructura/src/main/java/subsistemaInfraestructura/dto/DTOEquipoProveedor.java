package subsistemaInfraestructura.dto;

/** Datos remotos, independientes de las cantidades seleccionadas. */
public record DTOEquipoProveedor(int idEquipo, int idRecurso, String nombre, int disponibilidad) {
    public DTOEquipoProveedor {
        if (idEquipo <= 0 || idRecurso <= 0 || nombre == null || nombre.isBlank() || disponibilidad < 0) {
            throw new IllegalArgumentException("Equipo externo inválido");
        }
    }
}
