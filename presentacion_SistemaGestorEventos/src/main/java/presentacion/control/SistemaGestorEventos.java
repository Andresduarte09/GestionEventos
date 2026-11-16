/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion.control;

import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;
import negocioPropuesta.fachada.FachadaPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;

public class SistemaGestorEventos {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            AlmacenDatos almacen = AlmacenDatos.getInstance();

            ProveedorExterno proveedor = new ProveedorExterno();

            ISubsistemaPropuesta propuesta = new FachadaPropuesta(almacen,proveedor);

            ControlPresentacion control = new ControlPresentacion(propuesta, almacen, proveedor);

            control.iniciarSesion();
        });
    }
}