/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOEquipo;
import presentacion.control.ControlPresentacion;
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

/**
 *
 * @author Andre
 */
public class PanelProveedor extends JPanel {

    private final ControlPresentacion control;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnCerrar, btnUsarCantidad;
    private JTable tblConsulta;

    public PanelProveedor(ControlPresentacion control) {
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

        btnCerrar.addActionListener(e -> this.control.solicitarEquipos());
        btnUsarCantidad.addActionListener(e -> usarCantidad());

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
            if (!control.busquedaValida(txtBuscar)) {
                JOptionPane.showMessageDialog(this,
                        "El nombre solo debe contener letras y espacios.");
                return;
            }

            List<DTOEquipo> resultado = control.buscarEquiposExternos(txtBuscar.getText());
            solicitarEquiposExternos(resultado);
            if (resultado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron equipos del proveedor con ese nombre.");
            }
        });
    }
    
    public void solicitarEquiposExternos(List<DTOEquipo> equipos) {
        DefaultTableModel modelo = modeloSoloLectura(new String[]{"ID", "Equipo", "Disponibilidad", "Cantidad seleccionada"});
        for (DTOEquipo equipo : equipos) {
            modelo.addRow(new Object[]{equipo.getIdEquipo(), equipo.getNombre(), equipo.getDisponibilidad(), equipo.getCantidadSeleccionada()});
        }
        tblConsulta.setModel(modelo);
    }

    public void usarCantidad() {
        int fila = tblConsulta.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un equipo de la tabla.");
            return;
        }
        int idEquipo = (int) tblConsulta.getValueAt(fila, 0);
        String nombre = String.valueOf(tblConsulta.getValueAt(fila, 1));
        Object disponibilidad = tblConsulta.getValueAt(fila, 2);

        String texto = JOptionPane.showInputDialog(this,
                "Cantidad de " + nombre + " (disponibles: " + disponibilidad + "):");
        if (texto == null) {
            return;
        }
        if (!control.esCantidadEquipoValida(idEquipo, texto)) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un entero entre 1 y " + disponibilidad + ".");
            return;
        }
        control.seleccionarEquipos(idEquipo, Integer.parseInt(texto.trim()));
        control.solicitarEquipos();
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
