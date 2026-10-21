/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Andre
 */
public class PanelServicios extends JPanel {

    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JPanel pnlOpcionesServicios;
    private JButton btnVolver, btnGuardar, btnContinuar;

    public PanelServicios(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Selección de Servicios", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        pnlOpcionesServicios = new JPanel(new GridLayout(0, 2, 10, 10));
        pnlOpcionesServicios.setOpaque(false);

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

        add(lblEncabezado, BorderLayout.NORTH);
        add(pnlOpcionesServicios, BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
    }
}
