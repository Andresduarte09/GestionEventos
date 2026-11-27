/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.BO;

/**
 *
 * @author Andres
 */

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOEquipo;
import dto.DTOEvento;
import dto.DTOInventario;
import dto.DTOOrdenServicio;
import dto.DTOPersonal;
import dto.DTOServicio;
import dto.DTOVersionCotizacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import negocioOrden.interfaces.IOrdenServicioBO;

public class OrdenServicioBO implements IOrdenServicioBO {

    private static final String SUFIJO_EQUIPO = " (Equipo)";
    private static final String SUFIJO_PERSONAL = " (Personal)";

    private final AlmacenDatos almacen;

    public OrdenServicioBO(AlmacenDatos almacen) {
        if (almacen == null) {
            throw new IllegalArgumentException("AlmacenDatos no puede ser nulo.");
        }
        this.almacen = almacen;
    }

    @Override
    public DTOOrdenServicio generarOrdenServicio(int idEvento, int idCotizacion, List<String> elementosAceptados) {
        validarEventoYCotizacion(idEvento, idCotizacion);

        DTOVersionCotizacion ultimaVersion = obtenerUltimaVersionCotizacion(idCotizacion);
        if (ultimaVersion == null) {
            throw new IllegalStateException("No existe versión registrada para la cotización " + idCotizacion);
        }

        List<DTOInventario> inventarios = obtenerInventariosUltimaVersion(idCotizacion);
        List<DTOServicio> serviciosDisponibles = obtenerServiciosPorCotizacion(idCotizacion);
        List<DTOEquipo> equiposDisponibles = obtenerEquiposDeInventarios(inventarios);
        List<DTOPersonal> personalDisponible = obtenerPersonalDeInventarios(inventarios);

        List<String> aceptadosNormalizados = normalizarSelecciones(elementosAceptados);
        boolean incluirTodos = aceptadosNormalizados.isEmpty();

        List<DTOServicio> serviciosOrden = new ArrayList<>();

        for (int i = 0; i < serviciosDisponibles.size(); i++) {
            DTOServicio servicio = serviciosDisponibles.get(i);
            if (incluirTodos || coincideNombre(servicio.getNombre(), aceptadosNormalizados)) {
                agregarServicioSinDuplicar(serviciosOrden, servicio);
            }
        }

        for (int i = 0; i < equiposDisponibles.size(); i++) {
            DTOEquipo equipo = equiposDisponibles.get(i);
            if (incluirTodos || coincideNombre(equipo.getNombre(), aceptadosNormalizados)) {
                int cantidad = obtenerCantidadEquipo(equipo);
                DTOServicio equipoAdaptado = new DTOServicio(
                        equipo.getIdEquipo(),
                        equipo.getNombre(),
                        "Equipo cotizado (cantidad: " + cantidad + ")",
                        true,
                        "Versión #" + ultimaVersion.getNumero()
                );
                agregarServicioSinDuplicar(serviciosOrden, equipoAdaptado);
            }
        }

        for (int i = 0; i < personalDisponible.size(); i++) {
            DTOPersonal personal = personalDisponible.get(i);
            if (incluirTodos || coincideNombre(personal.getEspecialidad(), aceptadosNormalizados)) {
                DTOServicio personalAdaptado = new DTOServicio(
                        personal.getIdEspecialidad(),
                        personal.getEspecialidad(),
                        "Personal cotizado (cantidad: " + personal.getCantidad() + ")",
                        true,
                        "Versión #" + ultimaVersion.getNumero()
                );
                agregarServicioSinDuplicar(serviciosOrden, personalAdaptado);
            }
        }

        int nuevoId = almacen.getOrdenes().size() + 1;
        DTOOrdenServicio orden = new DTOOrdenServicio(
                nuevoId,
                idEvento,
                idCotizacion,
                LocalDate.now(),
                serviciosOrden
        );

        almacen.registrarOrden(orden);
        return orden;
    }

    @Override
    public DTOOrdenServicio consultarOrdenPorId(int idOrden) {
        if (almacen.getOrdenes() == null) {
            return null;
        }
        for (int i = 0; i < almacen.getOrdenes().size(); i++) {
            DTOOrdenServicio orden = almacen.getOrdenes().get(i);
            if (orden != null && orden.getIdOrden() == idOrden) {
                return orden;
            }
        }
        return null;
    }

    @Override
    public List<DTOOrdenServicio> consultarTodasOrdenes() {
        if (almacen.getOrdenes() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(almacen.getOrdenes());
    }

    @Override
    public List<DTOServicio> obtenerServiciosPorCotizacion(int idCotizacion) {
        List<DTOServicio> resultado = new ArrayList<>();
        List<DTOInventario> inventarios = obtenerInventariosUltimaVersion(idCotizacion);

        for (int i = 0; i < inventarios.size(); i++) {
            DTOInventario inv = inventarios.get(i);
            if (inv == null || inv.getServicios() == null) {
                continue;
            }
            for (int j = 0; j < inv.getServicios().size(); j++) {
                DTOServicio s = inv.getServicios().get(j);
                if (s != null) {
                    agregarServicioSinDuplicar(resultado, s);
                }
            }
        }
        return resultado;
    }

    @Override
    public List<String> obtenerRecursosCotizados(int idCotizacion) {
        List<String> items = new ArrayList<>();
        List<DTOInventario> inventarios = obtenerInventariosUltimaVersion(idCotizacion);

        for (int i = 0; i < inventarios.size(); i++) {
            DTOInventario inv = inventarios.get(i);
            if (inv == null) {
                continue;
            }

            if (inv.getServicios() != null) {
                for (int j = 0; j < inv.getServicios().size(); j++) {
                    DTOServicio s = inv.getServicios().get(j);
                    if (s != null && s.getNombre() != null && !s.getNombre().trim().isEmpty()) {
                        agregarEtiquetaSinDuplicar(items, s.getNombre().trim());
                    }
                }
            }

            if (inv.getEquipos() != null) {
                for (int j = 0; j < inv.getEquipos().size(); j++) {
                    DTOEquipo eq = inv.getEquipos().get(j);
                    if (eq != null && eq.getNombre() != null && !eq.getNombre().trim().isEmpty()) {
                        agregarEtiquetaSinDuplicar(items, eq.getNombre().trim() + SUFIJO_EQUIPO);
                    }
                }
            }

            if (inv.getPersonal() != null) {
                for (int j = 0; j < inv.getPersonal().size(); j++) {
                    DTOPersonal per = inv.getPersonal().get(j);
                    if (per != null && per.getEspecialidad() != null && !per.getEspecialidad().trim().isEmpty()) {
                        agregarEtiquetaSinDuplicar(items, per.getEspecialidad().trim() + SUFIJO_PERSONAL);
                    }
                }
            }
        }

        return items;
    }

    private String extraerNombreBase(String etiqueta) {
        if (etiqueta == null) {
            return "";
        }
        String limpia = etiqueta.trim();
        if (limpia.endsWith(SUFIJO_EQUIPO)) {
            return limpia.substring(0, limpia.length() - SUFIJO_EQUIPO.length()).trim();
        }
        if (limpia.endsWith(SUFIJO_PERSONAL)) {
            return limpia.substring(0, limpia.length() - SUFIJO_PERSONAL.length()).trim();
        }
        return limpia;
    }

    private List<String> normalizarSelecciones(List<String> elementos) {
        List<String> normalizados = new ArrayList<>();
        if (elementos == null) {
            return normalizados;
        }
        for (int i = 0; i < elementos.size(); i++) {
            String base = extraerNombreBase(elementos.get(i));
            if (!base.isEmpty() && !contieneTextoIgnorandoMayusculas(normalizados, base)) {
                normalizados.add(base);
            }
        }
        return normalizados;
    }

    private boolean coincideNombre(String candidato, List<String> aceptados) {
        if (candidato == null || candidato.trim().isEmpty()) {
            return false;
        }
        String limpio = candidato.trim();
        for (int i = 0; i < aceptados.size(); i++) {
            if (aceptados.get(i).equalsIgnoreCase(limpio)) {
                return true;
            }
        }
        return false;
    }

    private int obtenerCantidadEquipo(DTOEquipo equipo) {
        return equipo.getCantidadSeleccionada() > 0 ? equipo.getCantidadSeleccionada() : equipo.getDisponibilidad();
    }

    private List<DTOEquipo> obtenerEquiposDeInventarios(List<DTOInventario> inventarios) {
        List<DTOEquipo> equipos = new ArrayList<>();
        for (int i = 0; i < inventarios.size(); i++) {
            DTOInventario inv = inventarios.get(i);
            if (inv == null || inv.getEquipos() == null) {
                continue;
            }
            for (int j = 0; j < inv.getEquipos().size(); j++) {
                DTOEquipo eq = inv.getEquipos().get(j);
                if (eq != null && eq.getNombre() != null && !eq.getNombre().trim().isEmpty()) {
                    boolean existe = false;
                    for (int k = 0; k < equipos.size(); k++) {
                        if (equipos.get(k).getNombre().equalsIgnoreCase(eq.getNombre().trim())) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        equipos.add(eq);
                    }
                }
            }
        }
        return equipos;
    }

    private List<DTOPersonal> obtenerPersonalDeInventarios(List<DTOInventario> inventarios) {
        List<DTOPersonal> personalList = new ArrayList<>();
        for (int i = 0; i < inventarios.size(); i++) {
            DTOInventario inv = inventarios.get(i);
            if (inv == null || inv.getPersonal() == null) {
                continue;
            }
            for (int j = 0; j < inv.getPersonal().size(); j++) {
                DTOPersonal per = inv.getPersonal().get(j);
                if (per != null && per.getEspecialidad() != null && !per.getEspecialidad().trim().isEmpty()) {
                    boolean existe = false;
                    for (int k = 0; k < personalList.size(); k++) {
                        if (personalList.get(k).getEspecialidad().equalsIgnoreCase(per.getEspecialidad().trim())) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        personalList.add(per);
                    }
                }
            }
        }
        return personalList;
    }

    private List<DTOInventario> obtenerInventariosUltimaVersion(int idCotizacion) {
        List<DTOInventario> inventarios = new ArrayList<>();
        DTOVersionCotizacion ultimaVersion = obtenerUltimaVersionCotizacion(idCotizacion);
        if (ultimaVersion == null || almacen.getDetallesCotizacion() == null) {
            return inventarios;
        }

        for (int i = 0; i < almacen.getDetallesCotizacion().size(); i++) {
            DTODetalleCotizacion detalle = almacen.getDetallesCotizacion().get(i);
            if (detalle != null && detalle.getIdVersion() == ultimaVersion.getID()) {
                if (detalle.getInventario() != null) {
                    for (int j = 0; j < detalle.getInventario().size(); j++) {
                        DTOInventario inv = detalle.getInventario().get(j);
                        if (inv != null) {
                            inventarios.add(inv);
                        }
                    }
                }
            }
        }
        return inventarios;
    }

    private DTOVersionCotizacion obtenerUltimaVersionCotizacion(int idCotizacion) {
        DTOVersionCotizacion mejorVersion = null;
        if (almacen.getVersionesCotizacion() == null) {
            return null;
        }

        for (int i = 0; i < almacen.getVersionesCotizacion().size(); i++) {
            DTOVersionCotizacion version = almacen.getVersionesCotizacion().get(i);
            if (version == null || version.getIdCotizacion() != idCotizacion) {
                continue;
            }
            if (mejorVersion == null || esVersionMasReciente(version, mejorVersion)) {
                mejorVersion = version;
            }
        }
        return mejorVersion;
    }

    private boolean esVersionMasReciente(DTOVersionCotizacion candidata, DTOVersionCotizacion actual) {
        if (candidata.getNumero() != actual.getNumero()) {
            return candidata.getNumero() > actual.getNumero();
        }
        if (candidata.getFechaEmision() != null && actual.getFechaEmision() != null) {
            int cmpFecha = candidata.getFechaEmision().compareTo(actual.getFechaEmision());
            if (cmpFecha != 0) {
                return cmpFecha > 0;
            }
        }
        return candidata.getID() > actual.getID();
    }

    private void validarEventoYCotizacion(int idEvento, int idCotizacion) {
        if (idCotizacion <= 0) {
            throw new IllegalArgumentException("idCotizacion debe ser mayor que cero.");
        }
        if (idEvento <= 0) {
            throw new IllegalArgumentException("idEvento debe ser mayor que cero.");
        }

        boolean existeCotizacion = false;
        if (almacen.getCotizaciones() != null) {
            for (int i = 0; i < almacen.getCotizaciones().size(); i++) {
                DTOCotizacion c = almacen.getCotizaciones().get(i);
                if (c != null && c.getID() == idCotizacion) {
                    existeCotizacion = true;
                    break;
                }
            }
        }
        if (!existeCotizacion) {
            throw new IllegalArgumentException("No se encontró la cotización con ID " + idCotizacion);
        }

        boolean existeEvento = false;
        if (almacen.getEventos() != null) {
            for (int i = 0; i < almacen.getEventos().size(); i++) {
                DTOEvento e = almacen.getEventos().get(i);
                if (e != null && e.getIdEvento() == idEvento) {
                    existeEvento = true;
                    break;
                }
            }
        }
        if (!existeEvento) {
            throw new IllegalArgumentException("No se encontró el evento con ID " + idEvento);
        }
    }

    private void agregarServicioSinDuplicar(List<DTOServicio> destino, DTOServicio candidato) {
        if (candidato == null || candidato.getNombre() == null || candidato.getNombre().trim().isEmpty()) {
            return;
        }
        for (int i = 0; i < destino.size(); i++) {
            DTOServicio actual = destino.get(i);
            if (actual.getNombre() != null && actual.getNombre().trim().equalsIgnoreCase(candidato.getNombre().trim())) {
                return;
            }
        }
        destino.add(candidato);
    }

    private void agregarEtiquetaSinDuplicar(List<String> destino, String etiqueta) {
        if (!contieneTextoIgnorandoMayusculas(destino, etiqueta)) {
            destino.add(etiqueta);
        }
    }

    private boolean contieneTextoIgnorandoMayusculas(List<String> lista, String valor) {
        for (int i = 0; i < lista.size(); i++) {
            String item = lista.get(i);
            if (item != null && item.trim().equalsIgnoreCase(valor.trim())) {
                return true;
            }
        }
        return false;
    }
}
