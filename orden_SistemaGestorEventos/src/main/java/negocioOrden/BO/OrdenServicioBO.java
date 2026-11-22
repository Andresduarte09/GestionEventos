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
    public void generarOrdenServicio() {
        generarOrdenServicio(-1, -1, null);
    }

    @Override
    public void generarOrdenServicio(int idEvento, int idCotizacion, List<String> nombresServiciosAceptados) {
        int nuevoId = almacen.getOrdenes().size() + 1;

        int eventoFinal = idEvento;
        if (eventoFinal <= 0 && !almacen.getEventos().isEmpty()) {
            DTOEvento ultimoEvento = almacen.getEventos().get(almacen.getEventos().size() - 1);
            eventoFinal = ultimoEvento.getIdEvento();
        }

        int cotizacionFinal = idCotizacion;
        if (cotizacionFinal <= 0 && !almacen.getCotizaciones().isEmpty()) {
            DTOCotizacion ultimaCotizacion = almacen.getCotizaciones().get(almacen.getCotizaciones().size() - 1);
            cotizacionFinal = ultimaCotizacion.getID();
        }

        List<DTOServicio> serviciosFinales = new ArrayList<>();
        if (almacen.getServicios() != null) {
            for (DTOServicio s : almacen.getServicios()) {
                if (s == null) {
                    continue;
                }
                if (nombresServiciosAceptados != null && !nombresServiciosAceptados.isEmpty()) {
                    if (nombresServiciosAceptados.contains(s.getNombre())) {
                        serviciosFinales.add(s);
                    }
                } else if (s.isSeleccionado()) {
                    serviciosFinales.add(s);
                }
            }
        }

        DTOOrdenServicio orden = new DTOOrdenServicio(
                nuevoId,
                eventoFinal,
                cotizacionFinal,
                LocalDate.now(),
                serviciosFinales
        );

        almacen.registrarOrden(orden);
    }

    @Override
    public void registrarCotizacionEnAlmacen() {
        if (!almacen.getCotizaciones().isEmpty()) {
            DTOCotizacion ultima = almacen.getCotizaciones().get(almacen.getCotizaciones().size() - 1);
            if (ultima != null && !almacen.getCotizaciones().contains(ultima)) {
                almacen.registrarCotizacion(ultima);
            }
        }
    }

    @Override
    public DTOOrdenServicio consultarOrdenPorId(int idOrden) {
        for (DTOOrdenServicio orden : almacen.getOrdenes()) {
            if (orden != null && orden.getIdOrden() == idOrden) {
                return orden;
            }
        }
        return null;
    }

    @Override
    public List<DTOOrdenServicio> consultarTodasOrdenes() {
        return new ArrayList<>(almacen.getOrdenes());
    }

    @Override
    public List<String> obtenerServiciosCotizados() {
        List<String> servicios = new ArrayList<>();
        if (almacen == null || almacen.getServicios() == null) {
            return servicios;
        }
        for (DTOServicio s : almacen.getServicios()) {
            if (s != null && s.isSeleccionado()) {
                servicios.add(s.getNombre());
            }
        }
        return servicios;
    }
}
