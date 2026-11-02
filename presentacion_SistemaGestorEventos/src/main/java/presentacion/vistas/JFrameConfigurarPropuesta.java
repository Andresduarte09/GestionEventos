package presentacion.vistas;

import presentacion.paneles.PanelServicios;
import presentacion.paneles.PanelResumen;
import presentacion.paneles.PanelProveedor;
import presentacion.paneles.PanelPersonal;
import presentacion.paneles.PanelFolio;
import presentacion.paneles.PanelEquipo;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import presentacion.paneles.PanelInventario;
import dto.*;

/**
 * 
 * @author Andre
 */
public class JFrameConfigurarPropuesta extends JFrame {

    // Identificadores de cada módulo en el CardLayout
    public static final String PANEL_FOLIO = "PanelFolio";
    public static final String PANEL_SERVICIOS = "PanelServicios";
    public static final String PANEL_EQUIPO = "PanelEquipo";
    public static final String PANEL_INVENTARIO = "PanelInventario";
    public static final String PANEL_PROVEEDOR = "PanelProveedor";
    public static final String PANEL_PERSONAL = "PanelPersonal";
    public static final String PANEL_RESUMEN = "PanelResumen";

    // Componentes de navegación y estructura
    private JPanel pnlNavegacionLateral;
    private JPanel pnlContenido;
    private CardLayout layoutPaneles;

    // Botones de la barra lateral
    private JButton btnFolio;
    private JButton btnServicios;
    private JButton btnEquipo;
    private JButton btnProveedor;
    private JButton btnPersonal;
    private JButton btnResumen;

    // Controlador de presentación y subpaneles (cada uno en su propia clase)
    private final controlPresentacion control;
    private PanelFolio pnlFolio;
    private PanelServicios pnlServicios;
    private PanelEquipo pnlEquipo;
    private PanelInventario pnlInventario;
    private PanelProveedor pnlProveedor;
    private PanelPersonal pnlPersonal;
    private PanelResumen pnlResumen;

    public JFrameConfigurarPropuesta(controlPresentacion control) {
        this.control = control;
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        // Configuración de la pantalla principal
        setTitle("Configurar Propuesta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra lateral izquierda
        pnlNavegacionLateral = new JPanel(new GridLayout(8, 1, 5, 10));
        pnlNavegacionLateral.setBackground(new Color(25, 25, 25));
        pnlNavegacionLateral.setPreferredSize(new Dimension(180, 650));

        btnFolio = crearBotonNavegacion("Folio");
        btnServicios = crearBotonNavegacion("Servicios");
        btnEquipo = crearBotonNavegacion("Equipo");
        btnProveedor = crearBotonNavegacion("Proveedor");
        btnPersonal = crearBotonNavegacion("Personal");
        btnResumen = crearBotonNavegacion("Resumen");

        pnlNavegacionLateral.add(btnFolio);
        pnlNavegacionLateral.add(btnServicios);
        pnlNavegacionLateral.add(btnEquipo);
        pnlNavegacionLateral.add(btnProveedor);
        pnlNavegacionLateral.add(btnPersonal);
        pnlNavegacionLateral.add(btnResumen);

        layoutPaneles = new CardLayout();
        pnlContenido = new JPanel(layoutPaneles);

        pnlFolio = new PanelFolio(control);
        pnlServicios = new PanelServicios(control);
        pnlEquipo = new PanelEquipo(control);
        pnlInventario = new PanelInventario(control);
        pnlProveedor = new PanelProveedor(control);
        pnlPersonal = new PanelPersonal(control);
        pnlResumen = new PanelResumen(control);

        // Agregar paneles al contenedor con sus identificadores
        pnlContenido.add(pnlFolio, PANEL_FOLIO);
        pnlContenido.add(pnlServicios, PANEL_SERVICIOS);
        pnlContenido.add(pnlEquipo, PANEL_EQUIPO);
        pnlContenido.add(pnlInventario, PANEL_INVENTARIO);
        pnlContenido.add(pnlProveedor, PANEL_PROVEEDOR);
        pnlContenido.add(pnlPersonal, PANEL_PERSONAL);
        pnlContenido.add(pnlResumen, PANEL_RESUMEN);

        // Ensamblado general en la ventana principal
        add(pnlNavegacionLateral, BorderLayout.WEST);
        add(pnlContenido, BorderLayout.CENTER);

        // Eventos de los botones laterales para cambio de panel
        btnFolio.addActionListener(e -> control.solicitarFolio());
        btnServicios.addActionListener(e -> control.solicitarServicios());
        btnEquipo.addActionListener(e -> control.solicitarEquipos());
        btnProveedor.addActionListener(e -> control.solicitarEquiposExternos());
        btnPersonal.addActionListener(e -> control.solicitarPersonal());
        btnResumen.addActionListener(e -> control.obtenerResumen());

        // Abrir panel predilecto
        navegarA(PANEL_FOLIO);
    }

    private JButton crearBotonNavegacion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        return btn;
    }
    
    public void solicitarFolio(DTOCliente cliente, DTOEvento evento) {
        pnlFolio.solicitarFolio(cliente, evento);
    }
 
    public void solicitarServicios(List<DTOServicio> servicios) {
        pnlServicios.solicitarServicios(servicios);
    }
 
    public void solicitarEquipos(List<DTOEquipo> equipos) {
        pnlEquipo.solicitarEquipos(equipos);
    }
 
    public void solicitarInventario(List<DTOInventario> inventarios) {
        pnlInventario.solicitarInventario(inventarios);
    }
 
    public void solicitarEquiposExternos(List<DTOEquipo> equiposProveedor) {
        pnlProveedor.solicitarEquiposExternos(equiposProveedor);
    }
 
    public void solicitarPersonal(List<DTOPersonal> personal) {
        pnlPersonal.solicitarPersonal(personal);
    }
 
    public void solicitarResumen(DTOCliente cliente, DTOEvento evento, List<DTOServicio> servicios,
            List<DTOEquipo> equipos, List<DTOPersonal> personal) {
        pnlResumen.solicitarResumen(cliente, evento, servicios, equipos, personal);
    }

    public void navegarA(String nombrePanel) {
        layoutPaneles.show(pnlContenido, nombrePanel);
    }
}
