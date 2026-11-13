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
import negocioPropuesta.fachada.FachadaPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;
import negocioCotizacion.fachada.FachadaCotizacion;
import negocioCotizacion.interfaces.ISubsistemaCotizacion;
import negocioOrden.fachada.FachadaOrden;
import negocioOrden.interfaces.ISubsistemaOrden;

/**
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
    private DTOVersionCotizacion versionCotizacion;
    private DTODetalleCotizacion detalleCotizacion;
    private DTOLevantamiento levantamiento;
    private List<DTOPersonal> personalSeleccionado = new ArrayList<>();

    public ControlPresentacion() {
        //nomas por el momento, controlPresentacion no debe conocer el almacen
        this.almacen = AlmacenDatos.getInstance();
        this.proveedorExterno = new ProveedorExterno();

        //puede que en el futuro las fachadas ocupen mas peticiones conforme vayamos implementando
        this.subsistemaEvento = new FachadaEvento();
        this.subsistemaPropuesta = new FachadaPropuesta(this.almacen, this.proveedorExterno);
        this.subsistemaCotizacion = new FachadaCotizacion();
        this.subsistemaOrden = new FachadaOrden(this.almacen);
    }

    private DTODecisionCliente decisionCliente;
    private List<DTOServicio> serviciosAceptados = new ArrayList<>();
    private LocalTime horarioMontajePropuesta;
    private LocalTime horarioDesmontajePropuesta;
    private String comentariosPropuesta = "";
    private JFrameRegistrarDatosEvento registrarEvento;
    private JFrameAnalisisEvaluacion analisisEvaluacion;

    //DTO´s que se mueven entre las pantallas y solo el control las conserva
    private DTOCliente cliente;
    private DTOEvento evento;

    public void iniciarSesion() {
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }

    //prueba para probar si funciona el filtro de locales
    public List<DTOLocalEvento> consultarLocalesDisponibles() {
        if (evento == null) {
            return new ArrayList<>();
        }
        return subsistemaEvento.obtenerLocalesDisponibles(evento, evento.getUbicacion());
    }

    /*
    Consulta lo del almacen para mostrarlo unicamente
     */
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
        List<DTOEquipo> todos = new ArrayList<>(obtenerEquiposDisponibles());
        todos.addAll(consultarEquiposExternos());
        return todos;
    }

    public DTOCliente getCliente() {
        return cliente;
    }

    public DTOEvento getEvento() {
        return evento;
    }

    //METODOS DE INICIO DE SESION/REGISTRO DE CLIENTE
    public boolean procesarTelefonoIngresado(String telefono) {
        if (!Validaciones.telefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null,
                    "Ingresa un teléfono válido de 10 dígitos numéricos.",
                    "Teléfono inválido",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //crea el cliente a buscar si existe o no
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

    //muestra el frame para registrar cliente
    private void mostrarFormularioCliente(DTOCliente cliente) {
        this.cliente = cliente;
        JFrameFormularioRegistro formulario = new JFrameFormularioRegistro(this);
        formulario.setVisible(true);
    }

    //el metodo se encarga de validar, persistir los datos del cliente para el metodo del registro de formulario
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
            JOptionPane.showMessageDialog(null, errores.toString(),
                    "Datos invalidos", JOptionPane.WARNING_MESSAGE);
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

    //guarda el evento y mueve a la siguiente pantalla
    private void registrarDatosEvento(DTOEvento evento) {
        this.evento = evento;
        iniciarRecursosEvento();
    }

    //este metodo filtra, arma el objeto evento y despues lo manda al metodo que se encarga de guardarlo y cambiar de pantalla
    //asi separamos la logica con arquitectura por capas dividiendo responsabilidades
    public boolean procesarDatosEvento(String tipoEvento, String ubicacion,
            java.time.LocalDate fecha, java.time.LocalTime hora,
            String asistentesTexto) {
        //acumula errores por si existen varios en una misma viñeta
        StringBuilder errores = new StringBuilder();

        //validaciones
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
            JOptionPane.showMessageDialog(null, errores.toString(),
                    "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        DTOEvento evento = new DTOEvento();
        evento.setTipoEvento(tipoEvento);
        evento.setUbicacion(ubicacion);
        evento.setFechaEvento(fecha);
        evento.setHoraInicio(hora);
        evento.setNumAsistentes(Integer.parseInt(asistentesTexto.trim()));

        evento = subsistemaEvento.registrarDatosEvento(evento);

        //llama al metodo que guarda y mueve pantalla
        registrarDatosEvento(evento);
        return true;
    }

    public void cancelarRegistrarEvento() {
        this.cliente = null;
        this.evento = null;
        iniciarSesion();
    }

    //RECURSOS EVENTO
    public void iniciarRecursosEvento() {
        if (recursosFrmEvento != null && recursosFrmEvento.isDisplayable()) {
            recursosFrmEvento.setVisible(true);
        } else {
            recursosFrmEvento = new JFrameRecursosEvento(this);
            recursosFrmEvento.setVisible(true);
        }
    }

    //metodo para el panelCategoriaRecursos, va delegando
    public List<DTORecurso> obtenerCatalogoRecursos() {
        return subsistemaEvento.obtenerCatalogoRecursos();
    }

    public void cancelarRecursosEvento() {
        iniciarRegistrarEvento(this.cliente);
    }

    //metodo actualizado para que limpie los recursos de la categoria para evitar seleccionados
    public void actualizarRecurso(String categoria, Map<Integer, Integer> cantidades) {
        subsistemaEvento.actualizarRecursosTemporal(categoria, cantidades);
    }

    //Paneles de la pantalla de recursos del evento
    private void mostrarPanelRecursos(String panel) {
        if (recursosFrmEvento != null) {
            recursosFrmEvento.navegarA(panel);
        }
    }

    public void finalizarRegistroRecursos(String necesidadesExtra) {
        evento.setNecesidadesIniciales(necesidadesExtra);

        subsistemaEvento.guardarRecursos(evento.getIdEvento());

        //actualiza para traer la lista de detalleSolicitud
        this.evento = subsistemaEvento.consultarInformacionEvento(evento.getIdEvento());

        iniciarAnalisisEvaluacion();
    }

    //METODOS DE ANALISIS EVALUACION AQUI INICIA F2
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

    //METODOS F3 PROPUESTA   
    //Aqui va la inicializacion de la pantalla de configuracion de propuesta
    //metodo modificado para agregar la validacion isDisplayable para evitar ventanas duplicadas
    public void iniciarConfigurarPropuesta() {

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

    //Cada metodo carga en su panel la información del almacén y luego lo muestra
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

    //MOVER AL SUBSISTEMA Y BORRAR
    public boolean procesarSeleccionCantidadEquipo(int idEquipo, String texto) {
        if (!Validaciones.esEnteroPositivo(texto)) {
            return false;
        }
        int cantidad = Integer.parseInt(texto.trim());
        for (DTOEquipo e : consultarTodosLosEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                if (cantidad <= e.getDisponibilidad()) {
                    e.setCantidadSeleccionada(cantidad);
                    return true;
                }
                return false;
            }
        }
        return false;
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

    //MOVER AL SUBSISTEMA Y BORRAR
    public boolean actualizarCantidadPersonal(int idEspecialidad, String texto) {
        if (!Validaciones.noEsNegativo(texto)) {
            return false;
        }

        int cantidad = Integer.parseInt(texto.trim());

        for (DTOPersonal p : almacen.getPersonal()) {
            if (p.getIdEspecialidad() == idEspecialidad) {
                p.setCantidad(cantidad);
                return true;
            }
        }
        return false;
    }

    public void obtenerResumen() {
        if (configurarPropuesta != null) {
            configurarPropuesta.solicitarResumen(cliente, evento, consultarServiciosDisponibles(), consultarTodosLosEquipos(), obtenerPersonalDisponible());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_RESUMEN);
    }

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

    public void iniciarRegistrarCotizacion() {
        if (cotizacion == null || versionCotizacion == null) {
            generarCotizacion();
        }

        JFrameRegistrarCotizacion registrarCotizacion
                = new JFrameRegistrarCotizacion(this);

        registrarCotizacion.setVisible(true);
    }

    //metodo modificado para agregar la validacion isDisplayable para evitar ventanas duplicadas
    public void iniciarResumenCotizacion() {
        if (resumenCotizacion != null && resumenCotizacion.isDisplayable()) {
            mostrarResumenCotizacion();
            resumenCotizacion.setVisible(true);
            return;
        }
        configurarPropuesta.dispose();
        generarCotizacion();
        resumenCotizacion = new JFrameResumenCotizacion(this);
        mostrarResumenCotizacion();
        resumenCotizacion.setVisible(true);
    }

    public void mostrarResumenCotizacion() {
        if (resumenCotizacion == null) {
            return;
        }

        if (cotizacion != null) {
            resumenCotizacion.setIDCotizacion(String.valueOf(cotizacion.getID()));
        }

        if (versionCotizacion != null) {
            resumenCotizacion.setVersion(String.valueOf(versionCotizacion.getNumero()));
            resumenCotizacion.setEstado(versionCotizacion.getEstado());
        }

        if (evento != null) {
            resumenCotizacion.setEvento(evento.getTipoEvento());
            resumenCotizacion.setOperacion(formatearHorario(evento.getHoraInicio(), evento.getHoraFin()));
        }

        resumenCotizacion.setServicios(serviciosSeleccionados());
        resumenCotizacion.setEquipos(equiposSeleccionados());
        resumenCotizacion.setPersonalOperativo(personalSeleccionado());
        resumenCotizacion.ajustarTamano();
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

    //MOVER AL SUBSISTEMA Y BORRAR
    private void generarCotizacion() {
        int folio = almacen.getCotizaciones().size() + 1;
        int idVersion = almacen.getVersionesCotizacion().size() + 1;

        cotizacion = new DTOCotizacion(folio, LocalDate.now(), "Pendiente");
        versionCotizacion = new DTOVersionCotizacion(idVersion, 1, LocalDate.now(),
                "Pendiente", "Versión inicial", 0f, 20000f, 0f);
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    private List<String> serviciosSeleccionados() {
        List<String> nombres = new ArrayList<>();
        for (DTOServicio s : consultarServiciosDisponibles()) {
            if (s.isSeleccionado()) {
                nombres.add(s.getNombre());
            }
        }
        return nombres;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    private List<String> equiposSeleccionados() {
        List<String> nombres = new ArrayList<>();
        for (DTOEquipo e : consultarTodosLosEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                nombres.add(e.getNombre() + " (x" + e.getCantidadSeleccionada() + ")");
            }
        }
        return nombres;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    private List<String> personalSeleccionado() {
        List<String> nombres = new ArrayList<>();
        for (DTOPersonal p : obtenerPersonalDisponible()) {
            if (p.getCantidad() > 0) {
                nombres.add(p.getEspecialidad() + " (x" + p.getCantidad() + ")");
            }
        }
        return nombres;
    }

    public void iniciarGenerarOrden() {
        JFrameGenerarOrden generarOrden = new JFrameGenerarOrden(this);
        generarOrden.setVisible(true);
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public List<String> obtenerServiciosCotizadosConPrecio() {
        List<String> resultado = new ArrayList<>();

        for (DTOServicio servicio : consultarServiciosDisponibles()) {

            if (servicio.isSeleccionado()) {
                resultado.add(servicio.getNombre());
            }
        }

        return resultado;
    }

    //nunca se uso este metodo, lo comentareo por si acaso alguien tiene vista alguna funcionalidad despues?
//    private String obtenerCategoriaServicio(String nombreServicio) {
//
//        switch (nombreServicio) {
//
//            case "Audio":
//                return "Audio";
//
//            case "Alimentos y bebidas":
//                return "Comida";
//
//            case "Entretenimiento":
//                return "Entretenimiento";
//
//            case "Iluminación":
//                return "Iluminación";
//
//            case "Decoración":
//                return "Decoración";
//
//            case "Pantallas":
//                return "Pantallas";
//
//            default:
//                return null;
//        }
//    }
    //MOVER AL SUBSISTEMA Y BORRAR
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
            JOptionPane.showMessageDialog(null, "El cliente solicita modificaciones. Regresando a la configuración de la propuesta.");

            //abre f3 nuevamente
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

            JOptionPane.showMessageDialog(null, "La cotización fue rechazada. El evento ha sido cancelado.");

            cliente = null;
            evento = null;
            cotizacion = null;
            versionCotizacion = null;
            serviciosAceptados.clear();

            iniciarSesion();
            return true;
        }

        // Si llegó aquí es porque fue una aceptación (total o parcial)
        guardarDecisionCliente(decision, decision.equals("Aceptación parcial")
                ? "Servicios aceptados: " + obtenerNombresServiciosAceptados()
                : "El cliente aceptó todos los servicios cotizados."
        );

        registrarCotizacionEnAlmacen();

        // Al aceptar, se pasa a generar la orden
        iniciarGenerarOrden();

        return true;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    private String obtenerNombresServiciosAceptados() {

        List<String> nombres = new ArrayList<>();

        for (DTOServicio servicio : serviciosAceptados) {
            nombres.add(servicio.getNombre());
        }

        return String.join(", ", nombres);
    }

    //MOVER AL SUBSISTEMA Y BORRAR
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

    //MOVER AL SUBSISTEMA Y BORRAR
    private void registrarCotizacionEnAlmacen() {

        if (cotizacion != null && !almacen.getCotizaciones().contains(cotizacion)) {

            almacen.registrarCotizacion(cotizacion);
        }

        if (versionCotizacion != null && !almacen.getVersionesCotizacion().contains(versionCotizacion)) {

            almacen.registrarVersionCotizacion(versionCotizacion);
        }
    }

    public List<DTOServicio> getServiciosAceptados() {
        return new ArrayList<>(serviciosAceptados);
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public boolean verificarSeleccionServicios() {
        for (DTOServicio s : consultarServiciosDisponibles()) {
            if (s.isSeleccionado()) {
                return true;
            }
        }
        return false;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    //Este metodo verifica si se selecciono al menos un equipo
    public boolean verificarSeleccionEquipos() {
        for (DTOEquipo e : consultarTodosLosEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                return true;
            }
        }
        return false;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public void seleccionarEquipos(int idEquipo, int cantidad) {
        for (DTOEquipo e : consultarTodosLosEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                e.setCantidadSeleccionada(cantidad);
                return;
            }
        }
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public boolean verificarSeleccionPersonal() {
        for (DTOPersonal p : obtenerPersonalDisponible()) {
            if (p.getCantidad() > 0) {
                return true;
            }
        }
        return false;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public boolean enviarPropuestaHaciaCotizacion(String horarioMontaje, String horarioDesmontaje, String comentarios) {
        LocalTime montaje = null;
        LocalTime desmontaje = null;
        try {
            if (horarioMontaje != null && !horarioMontaje.trim().isEmpty()) {
                montaje = LocalTime.parse(horarioMontaje.trim());
            }
            if (horarioDesmontaje != null && !horarioDesmontaje.trim().isEmpty()) {
                desmontaje = LocalTime.parse(horarioDesmontaje.trim());
            }
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(null, "Los horarios deben tener el formato HH:mm, por ejemplo 08:30.");
            return false;
        }
        this.horarioMontajePropuesta = montaje;
        this.horarioDesmontajePropuesta = desmontaje;
        this.comentariosPropuesta = comentarios == null ? "" : comentarios.trim();
        iniciarResumenCotizacion();
        return true;
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

    //ES VALIDACION
    public boolean esCantidadEquipoValida(int idEquipo, String texto) {
        for (DTOEquipo e : consultarTodosLosEquipos()) {
            if (e.getIdEquipo() == idEquipo) {
                return Validaciones.esEnteroPositivo(texto)
                        && Integer.parseInt(texto.trim()) <= e.getDisponibilidad();
            }
        }
        return false;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public List<DTOEquipo> buscarEquiposExternos(String texto) {
        List<DTOEquipo> resultado = new ArrayList<>();
        for (DTOEquipo e : consultarEquiposExternos()) {
            if (e.getNombre() != null && e.getNombre().toLowerCase().contains(texto.trim().toLowerCase())) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public void seleccionarPersonal(int idPersonal, int cantidad) {
        for (DTOPersonal p : almacen.getPersonal()) {
            if (p.getIdEspecialidad() == idPersonal) {
                p.setCantidad(cantidad);
                return;
            }
        }
    }

    //MOVER AL SUBSISTEMA Y BORRAR
    public void seleccionarServicios(int idServicio, boolean seleccionado) {
        for (DTOServicio s : almacen.getServicios()) {
            if (s.getIdServicio() == idServicio) {
                s.setSeleccionado(seleccionado);
                return;
            }
        }
    }

    //para cuando se presione el boton cancelar evento
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

    //MOVER AL SUBSISTEMA Y BORRAR
    public void generarOrdenServicio() {
        int nuevoId = almacen.getOrdenes().size() + 1;
        DTOOrdenServicio orden = new DTOOrdenServicio(
                nuevoId,
                evento.getIdEvento(), // Si no tienes ID asignado, usa 1 provisional
                cotizacion.getID(),
                LocalDate.now(),
                serviciosAceptados
        );
        almacen.registrarOrden(orden);
    }
}
