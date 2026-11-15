
package negocioCotizacion.gestor;

import dto.DTOCotizacion;
import dto.DTOInventario;
import dto.DTOPropuesta;
import dto.DTOVersionCotizacion;
import java.time.LocalTime;
import java.util.List;

public class PruebaCotizacion {

    public static void main(String[] args) {
        GestorCotizacion gestor = new GestorCotizacion();

        DTOPropuesta propuesta = new DTOPropuesta();
        propuesta.setIdPropuesta(45001);

        DTOCotizacion cotizacion = gestor.obtenerOCrearCotizacion(propuesta);
        int idCotizacion = cotizacion.getID();

        DTOVersionCotizacion version1 = gestor.crearNuevaVersion(idCotizacion);
        int idVersion1 = version1.getID();

        gestor.crearDetalleCotizacion(idVersion1, LocalTime.of(8, 0), List.of(new DTOInventario()), 5000f);

        if (Math.abs(version1.getTotal() - 5800f) > 0.01) {
            throw new AssertionError("El total de la version 1 es incorrecto.");
        }

        DTOVersionCotizacion version2 = gestor.crearNuevaVersion(idCotizacion);
        int idVersion2 = version2.getID();

        gestor.crearDetalleCotizacion(idVersion2, LocalTime.of(10, 0), List.of(new DTOInventario()), 8000f);

        if (Math.abs(version2.getTotal() - 9280f) > 0.01) {
            throw new AssertionError("El total de la version 2 es incorrecto.");
        }

        DTOVersionCotizacion ultima = gestor.consultarUltimaVersion(idCotizacion);

        if (ultima == null || ultima.getID() != idVersion2) {
            throw new AssertionError("No se recupero la ultima version.");
        }

        if (Math.abs(version1.getTotal() - 5800f) > 0.01) {
            throw new AssertionError("La version 1 fue modificada.");
        }

        if (!gestor.hayDetalles(idVersion1) || !gestor.hayDetalles(idVersion2)) {
            throw new AssertionError("Alguna version perdio sus detalles.");
        }

        System.out.println("Version 1: $" + version1.getTotal());
        System.out.println("Version 2: $" + version2.getTotal());
        System.out.println("Ultima version: " + ultima.getNumero());
        System.out.println("Versiones independientes: correcto.");
        System.out.println("Prueba superada.");
    }
}
