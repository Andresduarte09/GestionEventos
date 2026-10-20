/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Andre
 */
public class JFrameConfigurarPropuesta extends JFrame {

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

    // Instancias de los subpaneles (clases internas)
    private PanelFolio pnlFolio;
    private PanelServicios pnlServicios;
    private PanelEquipo pnlEquipo;
    private PanelProveedor pnlProveedor;
    private PanelPersonal pnlPersonal;
    private PanelResumen pnlResumen;

    public JFrameConfigurarPropuesta() {
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        // Configuración de la pantalla principal
        setTitle("Configuración de Evento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        pnlFolio = new PanelFolio();
        pnlServicios = new PanelServicios();
        pnlEquipo = new PanelEquipo();
        pnlProveedor = new PanelProveedor();
        pnlPersonal = new PanelPersonal();
        pnlResumen = new PanelResumen();

        // Agregar paneles al contenedor con sus identificadores
        pnlContenido.add(pnlFolio, "PanelFolio");
        pnlContenido.add(pnlServicios, "PanelServicios");
        pnlContenido.add(pnlEquipo, "PanelEquipo");
        pnlContenido.add(pnlProveedor, "PanelProveedor");
        pnlContenido.add(pnlPersonal, "PanelPersonal");
        pnlContenido.add(pnlResumen, "PanelResumen");

        // Ensamblado general en la ventana principal
        add(pnlNavegacionLateral, BorderLayout.WEST);
        add(pnlContenido, BorderLayout.CENTER);

        // Eventos de los botones laterales para cambio de panel
        btnFolio.addActionListener(e -> navegarA("PanelFolio"));
        btnServicios.addActionListener(e -> navegarA("PanelServicios"));
        btnEquipo.addActionListener(e -> navegarA("PanelEquipo"));
        btnProveedor.addActionListener(e -> navegarA("PanelProveedor"));
        btnPersonal.addActionListener(e -> navegarA("PanelPersonal"));
        btnResumen.addActionListener(e -> navegarA("PanelResumen"));

        // Abrir panel predilecto
        navegarA("PanelFolio");
    }

    private JButton crearBotonNavegacion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 45, 45));
        btn.setFocusPainted(false);
        return btn;
    }

    public void navegarA(String nombrePanel) {
        layoutPaneles.show(pnlContenido, nombrePanel);
    }

    //Paneles
    
    private class PanelFolio extends JPanel {
        private JLabel lblTitulo;
        private JTextArea txtDetalles;
        private JButton btnContinuar;

        public PanelFolio() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            lblTitulo = new JLabel("Folio", JLabel.LEFT);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

            txtDetalles = new JTextArea();
            txtDetalles.setEditable(false);

            btnContinuar = new JButton("Continuar");
            btnContinuar.setFont(new Font("Arial", Font.BOLD, 14));
            btnContinuar.setBackground(new Color(30, 30, 30));
            btnContinuar.setForeground(Color.WHITE);
            btnContinuar.addActionListener(e -> navegarA("PanelServicios"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnContinuar);

            add(lblTitulo, BorderLayout.NORTH);
            add(new JScrollPane(txtDetalles), BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }

    private class PanelServicios extends JPanel {
        private JLabel lblEncabezado;
        private JPanel pnlOpcionesServicios;
        private JButton btnVolver, btnGuardar, btnContinuar;

        public PanelServicios() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            lblEncabezado = new JLabel("Selección de Servicios", JLabel.LEFT);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

            pnlOpcionesServicios = new JPanel(new GridLayout(0, 2, 10, 10));
            pnlOpcionesServicios.setOpaque(false);

            btnVolver = new JButton("Volver");
            btnGuardar = new JButton("Guardar");
            btnContinuar = new JButton("Continuar");

            btnVolver.addActionListener(e -> navegarA("PanelFolio"));
            btnContinuar.addActionListener(e -> navegarA("PanelEquipo"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnVolver);
            pnlSur.add(btnGuardar);
            pnlSur.add(btnContinuar);

            add(lblEncabezado, BorderLayout.NORTH);
            add(pnlOpcionesServicios, BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }

    private class PanelEquipo extends JPanel {
        private JLabel lblEncabezado;
        private JTable tblEquipos;
        private JButton btnConsultarInventario, btnConsultarProveedor, btnVolver, btnContinuar;

        public PanelEquipo() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            lblEncabezado = new JLabel("Selección De Equipos", JLabel.LEFT);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

            tblEquipos = new JTable();

            btnConsultarInventario = new JButton("Consultar Inventario");
            btnConsultarProveedor = new JButton("Consultar Proveedor");

            btnConsultarInventario.addActionListener(e -> navegarA("PanelProveedor"));
            btnConsultarProveedor.addActionListener(e -> navegarA("PanelProveedor"));

            JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
            pnlCentro.setOpaque(false);
            pnlCentro.add(new JScrollPane(tblEquipos), BorderLayout.CENTER);

            JPanel pnlAccionesTabla = new JPanel();
            pnlAccionesTabla.setOpaque(false);
            pnlAccionesTabla.add(btnConsultarInventario);
            pnlAccionesTabla.add(btnConsultarProveedor);
            pnlCentro.add(pnlAccionesTabla, BorderLayout.SOUTH);

            btnVolver = new JButton("Volver");
            btnContinuar = new JButton("Continuar");

            btnVolver.addActionListener(e -> navegarA("PanelServicios"));
            btnContinuar.addActionListener(e -> navegarA("PanelPersonal"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnVolver);
            pnlSur.add(btnContinuar);

            add(lblEncabezado, BorderLayout.NORTH);
            add(pnlCentro, BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }

    private class PanelProveedor extends JPanel {
        private JTextField txtBuscar;
        private JButton btnBuscar, btnCerrar, btnUsarCantidad;
        private JTable tblConsulta;

        public PanelProveedor() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            JLabel lblTitulo = new JLabel("Consulta de Inventario de Proveedor Externo / Propio", JLabel.LEFT);
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

            JPanel pnlBusqueda = new JPanel();
            pnlBusqueda.setOpaque(false);
            txtBuscar = new JTextField(20);
            btnBuscar = new JButton("Buscar");
            pnlBusqueda.add(new JLabel("Buscar equipo: "));
            pnlBusqueda.add(txtBuscar);
            pnlBusqueda.add(btnBuscar);

            tblConsulta = new JTable();

            btnCerrar = new JButton("Cerrar");
            btnUsarCantidad = new JButton("Usar cantidad");

            btnCerrar.addActionListener(e -> navegarA("PanelEquipo"));
            btnUsarCantidad.addActionListener(e -> navegarA("PanelEquipo"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnCerrar);
            pnlSur.add(btnUsarCantidad);

            JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
            pnlCentro.setOpaque(false);
            pnlCentro.add(pnlBusqueda, BorderLayout.NORTH);
            pnlCentro.add(new JScrollPane(tblConsulta), BorderLayout.CENTER);

            add(lblTitulo, BorderLayout.NORTH);
            add(pnlCentro, BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }

    private class PanelPersonal extends JPanel {
        private JLabel lblEncabezado;
        private JTable tblPersonal;
        private JButton btnVolver, btnContinuar;

        public PanelPersonal() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            lblEncabezado = new JLabel("Personal Requerido", JLabel.LEFT);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

            tblPersonal = new JTable();

            btnVolver = new JButton("Volver");
            btnContinuar = new JButton("Continuar");

            btnVolver.addActionListener(e -> navegarA("PanelEquipo"));
            btnContinuar.addActionListener(e -> navegarA("PanelResumen"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnVolver);
            pnlSur.add(btnContinuar);

            add(lblEncabezado, BorderLayout.NORTH);
            add(new JScrollPane(tblPersonal), BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }

    private class PanelResumen extends JPanel {
        private JLabel lblEncabezado;
        private JTextArea txtResumenPropuesta, txtComentarios;
        private JButton btnVolver, btnEnviarCotizacion;

        public PanelResumen() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(220, 220, 220));

            lblEncabezado = new JLabel("Resumen de la Propuesta", JLabel.LEFT);
            lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

            txtResumenPropuesta = new JTextArea();
            txtResumenPropuesta.setEditable(false);

            txtComentarios = new JTextArea(4, 20);
            txtComentarios.setBorder(BorderFactory.createTitledBorder("Comentarios:"));

            JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
            pnlCentro.setOpaque(false);
            pnlCentro.add(new JScrollPane(txtResumenPropuesta), BorderLayout.CENTER);
            pnlCentro.add(txtComentarios, BorderLayout.SOUTH);

            btnVolver = new JButton("Volver");
            btnEnviarCotizacion = new JButton("Enviar a cotización");

            btnVolver.addActionListener(e -> navegarA("PanelPersonal"));

            JPanel pnlSur = new JPanel();
            pnlSur.setOpaque(false);
            pnlSur.add(btnVolver);
            pnlSur.add(btnEnviarCotizacion);

            add(lblEncabezado, BorderLayout.NORTH);
            add(pnlCentro, BorderLayout.CENTER);
            add(pnlSur, BorderLayout.SOUTH);
        }
    }
}