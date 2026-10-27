/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Andre
 */
public class PanelEquipo extends JPanel {

    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JTable tblEquipos;
    private JButton btnConsultarInventario, btnConsultarProveedor, btnVolver, btnContinuar;

    public PanelEquipo(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Selección De Equipos", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        tblEquipos = new JTable();

        btnConsultarInventario = new JButton("Consultar Inventario");
        btnConsultarProveedor = new JButton("Consultar Proveedor");

        btnConsultarInventario.addActionListener(e -> this.control.mostrarInventario());
        btnConsultarProveedor.addActionListener(e -> this.control.mostrarProveedor());

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

        btnVolver.addActionListener(e -> this.control.mostrarServicios());
        btnContinuar.addActionListener(e -> this.control.mostrarPersonal());

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnContinuar);

        add(lblEncabezado, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
    }
}
