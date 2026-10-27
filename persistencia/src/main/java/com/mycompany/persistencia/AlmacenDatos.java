/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import java.util.ArrayList;
import java.util.List;
import dto.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * @Author andres y marcelo
 */
/**
 * Base de datos provisional en memoria, cuando se cierra la ejecucion no se guarda
 */
public class AlmacenDatos {

    private static final float IVA = 0.16f;

    private final List<DTOCliente> clientes = new ArrayList<>();
    private final List<DTODetalleSolicitud> detallesSolicitud = new ArrayList<>();
    private final List<DTOEvento> eventos = new ArrayList<>();
    private final List<DTOLevantamiento> levantamientos = new ArrayList<>();
    private final List<DTOLocalEvento> localesEvento = new ArrayList<>();
    private final List<DTORecurso> recursos = new ArrayList<>();
    private final List<DTOReserva> reservas = new ArrayList<>();
    private final List<DTOCotizacion> cotizaciones = new ArrayList<>();
    private final List<DTODecisionCliente> decisionesCliente = new ArrayList<>();
    private final List<DTODetalleCotizacion> detallesCotizacion = new ArrayList<>();
    private final List<DTOEquipo> equipos = new ArrayList<>();
    private final List<DTOInventario> inventario = new ArrayList<>();
    private final List<DTOPersonal> personal = new ArrayList<>();
    private final List<DTOServicio> servicios = new ArrayList<>();
    private final List<DTOVersionCotizacion> versionesCotizacion = new ArrayList<>();

    public AlmacenDatos() {
        cargarDatos();
    }

    private void cargarDatos() {
        cargarClientes();
        cargarLocalesEvento();
        cargarEventos();
        cargarDetallesSolicitud();
        cargarRecursos();
        cargarEquiposPersonalYServicios();
        cargarInventarios();
        cargarLevantamientos();
        cargarReservas();
        cargarCotizaciones();
    }

    //Clientes
    private void cargarClientes() {
        clientes.add(new DTOCliente(1, "Mario Jesús S. Kennedy", "mario20@gmail.com", "6444238967", "6441155423"));
        clientes.add(new DTOCliente(2, "Ana Lucía Valenzuela Ríos", "ana.valenzuela@correo.com", "6441237788", "6442019933"));
        clientes.add(new DTOCliente(3, "Carlos Eduardo Romero Pérez", "carlos.romero@empresa.mx", "6443345566", ""));
        clientes.add(new DTOCliente(4, "Fernanda Guadalupe Soto Armenta", "fer.soto@outlook.com", "6447781122", "6441002244"));
        clientes.add(new DTOCliente(5, "Jorge Luis Camacho Beltrán", "jorge.camacho@gmail.com", "6449905511", ""));
        clientes.add(new DTOCliente(6, "Daniela Montserrat Ibarra Félix", "dani.ibarra@hotmail.com", "6442266880", "6443390012"));
    }

    //Locales de evento
    private void cargarLocalesEvento() {
        localesEvento.add(new DTOLocalEvento(1, "Salón", "30 x 20 m", "Escenario y pista", "Acceso por entrada principal", "No fumar", "Se necesita conexión eléctrica"));
        localesEvento.add(new DTOLocalEvento(2, "Jardín", "40 x 25 m", "Área de mesas y pista al aire libre", "Acceso por portón lateral; piso de pasto", "Música permitida hasta las 23:00", "Se requieren extensiones de 50 m o planta eléctrica"));
        localesEvento.add(new DTOLocalEvento(3, "Auditorio", "35 x 22 m", "Escenario fijo y butacas", "Rampa de carga y elevador de equipo", "No pegar elementos en las paredes", "Tomas trifásicas disponibles en el escenario"));
        localesEvento.add(new DTOLocalEvento(4, "Terraza", "25 x 15 m", "Zona de mesas y barra", "Acceso por escalera, sin elevador", "Máximo 220 personas", "Cubrir las instalaciones por posible lluvia"));
        localesEvento.add(new DTOLocalEvento(5, "Salón de hotel", "28 x 18 m", "Salón con pista y mesa principal", "Acceso de carga por la parte trasera", "Montaje solo a partir de las 8:00", "El aire acondicionado limita el uso de humo"));
    }

    //Eventos
    private void cargarEventos() {
        eventos.add(crearEvento(1, "Boda", LocalDate.of(2026, 12, 12), "Salón de eventos Los Arcos, Ciudad Obregón",
                LocalDate.of(2026, 10, 10), 150, "Audio para ceremonia y baile, iluminación de pista y pantalla para fotos",
                LocalTime.of(18, 0), LocalTime.of(1, 0), "Los novios solicitan barra de postres", "Cotizada"));
        eventos.add(crearEvento(2, "Graduación", LocalDate.of(2026, 12, 18), "Auditorio Municipal, Ciudad Obregón",
                LocalDate.of(2026, 9, 28), 300, "Audio profesional, pantallas laterales y decoración de escenario",
                LocalTime.of(10, 0), LocalTime.of(14, 0), "Ensayo general el día anterior a las 16:00", "Orden generada"));
        eventos.add(crearEvento(3, "Cumpleaños infantil", LocalDate.of(2026, 11, 28), "Jardín Las Palmas, Ciudad Obregón",
                LocalDate.of(2026, 10, 22), 60, "Brincolín, payaso y alimentos para niños",
                LocalTime.of(15, 0), LocalTime.of(19, 0), "El cliente confirmará el menú", "En levantamiento"));
        eventos.add(crearEvento(4, "Posada empresarial", LocalDate.of(2026, 12, 11), "Salón de hotel, Ciudad Obregón",
                LocalDate.of(2026, 10, 5), 120, "Cena, audio y decoración navideña",
                LocalTime.of(20, 0), LocalTime.of(0, 0), "Presupuesto máximo definido por la empresa", "Rechazada"));
        eventos.add(crearEvento(5, "XV años", LocalDate.of(2027, 2, 6), "Terraza Vista Hermosa, Ciudad Obregón",
                LocalDate.of(2026, 10, 26), 200, "DJ, iluminación robótica, pantalla LED y decoración",
                LocalTime.of(19, 0), LocalTime.of(1, 30), "Falta la primera visita al lugar", "Solicitud registrada"));
    }

    private DTOEvento crearEvento(int id, String tipo, LocalDate fecha, String ubicacion, LocalDate registro,
            int asistentes, String necesidades, LocalTime inicio, LocalTime fin, String observaciones, String estado) {
        DTOEvento e = new DTOEvento();
        e.setIdEvento(id);
        e.setTipoEvento(tipo);
        e.setFechaEvento(fecha);
        e.setUbicacion(ubicacion);
        e.setFechaRegistro(registro);
        e.setNumAsistentes(asistentes);
        e.setNecesidadesIniciales(necesidades);
        e.setHoraInicio(inicio);
        e.setHoraFin(fin);
        e.setObservaciones(observaciones);
        e.setEstadoAtencion(estado);
        return e;
    }

    //Detalles de solicitud
    private void cargarDetallesSolicitud() {
        // Evento 1: Boda
        detallesSolicitud.add(crearDetalleSolicitud(1, "Audio", "Audio para ceremonia y baile", "Micrófono inalámbrico para el oficiante", 1, "Para 150 personas"));
        detallesSolicitud.add(crearDetalleSolicitud(2, "Iluminación", "Iluminación de pista", "Luces LED en tonos cálidos", 1, ""));
        detallesSolicitud.add(crearDetalleSolicitud(3, "Pantallas", "Pantalla para proyección de fotos", "", 1, ""));
        detallesSolicitud.add(crearDetalleSolicitud(4, "Necesidades extra", "Barra de postres", "", 1, "La proporciona el cliente"));
        // Evento 2: Graduación
        detallesSolicitud.add(crearDetalleSolicitud(5, "Audio", "Audio profesional para auditorio", "Dos micrófonos de solapa", 1, "300 asistentes"));
        detallesSolicitud.add(crearDetalleSolicitud(6, "Pantallas", "Pantallas laterales", "Pantalla LED", 2, ""));
        detallesSolicitud.add(crearDetalleSolicitud(7, "Decoración", "Decoración de escenario", "Colores azul y dorado", 1, ""));
        // Evento 3: Cumpleaños infantil
        detallesSolicitud.add(crearDetalleSolicitud(8, "Entretenimiento", "Brincolín", "", 1, "Para 25 niños"));
        detallesSolicitud.add(crearDetalleSolicitud(9, "Entretenimiento", "Payaso", "Show de 2 horas", 1, ""));
        detallesSolicitud.add(crearDetalleSolicitud(10, "Comida", "Menú infantil", "Sin cacahuate", 60, "Alergias por confirmar"));
        // Evento 4: Posada empresarial
        detallesSolicitud.add(crearDetalleSolicitud(11, "Comida", "Cena de tres tiempos", "Opción vegetariana", 120, ""));
        detallesSolicitud.add(crearDetalleSolicitud(12, "Audio", "Audio para presentaciones", "", 1, ""));
        detallesSolicitud.add(crearDetalleSolicitud(13, "Decoración", "Ambientación navideña", "Árboles y esferas", 1, ""));
        // Evento 5: XV años
        detallesSolicitud.add(crearDetalleSolicitud(14, "Entretenimiento", "DJ", "Música variada", 1, "Seis horas"));
        detallesSolicitud.add(crearDetalleSolicitud(15, "Iluminación", "Luces robóticas", "", 6, ""));
        detallesSolicitud.add(crearDetalleSolicitud(16, "Pantallas", "Pantalla LED", "Para el vals", 1, ""));
        detallesSolicitud.add(crearDetalleSolicitud(17, "Decoración", "Centros de mesa", "Flores blancas", 20, ""));
    }

    private DTODetalleSolicitud crearDetalleSolicitud(int id, String categoria, String descripcion,
            String preferencias, int cantidad, String observaciones) {
        DTODetalleSolicitud d = new DTODetalleSolicitud();
        d.setDetalleSolicitud(id);
        d.setCategoriaSolicitada(categoria);
        d.setDescripcionSolicitud(descripcion);
        d.setPreferencias(preferencias);
        d.setCantidadEstimada(cantidad);
        d.setObservaciones(observaciones);
        return d;
    }

    //Recursos
    private void cargarRecursos() {
        // Audio
        recursos.add(new DTORecurso(1, "Bocina", "Bocina amplificada", 100, 1500.0f, "personas", "Audio", "pieza", "Activo"));
        agregarRecurso(2, "Consola de audio", "Consola digital de 16 canales", 200, 2500f, "personas", "Audio", "pieza", "Activo");
        agregarRecurso(3, "Micrófono inalámbrico", "Micrófono de mano con receptor", 1, 350f, "personas", "Audio", "pieza", "Activo");
        agregarRecurso(4, "Sistema de audio completo", "Bocinas, consola y micrófonos para eventos medianos", 200, 6500f, "personas", "Audio", "servicio", "Activo");
        // Comida
        agregarRecurso(5, "Sopa fría", "Entrada: sopa fría de temporada", 1, 85f, "porción", "Comida", "porción", "Activo");
        agregarRecurso(6, "Ensalada", "Entrada: ensalada de la casa", 1, 70f, "porción", "Comida", "porción", "Activo");
        agregarRecurso(7, "Pasta", "Plato fuerte: pasta con salsa a elegir", 1, 120f, "porción", "Comida", "porción", "Activo");
        agregarRecurso(8, "Pollo asado", "Plato fuerte: porción de pollo asado", 1, 150f, "porción", "Comida", "porción", "Activo");
        agregarRecurso(9, "Pastel", "Postre: pastel de chocolate", 1, 45f, "porción", "Comida", "porción", "Activo");
        agregarRecurso(10, "Gelatina", "Postre: gelatina individual", 1, 25f, "porción", "Comida", "porción", "Inactivo");
        agregarRecurso(11, "Agua de jamaica", "Bebida: agua fresca", 1, 20f, "porción", "Comida", "vaso", "Activo");
        agregarRecurso(12, "Refresco", "Bebida: refresco embotellado", 1, 25f, "porción", "Comida", "pieza", "Activo");
        // Entretenimiento
        agregarRecurso(13, "DJ", "Música: DJ con equipo básico por 5 horas", 300, 4500f, "personas", "Entretenimiento", "servicio", "Activo");
        agregarRecurso(14, "Banda en vivo", "Música: banda musical en vivo", 300, 6000f, "personas", "Entretenimiento", "servicio", "Activo");
        agregarRecurso(15, "Payaso", "Infantil: show con payaso de 2 horas", 40, 1800f, "niños", "Entretenimiento", "servicio", "Activo");
        agregarRecurso(16, "Brincolín", "Infantil: brincolín inflable", 20, 1200f, "niños", "Entretenimiento", "pieza", "Activo");
        agregarRecurso(17, "Mago", "Show: espectáculo de magia", 100, 2500f, "personas", "Entretenimiento", "servicio", "Activo");
        // Iluminación
        agregarRecurso(18, "Lámparas colgantes", "Interior: lámparas decorativas", 10, 300f, "m²", "Iluminación", "pieza", "Activo");
        agregarRecurso(19, "Luces LED", "Interior: tiras y focos LED", 20, 450f, "m²", "Iluminación", "pieza", "Activo");
        agregarRecurso(20, "Reflectores", "Escenario: reflectores de escenario", 40, 600f, "m²", "Iluminación", "pieza", "Activo");
        agregarRecurso(21, "Luces robóticas", "Escenario: cabezas móviles", 60, 900f, "m²", "Iluminación", "pieza", "Activo");
        agregarRecurso(22, "Máquina de humo", "Efectos: humo para efectos", 100, 700f, "m²", "Iluminación", "pieza", "Activo");
        // Decoración
        agregarRecurso(23, "Mesas redondas", "Mobiliario: mesa para 10 personas", 10, 120f, "personas", "Decoración", "pieza", "Activo");
        agregarRecurso(24, "Sillas Tiffany", "Mobiliario: silla decorativa", 1, 25f, "personas", "Decoración", "pieza", "Activo");
        agregarRecurso(25, "Centros de mesa", "Ambientación: arreglo floral", 1, 180f, "mesa", "Decoración", "pieza", "Activo");
        agregarRecurso(26, "Globos", "Ambientación: arco o racimos de globos", 1, 900f, "arco", "Decoración", "pieza", "Activo");
        agregarRecurso(27, "Mantelería", "Textiles: mantel y camino de mesa", 1, 60f, "mesa", "Decoración", "juego", "Activo");
        // Pantallas
        agregarRecurso(28, "Pantalla LED", "LED: pantalla modular de 3 x 2 m", 150, 5500f, "personas", "Pantallas", "pieza", "Activo");
        agregarRecurso(29, "Proyector", "Proyección: proyector de alta luminosidad", 100, 1200f, "personas", "Pantallas", "pieza", "Activo");
        agregarRecurso(30, "Pantalla de proyección", "Proyección: pantalla con tripié", 100, 500f, "personas", "Pantallas", "pieza", "Activo");
        agregarRecurso(31, "Televisión", "Televisión: pantalla de 55 pulgadas", 30, 800f, "personas", "Pantallas", "pieza", "Activo");
    }

    private void agregarRecurso(int id, String nombre, String descripcion, int capacidad, float precioReferencia,
String unidadCapacidad, String tipoRecurso, String unidadMedida, String estadoEnCatalogo) {
        recursos.add(new DTORecurso(id, nombre, descripcion, capacidad, precioReferencia,
                unidadCapacidad, tipoRecurso, unidadMedida, estadoEnCatalogo));
    }

    //Equipos, personal y servicios
    private void cargarEquiposPersonalYServicios() {
        equipos.add(new DTOEquipo(1, "Silla", 100, 50));
        equipos.add(new DTOEquipo(2, "Mesa redonda", 30, 0));
        equipos.add(new DTOEquipo(3, "Bocina amplificada", 12, 0));
        equipos.add(new DTOEquipo(4, "Consola de audio", 3, 0));
        equipos.add(new DTOEquipo(5, "Micrófono inalámbrico", 10, 0));
        equipos.add(new DTOEquipo(6, "Reflector LED", 24, 0));
        equipos.add(new DTOEquipo(7, "Luz robótica", 8, 0));
        equipos.add(new DTOEquipo(8, "Pantalla LED modular", 6, 0));
        equipos.add(new DTOEquipo(9, "Proyector", 4, 0));
        equipos.add(new DTOEquipo(10, "Chafing dish", 20, 0));
        equipos.add(new DTOEquipo(11, "Cableado y soportes", 15, 0));

        // id, especialidad, cantidad, horas de montaje, horas de operación, horas de desmontaje
        personal.add(new DTOPersonal(1, "Técnico de audio", 3, 2, 5, 1));
        personal.add(new DTOPersonal(2, "Técnico de iluminación", 2, 2, 5, 1));
        personal.add(new DTOPersonal(3, "Operador de pantallas", 2, 1, 5, 1));
        personal.add(new DTOPersonal(4, "Montador", 6, 3, 0, 2));
        personal.add(new DTOPersonal(5, "Cocinero", 3, 0, 6, 0));
        personal.add(new DTOPersonal(6, "Mesero", 10, 0, 6, 0));
        personal.add(new DTOPersonal(7, "Coordinador de evento", 2, 1, 6, 1));

        // id, nombre, descripción, seleccionado, esAdicional, observaciones
        servicios.add(new DTOServicio(1, "Audio", "Servicio de audio para evento", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(2, "Iluminación", "Iluminación de pista, escenario y ambiente", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(3, "Pantallas", "Pantallas y proyección de contenido", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(4, "Decoración", "Ambientación y centros de mesa", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(5, "Mobiliario", "Mesas, sillas y mantelería", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(6, "Alimentos y bebidas", "Menú, servicio de meseros y cocina", true, false, "Sin observaciones"));
        servicios.add(new DTOServicio(7, "Entretenimiento", "Música y espectáculos", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(8, "Transporte y logística", "Traslado de equipo al lugar del evento", false, true, "Se cobra según la distancia"));
        servicios.add(new DTOServicio(9, "Personal de apoyo", "Personal adicional de montaje y operación", false, true, "Se cobra por hora"));
    }

    //Inventarios
    private void cargarInventarios() {
        // 0: Audio
        inventario.add(new DTOInventario(listaDe(equipo(3), equipo(4), equipo(5), equipo(11)), listaDe(especialista(1)), listaDe(servicio(1)), 1));
        // 1: Iluminación
        inventario.add(new DTOInventario(listaDe(equipo(6), equipo(7), equipo(11)), listaDe(especialista(2)), listaDe(servicio(2)), 1));
        // 2: Pantallas
        inventario.add(new DTOInventario(listaDe(equipo(8), equipo(9)), listaDe(especialista(3)), listaDe(servicio(3)), 1));
        // 3: Decoración y mobiliario
        inventario.add(new DTOInventario(listaDe(equipo(1), equipo(2)), listaDe(especialista(4)), listaDe(servicio(4), servicio(5)), 1));
        // 4: Alimentos y bebidas
        inventario.add(new DTOInventario(listaDe(equipo(10)), listaDe(especialista(5), especialista(6)), listaDe(servicio(6)), 1));
    }

    private DTOEquipo equipo(int id) {
        for (DTOEquipo e : equipos) {
            if (e.getIdEquipo() == id) {
                return e;
            }
        }
        throw new IllegalArgumentException("No existe el equipo " + id);
    }

    private DTOPersonal especialista(int id) {
        for (DTOPersonal p : personal) {
            if (p.getIdEspecialidad() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe la especialidad " + id);
    }

    private DTOServicio servicio(int id) {
        for (DTOServicio s : servicios) {
            if (s.getIdServicio() == id) {
                return s;
            }
        }
        throw new IllegalArgumentException("No existe el servicio " + id);
    }

    @SafeVarargs
    private static <T> List<T> listaDe(T... elementos) {
        return new ArrayList<>(Arrays.asList(elementos));
    }

    //Levantamientos
    private void cargarLevantamientos() {
        levantamientos.add(crearLevantamiento(1, LocalDate.of(2026, 10, 15), LocalTime.of(10, 0), LocalTime.of(1, 30), "Completo", "Salón con 12 m de fondo para el escenario; se requieren extensiones"));
        levantamientos.add(crearLevantamiento(2, LocalDate.of(2026, 10, 2), LocalTime.of(7, 0), LocalTime.of(15, 30), "Completo", "Acceso de carga por la rampa lateral"));
        levantamientos.add(crearLevantamiento(3, LocalDate.of(2026, 10, 26), LocalTime.of(12, 0), LocalTime.of(19, 30), "Pendiente", "Falta medir el área para el brincolín"));
        levantamientos.add(crearLevantamiento(4, LocalDate.of(2026, 10, 8), LocalTime.of(16, 0), LocalTime.of(0, 30), "Completo", "El hotel permite montar desde las 16:00"));
    }

    private DTOLevantamiento crearLevantamiento(int id, LocalDate fecha, LocalTime montaje,
            LocalTime desmontaje, String estado, String observaciones) {
        DTOLevantamiento l = new DTOLevantamiento();
        l.setIdLevatamiento(id);
        l.setFechaLevantamiento(fecha);
        l.setHorarioMontaje(montaje);
        l.setHorarioDesmontaje(desmontaje);
        l.setEstadoLevantamiento(estado);
        l.setObservaciones(observaciones);
        return l;
    }

    //Reservas
    private void cargarReservas() {
        reservas.add(crearReserva(1, LocalDate.of(2026, 12, 12), LocalTime.of(14, 0), LocalTime.of(1, 0), "Tentativa"));      // Evento 1
        reservas.add(crearReserva(2, LocalDate.of(2026, 12, 18), LocalTime.of(8, 0), LocalTime.of(15, 0), "Confirmada"));    // Evento 2
        reservas.add(crearReserva(3, LocalDate.of(2026, 11, 28), LocalTime.of(12, 0), LocalTime.of(19, 30), "Tentativa"));   // Evento 3
        reservas.add(crearReserva(4, LocalDate.of(2027, 2, 6), LocalTime.of(15, 0), LocalTime.of(2, 0), "Tentativa"));       // Evento 5
    }

    private DTOReserva crearReserva(int id, LocalDate fecha, LocalTime inicio, LocalTime fin, String estado) {
        DTOReserva r = new DTOReserva();
        r.setIdReserva(id);
        r.setFecha(fecha);
        r.setHoraInicio(inicio);
        r.setHoraFin(fin);
        r.setEstado(estado);
        return r;
    }

    //Cotizaciones 
    private void cargarCotizaciones() {
        // Cotización 1 (Evento 1, Boda): la primera versión no convenció y se emitió una segunda con descuento
        cotizaciones.add(new DTOCotizacion(1, LocalDate.of(2026, 10, 18), "Pendiente"));
        versionesCotizacion.add(crearVersion(1, 1, LocalDate.of(2026, 10, 18), "Reemplazada", "Versión inicial", 0f, 10000f));
        versionesCotizacion.add(crearVersion(2, 2, LocalDate.of(2026, 10, 23), "Vigente", "Se aplicó descuento del 5%", 5f, 10000f));
        decisionesCliente.add(new DTODecisionCliente(3, LocalDate.of(2026, 10, 21), "Modificaciones solicitadas", "Solicita aplicar un descuento"));
        detallesCotizacion.add(crearDetalleCotizacion(1, LocalTime.of(10, 0), 4500f, inventario.get(0)));   // Audio
        detallesCotizacion.add(crearDetalleCotizacion(2, LocalTime.of(11, 0), 3500f, inventario.get(1)));   // Iluminación
        detallesCotizacion.add(crearDetalleCotizacion(3, LocalTime.of(12, 0), 2000f, inventario.get(2)));   // Pantallas

        // Cotización 2 (Evento 2, Graduación): aceptada
        cotizaciones.add(new DTOCotizacion(2, LocalDate.of(2026, 10, 8), "Aceptada"));
        versionesCotizacion.add(crearVersion(3, 1, LocalDate.of(2026, 10, 8), "Aceptada", "Versión inicial", 0f, 25000f));
        decisionesCliente.add(new DTODecisionCliente(1, LocalDate.of(2026, 10, 14), "Aceptada", "Cotización aprobada"));
        detallesCotizacion.add(crearDetalleCotizacion(4, LocalTime.of(7, 0), 7000f, inventario.get(0)));    // Audio
        detallesCotizacion.add(crearDetalleCotizacion(5, LocalTime.of(8, 0), 6000f, inventario.get(1)));    // Iluminación
        detallesCotizacion.add(crearDetalleCotizacion(6, LocalTime.of(8, 30), 5000f, inventario.get(2)));   // Pantallas
        detallesCotizacion.add(crearDetalleCotizacion(7, LocalTime.of(7, 30), 7000f, inventario.get(3)));   // Decoración

        // Cotización 3 (Evento 4, Posada empresarial): rechazada
        cotizaciones.add(new DTOCotizacion(3, LocalDate.of(2026, 10, 12), "Rechazada"));
        versionesCotizacion.add(crearVersion(4, 1, LocalDate.of(2026, 10, 12), "Rechazada", "Versión inicial", 10f, 18000f));
        decisionesCliente.add(new DTODecisionCliente(2, LocalDate.of(2026, 10, 22), "Rechazada", "El presupuesto excede el límite de la empresa"));
        detallesCotizacion.add(crearDetalleCotizacion(8, LocalTime.of(16, 0), 12000f, inventario.get(4)));  // Alimentos
        detallesCotizacion.add(crearDetalleCotizacion(9, LocalTime.of(16, 30), 4000f, inventario.get(0)));  // Audio
        detallesCotizacion.add(crearDetalleCotizacion(10, LocalTime.of(17, 0), 2000f, inventario.get(3))); // Decoración
    }

    /** total = (subtotal - descuento%) + IVA, redondeado a centavos. */
    private DTOVersionCotizacion crearVersion(int id, int numero, LocalDate fecha, String estado,
            String observaciones, float descuento, float subtotal) {
        float total = Math.round(subtotal * (1 - descuento / 100f) * (1 + IVA) * 100f) / 100f;
        return new DTOVersionCotizacion(id, numero, fecha, estado, observaciones, descuento, subtotal, total);
    }

    private DTODetalleCotizacion crearDetalleCotizacion(int id, LocalTime horarioFases, float importe, DTOInventario inv) {
        DTODetalleCotizacion d = new DTODetalleCotizacion();
        d.setID(id);
        d.setHorarioFases(horarioFases);
        d.setImporte(importe);
        d.setInventario(listaDe(inv));
        return d;
    }

    //Getters
    public List<DTOCliente> getClientes() {
        return clientes;
    }

    public List<DTODetalleSolicitud> getDetallesSolicitud() {
        return detallesSolicitud;
    }

    public List<DTOEvento> getEventos() {
        return eventos;
    }

    public List<DTOLevantamiento> getLevantamientos() {
        return levantamientos;
    }

    public List<DTOLocalEvento> getLocalesEvento() {
        return localesEvento;
    }

    public List<DTORecurso> getRecursos() {
        return recursos;
    }

    public List<DTOReserva> getReservas() {
        return reservas;
    }

    public List<DTOCotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public List<DTODecisionCliente> getDecisionesCliente() {
        return decisionesCliente;
    }

    public List<DTODetalleCotizacion> getDetallesCotizacion() {
        return detallesCotizacion;
    }

    public List<DTOEquipo> getEquipos() {
        return equipos;
    }

    public List<DTOInventario> getInventario() {
        return inventario;
    }

    public List<DTOPersonal> getPersonal() {
        return personal;
    }

    public List<DTOServicio> getServicios() {
        return servicios;
    }

    public List<DTOVersionCotizacion> getVersionesCotizacion() {
        return versionesCotizacion;
    }
}