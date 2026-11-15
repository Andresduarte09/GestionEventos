
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
import java.util.ArrayList;
import java.util.ArrayList;
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
            throw new IllegalArgumentException("La cotizacion debe tener un ID valido.");
        }

        if (cotizacion.getIdPropuesta() <= 0) {
            throw new IllegalArgumentException("La cotizacion debe tener una propuesta valida.");
        }

        if (consultar(cotizacion.getID()) != null) {
            throw new IllegalArgumentException("Ya existe una cotizacion con ese ID.");
        }

        if (consultarPorPropuesta(cotizacion.getIdPropuesta()) != null) {
            throw new IllegalArgumentException("Ya existe una cotizacion para esta propuesta.");
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
    public DTOCotizacion consultarPorPropuesta(int idPropuesta) {
        if (idPropuesta <= 0) {
            throw new IllegalArgumentException("El ID de la propuesta debe ser mayor que cero.");
        }

        for (DTOCotizacion cotizacion : almacen.getCotizaciones()) {
            if (cotizacion.getIdPropuesta() == idPropuesta) {
                return cotizacion;
            }
        }

        return null;
    }
 
    @Override
    public void actualizar(DTOCotizacion cotizacion) {
        if (cotizacion == null) {
            throw new IllegalArgumentException("La cotizacion no puede ser nula.");
        }

        DTOCotizacion existente = consultar(cotizacion.getID());

        if (existente == null) {
            throw new IllegalArgumentException("La cotizacion no existe.");
        }

        if (cotizacion.getIdPropuesta() <= 0) {
            throw new IllegalArgumentException("La propuesta debe tener un ID valido.");
        }

        DTOCotizacion asociada = consultarPorPropuesta(cotizacion.getIdPropuesta());

        if (asociada != null && asociada.getID() != cotizacion.getID()) {
            throw new IllegalArgumentException("La propuesta ya tiene otra cotizacion asociada.");
        }

        existente.setFecha(cotizacion.getFecha());
        existente.setEstadoGeneral(cotizacion.getEstadoGeneral());
        existente.setReglaDescuento(cotizacion.getReglaDescuento());
        existente.setIdPropuesta(cotizacion.getIdPropuesta());
    }

    @Override
    public DTOVersionCotizacion guardarVersion(DTOVersionCotizacion version) {
        if (version == null || version.getID() <= 0) {
            throw new IllegalArgumentException("La version debe tener un ID valido.");
        }

        if (version.getIdCotizacion() <= 0 || consultar(version.getIdCotizacion()) == null) {
            throw new IllegalArgumentException("La cotizacion asociada no existe.");
        }

        if (version.getNumero() <= 0) {
            throw new IllegalArgumentException("El numero de version debe ser mayor que cero.");
        }

        if (consultarVersion(version.getID()) != null) {
            throw new IllegalArgumentException("Ya existe una version con ese ID.");
        }

        for (DTOVersionCotizacion existente : almacen.getVersionesCotizacion()) {
            if (existente.getIdCotizacion() == version.getIdCotizacion()
                    && existente.getNumero() == version.getNumero()) {
                throw new IllegalArgumentException("Ya existe ese numero de version para la cotizacion.");
            }
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
            throw new IllegalArgumentException("El detalle debe tener un ID valido.");
        }

        if (detalle.getIdVersion() <= 0 || consultarVersion(detalle.getIdVersion()) == null) {
            throw new IllegalArgumentException("La version asociada al detalle no existe.");
        }

        if (detalle.getHorarioFases() == null || detalle.getInventario() == null || detalle.getInventario().isEmpty()) {
            throw new IllegalArgumentException("El detalle debe tener horario e inventario.");
        }

        if (!Float.isFinite(detalle.getImporte()) || detalle.getImporte() < 0) {
            throw new IllegalArgumentException("El importe del detalle no es valido.");
        }

        for (DTODetalleCotizacion existente : almacen.getDetallesCotizacion()) {
            if (existente.getID() == detalle.getID()) {
                throw new IllegalArgumentException("Ya existe un detalle con ese ID.");
            }
        }

        almacen.getDetallesCotizacion().add(detalle);
        return detalle;
    }

    @Override
    public List<DTODetalleCotizacion> consultarDetallesVersion(int idVersion) {
        List<DTODetalleCotizacion> detalles = new ArrayList<>();

        for (DTODetalleCotizacion detalle : almacen.getDetallesCotizacion()) {
            if (detalle.getIdVersion() == idVersion) {
                detalles.add(detalle);
            }
        }

        return detalles;
    }

    @Override
    public List<DTOVersionCotizacion> consultarVersionesCotizacion(int idCotizacion) {
        if (consultar(idCotizacion) == null) {
            throw new IllegalArgumentException("La cotizacion indicada no existe.");
        }

        List<DTOVersionCotizacion> versiones = new ArrayList<>();

        for (DTOVersionCotizacion version : almacen.getVersionesCotizacion()) {
            if (version.getIdCotizacion() == idCotizacion) {
                versiones.add(version);
            }
        }

        versiones.sort((v1, v2) -> Integer.compare(v1.getNumero(), v2.getNumero()));
        return versiones;
    }

}
