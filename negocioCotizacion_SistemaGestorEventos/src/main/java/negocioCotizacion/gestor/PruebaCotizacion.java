
package negocioCotizacion.gestor;

import dto.DTOCotizacion;
import dto.DTOInventario;
import dto.DTOVersionCotizacion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PruebaCotizacion {

    public static void main(String[] args) {
        GestorCotizacion gestor = new GestorCotizacion();

        DTOCotizacion cotizacion = new DTOCotizacion();
        cotizacion.setID(9001);
        cotizacion.setIdPropuesta(15);
        cotizacion.setFecha(LocalDate.now());
        cotizacion.setEstadoGeneral("Pendiente");
        gestor.guardarCotizacion(cotizacion);

        DTOCotizacion encontrada = gestor.consultarCotizacionPorPropuesta(15);

        if (encontrada == null || encontrada.getID() != 9001) {
            throw new AssertionError("No se encontro la cotizacion de la propuesta.");
        }

        System.out.println("Cotizacion encontrada: " + encontrada.getID());

        DTOVersionCotizacion version = gestor.crearNuevaVersion(9001);
        int idVersion = version.getID();

        gestor.crearDetalleCotizacion(idVersion, LocalTime.of(8, 0), List.of(new DTOInventario()), 10000f);

        System.out.println("Hay detalles: " + gestor.hayDetalles(idVersion));

        version = gestor.actualizarDescuentoVersion(idVersion, 10f);

        double subtotal = version.getSubtotal();
        double descuento = gestor.calcularDescuento(subtotal, version.getDescuento());
        double iva = gestor.calcularIVA(subtotal - descuento);

        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Descuento: $" + descuento);
        System.out.println("IVA: $" + iva);
        System.out.println("Total: $" + version.getTotal());

        if (Math.abs(version.getTotal() - 10440f) > 0.01) {
            throw new AssertionError("El total calculado es incorrecto.");
        }

        System.out.println("Prueba de cotizacion superada.");
    }
}
