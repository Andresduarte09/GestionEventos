/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOEquipo;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import presentacion.validaciones.Validaciones;

/**
 *
 * @author Andre
 */
public class PanelProveedor extends JPanel {

    private final controlPresentacion control;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnCerrar, btnUsarCantidad;
    private JTable tblConsulta;

    public PanelProveedor(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        JLabel lblTitulo = new JLabel("Consulta de Inventario de Proveedor Externo", JLabel.LEFT);
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

        btnCerrar.addActionListener(e -> this.control.mostrarEquipo());
        btnUsarCantidad.addActionListener(e -> this.control.mostrarEquipo());

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
        
        btnBuscar.addActionListener(e -> {
            if (!Validaciones.soloLetras(txtBuscar)) {
                JOptionPane.showMessageDialog(this,
                        "El nombre solo debe contener letras y espacios.");
                return;
            }

            // Realizar búsqueda de proveedor
        });
    }
    
    //Muestra los equipos del proveedor externo (cuando el almacén los tenga)
    public void mostrarEquiposProveedor(List<DTOEquipo> equipos) {
        DefaultTableModel modelo = modeloSoloLectura(new String[]{"Equipo", "Disponibilidad", "Cantidad seleccionada"});
        for (DTOEquipo equipo : equipos) {
            modelo.addRow(new Object[]{equipo.getNombre(), equipo.getDisponibilidad(), equipo.getCantidadSeleccionada()});
        }
        tblConsulta.setModel(modelo);
    }
 
    //Modelo de tabla solo de lectura
    private static javax.swing.table.DefaultTableModel modeloSoloLectura(String[] columnas) {
        return new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }
}

