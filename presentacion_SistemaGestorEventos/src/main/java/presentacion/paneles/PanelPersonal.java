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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dto.DTOPersonal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import presentacion.validaciones.Validaciones;

public class PanelPersonal extends JPanel {

    private static final int COL_CANTIDAD = 1;

    private final controlPresentacion control;
    private JLabel lblEncabezado;
    private JTable tblPersonal;
    private JButton btnVolver, btnContinuar;

    // Personal que se muestra en la tabla (misma posición que cada fila)
    private final List<DTOPersonal> listaPersonal = new ArrayList<>();

    public PanelPersonal(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Personal Requerido", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        tblPersonal = new JTable();
        tblPersonal.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
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

    // Muestra el personal del almacén; la cantidad se puede escribir en la tabla
    public void mostrarPersonal(List<DTOPersonal> personal) {
        listaPersonal.clear();
        listaPersonal.addAll(personal);

        DefaultTableModel modelo = modeloCantidadEditable(
                new String[]{"Especialidad", "Cantidad", "Horas de montaje", "Horas de operación", "Horas de desmontaje"});
        for (DTOPersonal p : personal) {
            modelo.addRow(new Object[]{p.getEspecialidad(), p.getCantidad(), p.getHorasMontaje(),
                p.getHorasOperacion(), p.getHorasDesmontaje()});
        }

        // Cada vez que el usuario termina de escribir en una celda, se revisa y se guarda
        modelo.addTableModelListener(e -> guardarCantidad(modelo, e.getFirstRow(), e.getColumn()));
        tblPersonal.setModel(modelo);
    }

    private void guardarCantidad(DefaultTableModel modelo, int fila, int columna) {
        if (columna != COL_CANTIDAD) {
            return;
        }
        DTOPersonal personal = listaPersonal.get(fila);
        String texto = modelo.getValueAt(fila, columna).toString();

        if (Validaciones.noEsNegativo(texto)) {
            personal.setCantidad(Integer.parseInt(texto.trim()));
        } else {
            JOptionPane.showMessageDialog(this, "La cantidad no debe ser un número negativo");
            modelo.setValueAt(personal.getCantidad(), fila, columna); // regresa el valor anterior
        }
    }

    // Modelo de tabla donde solo se puede editar la columna Cantidad
    private static DefaultTableModel modeloCantidadEditable(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == COL_CANTIDAD;
            }
        };
    }
}