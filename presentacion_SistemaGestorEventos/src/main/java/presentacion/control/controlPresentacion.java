package presentacion.control;

import presentacion.vistas.JFrameAnalisisEvaluacion;
import presentacion.vistas.JFrameConfigurarPropuesta;
import presentacion.vistas.JFrameGenerarOrden;
import presentacion.vistas.JFrameInicioSesion;
import presentacion.vistas.JFrameRecursosEvento;
import presentacion.vistas.JFrameRegistrarCotizacion;
import presentacion.vistas.JFrameRegistrarDatosEvento;
import presentacion.vistas.JFrameResumenCotizacion;
import presentacion.vistas.JFrameRevisarAnalisisEvaluacion;
import dto.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lui
 */
public class controlPresentacion {
    private JFrameRecursosEvento recursosEvento;
    private JFrameConfigurarPropuesta configurarPropuesta;
    private JFrameResumenCotizacion resumenCotizacion;
    private DTOCotizacion cotizacion;
    private DTOVersionCotizacion versionCotizacion;
    private DTODetalleCotizacion detalleCotizacion;
    private DTOLevantamiento levantamiento;
    private List<DTOPersonal> personalSeleccionado = new ArrayList<>();    
    
    //DTO´s que se mueven entre las pantallas y solo el control las conserva
    private DTOCliente cliente;
    private DTOEvento evento;

    public void iniciarSesion() {
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }
    
    public DTOCliente getCliente() {
        return cliente;
    }

    public DTOEvento getEvento() {
        return evento;
    }
    
    
    //Registra datos del evento (recibe el DTO del cliente)
    public void iniciarRegistrarEvento(DTOCliente cliente){
        this.cliente = cliente;
        JFrameRegistrarDatosEvento registrarEvento = new JFrameRegistrarDatosEvento(this);
        registrarEvento.setVisible(true);
    }
    
    public void registrarDatosEvento(DTOEvento evento){
        this.evento = evento;
        iniciarRecursosEvento();
    }
            
    public void cancelarRegistrarEvento(){
        this.cliente = null;
        this.evento = null;
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion(this);
        inicioSesion.setVisible(true);
    }
    
    public void iniciarRecursosEvento(){
        recursosEvento = new JFrameRecursosEvento(this);
        recursosEvento.setVisible(true);
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
        configurarPropuesta.navegarA(JFrameConfigurarPropuesta.PANEL_FOLIO);
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
    
    public void mostrarFolio() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_FOLIO);
    }

    public void mostrarServicios() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_SERVICIOS);
    }

    public void mostrarEquipo() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_EQUIPO);
    }
    
    public void mostrarInventario() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_INVENTARIO);
    }
    
    public void mostrarProveedor() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PROVEEDOR);
    }

    public void mostrarPersonal() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PERSONAL);
    }

    public void mostrarResumen() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_RESUMEN);
    }
    
    public void registrarRecursosEvento(String necesidadesExtra){
    
        evento.setNecesidadesIniciales(necesidadesExtra);
        iniciarAnalisisEvaluacion();
    }
    
    
    public void iniciarAnalisisEvaluacion(){
        JFrameAnalisisEvaluacion analisisEvaluacion = new JFrameAnalisisEvaluacion(this, cliente, evento);
        analisisEvaluacion.setVisible(true);
    }
    
    public void iniciarRevisarAnalisisEvaluacion(){
        JFrameRevisarAnalisisEvaluacion revisarAnalisisEvaluacion = new JFrameRevisarAnalisisEvaluacion(this, evento);
        revisarAnalisisEvaluacion.setVisible(true);
    }
    
    public void iniciarRegistrarCotizacion(){
        JFrameRegistrarCotizacion registrarCotizacion = new JFrameRegistrarCotizacion(this);
        registrarCotizacion.setVisible(true);
    }
    
    public void iniciarResumenCotizacion(){
        configurarPropuesta.dispose();
        JFrameResumenCotizacion resumenCotizacion = new JFrameResumenCotizacion(this);
        resumenCotizacion.setVisible(true);
    }
    
    public void iniciarGenerarOrden(){
        JFrameGenerarOrden generarOrden = new JFrameGenerarOrden();
        generarOrden.setVisible(true);
    }
    
    
}   
