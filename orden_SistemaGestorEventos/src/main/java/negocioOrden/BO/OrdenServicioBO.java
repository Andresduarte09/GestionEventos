/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioOrden.BO;

/**
 *
 * @author Andres
 */
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCotizacion;
import dto.DTOEvento;
import dto.DTOOrdenServicio;
import dto.DTOServicio;
import dto.DTOVersionCotizacion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import negocioOrden.interfaces.IOrdenServicioBO;

public class OrdenServicioBO implements IOrdenServicioBO {

    private final AlmacenDatos almacen;

    public OrdenServicioBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public void registrarCotizacionEnAlmacen() {
        List<DTOCotizacion> cotizaciones = almacen.getCotizaciones();
        if (!cotizaciones.isEmpty()) {
            DTOCotizacion cotizacion = cotizaciones.get(cotizaciones.size() - 1);
            if (!almacen.getCotizaciones().contains(cotizacion)) {
                almacen.registrarCotizacion(cotizacion);
            }
        }
    }

    @Override
    public void generarOrdenServicio() {
        int nuevoId = almacen.getOrdenes().size() + 1;
        
        int idEvento = 1;
        if (!almacen.getEventos().isEmpty()) {
            DTOEvento ultimoEvento = almacen.getEventos().get(almacen.getEventos().size() - 1);
            idEvento = ultimoEvento.getIdEvento();
        }

        int idCotizacion = 1;
        if (!almacen.getCotizaciones().isEmpty()) {
            DTOCotizacion ultimaCotizacion = almacen.getCotizaciones().get(almacen.getCotizaciones().size() - 1);
            idCotizacion = ultimaCotizacion.getID();
        }

        List<DTOServicio> serviciosAceptados = new ArrayList<>();
        for (DTOServicio s : almacen.getServicios()) {
            if (s.isSeleccionado()) {
                serviciosAceptados.add(s);
            }
        }

        DTOOrdenServicio orden = new DTOOrdenServicio(
                nuevoId,
                idEvento,
                idCotizacion,
                LocalDate.now(),
                serviciosAceptados
        );

        almacen.registrarOrden(orden);
    }

    @Override
    public DTOOrdenServicio consultarOrdenPorId(int idOrden) {
        for (DTOOrdenServicio orden : almacen.getOrdenes()) {
            if (orden.getIdOrden() == idOrden) {
                return orden;
            }
        }
        return null;
    }

    @Override
    public List<DTOOrdenServicio> consultarTodasOrdenes() {
        return new ArrayList<>(almacen.getOrdenes());
    }
}
