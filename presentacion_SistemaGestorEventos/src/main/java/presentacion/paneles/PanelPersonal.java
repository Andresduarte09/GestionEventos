/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOPersonal;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre
 */
public class PanelPersonal extends JPanel {

    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JTable tblPersonal;
    private JButton btnVolver, btnContinuar;

    public PanelPersonal(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Personal Requerido", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        tblPersonal = new JTable();

        btnVolver = new JButton("Volver");
        btnContinuar = new JButton("Continuar");

        btnVolver.addActionListener(e -> this.control.mostrarEquipo());
        btnContinuar.addActionListener(e -> this.control.mostrarResumen());

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnContinuar);

        add(lblEncabezado, BorderLayout.NORTH);
        add(new JScrollPane(tblPersonal), BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
    }
    
    //Muestra el personal del almacén con sus horas de montaje, operación y desmontaje
    public void mostrarPersonal(List<DTOPersonal> personal) {
        DefaultTableModel modelo = modeloSoloLectura(
                new String[]{"Especialidad", "Cantidad", "Horas de montaje", "Horas de operación", "Horas de desmontaje"});
        for (DTOPersonal p : personal) {
            modelo.addRow(new Object[]{p.getEspecialidad(), p.getCantidad(), p.getHorasMontaje(),
                p.getHorasOperacion(), p.getHorasDesmontaje()});
        }
        tblPersonal.setModel(modelo);
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