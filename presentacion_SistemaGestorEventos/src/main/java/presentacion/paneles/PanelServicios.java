/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package presentacion.paneles;

import dto.DTOServicio;
import presentacion.control.ControlPresentacion;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelServicios extends JPanel {

    private final ControlPresentacion control;
    private JLabel lblEncabezado;
    private JPanel pnlOpcionesServicios;
    private JPanel pnlBusqueda;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnVolver, btnContinuar;

    public PanelServicios(ControlPresentacion control) {
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
        btnContinuar = new JButton("Continuar");

        btnVolver.addActionListener(e -> this.control.solicitarFolio());
        btnContinuar.addActionListener(e -> {
            if (!control.verificarSeleccionServicios()) {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "No hay servicios seleccionados. ¿Deseas continuar de todos modos?",
                        "Sin servicios",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            control.solicitarEquipos();
        });

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnContinuar);

        JPanel pnlSuperior = new JPanel(new BorderLayout(5, 10));
        pnlSuperior.setOpaque(false);

        pnlSuperior.add(lblEncabezado, BorderLayout.NORTH);
        pnlSuperior.add(pnlBusqueda, BorderLayout.CENTER);

        add(pnlSuperior, BorderLayout.NORTH);
        
        JPanel pnlContenedor = new JPanel(new BorderLayout());
        pnlContenedor.setOpaque(false);
        pnlContenedor.add(pnlOpcionesServicios, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(pnlContenedor);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
        
        add(pnlSur, BorderLayout.SOUTH);
        
        btnBuscar.addActionListener(e -> {
            if (!control.busquedaValida(txtBuscar)) {
                JOptionPane.showMessageDialog(this,
                        "Solo se permiten letras.");
                return;
            }

            buscarServicios(txtBuscar);
        });
    }
    
    public void solicitarServicios(List<DTOServicio> servicios) {
        pnlOpcionesServicios.removeAll();
        for (DTOServicio s : servicios) {
            JCheckBox chk = new JCheckBox(s.getNombre(), s.isSeleccionado());
            chk.setToolTipText(s.getDescripcion());
            chk.addActionListener(e -> control.seleccionarServicios(s.getIdServicio(), chk.isSelected()));
            pnlOpcionesServicios.add(chk);
        }
        pnlOpcionesServicios.revalidate();
        pnlOpcionesServicios.repaint();
    }
    
    public void buscarServicios(JTextField txt) {
        List<DTOServicio> servicios = control.consultarServiciosDisponibles();
        pnlOpcionesServicios.removeAll();
        for (DTOServicio s : servicios) {
            if(s.getNombre().toLowerCase().contains(txt.getText().toLowerCase())){
                JCheckBox chk = new JCheckBox(s.getNombre(), s.isSeleccionado()); 
                chk.setToolTipText(s.getDescripcion());
                chk.addActionListener(e -> control.seleccionarServicios(s.getIdServicio(), chk.isSelected()));
                pnlOpcionesServicios.add(chk);
            }
        }
        pnlOpcionesServicios.revalidate();
        pnlOpcionesServicios.repaint();
    }
}
