package presentacion;

/**
 *
 * @author lui
 */
public class controlPresentacion {
    
    JFrameRecursosEvento recursosEvento = new JFrameRecursosEvento();
    private JFrameConfigurarPropuesta configurarPropuesta;
    
    public void iniciarRegistrarEvento(){
        JFrameRegistrarDatosEvento registrarEvento = new JFrameRegistrarDatosEvento();
        registrarEvento.setVisible(true);
    }
    
    public void cancelarRegistrarEvento(){
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion();
        inicioSesion.setVisible(true);
    }
    
    public void iniciarRecursosEvento(){
        JFrameRecursosEvento recursosEvento = new JFrameRecursosEvento();
        recursosEvento.setVisible(true);
    }
    

    
    //Aqui va la inicializacion de la pantalla de configuracion de propuesta
    public void iniciarConfigurarPropuesta() {
        if (configurarPropuesta == null) {
            configurarPropuesta = new JFrameConfigurarPropuesta(this);
        }
        configurarPropuesta.navegarA(JFrameConfigurarPropuesta.PANEL_FOLIO);
        configurarPropuesta.setVisible(true);
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

    public void mostrarProveedor() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PROVEEDOR);
    }

    public void mostrarPersonal() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_PERSONAL);
    }

    public void mostrarResumen() {
        mostrarPanel(JFrameConfigurarPropuesta.PANEL_RESUMEN);
    }
    
    public void iniciarAnalisisEvaluacion(){
        JFrameAnalisisEvaluacion analisisEvaluacion = new JFrameAnalisisEvaluacion();
        analisisEvaluacion.setVisible(true);
    }
    
    public void iniciarRevisarAnalisisEvaluacion(){
        JFrameRevisarAnalisisEvaluacion revisarAnalisisEvaluacion = new JFrameRevisarAnalisisEvaluacion();
        revisarAnalisisEvaluacion.setVisible(true);
    }
    
    public void iniciarRegistrarCotizacion(){
        JFrameRegistrarCotizacion registrarCotizacion = new JFrameRegistrarCotizacion();
        registrarCotizacion.setVisible(true);
    }
}
