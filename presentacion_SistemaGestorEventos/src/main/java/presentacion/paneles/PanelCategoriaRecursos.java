/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import presentacion.control.controlPresentacion;

/**
 * Diseño común de los paneles de recursos (Comida, Entretenimiento, etc.),
 * según el Figma: título, combo "Seleccionar:", elementos con imagen y contador (+ / -)
 * y flecha de regreso. Cada panel concreto solo indica su título, su carpeta de imágenes
 * y sus elementos.
 *
 * @author andres
 */
public abstract class PanelCategoriaRecursos extends JPanel {

    private static final Color FONDO = new Color(217, 217, 217);
    private static final Color OSCURO = new Color(30, 30, 40);
    private static final String TODOS = "Todos";
    private static final int ANCHO_IMAGEN = 160;
    private static final int ALTO_IMAGEN = 120;

    private final controlPresentacion control;
    private final String carpetaImagenes;
    private final JComboBox<String> cmbTipo = new JComboBox<>();
    private final JPanel pnlElementos = new JPanel(new GridLayout(0, 2, 16, 14));
    private final List<TarjetaElemento> tarjetas = new ArrayList<>();

    //constructor que arma la UI base de todas las categorias, en el cual las subclases aportan los datos
    protected PanelCategoriaRecursos(controlPresentacion control, String titulo, String carpetaImagenes, String[][] elementos){
        this.control = control;
        this.carpetaImagenes = carpetaImagenes;
        setLayout(new BorderLayout());
        setBackground(FONDO);
        setPreferredSize(new Dimension(900, 700));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel lblSeleccionar = new JLabel("Seleccionar:");
        lblSeleccionar.setFont(new Font("Arial", Font.BOLD, 16));
        cmbTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel pnlTipo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        pnlTipo.setOpaque(false);
        pnlTipo.add(lblSeleccionar);
        pnlTipo.add(cmbTipo);

        JPanel pnlNorte = new JPanel(new BorderLayout(0, 8));
        pnlNorte.setOpaque(false);
        pnlNorte.setBorder(BorderFactory.createEmptyBorder(10, 10, 4, 10));
        pnlNorte.add(lblTitulo, BorderLayout.NORTH);
        pnlNorte.add(pnlTipo, BorderLayout.CENTER);

        for(String[] elemento : elementos){
            tarjetas.add(new TarjetaElemento(elemento[0], elemento[1], cargarImagen(elemento[0])));
        }
        List<String> tipos = new ArrayList<>();
        for(TarjetaElemento tarjeta : tarjetas){
            if (!tipos.contains(tarjeta.tipo)){
                tipos.add(tarjeta.tipo);
            }
        }
        cmbTipo.addItem(TODOS);
        for(String tipo : tipos){
            cmbTipo.addItem(tipo);
        }
        cmbTipo.addActionListener(e -> mostrarElementos());

        pnlElementos.setOpaque(false);
        JPanel pnlContenido = new JPanel(new BorderLayout());
        pnlContenido.setOpaque(false);
        pnlContenido.setBorder(BorderFactory.createEmptyBorder(6, 24, 6, 24));
        pnlContenido.add(pnlElementos, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(pnlContenido);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton btnRegresar = new JButton("\u2190");
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 24));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(new Color(25, 25, 25));
        btnRegresar.setOpaque(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setPreferredSize(new Dimension(56, 46));
        btnRegresar.addActionListener(e -> this.control.mostrarMenuRecursos());

        JPanel pnlSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlSur.setOpaque(false);
        pnlSur.add(btnRegresar);

        add(pnlNorte, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(pnlSur, BorderLayout.SOUTH);

        mostrarElementos();
    }

    //muestra los recursos por su tipo, viene por defecto Todos, pero se puede cambiar para filtrar
    //y elegir unicamente de un tipo en especifico
    private void mostrarElementos(){
        String tipo = (String) cmbTipo.getSelectedItem();
        pnlElementos.removeAll();
        for (TarjetaElemento tarjeta : tarjetas){
            if (tipo == null || TODOS.equals(tipo) || tipo.equals(tarjeta.tipo)){
                pnlElementos.add(tarjeta);
            }
        }
        pnlElementos.revalidate();
        pnlElementos.repaint();
    }

    //se encarga de obtener la cantidad de recursos que se han seleccionado 
    public Map<String, Integer> getCantidades(){
        Map<String, Integer> cantidades = new LinkedHashMap<>();
        for (TarjetaElemento tarjeta : tarjetas){
            if (tarjeta.cantidad > 0){
                cantidades.put(tarjeta.nombre, tarjeta.cantidad);
            }
        }
        return cantidades;
    }

    //busca la imagen para luego cargarla, de no encontrar solo da un lugar vacio con Sin imagen (luego se añadiran)
    private Icon cargarImagen(String nombre){
        URL url = getClass().getResource("/imagenes/" + carpetaImagenes + "/" + aArchivo(nombre) + ".png");
        if (url != null){
            ImageIcon original = new ImageIcon(url);
            int ancho = original.getIconWidth();
            int alto = original.getIconHeight();
            if (ancho > 0 && alto > 0){
                double escala = Math.min((double) ANCHO_IMAGEN / ancho, (double) ALTO_IMAGEN / alto);
                Image escalada = original.getImage().getScaledInstance(
                        Math.max(1, (int) (ancho * escala)), Math.max(1, (int) (alto * escala)), Image.SCALE_SMOOTH);
                return new ImageIcon(escalada);
            }
        }
        BufferedImage marcador = new BufferedImage(ANCHO_IMAGEN, ALTO_IMAGEN, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = marcador.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, ANCHO_IMAGEN, ALTO_IMAGEN);
        g.setColor(Color.DARK_GRAY);
        g.drawString("Sin imagen", 22, ALTO_IMAGEN / 2 + 4);
        g.dispose();
        return new ImageIcon(marcador);
    }

    //sirve solo para encontrar las imágenes sin escribir a mano el nombre de cada archivo
    //convierte el nombre que se muestra  el nombre del archivo para evitar problemas con acentos y que sea mas consistente
    //es provisional hasta que se conecte a una BD y no haya complicaciones con los nombres a la hora de manipular imagenes
    private static String aArchivo(String texto){
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinAcentos.toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("^_|_$", "");
    }

    //tarjeta que encapsula el recurso y sus datos para la manipulacion del asesor
    private static class TarjetaElemento extends JPanel{

        private static final int MAXIMO = 99;
        private final String nombre;
        private final String tipo;
        private final JLabel lblCantidad = new JLabel("0", JLabel.CENTER);
        private final BotonMasMenos btnMas = new BotonMasMenos(true);
        private final BotonMasMenos btnMenos = new BotonMasMenos(false);
        private int cantidad = 0;

        TarjetaElemento(String nombre, String tipo, Icon imagen){
            this.nombre = nombre;
            this.tipo = tipo;
            setOpaque(false);
            setLayout(new BorderLayout(8, 4));

            JLabel lblNombre = new JLabel(nombre, JLabel.CENTER);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 15));

            JLabel lblImagen = new JLabel(imagen, JLabel.CENTER);
            lblImagen.setPreferredSize(new Dimension(ANCHO_IMAGEN, ALTO_IMAGEN));

            lblCantidad.setFont(new Font("Arial", Font.BOLD, 16));
            lblCantidad.setOpaque(true);
            lblCantidad.setBackground(Color.WHITE);
            lblCantidad.setPreferredSize(new Dimension(36, 26));

            JPanel pnlControles = new JPanel(new GridLayout(3, 1, 0, 4));
            pnlControles.setOpaque(false);
            pnlControles.add(lblCantidad);
            pnlControles.add(btnMas);
            pnlControles.add(btnMenos);

            JPanel pnlLateral = new JPanel(new BorderLayout());
            pnlLateral.setOpaque(false);
            pnlLateral.add(pnlControles, BorderLayout.NORTH);

            btnMas.addActionListener(e -> cambiarCantidad(1));
            btnMenos.addActionListener(e -> cambiarCantidad(-1));

            add(lblNombre, BorderLayout.NORTH);
            add(lblImagen, BorderLayout.CENTER);
            add(pnlLateral, BorderLayout.EAST);

            actualizar();
        }

        private void cambiarCantidad(int cambio) {
            int nueva = cantidad + cambio;
            if (nueva < 0 || nueva > MAXIMO) {
                return;
            }
            cantidad = nueva;
            actualizar();
        }

        private void actualizar() {
            lblCantidad.setText(String.valueOf(cantidad));
            btnMenos.setEnabled(cantidad > 0);
            btnMas.setEnabled(cantidad < MAXIMO);
        }
    }

    //botones para agregar o restar recurso
    private static class BotonMasMenos extends JButton{

        private final boolean esMas;

        BotonMasMenos(boolean esMas){
            this.esMas = esMas;
            setPreferredSize(new Dimension(36, 30));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int diametro = Math.min(getWidth(), getHeight()) - 1;
            int x = (getWidth() - diametro) / 2;
            int y = (getHeight() - diametro) / 2;
            
            g2.setColor(isEnabled() ? OSCURO : new Color(150, 150, 150));
            g2.fillOval(x, y, diametro, diametro);       
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3f));
            
            int centroX = getWidth() / 2;
            int centroY = getHeight() / 2;
            
            g2.drawLine(centroX - 7, centroY, centroX + 7, centroY);
            
            if(esMas){
                g2.drawLine(centroX, centroY - 7, centroX, centroY + 7);
            }
            g2.dispose();
        }
    }
}