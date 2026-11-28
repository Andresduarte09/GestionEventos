/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.gestor;

import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOInventario;
import dto.DTOVersionCotizacion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import negocioCotizacion.BO.CotizacionBO;
import negocioCotizacion.interfaces.ICotizacionBO;
import java.util.ArrayList;
import dto.DTOPropuesta;
import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import negocioPropuesta.control.GestorPropuesta;
import dto.DTORecurso;
import dto.DTOConceptoCotizacion;

/**
 *
 * @author lui
 */
public class GestorCotizacion {

    private final ICotizacionBO cotizacionBO;
    private DTOVersionCotizacion versionActual;
    private GestorPropuesta gestorPropuesta;

    public GestorCotizacion() {
        this.cotizacionBO = new CotizacionBO();
    }

    public GestorCotizacion(GestorPropuesta gestorPropuesta) {
        this();
        if (gestorPropuesta == null) {
            throw new IllegalArgumentException("El gestor de propuesta no puede ser nulo.");
        }
        this.gestorPropuesta = gestorPropuesta;
    }

    public DTOPropuesta consultarPropuestaParaCotizar(int idPropuesta) {
        if (idPropuesta <= 0) {
            throw new IllegalArgumentException("El ID de la propuesta debe ser mayor que cero.");
        }

        if (gestorPropuesta == null) {
            throw new IllegalStateException("El gestor de propuesta no está conectado.");
        }

        DTOPropuesta propuesta = gestorPropuesta.consultarPropuesta(idPropuesta);

        if (propuesta == null) {
            throw new IllegalArgumentException("No existe una propuesta con ese ID.");
        }

        if (propuesta.getIdEvento() <= 0) {
            throw new IllegalStateException("La propuesta no tiene un evento válido.");
        }

        DTOInventario inventario = gestorPropuesta.consultarRecursosPropuesta(idPropuesta);

        if (!validarInventario(inventario)) {
            throw new IllegalStateException("La propuesta no tiene recursos válidos para cotizar.");
        }

        propuesta.setInventario(inventario);
        return propuesta;
    }

    private List<String> obtenerFirmaDetalle(DTOInventario inventario, List<DTOConceptoCotizacion> conceptos) {
        List<String> firma = new ArrayList<>();
        if (inventario == null || conceptos == null) {
            return firma;
        }

        if (inventario.getServicios() != null) {
            for (DTOServicio servicio : inventario.getServicios()) {
                if (servicio != null && servicio.isSeleccionado()) {
                    firma.add("S|" + servicio.getIdServicio() + "|" + servicio.getNombre() + "|" + servicio.getDescripcion()
                            + "|" + servicio.getObservaciones());
                }
            }
        }

        if (inventario.getEquipos() != null) {
            for (DTOEquipo equipo : inventario.getEquipos()) {
                if (equipo != null && equipo.getCantidadSeleccionada() > 0) {
                    firma.add("E|" + equipo.getIdEquipo() + "|" + equipo.getNombre() + "|" + equipo.getCantidadSeleccionada());
                }
            }
        }

        if (inventario.getPersonal() != null) {
            for (DTOPersonal personal : inventario.getPersonal()) {
                if (personal != null && personal.getCantidad() > 0) {
                    firma.add("P|" + personal.getIdEspecialidad() + "|" + personal.getEspecialidad() + "|" + personal.getCantidad()
                            + "|" + personal.getHorasMontaje() + "|" + personal.getHorasOperacion() + "|" + personal.getHorasDesmontaje());
                }
            }
        }

        for (DTOConceptoCotizacion concepto : conceptos) {
            if (concepto != null) {
                firma.add("C|" + concepto.getTipo() + "|" + concepto.getNombre() + "|" + concepto.getCantidad()
                        + "|" + Float.floatToIntBits(concepto.getPrecioUnitario()) + "|" + Float.floatToIntBits(concepto.getImporte()));
            }
        }

        firma.sort(String::compareTo);
        return firma;
    }

    private boolean coincideConUltimaVersion(DTOVersionCotizacion version, LocalTime horario, DTOInventario inventario,
                                             List<DTOConceptoCotizacion> conceptos) {
        List<DTODetalleCotizacion> detalles = consultarDetallesVersion(version.getID());
        if (detalles.size() != 1) {
            return false;
        }

        DTODetalleCotizacion detalle = detalles.get(0);
        if (!horario.equals(detalle.getHorarioFases()) || detalle.getInventario() == null
                || detalle.getInventario().size() != 1 || detalle.getConceptos().isEmpty()) {
            return false;
        }

        List<String> anterior = obtenerFirmaDetalle(detalle.getInventario().get(0), detalle.getConceptos());
        List<String> actual = obtenerFirmaDetalle(inventario, conceptos);
        return anterior.equals(actual);
    }

    private DTOVersionCotizacion crearVersionConDetalle(int idCotizacion, LocalTime horario, DTOInventario inventario, boolean cotizacionNueva) {
        DTOVersionCotizacion anterior = consultarUltimaVersion(idCotizacion);
        DTOVersionCotizacion nuevaVersion = null;

        try {
            nuevaVersion = crearNuevaVersion(idCotizacion);
            registrarDetalleInventario(nuevaVersion.getID(), horario, inventario);

            if (anterior != null) {
                anterior.setEstado("Reemplazada");
            }

            return nuevaVersion;
        } catch (RuntimeException ex) {
            try {
                cotizacionBO.revertirCreacionVersion(idCotizacion, nuevaVersion == null ? 0 : nuevaVersion.getID(), cotizacionNueva);

                if (nuevaVersion != null && versionActual == nuevaVersion) {
                    versionActual = null;
                }
            } catch (RuntimeException errorReversion) {
                ex.addSuppressed(errorReversion);
            }

            throw ex;
        }
    }

    public DTOCotizacion calcularCotizacion(int idPropuesta) {
        DTOPropuesta propuesta = consultarPropuestaParaCotizar(idPropuesta);
        DTOInventario inventario = propuesta.getInventario();

        if (inventario.getServicios() == null || inventario.getEquipos() == null || inventario.getPersonal() == null) {
            throw new IllegalStateException("El inventario de la propuesta está incompleto.");
        }

        List<String> sinTarifa = consultarRecursosSinTarifa(inventario);
        if (!sinTarifa.isEmpty()) {
            throw new IllegalStateException("No se puede cotizar. Revisa las tarifas de:\n- " + String.join("\n- ", sinTarifa));
        }

        String horarioMontaje = propuesta.getHorarioMontaje();
        if (horarioMontaje == null || horarioMontaje.trim().isEmpty()) {
            throw new IllegalStateException("La propuesta no tiene horario de montaje.");
        }

        LocalTime horario;
        try {
            horario = LocalTime.parse(horarioMontaje.trim());
        } catch (java.time.format.DateTimeParseException ex) {
            throw new IllegalStateException("El horario de montaje no tiene un formato válido.", ex);
        }

        List<DTOConceptoCotizacion> conceptos = generarDesgloseInventario(inventario);
        double importe = calcularImporteInventario(inventario);

        if (conceptos.isEmpty() || importe <= 0 || importe > Float.MAX_VALUE) {
            throw new IllegalStateException("La propuesta no tiene un importe válido.");
        }

        DTOCotizacion cotizacion = consultarCotizacionPorPropuesta(idPropuesta);
        boolean cotizacionNueva = cotizacion == null;

        if (cotizacionNueva) {
            cotizacion = obtenerOCrearCotizacion(propuesta);
        } else {
            DTOVersionCotizacion ultimaVersion = consultarUltimaVersion(cotizacion.getID());

            if (ultimaVersion != null) {
                if (!hayDetalles(ultimaVersion.getID())) {
                    throw new IllegalStateException("La última versión no tiene detalles registrados.");
                }
                if (coincideConUltimaVersion(ultimaVersion, horario, inventario, conceptos)) {
                    return cotizacion;
                }
            }
        }

        crearVersionConDetalle(cotizacion.getID(), horario, inventario, cotizacionNueva);
        return cotizacion;
    }

    private void validarVersionEditable(DTOVersionCotizacion version) {
        if (version == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }

        DTOCotizacion cotizacion = consultarCotizacion(version.getIdCotizacion());
        if (cotizacion == null) {
            throw new IllegalStateException("La cotización de esta versión no existe.");
        }

        String estado = version.getEstado();
        if (!"Pendiente".equalsIgnoreCase(estado) && !"Vigente".equalsIgnoreCase(estado)) {
            throw new IllegalStateException("No se puede modificar una versión con estado: " + estado);
        }

        if (!"Pendiente".equalsIgnoreCase(cotizacion.getEstadoGeneral())) {
            throw new IllegalStateException("La cotización ya tiene una decisión registrada y no puede modificarse.");
        }

        DTOVersionCotizacion ultima = consultarUltimaVersion(cotizacion.getID());
        if (ultima == null || ultima.getID() != version.getID()) {
            throw new IllegalStateException("Solo se puede modificar la versión más reciente.");
        }
    }

    public DTOCotizacion guardarCotizacion(DTOCotizacion cotizacion) {
        return cotizacionBO.guardar(cotizacion);
    }

    public DTOCotizacion consultarCotizacion(int idCotizacion) {
        return cotizacionBO.consultar(idCotizacion);
    }

    public DTOCotizacion consultarCotizacionPorPropuesta(int idPropuesta) {
        return cotizacionBO.consultarPorPropuesta(idPropuesta);
    }

    public void actualizarCotizacion(DTOCotizacion cotizacion) {
        cotizacionBO.actualizar(cotizacion);
    }

    public DTOVersionCotizacion guardarVersion(DTOVersionCotizacion version) {
        return cotizacionBO.guardarVersion(version);
    }

    public DTOVersionCotizacion consultarVersion(int idVersion) {
        return cotizacionBO.consultarVersion(idVersion);
    }

    public DTODetalleCotizacion guardarDetalle(DTODetalleCotizacion detalle) {
        return cotizacionBO.guardarDetalle(detalle);
    }

    public boolean hayDetalles(int idVersion) {
        return !cotizacionBO.consultarDetallesVersion(idVersion).isEmpty();
    }

    public List<DTODetalleCotizacion> consultarDetallesVersion(int idVersion) {
        if (consultarVersion(idVersion) == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }

        return cotizacionBO.consultarDetallesVersion(idVersion);
    }

    public double calcularSubtotal(List<DTODetalleCotizacion> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La cotización debe contener detalles.");
        }

        double subtotal = 0;

        for (DTODetalleCotizacion detalle : detalles) {
            if (detalle == null || !Float.isFinite(detalle.getImporte()) || detalle.getImporte() < 0) {
                throw new IllegalArgumentException("Existe un detalle con importe inválido.");
            }

            subtotal += detalle.getImporte();
        }

        return subtotal;
    }

    public double calcularSubtotalVersion(int idVersion) {
        if (cotizacionBO.consultarVersion(idVersion) == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }

        List<DTODetalleCotizacion> detalles = cotizacionBO.consultarDetallesVersion(idVersion);
        return calcularSubtotal(detalles);
    }

    public DTOVersionCotizacion calcularImportesVersion(int idVersion) {
        DTOVersionCotizacion version = cotizacionBO.consultarVersion(idVersion);

        if (version == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }

        if (!hayDetalles(idVersion)) {
            throw new IllegalStateException("No hay detalles para calcular esta versión.");
        }

        double subtotal = calcularSubtotalVersion(idVersion);
        double descuento = calcularDescuento(subtotal, version.getDescuento());
        double base = subtotal - descuento;
        double iva = calcularIVA(base);
        double total = calcularTotal(subtotal, descuento, iva);

        version.setSubtotal((float) subtotal);
        version.setImporteDescuento((float) descuento);
        version.setImporteIVA((float) iva);
        version.setTotal((float) total);

        return version;
    }

    public int calcularHorasPersonal(DTOPersonal personal) {
        if (personal == null || personal.getIdEspecialidad() <= 0 || personal.getCantidad() < 0
                || personal.getHorasMontaje() < 0 || personal.getHorasOperacion() < 0
                || personal.getHorasDesmontaje() < 0) {
            throw new IllegalArgumentException("Los datos del personal no son válidos.");
        }

        if (personal.getCantidad() == 0) {
            return 0;
        }

        long horas = (long) personal.getHorasMontaje() + personal.getHorasOperacion()
                + personal.getHorasDesmontaje();

        if (horas == 0) {
            throw new IllegalStateException("El personal seleccionado no tiene horas asignadas.");
        }

        long total = horas * personal.getCantidad();

        if (total > Integer.MAX_VALUE) {
            throw new IllegalStateException("La cantidad de horas excede el límite permitido.");
        }

        return (int) total;
    }

    public int calcularTotalHorasPersonal(DTOInventario inventario) {
        if (inventario == null || inventario.getPersonal() == null) {
            throw new IllegalArgumentException("El inventario de personal no es válido.");
        }

        long total = 0;

        for (DTOPersonal personal : inventario.getPersonal()) {
            total += calcularHorasPersonal(personal);

            if (total > Integer.MAX_VALUE) {
                throw new IllegalStateException("El total de horas excede el límite permitido.");
            }
        }

        return (int) total;
    }

    public double calcularImportePersonal(DTOInventario inventario) {
        if (inventario == null || inventario.getPersonal() == null) {
            throw new IllegalArgumentException("El inventario de personal no es válido.");
        }

        double total = 0;

        for (DTOPersonal personal : inventario.getPersonal()) {
            int horas = calcularHorasPersonal(personal);

            if (horas == 0) {
                continue;
            }

            DTORecurso recursoEncontrado = buscarRecursoPorNombre(personal.getEspecialidad());

            if (recursoEncontrado == null) {
                throw new IllegalStateException("No hay tarifa para: " + personal.getEspecialidad());
            }

            if (!"hora".equalsIgnoreCase(recursoEncontrado.getUnidadMedida())) {
                throw new IllegalStateException("La tarifa no es por hora: " + personal.getEspecialidad());
            }

            float tarifa = consultarTarifaRecurso(personal.getEspecialidad());
            total += horas * (double) tarifa;

            if (!Double.isFinite(total)) {
                throw new IllegalStateException("El importe de personal excede el rango permitido.");
            }
        }

        return total;
    }

    public double calcularImporteInventario(DTOInventario inventario) {
        if (!validarInventario(inventario)) {
            throw new IllegalArgumentException("El inventario no contiene recursos válidos.");
        }

        if (inventario.getServicios() == null || inventario.getEquipos() == null
                || inventario.getPersonal() == null) {
            throw new IllegalArgumentException("Las listas del inventario no pueden ser nulas.");
        }

        validarPaqueteAudio(inventario);

        double importeServicios = calcularImporteServicios(inventario);
        double importeEquipos = calcularImporteEquipos(inventario);
        double importePersonal = calcularImportePersonal(inventario);

        double total = importeServicios + importeEquipos + importePersonal;

        if (!Double.isFinite(total) || total < 0 || total > Float.MAX_VALUE) {
            throw new IllegalStateException("El importe total del inventario no es válido.");
        }

        return total;
    }

    public List<DTOConceptoCotizacion> generarDesgloseInventario(DTOInventario inventario) {
        if (!validarInventario(inventario)) {
            throw new IllegalArgumentException("El inventario no contiene recursos válidos.");
        }
        if (inventario.getServicios() == null || inventario.getEquipos() == null
                || inventario.getPersonal() == null) {
            throw new IllegalArgumentException("Las listas del inventario no pueden ser nulas.");
        }

        validarPaqueteAudio(inventario);
        List<DTOConceptoCotizacion> conceptos = new ArrayList<>();

        for (DTOServicio servicio : inventario.getServicios()) {
            if (servicio == null || servicio.getIdServicio() <= 0) {
                throw new IllegalArgumentException("Existe un servicio con datos inválidos.");
            }
            if (!servicio.isSeleccionado()) {
                continue;
            }

            float tarifa = consultarTarifaServicio(servicio.getNombre(), inventario);
            if (tarifa > 0) {
                conceptos.add(new DTOConceptoCotizacion("Servicio", servicio.getNombre(), 1, tarifa));
            }
        }

        for (DTOEquipo equipo : inventario.getEquipos()) {
            if (equipo == null) {
                throw new IllegalArgumentException("Existe un equipo nulo.");
            }
            if (equipo.getCantidadSeleccionada() == 0) {
                continue;
            }

            float tarifa = consultarTarifaRecurso(equipo.getNombre());
            conceptos.add(new DTOConceptoCotizacion("Equipo", equipo.getNombre(),
                    equipo.getCantidadSeleccionada(), tarifa));
        }

        for (DTOPersonal personal : inventario.getPersonal()) {
            if (personal == null) {
                throw new IllegalArgumentException("Existe personal nulo.");
            }
            int horas = calcularHorasPersonal(personal);
            if (horas == 0) {
                continue;
            }

            float tarifa = consultarTarifaRecurso(personal.getEspecialidad());
            conceptos.add(new DTOConceptoCotizacion("Personal", personal.getEspecialidad(), horas, tarifa));
        }

        double subtotal = 0;
        for (DTOConceptoCotizacion concepto : conceptos) {
            subtotal += concepto.getImporte();
        }
        double importeInventario = calcularImporteInventario(inventario);
        if (Math.abs(subtotal - importeInventario) > 0.01) {
            throw new IllegalStateException("El desglose no coincide con el importe del inventario.");
        }

        return conceptos;
    }

    public DTODetalleCotizacion registrarDetalleInventario(int idVersion, LocalTime horario, DTOInventario inventario) {
        if (inventario == null) {
            throw new IllegalArgumentException("El inventario del detalle es obligatorio.");
        }

        double importe = calcularImporteInventario(inventario);
        if (!Double.isFinite(importe) || importe <= 0 || importe > Float.MAX_VALUE) {
            throw new IllegalStateException("El importe del inventario no es válido.");
        }

        List<DTOInventario> inventarios = new ArrayList<>();
        inventarios.add(inventario);
        return crearDetalleCotizacion(idVersion, horario, inventarios, (float) importe);
    }

    public DTOVersionCotizacion generarVersionModificada(int idCotizacion, LocalTime horario, DTOInventario inventario) {
        DTOCotizacion cotizacion = consultarCotizacion(idCotizacion);
        if (cotizacion == null) {
            throw new IllegalArgumentException("La cotización indicada no existe.");
        }

        DTOVersionCotizacion anterior = consultarUltimaVersion(idCotizacion);
        if (anterior == null || !hayDetalles(anterior.getID())) {
            throw new IllegalStateException("La cotización no tiene una versión completa.");
        }
        if (horario == null) {
            throw new IllegalArgumentException("El horario es obligatorio.");
        }

        List<DTOConceptoCotizacion> conceptos = generarDesgloseInventario(inventario);
        double subtotal = calcularImporteInventario(inventario);

        if (conceptos.isEmpty() || subtotal <= 0 || subtotal > Float.MAX_VALUE) {
            throw new IllegalStateException("El nuevo inventario no tiene un importe válido.");
        }

        return crearVersionConDetalle(idCotizacion, horario, inventario, false);
    }

    private boolean tieneEquiposAudioSeleccionados(DTOInventario inventario) {
        if (inventario == null || inventario.getEquipos() == null) {
            throw new IllegalArgumentException("El inventario de equipos no es válido.");
        }

        for (DTOEquipo equipo : inventario.getEquipos()) {
            if (equipo == null || equipo.getNombre() == null || equipo.getCantidadSeleccionada() <= 0) {
                continue;
            }

            String nombre = equipo.getNombre().trim();
            if ("Bocina amplificada".equalsIgnoreCase(nombre) || "Bocina".equalsIgnoreCase(nombre)
                    || "Consola de audio".equalsIgnoreCase(nombre) || "Micrófono inalámbrico".equalsIgnoreCase(nombre)) {
                return true;
            }
        }

        return false;
    }

    private void validarPaqueteAudio(DTOInventario inventario) {
        boolean paqueteAudio = false;

        for (DTOServicio servicio : inventario.getServicios()) {
            if (servicio != null && servicio.isSeleccionado()
                    && "Sistema de audio completo".equalsIgnoreCase(servicio.getNombre())) {
                paqueteAudio = true;
                break;
            }
        }

        if (paqueteAudio && tieneEquiposAudioSeleccionados(inventario)) {
            throw new IllegalStateException("El paquete de audio completo no puede cobrarse junto con sus equipos incluidos.");
        }
    }

    public DTOVersionCotizacion actualizarDescuentoVersion(int idVersion, float porcentaje) {
        DTOVersionCotizacion version = cotizacionBO.consultarVersion(idVersion);
        validarVersionEditable(version);

        if (!Float.isFinite(porcentaje) || porcentaje < 0 || porcentaje > 10) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 10%.");
        }
        if (!hayDetalles(idVersion)) {
            throw new IllegalStateException("No hay detalles para calcular esta versión.");
        }

        version.setDescuento(porcentaje);
        return calcularImportesVersion(idVersion);
    }

    public double calcularDescuento(double subtotal, double porcentaje) {
        if (!Double.isFinite(subtotal) || subtotal < 0 || !Double.isFinite(porcentaje)
                || porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Subtotal o porcentaje inválido.");
        }

        return subtotal * porcentaje / 100;
    }

    public double calcularIVA(double base) {
        if (!Double.isFinite(base) || base < 0) {
            throw new IllegalArgumentException("La base del IVA no es válida.");
        }

        return base * 0.16;
    }

    public double calcularTotal(double subtotal, double descuento, double iva) {
        if (!Double.isFinite(subtotal) || !Double.isFinite(descuento) || !Double.isFinite(iva)
                || subtotal < 0 || descuento < 0 || iva < 0 || descuento > subtotal) {
            throw new IllegalArgumentException("Los importes no son válidos.");
        }

        return subtotal - descuento + iva;
    }

    public DTOVersionCotizacion crearNuevaVersion(int idCotizacion) {
        DTOCotizacion cotizacion = cotizacionBO.consultar(idCotizacion);
        if (cotizacion == null) {
            throw new IllegalArgumentException("No existe la cotización indicada.");
        }
        if (!"Pendiente".equalsIgnoreCase(cotizacion.getEstadoGeneral())) {
            throw new IllegalStateException("No se puede crear una versión de una cotización cerrada.");
        }

        DTOVersionCotizacion anterior = consultarUltimaVersion(idCotizacion);
        float descuentoAnterior = 0f;

        if (anterior != null) {
            validarVersionEditable(anterior);
            descuentoAnterior = anterior.getDescuento();
        }

        int ultimoNumero = anterior == null ? 0 : anterior.getNumero();
        int nuevoID = cotizacionBO.obtenerSiguienteIdVersion();

        DTOVersionCotizacion nuevaVersion = new DTOVersionCotizacion(
                nuevoID, ultimoNumero + 1, LocalDate.now(), "Pendiente", "Nueva versión", descuentoAnterior, 0f, 0f);

        nuevaVersion.setIdCotizacion(idCotizacion);
        versionActual = cotizacionBO.guardarVersion(nuevaVersion);
        return versionActual;
    }

    public DTODetalleCotizacion crearDetalleCotizacion(int idVersion, LocalTime horario,
                                                       List<DTOInventario> inventarios, float importe) {

        DTOVersionCotizacion version = cotizacionBO.consultarVersion(idVersion);
        if (version == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }
        if (horario == null || inventarios == null || inventarios.isEmpty()) {
            throw new IllegalArgumentException("Faltan datos para crear el detalle.");
        }
        if (!Float.isFinite(importe) || importe <= 0) {
            throw new IllegalArgumentException("El importe del detalle no es válido.");
        }
        if (hayDetalles(idVersion)) {
            throw new IllegalStateException("La versión ya contiene detalles registrados.");
        }
        if (!Float.isFinite(version.getDescuento()) || version.getDescuento() < 0 || version.getDescuento() > 10) {
            throw new IllegalStateException("El descuento de la versión no es válido.");
        }

        List<DTOConceptoCotizacion> conceptos = new ArrayList<>();
        double totalCalculado = 0;

        for (DTOInventario inventario : inventarios) {
            if (inventario == null) {
                throw new IllegalArgumentException("Existe un inventario nulo.");
            }

            conceptos.addAll(generarDesgloseInventario(inventario));
            totalCalculado += calcularImporteInventario(inventario);
        }

        if (conceptos.isEmpty() || !Double.isFinite(totalCalculado) || totalCalculado > Float.MAX_VALUE
                || Math.abs(totalCalculado - importe) > 0.01) {
            throw new IllegalStateException("El desglose no coincide con el importe del detalle.");
        }

        int nuevoID = cotizacionBO.obtenerSiguienteIdDetalle();

        DTODetalleCotizacion detalle = new DTODetalleCotizacion();
        detalle.setID(nuevoID);
        detalle.setIdVersion(idVersion);
        detalle.setHorarioFases(horario);
        detalle.setInventario(new ArrayList<>(inventarios));
        detalle.setImporte(importe);
        detalle.setConceptos(conceptos);

        DTODetalleCotizacion guardado = cotizacionBO.guardarDetalle(detalle);
        calcularImportesVersion(idVersion);
        return guardado;
    }

    public DTOVersionCotizacion consultarUltimaVersion(int idCotizacion) {
        List<DTOVersionCotizacion> versiones = cotizacionBO.consultarVersionesCotizacion(idCotizacion);
        return versiones.isEmpty() ? null : versiones.get(versiones.size() - 1);
    }

    public DTOCotizacion obtenerOCrearCotizacion(DTOPropuesta propuesta) {
        if (propuesta == null || propuesta.getIdPropuesta() <= 0) {
            throw new IllegalArgumentException("La propuesta debe tener un ID valido.");
        }

        DTOCotizacion existente = consultarCotizacionPorPropuesta(propuesta.getIdPropuesta());

        if (existente != null) {
            return existente;
        }

        int nuevoID = cotizacionBO.obtenerSiguienteIdCotizacion();

        DTOCotizacion nuevaCotizacion = new DTOCotizacion();
        nuevaCotizacion.setID(nuevoID);
        nuevaCotizacion.setIdPropuesta(propuesta.getIdPropuesta());
        nuevaCotizacion.setFecha(LocalDate.now());
        nuevaCotizacion.setEstadoGeneral("Pendiente");

        return cotizacionBO.guardar(nuevaCotizacion);
    }

    public boolean validarInventario(DTOInventario inventario) {
        if (inventario == null) {
            return false;
        }

        boolean tieneRecursos = false;

        if (inventario.getServicios() != null) {
            for (DTOServicio servicio : inventario.getServicios()) {
                if (servicio == null || servicio.getIdServicio() <= 0) {
                    return false;
                }
                if (servicio.isSeleccionado()) {
                    tieneRecursos = true;
                }
            }
        }

        if (inventario.getEquipos() != null) {
            for (DTOEquipo equipo : inventario.getEquipos()) {
                if (equipo == null || equipo.getIdEquipo() <= 0 || equipo.getCantidadSeleccionada() < 0) {
                    return false;
                }
                if (equipo.getCantidadSeleccionada() > equipo.getDisponibilidad()) {
                    return false;
                }
                if (equipo.getCantidadSeleccionada() > 0) {
                    tieneRecursos = true;
                }
            }
        }

        if (inventario.getPersonal() != null) {
            for (DTOPersonal personal : inventario.getPersonal()) {
                if (personal == null || personal.getIdEspecialidad() <= 0 || personal.getCantidad() < 0) {
                    return false;
                }
                if (personal.getHorasMontaje() < 0 || personal.getHorasOperacion() < 0
                        || personal.getHorasDesmontaje() < 0) {
                    return false;
                }
                if (personal.getCantidad() > 0) {
                    tieneRecursos = true;
                }
            }
        }

        return tieneRecursos;
    }

    private DTORecurso buscarRecursoPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del recurso no es válido.");
        }

        String nombreCatalogo = nombre.trim();

        if (nombreCatalogo.equalsIgnoreCase("Bocina amplificada")) {
            nombreCatalogo = "Bocina";
        } else if (nombreCatalogo.equalsIgnoreCase("Mesa redonda")) {
            nombreCatalogo = "Mesas redondas";
        }

        return cotizacionBO.consultarRecursoPorNombre(nombreCatalogo);
    }

    public float consultarTarifaRecurso(String nombre) {
        DTORecurso encontrado = buscarRecursoPorNombre(nombre);

        if (encontrado == null) {
            throw new IllegalArgumentException("No existe una tarifa registrada para: " + nombre);
        }

        if (!"Activo".equalsIgnoreCase(encontrado.getEstadoEnCatalogo())) {
            throw new IllegalStateException("El recurso no está activo: " + nombre);
        }

        if (!Float.isFinite(encontrado.getPrecioReferencia()) || encontrado.getPrecioReferencia() < 0) {
            throw new IllegalStateException("El recurso tiene una tarifa inválida: " + nombre);
        }

        return encontrado.getPrecioReferencia();
    }

    public List<String> consultarRecursosSinTarifa(DTOInventario inventario) {
        if (!validarInventario(inventario)) {
            throw new IllegalArgumentException("El inventario no es válido.");
        }

        List<String> sinTarifa = new ArrayList<>();

        if (inventario.getServicios() != null) {
            for (DTOServicio servicio : inventario.getServicios()) {
                if (servicio != null && servicio.isSeleccionado()) {
                    try {
                        consultarTarifaServicio(servicio.getNombre(), inventario);
                    } catch (IllegalArgumentException | IllegalStateException ex) {
                        sinTarifa.add("Servicio: " + servicio.getNombre());
                    }
                }
            }
        }

        if (inventario.getEquipos() != null) {
            for (DTOEquipo equipo : inventario.getEquipos()) {
                if (equipo != null && equipo.getCantidadSeleccionada() > 0
                        && !existeTarifaRecurso(equipo.getNombre(), "pieza")) {
                    sinTarifa.add("Equipo: " + equipo.getNombre());
                }
            }
        }

        if (inventario.getPersonal() != null) {
            for (DTOPersonal personal : inventario.getPersonal()) {
                if (personal != null && personal.getCantidad() > 0
                        && !existeTarifaRecurso(personal.getEspecialidad(), "hora")) {
                    sinTarifa.add("Personal: " + personal.getEspecialidad());
                }
            }
        }

        return sinTarifa;
    }

    public double calcularImporteEquipos(DTOInventario inventario) {
        if (inventario == null || inventario.getEquipos() == null) {
            throw new IllegalArgumentException("El inventario de equipos no es válido.");
        }

        double total = 0;

        for (DTOEquipo equipo : inventario.getEquipos()) {
            if (equipo == null || equipo.getIdEquipo() <= 0 || equipo.getCantidadSeleccionada() < 0
                    || equipo.getCantidadSeleccionada() > equipo.getDisponibilidad()) {
                throw new IllegalArgumentException("Existe un equipo con datos inválidos.");
            }

            if (equipo.getCantidadSeleccionada() == 0) {
                continue;
            }

            DTORecurso recursoEncontrado = buscarRecursoPorNombre(equipo.getNombre());

            if (recursoEncontrado == null) {
                throw new IllegalStateException("No hay tarifa para el equipo: " + equipo.getNombre());
            }

            if (!"pieza".equalsIgnoreCase(recursoEncontrado.getUnidadMedida())) {
                throw new IllegalStateException("El equipo no tiene una tarifa por pieza: " + equipo.getNombre());
            }

            float tarifa = consultarTarifaRecurso(equipo.getNombre());
            total += (double) equipo.getCantidadSeleccionada() * tarifa;

            if (!Double.isFinite(total)) {
                throw new IllegalStateException("El importe calculado excede el rango permitido.");
            }
        }

        return total;
    }

    public double calcularImporteServicios(DTOInventario inventario) {
        if (inventario == null || inventario.getServicios() == null || inventario.getEquipos() == null) {
            throw new IllegalArgumentException("El inventario de servicios no es válido.");
        }

        double total = 0;
        for (DTOServicio servicio : inventario.getServicios()) {
            if (servicio == null || servicio.getIdServicio() <= 0) {
                throw new IllegalArgumentException("Existe un servicio con datos inválidos.");
            }
            if (!servicio.isSeleccionado()) {
                continue;
            }

            total += consultarTarifaServicio(servicio.getNombre(), inventario);
            if (!Double.isFinite(total) || total > Float.MAX_VALUE) {
                throw new IllegalStateException("El importe de servicios excede el rango permitido.");
            }
        }

        return total;
    }

    private float consultarTarifaServicio(String nombre, DTOInventario inventario) {
        if (nombre == null || nombre.isBlank() || inventario == null || inventario.getEquipos() == null) {
            throw new IllegalArgumentException("No se puede consultar la tarifa del servicio.");
        }

        String nombreTarifa = nombre.trim();

        if ("Audio".equalsIgnoreCase(nombreTarifa)) {
            if (tieneEquiposAudioSeleccionados(inventario)) {
                return 0f;
            }
            nombreTarifa = "Sistema de audio completo";
        }

        DTORecurso recurso = buscarRecursoPorNombre(nombreTarifa);

        if (recurso == null) {
            throw new IllegalStateException("No hay tarifa para el servicio: " + nombre);
        }

        if (!"servicio".equalsIgnoreCase(recurso.getUnidadMedida())) {
            throw new IllegalStateException("La tarifa no corresponde a un servicio: " + nombre);
        }

        return consultarTarifaRecurso(nombreTarifa);
    }

    private boolean existeTarifaRecurso(String nombre, String unidadEsperada) {
        try {
            DTORecurso recurso = buscarRecursoPorNombre(nombre);

            if (recurso == null || !unidadEsperada.equalsIgnoreCase(recurso.getUnidadMedida())) {
                return false;
            }

            consultarTarifaRecurso(nombre);
            return true;
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return false;
        }
    }

    public List<DTOVersionCotizacion> consultarVersionesCotizacion(int idCotizacion) {
        return cotizacionBO.consultarVersionesCotizacion(idCotizacion);
    }

}
