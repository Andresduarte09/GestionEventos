/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion.control;

/**
 *
 * @author lui
 */

public class SistemaGestorEventos {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(()-> {
            controlPresentacion control = new controlPresentacion();
            control.iniciarSesion();
        });
    }
}
