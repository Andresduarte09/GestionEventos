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
 * Base de datos provisional en memoria, cuando se cierra la ejecucion no se
 * guarda
 */
public class AlmacenDatos {
    //AHORA SE ESTA USANDO SINGLETON

    private static AlmacenDatos instancia = null;
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
    private final List<DTOOrdenServicio> ordenes = new ArrayList<>();

    //singleton
    private AlmacenDatos() {
        cargarDatos();
    }

    //acceso global
    public static AlmacenDatos getInstance() {
        if (instancia == null) {
            instancia = new AlmacenDatos();
        }
        return instancia;
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
        localesEvento.add(new DTOLocalEvento(1, "Salón Los Ángeles", "Salón", "30 x 20 m", "Escenario y pista",
                "Acceso por entrada principal", "No fumar",
                "Se necesita conexión eléctrica", 200, 8));
        localesEvento.add(new DTOLocalEvento(2, "Jardín de Eventos La Colibrí", "Jardín", "40 x 25 m", "Área de mesas y pista al aire libre",
                "Acceso por portón lateral; piso de pasto", "Música permitida hasta las 23:00",
                "Se requieren extensiones de 50 m o planta eléctrica", 350, 6));
        localesEvento.add(new DTOLocalEvento(3, "Auditorio Cintermex", "Auditorio", "35 x 22 m", "Escenario fijo y butacas",
                "Rampa de carga y elevador de equipo", "No pegar elementos en las paredes",
                "Tomas trifásicas disponibles en el escenario", 400, 7));
        localesEvento.add(new DTOLocalEvento(4, "Salón Cristal", "Salón", "25 x 15 m", "Zona de mesas y barra",
                "Acceso por escalera, sin elevador", "Máximo 220 personas",
                "Cubrir las instalaciones por posible lluvia", 220, 8));
        localesEvento.add(new DTOLocalEvento(5, "Jardín Las Fuentes", "Jardín", "28 x 18 m", "Área verde con fuente central",
                "Acceso de carga por la parte trasera", "Montaje solo a partir de las 8:00",
                "El riego automático se programa por la noche", 180, 6));
        localesEvento.add(new DTOLocalEvento(6, "Auditorio Metropolitano", "Auditorio", "50 x 30 m", "Butacas numeradas y pantalla gigante",
                "Entrada principal con rampa para discapacitados", "Prohibido consumir alimentos en sala",
                "Requiere operador de planta de luz residente", 800, 9));
        localesEvento.add(new DTOLocalEvento(7, "Salón Versalles", "Salón", "32 x 20 m", "Pista de baile de madera y candiles",
                "Acceso directo desde el estacionamiento", "Uso obligatorio de portagafetes",
                "Verificar voltaje para iluminación robótica", 300, 8));
        localesEvento.add(new DTOLocalEvento(8, "Jardín El Roble", "Jardín", "45 x 30 m", "Explanada de adoquín bajo árboles",
                "Portón amplio para ingreso de camiones de carga", "Terminar eventos musicales a las 00:00",
                "Instalación de iluminación exterior requerida", 500, 7));
        localesEvento.add(new DTOLocalEvento(9, "Auditorio Magna", "Auditorio", "25 x 20 m", "Escenario modular y silletería móvil",
                "Acceso por pasillo central del edificio", "No se permite equipo pesado sin protección",
                "Salidas de emergencia libres de cables", 250, 7));
        localesEvento.add(new DTOLocalEvento(10, "Salón Imperial", "Salón", "38 x 22 m", "Dos niveles con barandales de cristal",
                "Montacargas disponible para proveedores", "No encender pirotecnia fría en interiores",
                "Circuitos independientes para sonido y luces", 450, 9));
        localesEvento.add(new DTOLocalEvento(11, "Jardín Rincón Escondido", "Jardín", "20 x 15 m", "Pérgola central y jardín floral",
                "Acceso peatonal por calle privada", "Aforo estricto para eventos privados",
                "Conexión limitada, se sugiere generador", 120, 5));
        localesEvento.add(new DTOLocalEvento(12, "Auditorio Cultural Bicentenario", "Auditorio", "40 x 25 m", "Fosa de orquesta y tramoya",
                "Muelle de carga con andén ajustable", "Cuidado con el piso de duela del escenario",
                "Consola de audio integrada en cabina", 600, 9));
        localesEvento.add(new DTOLocalEvento(13, "Salón Diamante", "Salón", "28 x 16 m", "Pista iluminada y muro de espejos",
                "Acceso a nivel de calle por puerta doble", "Mantener salidas de aire libre de obstáculos",
                "Aire acondicionado central programado", 200, 8));
        localesEvento.add(new DTOLocalEvento(14, "Jardín Hacienda San José", "Jardín", "60 x 40 m", "Explanada empedrada y kiosco",
                "Entrada principal por camino empedrado", "Apagar equipos de sonido al aire libre a la 1:00",
                "Tomas de corriente distribuidas perimetralmente", 700, 9));
        localesEvento.add(new DTOLocalEvento(15, "Auditorio Tecno", "Auditorio", "22 x 18 m", "Gradas retráctiles y proyector láser",
                "Acceso por pasillo norte del campus", "No introducir bebidas azucaradas",
                "Red de internet de alta velocidad dedicada", 150, 6));
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
//        d.setDetalleSolicitud(id);
        d.setCategoriaSolicitada(categoria);
        d.setDescripcionSolicitud(descripcion);
        d.setPreferencias(preferencias);
        d.setCantidadEstimada(cantidad);
        d.setObservaciones(observaciones);
        return d;
    }

    //Recursos (solo Audio y Comida)
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
        equipos.add(new DTOEquipo(10, "Chafing dish", 20, 0));
        equipos.add(new DTOEquipo(11, "Cableado y soportes", 15, 0));

        // id, especialidad, cantidad, horas de montaje, horas de operación, horas de desmontaje
        personal.add(new DTOPersonal(1, "Técnico de audio", 0, 2, 5, 1));
        personal.add(new DTOPersonal(4, "Montador", 0, 3, 0, 2));
        personal.add(new DTOPersonal(5, "Cocinero", 0, 0, 6, 0));
        personal.add(new DTOPersonal(6, "Mesero", 0, 0, 6, 0));
        personal.add(new DTOPersonal(7, "Coordinador de evento", 0, 1, 6, 1));

        // Solo se mantienen servicios que no son de las categorías eliminadas
        servicios.add(new DTOServicio(1, "Audio", "Servicio de audio para evento", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(5, "Mobiliario", "Mesas, sillas y mantelería", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(6, "Alimentos y bebidas", "Menú, servicio de meseros y cocina", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(8, "Transporte y logística", "Traslado de equipo al lugar del evento", false, false, "Se cobra según la distancia"));
        servicios.add(new DTOServicio(9, "Personal de apoyo", "Personal adicional de montaje y operación", false, false, "Se cobra por hora"));
        servicios.add(new DTOServicio(10, "Fotografía y video", "Cobertura completa del evento en foto y video", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(11, "Seguridad", "Personal de control de acceso y vigilancia", false, false, "Sin observaciones"));
        servicios.add(new DTOServicio(12, "Coordinación", "Planeación y logística general durante el evento", false, false, "Incluye un coordinador principal"));
        servicios.add(new DTOServicio(13, "Pirotecnia", "Efectos especiales y fuegos artificiales seguros", false, false, "Requiere permisos especiales del lugar"));
        servicios.add(new DTOServicio(14, "Souvenirs y recuerdos", "Artículos personalizados para los invitados", false, false, "Sin observaciones"));
    }

    //Inventarios (solo los que quedan válidos)
    private void cargarInventarios() {
        // 0: Audio
        inventario.add(new DTOInventario(listaDe(equipo(3), equipo(4), equipo(5), equipo(11)),
                listaDe(especialista(1)),
                listaDe(servicio(1)), 1));
        // 1: Alimentos y bebidas
        inventario.add(new DTOInventario(listaDe(equipo(10)),
                listaDe(especialista(5), especialista(6)),
                listaDe(servicio(6)), 1));
        // 2: Mobiliario
        inventario.add(new DTOInventario(listaDe(equipo(1), equipo(2)),
                listaDe(especialista(4)),
                listaDe(servicio(5)), 1));
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
        l.setIdLevantamiento(id);
        l.setFechaLevantamiento(fecha);
        l.setHorarioMontaje(montaje);
        l.setHorarioDesmontaje(desmontaje);
        l.setEstadoLevantamiento(estado);
        l.setObservaciones(observaciones);
        return l;
    }

    //Reservas
    private void cargarReservas() {
        reservas.add(crearReserva(1, LocalDate.of(2026, 12, 12), LocalTime.of(14, 0), LocalTime.of(1, 0), "Tentativa"));
        reservas.add(crearReserva(2, LocalDate.of(2026, 12, 18), LocalTime.of(8, 0), LocalTime.of(15, 0), "Confirmada"));
        reservas.add(crearReserva(3, LocalDate.of(2026, 11, 28), LocalTime.of(12, 0), LocalTime.of(19, 30), "Tentativa"));
        reservas.add(crearReserva(4, LocalDate.of(2027, 2, 6), LocalTime.of(15, 0), LocalTime.of(2, 0), "Tentativa"));
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
        // Cotización 1 (Evento 1, Boda)
        cotizaciones.add(new DTOCotizacion(1, LocalDate.of(2026, 10, 18), "Pendiente"));
        versionesCotizacion.add(crearVersion(1, 1, LocalDate.of(2026, 10, 18), "Reemplazada", "Versión inicial", 0f, 10000f, 1));
        versionesCotizacion.add(crearVersion(2, 2, LocalDate.of(2026, 10, 23), "Vigente", "Se aplicó descuento del 5%", 5f, 10000f, 1));

        decisionesCliente.add(new DTODecisionCliente(3, LocalDate.of(2026, 10, 21), "Modificaciones solicitadas", "Solicita aplicar un descuento"));
        detallesCotizacion.add(crearDetalleCotizacion(1, LocalTime.of(10, 0), 4500f, inventario.get(0), 2));   // Audio

        // Cotización 2 (Evento 2, Graduación)
        cotizaciones.add(new DTOCotizacion(2, LocalDate.of(2026, 10, 8), "Aceptada"));

        versionesCotizacion.add(crearVersion(3, 1, LocalDate.of(2026, 10, 8), "Aceptada", "Versión inicial", 0f, 25000f, 2));
        decisionesCliente.add(new DTODecisionCliente(1, LocalDate.of(2026, 10, 14), "Aceptada", "Cotización aprobada"));
        detallesCotizacion.add(crearDetalleCotizacion(4, LocalTime.of(7, 0), 7000f, inventario.get(0), 3));    // Audio

        // Cotización 3 (Evento 4, Posada empresarial)
        cotizaciones.add(new DTOCotizacion(3, LocalDate.of(2026, 10, 12), "Rechazada"));
        versionesCotizacion.add(crearVersion(4, 1, LocalDate.of(2026, 10, 12), "Rechazada", "Versión inicial", 10f, 18000f, 3));
        decisionesCliente.add(new DTODecisionCliente(2, LocalDate.of(2026, 10, 22), "Rechazada", "El presupuesto excede el límite de la empresa"));
        detallesCotizacion.add(crearDetalleCotizacion(8, LocalTime.of(16, 0), 12000f, inventario.get(1), 4));  // Alimentos
        detallesCotizacion.add(crearDetalleCotizacion(9, LocalTime.of(16, 30), 4000f, inventario.get(0), 4));  // Audio
    }

    private DTOVersionCotizacion crearVersion(
            int id,
            int numero,
            LocalDate fecha,
            String estado,
            String observaciones,
            float descuento,
            float subtotal,
            int idCotizacion) {

        DTOVersionCotizacion version
                = new DTOVersionCotizacion(
                        id, numero, fecha, estado,
                        observaciones, descuento, subtotal, 0f
                );

        version.setIdCotizacion(idCotizacion);

        return version;
    }

    private DTODetalleCotizacion crearDetalleCotizacion(int id, LocalTime horarioFases, float importe,
            DTOInventario inv, int idVersion) {

        DTODetalleCotizacion detalle = new DTODetalleCotizacion();
        detalle.setID(id);
        detalle.setHorarioFases(horarioFases);
        detalle.setImporte(importe);
        detalle.setInventario(listaDe(inv));
        detalle.setIdVersion(idVersion);

        return detalle;
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

    public void registrarOrden(DTOOrdenServicio orden) {
        this.ordenes.add(orden);
    }

    public List<DTOOrdenServicio> getOrdenes() {
        return ordenes;
    }

    public void registrarCliente(DTOCliente c) {
        this.clientes.add(c);
    }

    public void registrarEvento(DTOEvento e) {
        this.eventos.add(e);
    }

    public void registrarCotizacion(DTOCotizacion c) {
        this.cotizaciones.add(c);
    }

    public void registrarVersionCotizacion(DTOVersionCotizacion v) {
        this.versionesCotizacion.add(v);
    }

    public void registrarDecisionCliente(DTODecisionCliente d) {
        this.decisionesCliente.add(d);
    }
}
