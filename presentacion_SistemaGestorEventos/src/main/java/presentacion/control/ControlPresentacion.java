package presentacion.control;

import presentacion.vistas.*;
import dto.*;
import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import presentacion.validaciones.Validaciones;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import negocioEvento.fachada.FachadaEvento;
import negocioEvento.interfaces.ISubsistemaEvento;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;
import negocioCotizacion.fachada.FachadaCotizacion;
import negocioCotizacion.interfaces.ISubsistemaCotizacion;
import negocioOrden.fachada.FachadaOrden;
import negocioOrden.interfaces.ISubsistemaOrden;

/**
 * Control de navegación y comunicación entre las pantallas y los subsistemas.
 *
 * @author lui
 */
public class ControlPresentacion {
    private ISubsistemaEvento subsistemaEvento;
    private ISubsistemaPropuesta subsistemaPropuesta;
    private ISubsistemaCotizacion subsistemaCotizacion;
    private ISubsistemaOrden subsistemaOrden;
    private AlmacenDatos almacen;
    private ProveedorExterno proveedorExterno;
    private JFrameRecursosEvento recursosFrmEvento;
    private Map<Integer, Integer> seleccionTemporalRecursos = new LinkedHashMap<>();
    private JFrameConfigurarPropuesta configurarPropuesta;
    private JFrameResumenCotizacion resumenCotizacion;
    private DTOCotizacion cotizacion;
    private DTOPropuesta propuestaActual;
    private DTOVersionCotizacion versionCotizacion;
    private DTODetalleCotizacion detalleCotizacion;
    private DTOLevantamiento levantamiento;
    private List<DTOPersonal> personalSeleccionado = new ArrayList<>();
    private DTODecisionCliente decisionCliente;
    private List<DTOServicio> serviciosAceptados = new ArrayList<>();
    private LocalTime horarioMontajePropuesta;
    private LocalTime horarioDesmontajePropuesta;
    private String comentariosPropuesta = "";
    private JFrameRegistrarDatosEvento registrarEvento;
    private JFrameAnalisisEvaluacion analisisEvaluacion;
    private DTOCliente cliente;
    private DTOEvento evento;
    public ControlPresentacion(ISubsistemaPropuesta subsistemaPropuesta, AlmacenDatos almacen, ProveedorExterno proveedorExterno) {
        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
        this.subsistemaEvento = new FachadaEvento();
        this.subsistemaPropuesta = java.util.Objects.requireNonNull(subsistemaPropuesta);
        this.subsistemaCotizacion = new FachadaCotizacion(this.subsistemaPropuesta);
        this.subsistemaOrden = new FachadaOrden(almacen);
    }

    public void iniciarSesion() {
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }

    public List<DTOLocalEvento> consultarLocalesDisponibles() {
        if (evento == null) {
            return new ArrayList<>();
        }

        return subsistemaEvento.obtenerLocalesDisponibles(evento, evento.getUbicacion());
    }

    // CONSULTAS DE F3
    // Los catalogos se consultan en los almacenes existentes.
    // La seleccion y el guardado siguen delegandose al subsistemaPropuesta.
    public List<DTOServicio> consultarServiciosDisponibles() {
        return new ArrayList<>(almacen.getServicios());
    }

    public List<DTOEquipo> obtenerEquiposDisponibles() {
        return new ArrayList<>(almacen.getEquipos());
    }

    public List<DTOInventario> obtenerInventarioDisponible() {
        return new ArrayList<>(almacen.getInventario());
    }

    public List<DTOPersonal> obtenerPersonalDisponible() {
        return new ArrayList<>(almacen.getPersonal());
    }

    public List<DTOEquipo> consultarEquiposExternos() {
        return new ArrayList<>(proveedorExterno.getEquipos());
    }

    private List<DTOEquipo> consultarTodosLosEquipos() {
        List<DTOEquipo> equipos = obtenerEquiposDisponibles();
        equipos.addAll(consultarEquiposExternos());
        return equipos;
    }

    public DTOCliente getCliente() {
        return cliente;
    }

    public DTOEvento getEvento() {
        return evento;
    }

    // INICIO DE SESION Y REGISTRO DE CLIENTE
    public boolean procesarTelefonoIngresado(String telefono) {
        if (!Validaciones.telefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Ingresa un teléfono válido de 10 dígitos numéricos.", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        DTOCliente existente = subsistemaEvento.ingresarTelefono(telefono);
        if (existente != null) {
            iniciarRegistrarEvento(existente);
        } else {
            DTOCliente nuevo = new DTOCliente();
            nuevo.setTelefono(telefono);
            mostrarFormularioCliente(nuevo);
        }

        return true;
    }

    private void mostrarFormularioCliente(DTOCliente cliente) {
        this.cliente = cliente;
        JFrameFormularioRegistro formulario = new JFrameFormularioRegistro(this);
        formulario.setVisible(true);
    }

    public boolean procesarRegistroCliente(String nombre, String correo, String telefono, String telefonoAlt) {
        StringBuilder errores = new StringBuilder();
        if (!Validaciones.textoValido(nombre)) {
            errores.append("El nombre no puede estar vacío.\n");
        }

        if (!Validaciones.telefonoValido(telefono)) {
            errores.append("El teléfono debe tener 10 dígitos numéricos.\n");
        }

        if (!Validaciones.correoValido(correo)) {
            errores.append("El correo electrónico no tiene un formato válido.\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(null, errores.toString(), "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (telefonoAlt != null && !telefonoAlt.trim().isEmpty()) {
            if (!Validaciones.telefonoValido(telefonoAlt)) {
                errores.append("El teléfono alterno debe tener 10 dígitos numéricos.\n");
            }
        }

        this.cliente.setNombre(nombre);
        this.cliente.setCorreo(correo);
        this.cliente.setTelefono(telefono);
        this.cliente.setTelefonoAlt(telefonoAlt);
        this.cliente = subsistemaEvento.registrarCliente(this.cliente);
        iniciarRegistrarEvento(this.cliente);
        return true;
    }

    //METODOS PARA REGISTRAR EVENTOS
    //Inicia el registro de datos del evento
    public void iniciarRegistrarEvento(DTOCliente cliente) {
        this.cliente = cliente;
        if (registrarEvento != null && registrarEvento.isDisplayable()) {
            registrarEvento.cargarDatosEvento(this.evento);
            registrarEvento.setVisible(true);
        } else {
            registrarEvento = new JFrameRegistrarDatosEvento(this);
            registrarEvento.setVisible(true);
        }
    }

    private void registrarDatosEvento(DTOEvento evento) {
        this.evento = evento;
        iniciarRecursosEvento();
    }

    public boolean procesarDatosEvento(String tipoEvento, String ubicacion, java.time.LocalDate fecha, java.time.LocalTime hora, String asistentesTexto) {
        StringBuilder errores = new StringBuilder();
        if (!Validaciones.textoValido(tipoEvento)) {
            errores.append("Selecciona un tipo de evento.\n");
        }

        if (!Validaciones.textoValido(ubicacion)) {
            errores.append("Selecciona una ubicación.\n");
        }

        if (!Validaciones.fechaValida(fecha)) {
            errores.append("Selecciona una fecha.\n");
        }

        if (!Validaciones.horaValida(hora)) {
            errores.append("Selecciona un horario.\n");
        }

        if (!Validaciones.esEnteroPositivo(asistentesTexto)) {
            errores.append("El número de asistentes debe ser un entero positivo.\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(null, errores.toString(), "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        DTOEvento evento = new DTOEvento();
        evento.setTipoEvento(tipoEvento);
        evento.setUbicacion(ubicacion);
        evento.setFechaEvento(fecha);
        evento.setHoraInicio(hora);
        evento.setNumAsistentes(Integer.parseInt(asistentesTexto.trim()));
        evento = subsistemaEvento.registrarDatosEvento(evento);
        registrarDatosEvento(evento);
        return true;
    }

    public void cancelarRegistrarEvento() {
        this.cliente = null;
        this.evento = null;
        iniciarSesion();
    }

    // RECURSOS DEL EVENTO
    public void iniciarRecursosEvento() {
        if (recursosFrmEvento != null && recursosFrmEvento.isDisplayable()) {
            recursosFrmEvento.setVisible(true);
        } else {
            recursosFrmEvento = new JFrameRecursosEvento(this);
            recursosFrmEvento.setVisible(true);
        }
    }

    public List<DTORecurso> obtenerCatalogoRecursos() {
        return subsistemaEvento.obtenerCatalogoRecursos();
    }

    public void cancelarRecursosEvento() {
        iniciarRegistrarEvento(this.cliente);
    }

    public void actualizarRecurso(String categoria, Map<Integer, Integer> cantidades) {
        subsistemaEvento.actualizarRecursos(categoria, cantidades);
    }

    private void mostrarPanelRecursos(String panel) {
        if (recursosFrmEvento != null) {
            recursosFrmEvento.navegarA(panel);
        }
    }

    public void finalizarRegistroRecursos(String necesidadesExtra) {
        if (evento != null) {
            evento.setNecesidadesIniciales(necesidadesExtra);
            subsistemaEvento.guardarRecursos(evento.getIdEvento());
        }
        iniciarAnalisisEvaluacion();
    }

    // ANALISIS Y EVALUACION - F2
    public void iniciarAnalisisEvaluacion() {
        if (analisisEvaluacion != null && analisisEvaluacion.isDisplayable()) {
            analisisEvaluacion.setVisible(true);
        } else {
            analisisEvaluacion = new JFrameAnalisisEvaluacion(this);
            analisisEvaluacion.setVisible(true);
        }
    }

    public void cancelarAnalisisEvaluacion() {
        iniciarRecursosEvento();
    }

    public void iniciarRevisarAnalisisEvaluacion() {
        JFrameRevisarAnalisisEvaluacion revisar = new JFrameRevisarAnalisisEvaluacion(this);
        revisar.setVisible(true);
    }

    public void cancelarRevisarAnalisisEvaluacion() {
        iniciarAnalisisEvaluacion();
    }

    // FLUJO 3: CONFIGURACION DE PROPUESTAS
    public void iniciarConfigurarPropuesta() {
        if (evento == null || evento.getIdEvento() <= 0) {
            JOptionPane.showMessageDialog(null, "Debes registrar un evento antes de configurar la propuesta.");
            return;
        }

        subsistemaPropuesta.establecerEventoActual(evento.getIdEvento());
        if (configurarPropuesta != null && configurarPropuesta.isDisplayable()) {
            configurarPropuesta.setVisible(true);
            solicitarFolio();
            return;
        }

        configurarPropuesta = new JFrameConfigurarPropuesta(this);
        solicitarFolio();
        configurarPropuesta.setVisible(true);
    }

    public void mostrarMenuRecursos() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_MENU);
    }

    public void mostrarComida() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_COMIDA);
    }

    public void mostrarEntretenimiento() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_ENTRETENIMIENTO);
    }

    public void mostrarIluminacion() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_ILUMINACION);
    }

    public void mostrarDecoracion() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_DECORACION);
    }

    public void mostrarPantallas() {
        mostrarPanelRecursos(JFrameRecursosEvento.PANEL_PANTALLAS);
    }

    private void mostrarPanel(String panel) {
        if (configurarPropuesta != null) {
            configurarPropuesta.navegarA(panel);
        }
    }

    public void solicitarFolio() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarFolio(cliente, evento);
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_FOLIO);
    }

    public void solicitarServicios() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarServicios(consultarServiciosDisponibles());
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_SERVICIOS);
    }

    public void solicitarEquipos() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarEquipos(consultarTodosLosEquipos());
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_EQUIPO);
    }

    public boolean procesarSeleccionCantidadEquipo(int idEquipo, String texto) {
        if (!Validaciones.esEnteroPositivo(texto)) {
            return false;
        }

        try {
            int cantidad = Integer.parseInt(texto.trim());
            return subsistemaPropuesta.seleccionarEquipos(idEquipo, cantidad);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void solicitarInventario() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarInventario(obtenerInventarioDisponible());
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_INVENTARIO);
    }

    public void solicitarEquiposExternos() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarEquiposExternos(consultarEquiposExternos());
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PROVEEDOR);
    }

    public void solicitarPersonal() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarPersonal(obtenerPersonalDisponible());
        }

        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PERSONAL);
    }

    public boolean actualizarCantidadPersonal(int idEspecialidad, String texto) {
        if (!Validaciones.noEsNegativo(texto)) {
            return false;
        }

        try {
            int cantidad = Integer.parseInt(texto.trim());
            return subsistemaPropuesta.seleccionarPersonal(idEspecialidad, cantidad);
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void obtenerResumen() {
        if (configurarPropuesta == null || evento == null) {
            return;
        }

        try {
            DTOInventario resumen = subsistemaPropuesta.obtenerResumenSeleccion(evento.getIdEvento());
            configurarPropuesta.solicitarResumen(cliente, evento, resumen.getServicios(), resumen.getEquipos(), resumen.getPersonal());
            mostrarPanel(JFrameConfigurarPropuesta.PANEL_RESUMEN);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // COTIZACION - F4
    public DTOCotizacion getCotizacion() {
        return cotizacion;
    }

    public DTOVersionCotizacion getVersionCotizacion() {
        return versionCotizacion;
    }

    public DTODetalleCotizacion getDetalleCotizacion() {
        return detalleCotizacion;
    }

    public DTOLevantamiento getLevantamiento() {
        return levantamiento;
    }

    public int getIdPropuestaActual() {
        return propuestaActual == null ? 0 : propuestaActual.getIdPropuesta();
    }

    public void iniciarRegistrarCotizacion() {
        if ((cotizacion == null || versionCotizacion == null) && !generarCotizacion()) {
            return;
        }

        JFrameRegistrarCotizacion registrarCotizacion = new JFrameRegistrarCotizacion(this);
        registrarCotizacion.setVisible(true);
    }

    public void iniciarResumenCotizacion() {
        if (!generarCotizacion()) {
            return;
        }

        if (resumenCotizacion != null && resumenCotizacion.isDisplayable()) {
            mostrarResumenCotizacion();
            resumenCotizacion.setVisible(true);
            resumenCotizacion.toFront();
            return;
        }

        resumenCotizacion = new JFrameResumenCotizacion(this);
        mostrarResumenCotizacion();
        if (configurarPropuesta != null) {
            configurarPropuesta.dispose();
        }

        resumenCotizacion.setVisible(true);
    }

    public void mostrarResumenCotizacion() {
        if (resumenCotizacion == null) return;
        if (cotizacion != null) resumenCotizacion.setIDCotizacion(String.valueOf(cotizacion.getID()));
        if (versionCotizacion != null) {
            resumenCotizacion.setVersion(String.valueOf(versionCotizacion.getNumero()));
            resumenCotizacion.setEstado(versionCotizacion.getEstado());
        }

        if (evento != null) {
            resumenCotizacion.setEvento(evento.getTipoEvento());
            resumenCotizacion.setOperacion(formatearHorario(evento.getHoraInicio(), evento.getHoraFin()));
        }

        resumenCotizacion.setHorariosPropuesta(
                horarioMontajePropuesta == null ? "Por definir" : horarioMontajePropuesta.toString(),
                horarioDesmontajePropuesta == null ? "Por definir" : horarioDesmontajePropuesta.toString());
        
        DTOInventario inventario = obtenerInventarioPropuestaActual();
        if (inventario != null) {
            resumenCotizacion.setServicios(serviciosDeInventario(inventario));
            resumenCotizacion.setEquipos(equiposDeInventario(inventario));
            resumenCotizacion.setPersonalOperativo(personalDeInventario(inventario));
        } else {
            resumenCotizacion.setServicios(new ArrayList<>());
            resumenCotizacion.setEquipos(new ArrayList<>());
            resumenCotizacion.setPersonalOperativo(new ArrayList<>());
        }

        if (versionCotizacion != null) {
            resumenCotizacion.setImportes(versionCotizacion.getSubtotal(), versionCotizacion.getDescuento(), versionCotizacion.getIVA(), versionCotizacion.getTotal());
        }

        resumenCotizacion.ajustarTamano();
    }

    private DTOInventario obtenerInventarioPropuestaActual() {
        if (propuestaActual == null || propuestaActual.getIdPropuesta() <= 0) return null;
        return subsistemaPropuesta.consultarRecursosPropuesta(propuestaActual.getIdPropuesta());
    }

    private List<String> serviciosDeInventario(DTOInventario inventario) {
        List<String> nombres = new ArrayList<>();
        if (inventario.getServicios() == null) return nombres;
        for (DTOServicio servicio : inventario.getServicios()) {
            if (servicio != null && servicio.isSeleccionado()) nombres.add(servicio.getNombre());
        }
        return nombres;
    }

    private List<String> equiposDeInventario(DTOInventario inventario) {
        List<String> nombres = new ArrayList<>();
        if (inventario.getEquipos() == null) return nombres;
        for (DTOEquipo equipo : inventario.getEquipos()) {
            if (equipo != null && equipo.getCantidadSeleccionada() > 0) {
                nombres.add(equipo.getNombre() + " (x" + equipo.getCantidadSeleccionada() + ")");
            }
        }
        return nombres;
    }

    private List<String> personalDeInventario(DTOInventario inventario) {
        List<String> nombres = new ArrayList<>();
        if (inventario.getPersonal() == null) return nombres;
        for (DTOPersonal personal : inventario.getPersonal()) {
            if (personal != null && personal.getCantidad() > 0) {
                nombres.add(personal.getEspecialidad() + " (x" + personal.getCantidad() + ")");
            }
        }
        return nombres;
    }

    private String formatearHorario(LocalTime inicio, LocalTime fin) {
        if (inicio == null) {
            return "";
        }

        if (fin == null) {
            return inicio.toString();
        }

        return inicio + " - " + fin;
    }

    private boolean generarCotizacion() {
        try {
            if (propuestaActual == null || propuestaActual.getIdPropuesta() <= 0) {
                throw new IllegalStateException("Primero debes guardar una propuesta válida.");
            }

            cotizacion = subsistemaCotizacion.calcularCotizacion(propuestaActual.getIdPropuesta());
            if (cotizacion == null) {
                throw new IllegalStateException("No se pudo generar la cotización.");
            }

            versionCotizacion = subsistemaCotizacion.consultarUltimaVersion(cotizacion.getID());
            if (versionCotizacion == null) {
                throw new IllegalStateException("La cotización no tiene una versión registrada.");
            }

            return true;
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al generar cotización", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private List<String> serviciosSeleccionados() {
        DTOInventario inventario = obtenerInventarioPropuestaActual();
        return inventario == null ? new ArrayList<>() : serviciosDeInventario(inventario);
    }

    private List<String> equiposSeleccionados() {
        DTOInventario inventario = obtenerInventarioPropuestaActual();
        return inventario == null ? new ArrayList<>() : equiposDeInventario(inventario);
    }

    private List<String> personalSeleccionado() {
        DTOInventario inventario = obtenerInventarioPropuestaActual();
        return inventario == null ? new ArrayList<>() : personalDeInventario(inventario);
    }

    public void iniciarGenerarOrden() {
        JFrameGenerarOrden generarOrden = new JFrameGenerarOrden(this);
        generarOrden.setVisible(true);
    }

    public List<String> obtenerServiciosCotizadosConPrecio() {
        return serviciosSeleccionados();
    }

    public boolean registrarDecisionCotizacion(String decision, List<String> serviciosSeleccionados) {
        if (decision == null || decision.equals("Selecciona una decisión")) {
            JOptionPane.showMessageDialog(null, "Selecciona el tipo de decisión del cliente.");
            return false;
        }

        serviciosAceptados.clear();
        if (decision.equals("Aceptación total")) {
            for (DTOServicio servicio : consultarServiciosDisponibles()) {
                if (servicio.isSeleccionado()) {
                    serviciosAceptados.add(servicio);
                }
            }

            if (serviciosAceptados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay servicios cotizados para aceptar.");
                return false;
            }

            cotizacion.setEstadoGeneral("Aceptada");
            versionCotizacion.setEstado("Aceptada");
        } else if (decision.equals("Aceptación parcial")) {
            if (serviciosSeleccionados == null || serviciosSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecciona los servicios que el cliente acepta.");
                return false;
            }

            for (String nombreServicio : serviciosSeleccionados) {
                for (DTOServicio servicio : consultarServiciosDisponibles()) {
                    if (servicio.isSeleccionado() && servicio.getNombre().equals(nombreServicio)) {
                        serviciosAceptados.add(servicio);
                        break;
                    }
                }
            }

            if (serviciosAceptados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron servicios aceptados.");
                return false;
            }

            cotizacion.setEstadoGeneral("Aceptada parcialmente");
            versionCotizacion.setEstado("Aceptada parcialmente");
        } else if (decision.equals("Solicitud de modificación")) {
            JOptionPane.showMessageDialog(null, "El cliente solicita modificaciones. " + "Regresando a la configuración de la propuesta.");
            iniciarConfigurarPropuesta();
            return true;
        } else if (decision.equals("Negación total")) {
            cotizacion.setEstadoGeneral("Rechazada");
            versionCotizacion.setEstado("Rechazada");
            if (evento != null) {
                evento.setEstadoAtencion("Cancelado");
            }

            guardarDecisionCliente(decision, "El cliente rechazó todos los servicios.");
            registrarCotizacionEnAlmacen();
            JOptionPane.showMessageDialog(null, "La cotización fue rechazada. " + "El evento ha sido cancelado.");
            cliente = null;
            evento = null;
            cotizacion = null;
            versionCotizacion = null;
            serviciosAceptados.clear();
            iniciarSesion();
            return true;
        }

        guardarDecisionCliente(decision, decision.equals("Aceptación parcial") ? "Servicios aceptados: " + obtenerNombresServiciosAceptados() : "El cliente aceptó todos los servicios cotizados.");
        registrarCotizacionEnAlmacen();
        iniciarGenerarOrden();
        return true;
    }

    private String obtenerNombresServiciosAceptados() {
        List<String> nombres = new ArrayList<>();
        for (DTOServicio servicio : serviciosAceptados) {
            nombres.add(servicio.getNombre());
        }

        return String.join(", ", nombres);
    }

    private void guardarDecisionCliente(String decision, String observaciones) {
        int nuevoID = 1;
        for (DTODecisionCliente d : almacen.getDecisionesCliente()) {
            if (d.getIdDecision() >= nuevoID) {
                nuevoID = d.getIdDecision() + 1;
            }
        }

        DTODecisionCliente nuevaDecision = new DTODecisionCliente(nuevoID, LocalDate.now(), decision, observaciones);
        almacen.registrarDecisionCliente(nuevaDecision);
    }

    private void registrarCotizacionEnAlmacen() {
        if (cotizacion != null && !almacen.getCotizaciones().contains(cotizacion)) {
            almacen.registrarCotizacion(cotizacion);
        }

        if (versionCotizacion != null && !almacen.getVersionesCotizacion() .contains(versionCotizacion)) {
            almacen.registrarVersionCotizacion(versionCotizacion);
        }
    }

    public List<DTOServicio> getServiciosAceptados() {
        return new ArrayList<>(serviciosAceptados);
    }

    // DELEGACION DE OPERACIONES DE F3
    public boolean verificarSeleccionServicios() {
        return subsistemaPropuesta.verificarSeleccionServicios();
    }

    public boolean verificarSeleccionEquipos() {
        return subsistemaPropuesta.verificarSeleccionEquipo();
    }

    public void seleccionarEquipos(int idEquipo, int cantidad) {
        subsistemaPropuesta.seleccionarEquipos(idEquipo, cantidad);
    }

    public boolean verificarSeleccionPersonal() {
        return subsistemaPropuesta.verificarSeleccionPersonal();
    }

    public boolean enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios) {
        try {
            DTOPropuesta guardada = subsistemaPropuesta.enviarPropuestaHaciaCotizacion(horarioMontaje, horarioDesmontaje, comentarios);
            if (guardada == null || guardada.getIdPropuesta() <= 0) {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la propuesta.");
                return false;
            }

            propuestaActual = guardada;
            if (cotizacion != null && cotizacion.getIdPropuesta() != guardada.getIdPropuesta()) {
                cotizacion = null;
                versionCotizacion = null;
                detalleCotizacion = null;
            }

            horarioMontajePropuesta = (horarioMontaje == null || horarioMontaje.isBlank()) ? null : LocalTime.parse(horarioMontaje.trim());
            horarioDesmontajePropuesta = (horarioDesmontaje == null || horarioDesmontaje.isBlank()) ? null : LocalTime.parse(horarioDesmontaje.trim());
            comentariosPropuesta = comentarios == null ? "" : comentarios.trim();
            iniciarResumenCotizacion();
            return true;
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return false;
        }
    }

    public LocalTime getHorarioMontajePropuesta() {
        return horarioMontajePropuesta;
    }

    public LocalTime getHorarioDesmontajePropuesta() {
        return horarioDesmontajePropuesta;
    }

    public String getComentariosPropuesta() {
        return comentariosPropuesta;
    }

    public void eliminarEquipoSeleccionado(int idEquipo) {
        seleccionarEquipos(idEquipo, 0);
    }

    public boolean busquedaValida(javax.swing.JTextField txt) {
        return Validaciones.soloLetras(txt);
    }

    public boolean esCantidadPersonalValida(String texto) {
        return Validaciones.noEsNegativo(texto);
    }

    public boolean esCantidadEquipoValida(int idEquipo, String texto) {
        if (!Validaciones.esEnteroPositivo(texto)) {
            return false;
        }

        try {
            return subsistemaPropuesta.validarCantidadEquipo(idEquipo, Integer.parseInt(texto.trim()));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public List<DTOEquipo> buscarEquiposExternos(String texto) {
        List<DTOEquipo> resultado = new ArrayList<>();
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        for (DTOEquipo equipo : consultarEquiposExternos()) {
            if (equipo.getNombre() != null && equipo.getNombre().toLowerCase().contains(filtro)) {
                resultado.add(equipo);
            }
        }

        return resultado;
    }

    public void seleccionarPersonal(int idPersonal, int cantidad) {
        subsistemaPropuesta.seleccionarPersonal(idPersonal, cantidad);
    }

    public void seleccionarServicios(int idServicio, boolean seleccionado) {
        subsistemaPropuesta.seleccionarServicios(idServicio, seleccionado);
    }

    public void cancelarEventoGuardandoRegistro() {
        if (evento != null) {
            evento.setEstadoAtencion("Cancelado");
            subsistemaEvento.registrarDatosEvento(evento);
        }

        if (cliente != null && subsistemaEvento.ingresarTelefono(cliente.getTelefono()) == null) {
            subsistemaEvento.registrarCliente(cliente);
        }

        this.cliente = null;
        this.evento = null;
        iniciarSesion();
    }

    public void generarOrdenServicio() {
        int nuevoId = almacen.getOrdenes().size() + 1;
        DTOOrdenServicio orden = new DTOOrdenServicio(nuevoId, evento.getIdEvento(), cotizacion.getID(), LocalDate.now(), serviciosAceptados);
        almacen.registrarOrden(orden);
    }
}
