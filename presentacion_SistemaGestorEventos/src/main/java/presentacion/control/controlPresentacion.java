package presentacion.control;

import presentacion.vistas.*;
import dto.*;
import com.mycompany.persistencia.AlmacenDatos;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import presentacion.validaciones.Validaciones;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author lui
 */
public class controlPresentacion {

    private JFrameRecursosEvento recursosEvento;
    private Map<String, Integer> seleccionTemporalRecursos = new LinkedHashMap<>();
    private JFrameConfigurarPropuesta configurarPropuesta;
    private JFrameResumenCotizacion resumenCotizacion;
    private DTOCotizacion cotizacion;
    private DTOVersionCotizacion versionCotizacion;
    private DTODetalleCotizacion detalleCotizacion;
    private DTOLevantamiento levantamiento;
    private List<DTOPersonal> personalSeleccionado = new ArrayList<>();
    private final AlmacenDatos almacen = new AlmacenDatos();
    private DTODecisionCliente decisionCliente;
    private List<DTOServicio> serviciosAceptados = new ArrayList<>();
    

    //DTO´s que se mueven entre las pantallas y solo el control las conserva
    private DTOCliente cliente;
    private DTOEvento evento;

    public void iniciarSesion() {
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }

    /*
    Consulta lo del almacen para mostrarlo unicamente
     */
    public List<DTOServicio> consultarServicios() {
        return new ArrayList<>(almacen.getServicios());
    }

    public List<DTOEquipo> consultarEquipos() {
        return new ArrayList<>(almacen.getEquipos());
    }

    public List<DTOInventario> consultarInventarios() {
        return new ArrayList<>(almacen.getInventario());
    }

    public List<DTOPersonal> consultarPersonal() {
        return new ArrayList<>(almacen.getPersonal());
    }

    //Por ahora el almacén no guarda inventario de proveedor externo, por eso la lista va vacía
    public List<DTOEquipo> consultarEquiposProveedor() {
        return new ArrayList<>();
    }

    public DTOCliente getCliente() {
        return cliente;
    }

    public DTOEvento getEvento() {
        return evento;
    }

    public boolean procesarTelefonoIngresado(String telefono) {
        if (!Validaciones.telefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null,
                    "Ingresa un teléfono válido de 10 dígitos numéricos.",
                    "Teléfono inválido",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //crea el cliente a buscar si existe o no
        DTOCliente existente = buscarClientePorTelefono(telefono);

        //decide el flujo
        if (existente != null) {
            iniciarRegistrarEvento(existente);
        } else {
            DTOCliente nuevo = new DTOCliente();
            nuevo.setTelefono(telefono);
            registrarCliente(nuevo);
        }
        return true;
    }

    //busca el cliente en el almacen actualmente para la comunicacion entre patnallas
    private DTOCliente buscarClientePorTelefono(String telefono) {
        for (DTOCliente c : almacen.getClientes()) {
            if (c.getTelefono() != null && c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }

    private void registrarCliente(DTOCliente cliente) {
        JFrameFormularioRegistro formulario = new JFrameFormularioRegistro(this, cliente);
        formulario.setVisible(true);
    }

    //el metodo se encarga de validar, persistir los datos del cliente para el metodo del registro de formulario
    //me estoy basando en la arquitectura por capas para su creacion, ya que se dividen las responsabilidades
    public boolean procesarRegistroCliente(DTOCliente cliente) {
        StringBuilder errores = new StringBuilder();

        if (!Validaciones.textoValido(cliente.getNombre())) {
            errores.append("El nombre no puede estar vacío.\n");
        }
        if (!Validaciones.telefonoValido(cliente.getTelefono())) {
            errores.append("El teléfono debe tener 10 dígitos numéricos.\n");
        }
        if (!Validaciones.correoValido(cliente.getCorreo())) {
            errores.append("El correo electrónico no tiene un formato válido.\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(null, errores.toString(),
                    "Datos invalidos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        ;
        //Continuar flujo
        iniciarRegistrarEvento(cliente);
        return true;
    }

    //Inicia el registro de datos del evento 
    public void iniciarRegistrarEvento(DTOCliente cliente) {
        this.cliente = cliente;
        JFrameRegistrarDatosEvento registrarEvento = new JFrameRegistrarDatosEvento(this);
        registrarEvento.setVisible(true);
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

        //llama al metodo que guarda y mueve pantalla
        registrarDatosEvento(evento);
        return true;
    }

    public void cancelarRegistrarEvento() {
        if (this.cliente != null) {
            almacen.getClientes().remove(this.cliente);
        }

        this.cliente = null;
        this.evento = null;
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }

    //cancelar recursos del evento
    public void cancelarRecursosEvento() {
        JFrameRegistrarDatosEvento registrarDatos = new JFrameRegistrarDatosEvento(this);
        registrarDatos.setVisible(true);

    }

    public void actualizarRecursosTemporales(String categoria, Map<String, Integer> cantidades) {
        for (java.util.Map.Entry<String, Integer> entry : cantidades.entrySet()) {
            seleccionTemporalRecursos.put(categoria + "|" + entry.getKey(), entry.getValue());
        }
    }

    public void CancelarAnalisisEvaluacion() {
        JFrameRecursosEvento recursoEvento = new JFrameRecursosEvento(this);
        recursoEvento.setVisible(true);
    }

    public void CancelarRevisarAnalisisEvaluacion() {
        JFrameAnalisisEvaluacion analisisEvaluacion = new JFrameAnalisisEvaluacion(this, cliente, evento);
        analisisEvaluacion.setVisible(true);
    }

    public void iniciarRecursosEvento() {
        recursosEvento = new JFrameRecursosEvento(this);
        recursosEvento.setVisible(true);
    }

    //actualizar cuando haya logica de reservas 
    public List<DTOLocalEvento> recomendarLocales(DTOEvento evento) {
        List<DTOLocalEvento> recomendados = new ArrayList<>();
        if (evento == null) {
            return recomendados;
        }

        for (DTOLocalEvento local : almacen.getLocalesEvento()) {
            if (local.getCapacidad() >= evento.getNumAsistentes()) {
                recomendados.add(local);
            }
        }
        recomendados.sort((a, b) -> Integer.compare(a.getCapacidad(), b.getCapacidad()));
        return recomendados;
    }

    //Paneles de la pantalla de recursos del evento
    private void mostrarPanelRecursos(String panel) {
        if (recursosEvento != null) {
            recursosEvento.navegarA(panel);
        }
    }

    //Aqui va la inicializacion de la pantalla de configuracion de propuesta
    public void iniciarConfigurarPropuesta() {
        if (configurarPropuesta == null) {
            configurarPropuesta = new JFrameConfigurarPropuesta(this);
        }
        mostrarFolio();
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

    //Cada método carga en su panel la información del almacén y luego lo muestra
    public void mostrarFolio() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarFolio(cliente, evento);
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_FOLIO);
    }

    public void mostrarServicios() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarServicios(consultarServicios());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_SERVICIOS);
    }

    public void mostrarEquipo() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarEquipos(consultarEquipos());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_EQUIPO);
    }

    public void mostrarInventario() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarInventario(consultarInventarios());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_INVENTARIO);
    }

    public void mostrarProveedor() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarProveedor(consultarEquiposProveedor());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PROVEEDOR);
    }

    public void mostrarPersonal() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarPersonal(consultarPersonal());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PERSONAL);
    }

    public void mostrarResumen() {
        if (configurarPropuesta != null) {
            configurarPropuesta.cargarResumen(cliente, evento, consultarServicios(), consultarEquipos(), consultarPersonal());
        }
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_RESUMEN);
    }

    public void registrarRecursosEvento(String necesidadesExtra) {
        evento.setNecesidadesIniciales(necesidadesExtra);

        List<DTODetalleSolicitud> detalles = new java.util.ArrayList<>();

        for (Map.Entry<String, Integer> entry : seleccionTemporalRecursos.entrySet()) {
            String[] partes = entry.getKey().split("\\|");
            //el if es para asegurar formato correcto
            if (partes.length == 2) {
                DTODetalleSolicitud detalle = new DTODetalleSolicitud();
                //inserta en el arreglo la categoria de la comida y la pone en indice 0
                detalle.setCategoriaSolicitada(partes[0]);
                //pone en conjunto a la categoria, el recurso que se selecciono de dicha categoria en indice 1
                detalle.setDescripcionSolicitud(partes[1]);
                detalle.setCantidadEstimada(entry.getValue());
                detalles.add(detalle);
                //termina quedando algo como: Comida|Sopa fría
            }
        }

        //asigna los detalles de la solicitud al evento
        evento.setDetallesSolicitud(detalles);

        //termina guardando el cliente si es nuevo
        //lo pongo aqui de una vez pero probablemente se deba poner a la hora de crear la orden final
        if (cliente != null && buscarClientePorTelefono(cliente.getTelefono()) == null) {
            almacen.getClientes().add(cliente);
        }

        iniciarAnalisisEvaluacion();
    }

    public void iniciarAnalisisEvaluacion() {
        JFrameAnalisisEvaluacion analisisEvaluacion = new JFrameAnalisisEvaluacion(this, cliente, evento);
        analisisEvaluacion.setVisible(true);
    }

    public void iniciarRevisarAnalisisEvaluacion() {
        JFrameRevisarAnalisisEvaluacion revisarAnalisisEvaluacion = new JFrameRevisarAnalisisEvaluacion(this, evento);
        revisarAnalisisEvaluacion.setVisible(true);
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

        JFrameRegistrarCotizacion registrarCotizacion =
                new JFrameRegistrarCotizacion(this);

        registrarCotizacion.setVisible(true);
    }

    public void iniciarResumenCotizacion() {
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

    private void generarCotizacion() {
        int folio = almacen.getCotizaciones().size() + 1;
        int idVersion = almacen.getVersionesCotizacion().size() + 1;

        cotizacion = new DTOCotizacion(folio, LocalDate.now(), "Pendiente");
        versionCotizacion = new DTOVersionCotizacion(idVersion, 1, LocalDate.now(),
                "Pendiente", "Versión inicial", 0f, 0f, 0f);
    }

    private List<String> serviciosSeleccionados() {
        List<String> nombres = new ArrayList<>();
        for (DTOServicio s : consultarServicios()) {
            if (s.isSeleccionado()) {
                nombres.add(s.getNombre());
            }
        }
        return nombres;
    }

    private List<String> equiposSeleccionados() {
        List<String> nombres = new ArrayList<>();
        for (DTOEquipo e : consultarEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                nombres.add(e.getNombre() + " (x" + e.getCantidadSeleccionada() + ")");
            }
        }
        return nombres;
    }

    private List<String> personalSeleccionado() {
        List<String> nombres = new ArrayList<>();
        for (DTOPersonal p : consultarPersonal()) {
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

        public List<String> obtenerServiciosCotizadosConPrecio() {
             List<String> resultado = new ArrayList<>();

        for (DTOServicio servicio : consultarServicios()) {

            if (servicio.isSeleccionado()) {
                resultado.add(servicio.getNombre());
            }
        }

        return resultado;
    }
    
    private String obtenerCategoriaServicio(String nombreServicio) {

        switch (nombreServicio) {

            case "Audio":
                return "Audio";

            case "Alimentos y bebidas":
                return "Comida";

            case "Entretenimiento":
                return "Entretenimiento";

            case "Iluminación":
                return "Iluminación";

            case "Decoración":
                return "Decoración";

            case "Pantallas":
                return "Pantallas";

            default:
                return null;
        }
    }
    
    public boolean registrarDecisionCotizacion(
        String decision, List<String> serviciosSeleccionados) {

        if (decision == null || decision.equals("Selecciona una decisión")) {
            JOptionPane.showMessageDialog(null, "Selecciona el tipo de decisión del cliente.");
            return false;
        }

        serviciosAceptados.clear();

        if (decision.equals("Aceptación total")) {

            for (DTOServicio servicio : consultarServicios()) {
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

            for (String elemento : serviciosSeleccionados) {

                String nombreServicio = elemento.split("\\s\\|\\s")[0];

                for (DTOServicio servicio : consultarServicios()) {

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

        guardarDecisionCliente(decision, decision.equals("Aceptación parcial")
                        ? "Servicios aceptados: " + obtenerNombresServiciosAceptados()
                        : "El cliente aceptó todos los servicios cotizados."
        );

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

        almacen.getDecisionesCliente().add(nuevaDecision);
    }
    
    private void registrarCotizacionEnAlmacen() {

        if (cotizacion != null && !almacen.getCotizaciones().contains(cotizacion)) {

            almacen.getCotizaciones().add(cotizacion);
        }

        if (versionCotizacion != null && !almacen.getVersionesCotizacion().contains(versionCotizacion)) {

            almacen.getVersionesCotizacion().add(versionCotizacion);
        }
    }
    
    public List<DTOServicio> getServiciosAceptados() {
        return new ArrayList<>(serviciosAceptados);
    }
    
    
}
