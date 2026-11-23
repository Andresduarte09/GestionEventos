/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion.vistas;

import dto.DTOEvento;
import presentacion.control.ControlPresentacion;

/**
 * SE MODIFICO ESTA CLASE, SI HAY ALGUN ERROR CHECAR LOGICA DE AQUI
 *
 * @author armen
 */
public class JFrameRevisarAnalisisEvaluacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFrameRevisarAnalisisEvaluacion.class.getName());
    private ControlPresentacion control;

    private DTOEvento evento;
    private java.util.List<dto.DTOLocalEvento> locales = new java.util.ArrayList<>();

    /**
     * Creates new form JFrameRevisarAnalisisEvaluacion
     */
    public JFrameRevisarAnalisisEvaluacion(ControlPresentacion control) {
        this.control = control;
        this.evento = control.getEvento();
        initComponents();
        this.setLocationRelativeTo(null);
        //para hacer que los campos de txt no se puedan modificar
        txtTipoEvento.setEditable(false);
        txtNumAsistentes.setEditable(false);
        txtFecha.setEditable(false);
        txtHora.setEditable(false);

        lblimagenUbicacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        //configuracion de la informacion del local
        
        txtEjemploInfo.setEditable(false);
        txtEjemploInfo.setLineWrap(true);
        txtEjemploInfo.setWrapStyleWord(true);
        txtEjemploInfo.setFont(lblUbicacionInfo.getFont());
        txtEjemploInfo.setOpaque(false);
        txtEjemploInfo.setBorder(null);
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setBorder(null);
        
        
        locales = control.consultarLocalesDisponibles();
        lblUbicacionInfo.removeAllItems();
        for (dto.DTOLocalEvento local : locales) {
            lblUbicacionInfo.addItem(local.getNombreLocal());
        }
        lblUbicacionInfo.addActionListener(e -> actualizarUbicacion());

        if (evento != null) {
            txtTipoEvento.setText(evento.getTipoEvento());
            txtNumAsistentes.setText(String.valueOf(evento.getNumAsistentes()));
            txtFecha.setText(String.valueOf(evento.getFechaEvento()));
            txtHora.setText(String.valueOf(evento.getHoraInicio()));
        }
        
        actualizarUbicacion();
    }

    private static final int ANCHO_IMAGEN = 350;
    private static final int ALTO_IMAGEN = 280;

    private void actualizarUbicacion() {
        int i = lblUbicacionInfo.getSelectedIndex();
        if (i < 0 || i >= locales.size()) {
            txtEjemploInfo.setText("No hay locales disponibles para este evento.");
            mostrarImagen(null);
            return;
        }
        dto.DTOLocalEvento l = locales.get(i);
        txtEjemploInfo.setText(
                "Tipo: " + l.getTipoLocal() + "\n"
                + "Capacidad: " + l.getCapacidad() + " personas\n"
                + "Dimensiones: " + l.getDimensionesLugar() + "\n"
                + "Espacios: " + l.getDistribucionEspacios() + "\n"
                + "Acceso: " + l.getCondicionesAcceso() + "\n"
                + "Restricciones: " + l.getRestriccionesLocal() + "\n"
                + "Precio: " + l.getPrecioLocal()+ "\n");
        mostrarImagen(rutaImagen(l.getNombreLocal()));
    }

    //--LO SIGUIENTE ES TEMPORAL LO MAS PROBABLE POR QUE AUN ME FALTAN AGREGAR --
    //              --IMAGENES PARA LOS DEMAS LOCALES--
    private String rutaImagen(String nombreLocal) {
        switch (nombreLocal) {
            case "Salón Los Ángeles":
                return "/imagenes/imagenSalonLosAngeles.png";
            case "Jardín de Eventos La Colibrí":
                return "/imagenes/imagenJardinLaColibri.png";
            default:
                return null;
     
        }
    }



    
    private void mostrarImagen(String ruta) {
        java.net.URL url = (ruta == null) ? null : getClass().getResource(ruta);
        if (url == null) {
            lblimagenUbicacion.setIcon(null);
            lblimagenUbicacion.setText("Sin imagen disponible");
            return;
        }
        javax.swing.ImageIcon original = new javax.swing.ImageIcon(url);
        int ancho = original.getIconWidth();
        int alto = original.getIconHeight();
        double escala = Math.min(1.0, Math.min((double) ANCHO_IMAGEN / ancho, (double) ALTO_IMAGEN / alto));
        java.awt.Image reducida = original.getImage().getScaledInstance(
                Math.max(1, (int) (ancho * escala)), Math.max(1, (int) (alto * escala)), java.awt.Image.SCALE_SMOOTH);
        lblimagenUbicacion.setText(null);
        lblimagenUbicacion.setIcon(new javax.swing.ImageIcon(reducida));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        lblTipoEvento = new javax.swing.JLabel();
        lblTipoEvento1 = new javax.swing.JLabel();
        txtTipoEvento = new javax.swing.JTextField();
        lblTipoEvento2 = new javax.swing.JLabel();
        lblTipoEvento3 = new javax.swing.JLabel();
        txtNumAsistentes = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        lblTipoEvento4 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTituloAnalisisEvaluacion = new javax.swing.JLabel();
        lblUbicacionInfo = new javax.swing.JComboBox<>();
        lblImagenUbicacion = new javax.swing.JLabel();
        lblimagenUbicacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHora = new javax.swing.JTextPane();
        lblHora = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEjemploInfo = new javax.swing.JTextArea();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

        lblTipoEvento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoEvento.setText("Tipo de evento:");

        lblTipoEvento1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoEvento1.setText("Num. Asistentes");

        lblTipoEvento2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoEvento2.setText("Ubicación");

        lblTipoEvento3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoEvento3.setText("Fecha");

        txtFecha.addActionListener(this::txtFechaActionPerformed);

        lblTipoEvento4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoEvento4.setText("Información");

        btnConfirmar.setBackground(new java.awt.Color(101, 255, 101));
        btnConfirmar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(this::btnConfirmarActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        lblTituloAnalisisEvaluacion.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        lblTituloAnalisisEvaluacion.setText("Análisis y Evaluación de espacio");
        lblTituloAnalisisEvaluacion.setMaximumSize(new java.awt.Dimension(400, 40));

        lblUbicacionInfo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", " " }));
        lblUbicacionInfo.setBorder(null);
        lblUbicacionInfo.addActionListener(this::lblUbicacionInfoActionPerformed);

        lblimagenUbicacion.setText("jLabel1");

        jScrollPane1.setViewportView(txtHora);

        lblHora.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHora.setText("Hora");

        txtEjemploInfo.setColumns(20);
        txtEjemploInfo.setRows(5);
        jScrollPane2.setViewportView(txtEjemploInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNumAsistentes, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTipoEvento1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTipoEvento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTipoEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addComponent(lblTipoEvento3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUbicacionInfo, 0, 212, Short.MAX_VALUE)
                        .addComponent(lblTipoEvento2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1))
                    .addComponent(lblHora))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblImagenUbicacion)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTipoEvento4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblimagenUbicacion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfirmar))
                        .addGap(60, 60, 60))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(lblTituloAnalisisEvaluacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblTituloAnalisisEvaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(lblimagenUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTipoEvento4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImagenUbicacion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(lblTipoEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTipoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(lblTipoEvento1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumAsistentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lblTipoEvento2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUbicacionInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lblTipoEvento3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(lblHora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        control.iniciarConfigurarPropuesta();
        this.dispose();
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        control.cancelarRevisarAnalisisEvaluacion();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void lblUbicacionInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblUbicacionInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUbicacionInfoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblImagenUbicacion;
    private javax.swing.JLabel lblTipoEvento;
    private javax.swing.JLabel lblTipoEvento1;
    private javax.swing.JLabel lblTipoEvento2;
    private javax.swing.JLabel lblTipoEvento3;
    private javax.swing.JLabel lblTipoEvento4;
    private javax.swing.JLabel lblTituloAnalisisEvaluacion;
    private javax.swing.JComboBox<String> lblUbicacionInfo;
    private javax.swing.JLabel lblimagenUbicacion;
    private javax.swing.JTextArea txtEjemploInfo;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextPane txtHora;
    private javax.swing.JTextField txtNumAsistentes;
    private javax.swing.JTextField txtTipoEvento;
    // End of variables declaration//GEN-END:variables
}
