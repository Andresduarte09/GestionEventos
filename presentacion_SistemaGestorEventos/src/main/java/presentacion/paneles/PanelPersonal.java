
package presentacion.paneles;

import presentacion.control.ControlPresentacion;
import dto.DTOPersonal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.TableModelEvent;

public class PanelPersonal extends JPanel {

    private static final int COL_CANTIDAD = 1;
    private static final int ANCHO_BOTON = 30;

    private final ControlPresentacion control;

    private JLabel lblEncabezado;
    private JTable tblPersonal;
    private JButton btnVolver, btnContinuar;

    private DefaultTableModel modelo;

    private final List<DTOPersonal> listaPersonal = new ArrayList<>();
    private final List<Integer> cantidadesValidas = new ArrayList<>();

    private boolean actualizandoModelo = false;

    public PanelPersonal(ControlPresentacion control) {
        this.control = control;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(220, 220, 220));

        lblEncabezado = new JLabel("Personal Requerido", JLabel.LEFT);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 16));

        tblPersonal = new JTable();
        tblPersonal.setRowHeight(36);
        tblPersonal.setFillsViewportHeight(true);

        tblPersonal.putClientProperty(
                "terminateEditOnFocusLost", Boolean.TRUE
        );

        tblPersonal.setToolTipText(
                "Utiliza + y - para ajustar la cantidad, "
                + "o doble clic para escribirla"
        );

        configurarBotonesCantidad();

        btnVolver = new JButton("Volver");
        btnContinuar = new JButton("Continuar");

        btnVolver.addActionListener(e -> {
            finalizarEdicion();
            this.control.solicitarEquipos();
        });

        btnContinuar.addActionListener(e -> {
            finalizarEdicion();

            if (!control.verificarSeleccionPersonal()) {
                int opcion = JOptionPane.showConfirmDialog(
                        this,
                        "No hay personal asignado. "
                        + "¿Deseas continuar de todos modos?",
                        "Sin personal",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            control.obtenerResumen();
        });

        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);

        pnlSur.add(btnVolver);
        pnlSur.add(btnContinuar);

        add(lblEncabezado, BorderLayout.NORTH);
        add(new JScrollPane(tblPersonal), BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);
    }

    public void solicitarPersonal(List<DTOPersonal> personal) {

        listaPersonal.clear();
        cantidadesValidas.clear();

        listaPersonal.addAll(personal);

        modelo = modeloCantidadEditable(new String[]{
            "Especialidad",
            "Cantidad",
            "Horas de montaje",
            "Horas de operación",
            "Horas de desmontaje"
        });

        for (DTOPersonal p : personal) {

            cantidadesValidas.add(p.getCantidad());

            modelo.addRow(new Object[]{
                p.getEspecialidad(),
                p.getCantidad(),
                p.getHorasMontaje(),
                p.getHorasOperacion(),
                p.getHorasDesmontaje()
            });
        }

        modelo.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                guardarCantidad(
                        e.getFirstRow(),
                        e.getColumn()
                );
            }
        });

        tblPersonal.setModel(modelo);

        tblPersonal.getColumnModel()
                .getColumn(COL_CANTIDAD)
                .setCellRenderer(new CantidadRenderer());

        tblPersonal.getColumnModel()
                .getColumn(COL_CANTIDAD)
                .setPreferredWidth(140);

        tblPersonal.getColumnModel()
                .getColumn(COL_CANTIDAD)
                .setMinWidth(120);

        DefaultCellEditor editor = new DefaultCellEditor(
                new JTextField()
        );

        editor.setClickCountToStart(2);

        tblPersonal.getColumnModel()
                .getColumn(COL_CANTIDAD)
                .setCellEditor(editor);
    }

    // Detecta los clics sobre los botones + y -
    private void configurarBotonesCantidad() {

        tblPersonal.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() != 1) {
                    return;
                }

                int filaVista = tblPersonal.rowAtPoint(e.getPoint());
                int columnaVista = tblPersonal.columnAtPoint(e.getPoint());

                if (filaVista < 0 || columnaVista < 0) {
                    return;
                }

                int columnaModelo = tblPersonal
                        .convertColumnIndexToModel(columnaVista);

                if (columnaModelo != COL_CANTIDAD) {
                    return;
                }

                int filaModelo = tblPersonal
                        .convertRowIndexToModel(filaVista);

                Rectangle celda = tblPersonal.getCellRect(
                        filaVista, columnaVista, true
                );

                int posicionX = e.getX() - celda.x;

                if (posicionX < ANCHO_BOTON) {
                    cambiarCantidad(filaModelo, -1);

                } else if (posicionX >= celda.width - ANCHO_BOTON) {
                    cambiarCantidad(filaModelo, 1);
                }
            }
        });
    }

    // Aumenta o disminuye una cantidad
    private void cambiarCantidad(int fila, int cambio) {

        if (fila < 0 || fila >= cantidadesValidas.size()) {
            return;
        }

        int cantidadActual = cantidadesValidas.get(fila);

        if (cambio < 0 && cantidadActual == 0) {
            return;
        }

        if (cambio > 0 && cantidadActual == Integer.MAX_VALUE) {
            return;
        }

        int nuevaCantidad = cantidadActual + cambio;

        modelo.setValueAt(
                nuevaCantidad,
                fila,
                COL_CANTIDAD
        );
    }

    // Valida y guarda la cantidad en el controlador
    private void guardarCantidad(int fila, int columna) {

        if (actualizandoModelo
                || columna != COL_CANTIDAD
                || fila < 0
                || fila >= listaPersonal.size()) {
            return;
        }

        DTOPersonal personal = listaPersonal.get(fila);

        Object valor = modelo.getValueAt(fila, columna);

        int cantidad;

        try {
            cantidad = Integer.parseInt(
                    valor.toString().trim()
            );

            if (cantidad < 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException | NullPointerException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa un número entero mayor o igual a 0.",
                    "Cantidad inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            restaurarCantidad(fila);
            return;
        }

        int cantidadAnterior = cantidadesValidas.get(fila);

        if (cantidad == cantidadAnterior) {
            normalizarCantidad(fila, cantidad);
            return;
        }

        boolean actualizado = control.actualizarCantidadPersonal(
                personal.getIdEspecialidad(),
                String.valueOf(cantidad)
        );

        if (actualizado) {

            cantidadesValidas.set(fila, cantidad);
            normalizarCantidad(fila, cantidad);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la cantidad.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );

            restaurarCantidad(fila);
        }
    }

    private void restaurarCantidad(int fila) {
        normalizarCantidad(
                fila,
                cantidadesValidas.get(fila)
        );
    }

    private void normalizarCantidad(int fila, int cantidad) {

        actualizandoModelo = true;

        try {
            modelo.setValueAt(
                    cantidad,
                    fila,
                    COL_CANTIDAD
            );
        } finally {
            actualizandoModelo = false;
        }
    }

    private void finalizarEdicion() {

        if (tblPersonal.isEditing()) {
            tblPersonal.getCellEditor().stopCellEditing();
        }
    }

    // Únicamente Cantidad es editable
    private static DefaultTableModel modeloCantidadEditable(
            String[] columnas) {

        return new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == COL_CANTIDAD;
            }
        };
    }

    // Dibuja los botones dentro de la columna Cantidad
    private static class CantidadRenderer extends JPanel
            implements TableCellRenderer {

        private final JButton btnMenos;
        private final JButton btnMas;
        private final JLabel lblCantidad;

        public CantidadRenderer() {

            setLayout(new BorderLayout(4, 0));

            btnMenos = new JButton("-");
            btnMas = new JButton("+");

            lblCantidad = new JLabel("0", SwingConstants.CENTER);
            lblCantidad.setFont(new Font("Arial", Font.BOLD, 14));

            btnMenos.setPreferredSize(
                    new Dimension(ANCHO_BOTON, 28)
            );

            btnMas.setPreferredSize(
                    new Dimension(ANCHO_BOTON, 28)
            );

            btnMenos.setMargin(new Insets(0, 0, 0, 0));
            btnMas.setMargin(new Insets(0, 0, 0, 0));

            add(btnMenos, BorderLayout.WEST);
            add(lblCantidad, BorderLayout.CENTER);
            add(btnMas, BorderLayout.EAST);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable tabla,
                Object valor,
                boolean seleccionado,
                boolean tieneFoco,
                int fila,
                int columna) {

            lblCantidad.setText(
                    valor == null ? "0" : valor.toString()
            );

            setBackground(
                    seleccionado
                    ? tabla.getSelectionBackground()
                    : tabla.getBackground()
            );

            lblCantidad.setForeground(
                    seleccionado
                    ? tabla.getSelectionForeground()
                    : tabla.getForeground()
            );

            btnMenos.setEnabled(!"0".equals(lblCantidad.getText()));

            return this;
        }
    }
}
