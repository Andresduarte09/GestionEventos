/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion.control;

/**
 *
 * @author lui
 */
import presentacion.vistas.JFrameInicioSesion;
public class SistemaGestorEventos {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(()-> {
            JFrameInicioSesion inicio = new JFrameInicioSesion();
            inicio.setVisible(true);
        });
    }
}
