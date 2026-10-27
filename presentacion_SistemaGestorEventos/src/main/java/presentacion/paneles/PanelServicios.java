/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package presentacion.paneles;

import dto.DTOServicio;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import presentacion.validaciones.Validaciones;

public class PanelServicios extends JPanel {

    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JPanel pnlOpcionesServicios;
    private JPanel pnlBusqueda;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnVolver, btnGuardar, btnContinuar;

    public PanelServicios(controlPresentacion control) {
        this.control = control;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Selección de Servicios", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        // Campo de búsqueda y botón
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");

        pnlBusqueda = new JPanel(new BorderLayout(5, 0));
        pnlBusqueda.setOpaque(false);
        pnlBusqueda.add(txtBuscar, BorderLayout.CENTER);
        pnlBusqueda.add(btnBuscar, BorderLayout.EAST);

        // Opciones de servicios
        pnlOpcionesServicios = new JPanel(new GridLayout(0, 2, 10, 10));
        pnlOpcionesServicios.setOpaque(false);

        // Botones inferiores
        btnVolver = new JButton("Volver");
        btnGuardar = new JButton("Guardar");
        btnContinuar = new JButton("Continuar");

        btnVolver.addActionListener(e -> this.control.mostrarFolio());
        btnContinuar.addActionListener(e -> this.control.mostrarEquipo());

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnGuardar);
        pnlSur.add(btnContinuar);

        JPanel pnlSuperior = new JPanel(new BorderLayout(5, 10));
        pnlSuperior.setOpaque(false);

        pnlSuperior.add(lblEncabezado, BorderLayout.NORTH);
        pnlSuperior.add(pnlBusqueda, BorderLayout.CENTER);

        add(pnlSuperior, BorderLayout.NORTH);
        add(pnlOpcionesServicios, BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
        
        btnBuscar.addActionListener(e -> {
            if (!Validaciones.soloNumeros(txtBuscar)) {
                JOptionPane.showMessageDialog(this,
                        "Solo se permiten números.");
                return;
            }

            // Realizar la busqueda
        });
    }
    
    public void mostrarServicios(List<DTOServicio> servicios) {
        pnlOpcionesServicios.removeAll();
        for (DTOServicio servicio : servicios) {
            JCheckBox chk = new JCheckBox(servicio.getNombre() + (servicio.isEsAdicional() ? " (adicional)" : ""),
                    servicio.isSeleccionado());
            chk.setOpaque(false);
            chk.setToolTipText("<html>" + servicio.getDescripcion() + "<br>" + servicio.getObservaciones() + "</html>");
            pnlOpcionesServicios.add(chk);
        }
        pnlOpcionesServicios.revalidate();
        pnlOpcionesServicios.repaint();
    }
}
