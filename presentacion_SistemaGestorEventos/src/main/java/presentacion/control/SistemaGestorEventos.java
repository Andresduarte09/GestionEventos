/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion.control;

import com.mycompany.persistencia.AlmacenDatos;
import subsistemaInfraestructura.fachada.FachadaInfraestructura;
import subsistemaInfraestructura.interfaces.ISubsistemaInfraestructura;
import negocioPropuesta.BO.ConfigPropuestaBO;
import negocioPropuesta.BO.PropuestaBO;
import negocioPropuesta.control.GestorPropuesta;
import negocioPropuesta.fachada.FachadaPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;

public class SistemaGestorEventos {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            AlmacenDatos almacen = AlmacenDatos.getInstance();

            ISubsistemaInfraestructura proveedor = FachadaInfraestructura.getInstance();

            GestorPropuesta gestorPropuesta = new GestorPropuesta(ConfigPropuestaBO.getInstance(almacen, proveedor),PropuestaBO.getInstance(almacen, proveedor),almacen, proveedor);

            ISubsistemaPropuesta propuesta = new FachadaPropuesta(gestorPropuesta);

            ControlPresentacion control = new ControlPresentacion(propuesta, gestorPropuesta, almacen);

            control.iniciarSesion();
        });
    }
}