/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import dto.DTOEquipo;
import presentacion.control.controlPresentacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
public class PanelEquipo extends JPanel {

    private static final int COL_BORRAR = 2;
    private static final Color GRIS = new Color(220, 220, 220);

    private final controlPresentacion control;
    private JLabel lblEncabezado, lblLista;
    private JTable tblEquipos;
    private final JButton btnBasura = new JButton("🗑️");
    private JButton btnConsultarInventario, btnConsultarProveedor, btnVolver, btnContinuar;

    // Equipos que se muestran en la tabla (misma posición que cada fila)
    private final List<DTOEquipo> seleccionados = new ArrayList<>();

    public PanelEquipo(controlPresentacion control) {
        this.control = control;
        setLayout(new BorderLayout(15, 15));
        setBackground(GRIS);

        lblEncabezado = new JLabel("Selección De Equipos", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        lblLista = new JLabel("Lista de equipos seleccionados", JLabel.LEFT);

        btnBasura.setFocusPainted(false);

        tblEquipos = new JTable();
        tblEquipos.setRowHeight(28);
        tblEquipos.setShowGrid(false);
        tblEquipos.setBackground(GRIS);
        tblEquipos.getTableHeader().setReorderingAllowed(false);

        // Clic sobre el botón de basura: quita el equipo de la lista
        tblEquipos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblEquipos.rowAtPoint(e.getPoint());
                int col = tblEquipos.columnAtPoint(e.getPoint());
                if (fila >= 0 && col == COL_BORRAR) {
                    eliminarEquipo(fila);
                }
            }
        });
        
        // Cursor de mano al pasar sobre el botón de basura
        tblEquipos.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean sobreBasura = tblEquipos.rowAtPoint(e.getPoint()) >= 0
                        && tblEquipos.columnAtPoint(e.getPoint()) == COL_BORRAR;
                tblEquipos.setCursor(Cursor.getPredefinedCursor(sobreBasura ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
            }
        });

        btnConsultarInventario = new JButton("Consultar Inventario");
        btnConsultarProveedor = new JButton("Consultar Proveedor");

        btnConsultarInventario.addActionListener(e -> this.control.mostrarInventario());
        btnConsultarProveedor.addActionListener(e -> this.control.mostrarProveedor());

        JScrollPane scroll = new JScrollPane(tblEquipos);
        scroll.getViewport().setBackground(GRIS);

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        pnlCentro.setOpaque(false);
        pnlCentro.add(lblLista, BorderLayout.NORTH);
        pnlCentro.add(scroll, BorderLayout.CENTER);

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

    // Muestra solo los equipos ya seleccionados (cantidad mayor a 0)
    public void mostrarEquipos(List<DTOEquipo> equipos) {
        seleccionados.clear();
        DefaultTableModel modelo = modeloSoloLectura(new String[]{"Equipo", "Cantidad", ""});
        for (DTOEquipo equipo : equipos) {
            if (equipo.getCantidadSeleccionada() > 0) {
                seleccionados.add(equipo);
                modelo.addRow(new Object[]{equipo.getNombre(), equipo.getCantidadSeleccionada(), ""});
            }
        }
        tblEquipos.setModel(modelo);

        // La columna de borrar: angosta y con un botón (el texto lo cambias por tu icono)
        tblEquipos.getColumnModel().getColumn(COL_BORRAR).setMinWidth(100);
        tblEquipos.getColumnModel().getColumn(COL_BORRAR).setMaxWidth(100);
        tblEquipos.getColumnModel().getColumn(COL_BORRAR).setCellRenderer((t, v, sel, foco, fila, col) -> btnBasura);
    }

    // Quita el equipo de la tabla y lo deja en cantidad 0
    private void eliminarEquipo(int fila) {
        seleccionados.get(fila).setCantidadSeleccionada(0);
        seleccionados.remove(fila);
        ((DefaultTableModel) tblEquipos.getModel()).removeRow(fila);
    }

    // Modelo de tabla solo de lectura
    private static DefaultTableModel modeloSoloLectura(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }
}