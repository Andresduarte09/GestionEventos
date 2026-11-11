
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.BO;

import negocioCotizacion.interfaces.ICotizacionBO;
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOVersionCotizacion;
import java.util.List;

/**
 *
 * @author lui
 */
public class CotizacionBO implements ICotizacionBO {

    private final AlmacenDatos almacen;

    public CotizacionBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTOCotizacion guardar(DTOCotizacion cotizacion) {
        if (cotizacion == null || cotizacion.getID() <= 0) {
            throw new IllegalArgumentException("La cotización debe tener un ID válido.");
        }

        if (consultar(cotizacion.getID()) != null) {
            throw new IllegalArgumentException("Ya existe una cotización con ese ID.");
        }

        almacen.registrarCotizacion(cotizacion);
        return cotizacion;
    }

    @Override
    public DTOCotizacion consultar(int idCotizacion) {
        for (DTOCotizacion cotizacion : almacen.getCotizaciones()) {
            if (cotizacion.getID() == idCotizacion) {
                return cotizacion;
            }
        }

        return null;
    }

    @Override
    public void actualizar(DTOCotizacion cotizacion) {
        if (cotizacion == null) {
            throw new IllegalArgumentException("La cotización no puede ser nula.");
        }

        DTOCotizacion existente = consultar(cotizacion.getID());

        if (existente == null) {
            throw new IllegalArgumentException("La cotización no existe.");
        }

        existente.setFecha(cotizacion.getFecha());
        existente.setEstadoGeneral(cotizacion.getEstadoGeneral());
        existente.setReglaDescuento(cotizacion.getReglaDescuento());
    }

    @Override
    public DTOVersionCotizacion guardarVersion(DTOVersionCotizacion version) {
        if (version == null || version.getID() <= 0) {
            throw new IllegalArgumentException("La versión debe tener un ID válido.");
        }

        if (consultarVersion(version.getID()) != null) {
            throw new IllegalArgumentException("Ya existe una versión con ese ID.");
        }

        almacen.registrarVersionCotizacion(version);
        return version;
    }

    @Override
    public DTOVersionCotizacion consultarVersion(int idVersion) {
        for (DTOVersionCotizacion version : almacen.getVersionesCotizacion()) {
            if (version.getID() == idVersion) {
                return version;
            }
        }

        return null;
    }

    @Override
    public DTODetalleCotizacion guardarDetalle(DTODetalleCotizacion detalle) {
        if (detalle == null || detalle.getID() <= 0) {
            throw new IllegalArgumentException("El detalle debe tener un ID válido.");
        }

        for (DTODetalleCotizacion existente : almacen.getDetallesCotizacion()) {
            if (existente.getID() == detalle.getID()) {
                throw new IllegalArgumentException("Ya existe un detalle con ese ID.");
            }
        }

        almacen.getDetallesCotizacion().add(detalle);
        return detalle;
    }
}
