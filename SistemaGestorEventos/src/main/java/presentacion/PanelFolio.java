/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Andre
 */
public class PanelFolio extends JPanel {

    private final controlPresentacion control;
    private JLabel lblTitulo;
    private JTextArea txtDetalles;
    private JButton btnContinuar;

    public PanelFolio(controlPresentacion control) {
        this.control = control;
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
        btnContinuar.addActionListener(e -> this.control.mostrarServicios());

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnContinuar);

        add(lblTitulo, BorderLayout.NORTH);
        add(new JScrollPane(txtDetalles), BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
    }
}
