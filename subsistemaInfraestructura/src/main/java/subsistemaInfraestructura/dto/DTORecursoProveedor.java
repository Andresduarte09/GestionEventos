package subsistemaInfraestructura.dto;

import dto.DTORecurso;

public record DTORecursoProveedor(int idRecurso, String nombre, String descripcion,
        int capacidad, float precioReferencia, String unidadCapacidad, String tipoRecurso,
        String unidadMedida, String estadoEnCatalogo) {
    public DTORecursoProveedor {
        if (idRecurso <= 0 || nombre == null || nombre.isBlank() || capacidad < 0
                || !Float.isFinite(precioReferencia) || precioReferencia < 0
                || descripcion == null || unidadCapacidad == null || tipoRecurso == null
                || unidadMedida == null || estadoEnCatalogo == null) {
            throw new IllegalArgumentException("Recurso externo inválido");
        }
    }

    public DTORecurso aDTORecurso() {
        return new DTORecurso(idRecurso, nombre, descripcion, capacidad, precioReferencia,
                unidadCapacidad, tipoRecurso, unidadMedida, estadoEnCatalogo);
    }
}
