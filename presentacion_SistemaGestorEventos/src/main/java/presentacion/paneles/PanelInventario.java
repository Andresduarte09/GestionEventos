/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import presentacion.control.controlPresentacion;

/**
 *
 * @author Andre
 */
public class PanelInventario extends JPanel{
    
    private final controlPresentacion control;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnCerrar, btnUsarCantidad;
    private JTable tblConsulta;

    public PanelInventario(controlPresentacion control) {
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
    }
}
