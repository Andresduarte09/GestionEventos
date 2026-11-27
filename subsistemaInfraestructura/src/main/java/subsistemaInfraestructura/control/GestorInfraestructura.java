package subsistemaInfraestructura.control;

import dto.DTOEquipo;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;
import subsistemaInfraestructura.interfaces.IInfraestructuraBO;

public class GestorInfraestructura {
    private final IInfraestructuraBO bo;
    private final Map<Integer, DTOEquipo> equiposLocales = new LinkedHashMap<>();
    private List<DTOEquipoProveedor> equiposRemotos;
    private List<DTORecursoProveedor> recursosRemotos;
    private long ultimaConsultaEquipos;
    private long ultimaConsultaRecursos;
    private static final long VIGENCIA = Duration.ofSeconds(10).toNanos();

    public GestorInfraestructura(IInfraestructuraBO bo) {
        this.bo = Objects.requireNonNull(bo);
    }

    public synchronized List<DTOEquipoProveedor> consultarCatalogoEquipos() {
        if (equiposRemotos == null || System.nanoTime() - ultimaConsultaEquipos >= VIGENCIA) {
            equiposRemotos = List.copyOf(bo.consultarEquipos());
            ultimaConsultaEquipos = System.nanoTime();
        }
        return equiposRemotos;
    }

    public synchronized List<DTOEquipo> consultarEquipos() {
        Map<Integer, DTOEquipo> actualizados = new LinkedHashMap<>();
        for (DTOEquipoProveedor remoto : consultarCatalogoEquipos()) {
            DTOEquipo equipo = equiposLocales.get(remoto.idEquipo());
            if (equipo == null) {
                equipo = new DTOEquipo(remoto.idEquipo(), remoto.nombre(), remoto.disponibilidad(), 0);
            } else {
                equipo.setNombre(remoto.nombre());
                equipo.setDisponibilidad(remoto.disponibilidad());
            }
            actualizados.put(remoto.idEquipo(), equipo);
        }
        equiposLocales.clear();
        equiposLocales.putAll(actualizados);
        return new ArrayList<>(equiposLocales.values());
    }

    public DTOEquipoProveedor consultarEquipo(int id) {
        return consultarCatalogoEquipos().stream().filter(e -> e.idEquipo() == id).findFirst().orElse(null);
    }

    public List<DTOEquipoProveedor> buscarEquipos(String nombre) {
        String buscado = Objects.requireNonNull(nombre).trim().toLowerCase(Locale.ROOT);
        return consultarCatalogoEquipos().stream()
                .filter(e -> e.nombre().toLowerCase(Locale.ROOT).contains(buscado)).toList();
    }

    public synchronized List<DTORecursoProveedor> consultarRecursos() {
        if (recursosRemotos == null || System.nanoTime() - ultimaConsultaRecursos >= VIGENCIA) {
            recursosRemotos = List.copyOf(bo.consultarRecursos());
            ultimaConsultaRecursos = System.nanoTime();
        }
        return recursosRemotos;
    }

    public DTORecursoProveedor consultarRecurso(int id) {
        return consultarRecursos().stream().filter(r -> r.idRecurso() == id).findFirst().orElse(null);
    }

    public DTORecursoProveedor consultarRecursoPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        List<DTORecursoProveedor> encontrados = consultarRecursos().stream()
                .filter(r -> r.nombre().equalsIgnoreCase(nombre.trim())).toList();
        if (encontrados.size() > 1) throw new IllegalStateException("Nombre de recurso duplicado: " + nombre);
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    public float consultarTarifaEquipo(int idEquipo) {
        DTOEquipoProveedor equipo = consultarEquipo(idEquipo);
        if (equipo == null) throw new IllegalArgumentException("Equipo inexistente: " + idEquipo);
        DTORecursoProveedor recurso = consultarRecurso(equipo.idRecurso());
        if (recurso == null || !"Activo".equalsIgnoreCase(recurso.estadoEnCatalogo())
                || !"pieza".equalsIgnoreCase(recurso.unidadMedida())) {
            throw new IllegalStateException("El equipo no tiene una tarifa activa por pieza");
        }
        return recurso.precioReferencia();
    }

    public boolean verificarDisponibilidad(int idEquipo, int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Cantidad negativa");
        DTOEquipoProveedor equipo = consultarEquipo(idEquipo);
        return equipo != null && equipo.disponibilidad() >= cantidad;
    }
}
