/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOCliente;
import dto.DTOEquipo;
import dto.DTOEvento;
import dto.DTOPersonal;
import dto.DTOServicio;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import presentacion.vistas.JFrameConfigurarPropuesta;

/**
 *
 * @author Andre
 */
public class PanelResumen extends JPanel {
    
    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JTextArea txtResumenPropuesta, txtComentarios;
    private JButton btnVolver, btnEnviarCotizacion;
    private JFrameConfigurarPropuesta configurarPropuesta;

    public PanelResumen(controlPresentacion control) {
        this.control = control;
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

        btnVolver.addActionListener(e -> this.control.mostrarPersonal());

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnEnviarCotizacion);

        add(lblEncabezado, BorderLayout.NORTH);
        add(pnlCentro, BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
        
        btnEnviarCotizacion.addActionListener(e -> this.control.iniciarResumenCotizacion());
        
    }
    //Muestra un resumen con la información que ya existe en los DTO (sin calcular precios)
    public void mostrarResumen(DTOCliente cliente, DTOEvento evento, List<DTOServicio> servicios,
            List<DTOEquipo> equipos, List<DTOPersonal> personal) {
        StringBuilder sb = new StringBuilder();
        if (cliente != null) {
            sb.append("Cliente: ").append(cliente.getNombre()).append("\n");
        }
        if (evento != null) {
            sb.append("Evento: ").append(evento.getTipoEvento()).append(" - ").append(evento.getFechaEvento()).append("\n");
            sb.append("Ubicación: ").append(evento.getUbicacion()).append("\n");
            sb.append("Asistentes: ").append(evento.getNumAsistentes()).append("\n");
        }
        sb.append("\nServicios seleccionados:\n");
        for (DTOServicio s : servicios) {
            if (s.isSeleccionado()) {
                sb.append("  - ").append(s.getNombre()).append(s.isEsAdicional() ? " (adicional)" : "").append("\n");
            }
        }
        sb.append("\nEquipo seleccionado:\n");
        for (DTOEquipo e : equipos) {
            if (e.getCantidadSeleccionada() > 0) {
                sb.append("  - ").append(e.getNombre()).append(" x").append(e.getCantidadSeleccionada()).append("\n");
            }
        }
        sb.append("\nPersonal:\n");
        for (DTOPersonal p : personal) {
            sb.append("  - ").append(p.getEspecialidad()).append(" x").append(p.getCantidad()).append("\n");
        }
        txtResumenPropuesta.setText(sb.toString());
        txtResumenPropuesta.setCaretPosition(0);
    }
    
}
