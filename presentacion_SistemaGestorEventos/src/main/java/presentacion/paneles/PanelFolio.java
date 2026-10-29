/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOCliente;
import dto.DTOEvento;
import presentacion.control.controlPresentacion;
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
    
    public void mostrarFolio(DTOCliente cliente, DTOEvento evento) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Cliente\n");
        sb.append("Nombre: ").append(cliente.getNombre()).append("\n");
        sb.append("Correo: ").append(cliente.getCorreo()).append("\n");
        sb.append("Teléfono: ").append(cliente.getTelefono()).append("\n");
        sb.append("Teléfono alterno: ").append(valor(cliente.getTelefonoAlt())).append("\n\n");
        
        sb.append("Evento\n");
        sb.append("Tipo: ").append(evento.getTipoEvento()).append("\n");
        sb.append("Fecha: ").append(evento.getFechaEvento()).append("\n");
        sb.append("Horario: ").append(evento.getHoraInicio()).append(" a ").append(evento.getHoraFin() == null ? "-" : evento.getHoraFin()).append("\n");
        sb.append("Ubicación: ").append(evento.getUbicacion()).append("\n");
        sb.append("Asistentes: ").append(evento.getNumAsistentes()).append("\n");
        sb.append("Necesidades: ").append(valor(evento.getNecesidadesIniciales())).append("\n");
        sb.append("Observaciones: ").append(valor(evento.getObservaciones())).append("\n");
        txtDetalles.setText(sb.toString());
        txtDetalles.setCaretPosition(0);
    }
   
    private static String valor(String texto) {
        return texto == null || texto.isBlank() ? "-" : texto;
    }
}
