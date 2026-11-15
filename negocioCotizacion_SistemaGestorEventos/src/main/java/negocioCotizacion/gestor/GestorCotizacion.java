/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.gestor;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOInventario;
import dto.DTOVersionCotizacion;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import negocioCotizacion.BO.CotizacionBO;
import negocioCotizacion.interfaces.ICotizacionBO;
import java.util.ArrayList;
import dto.DTOPropuesta;
import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
/**
 *
 * @author lui
 */
public class GestorCotizacion {

    private final AlmacenDatos almacen;
    private final ICotizacionBO cotizacionBO;
    private DTOVersionCotizacion versionActual;

    public GestorCotizacion() {
        this.almacen = AlmacenDatos.getInstance();
        this.cotizacionBO = new CotizacionBO(this.almacen);
    }

    public DTOCotizacion calcularCotizacion(int idPropuesta) {
        if (idPropuesta <= 0) {
            throw new IllegalArgumentException("El ID de la propuesta debe ser mayor que cero.");
        }

        throw new UnsupportedOperationException("Pendiente conectar F4 con la propuesta de F3.");
    }

    public DTOCotizacion guardarCotizacion(DTOCotizacion cotizacion) {
        return cotizacionBO.guardar(cotizacion);
    }

    public DTOCotizacion consultarCotizacion(int idCotizacion) {
        return cotizacionBO.consultar(idCotizacion);
    }
    
    public DTOCotizacion consultarCotizacionPorPropuesta(int idPropuesta) {
        return cotizacionBO.consultarPorPropuesta(idPropuesta);
    }

    public void actualizarCotizacion(DTOCotizacion cotizacion) {
        cotizacionBO.actualizar(cotizacion);
    }

    public DTOVersionCotizacion guardarVersion(DTOVersionCotizacion version) {
        return cotizacionBO.guardarVersion(version);
    }

    public DTOVersionCotizacion consultarVersion(int idVersion) {
        return cotizacionBO.consultarVersion(idVersion);
    }

    public DTODetalleCotizacion guardarDetalle(DTODetalleCotizacion detalle) {
        return cotizacionBO.guardarDetalle(detalle);
    }
      
    public boolean hayDetalles(int idVersion) {
        return !cotizacionBO.consultarDetallesVersion(idVersion).isEmpty();
    }

    public double calcularSubtotal(List<DTODetalleCotizacion> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La cotización debe contener detalles.");
        }

        double subtotal = 0;

        for (DTODetalleCotizacion detalle : detalles) {
            if (detalle == null || !Float.isFinite(detalle.getImporte()) || detalle.getImporte() < 0) {
                throw new IllegalArgumentException("Existe un detalle con importe inválido.");
            }

            subtotal += detalle.getImporte();
        }

        return subtotal;
    }

    public double calcularSubtotalVersion(int idVersion) {
        if (cotizacionBO.consultarVersion(idVersion) == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
    }

        List<DTODetalleCotizacion> detalles = cotizacionBO.consultarDetallesVersion(idVersion);
        return calcularSubtotal(detalles);
    }

    public DTOVersionCotizacion calcularImportesVersion(int idVersion) {
        DTOVersionCotizacion version = cotizacionBO.consultarVersion(idVersion);

        if (version == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }
        
        if (!hayDetalles(idVersion)) {
            throw new IllegalStateException("No hay detalles para calcular esta versión.");
        }

        double subtotal = calcularSubtotalVersion(idVersion);
        double descuento = calcularDescuento(subtotal, version.getDescuento());
        double base = subtotal - descuento;
        double iva = calcularIVA(base);
        double total = calcularTotal(subtotal, descuento, iva);

        version.setSubtotal((float) subtotal);
        version.setTotal((float) total);

        return version;
    }
    
    public DTOVersionCotizacion actualizarDescuentoVersion(int idVersion, float porcentaje) {
        DTOVersionCotizacion version = cotizacionBO.consultarVersion(idVersion);

        if (version == null) {
            throw new IllegalArgumentException("La versión indicada no existe.");
        }

        if (!Float.isFinite(porcentaje) || porcentaje < 0 || porcentaje > 10) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 10%.");
        }

        if (!hayDetalles(idVersion)) {
            throw new IllegalStateException("No hay detalles para calcular esta versión.");
        }

        version.setDescuento(porcentaje);
        return calcularImportesVersion(idVersion);
    }


    public double calcularDescuento(double subtotal, double porcentaje) {
        if (!Double.isFinite(subtotal) || subtotal < 0 || !Double.isFinite(porcentaje)
                || porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Subtotal o porcentaje inválido.");
        }

        return subtotal * porcentaje / 100;
    }

    public double calcularIVA(double base) {
        if (!Double.isFinite(base) || base < 0) {
            throw new IllegalArgumentException("La base del IVA no es válida.");
        }

        return base * 0.16;
    }

    public double calcularTotal(double subtotal, double descuento, double iva) {
        if (!Double.isFinite(subtotal) || !Double.isFinite(descuento) || !Double.isFinite(iva)
                || subtotal < 0 || descuento < 0 || iva < 0 || descuento > subtotal) {
            throw new IllegalArgumentException("Los importes no son válidos.");
        }

        return subtotal - descuento + iva;
    }

    public DTOVersionCotizacion crearNuevaVersion(int idCotizacion) {
        DTOCotizacion cotizacion = cotizacionBO.consultar(idCotizacion);

        if (cotizacion == null) {
            throw new IllegalArgumentException("No existe la cotización indicada.");
        }

        int ultimoNumero = 0;

        for (DTOVersionCotizacion version : almacen.getVersionesCotizacion()) {
            if (version.getIdCotizacion() == idCotizacion) {
                ultimoNumero = Math.max(ultimoNumero, version.getNumero());
            }
        }

        int nuevoID = 1;

        for (DTOVersionCotizacion version : almacen.getVersionesCotizacion()) {
            nuevoID = Math.max(nuevoID, version.getID() + 1);
        }

        DTOVersionCotizacion nuevaVersion = new DTOVersionCotizacion(
                nuevoID, ultimoNumero + 1, LocalDate.now(), "Pendiente", "Nueva versión", 0f, 0f, 0f);

        nuevaVersion.setIdCotizacion(idCotizacion);
        versionActual = cotizacionBO.guardarVersion(nuevaVersion);

        return versionActual;
    }

    public DTODetalleCotizacion crearDetalleCotizacion(int idVersion, LocalTime horario,
            List<DTOInventario> inventario, float importe) {

        if (cotizacionBO.consultarVersion(idVersion) == null) {
            throw new IllegalArgumentException("La version indicada no existe.");
        }

        if (horario == null || inventario == null || inventario.isEmpty()) {
            throw new IllegalArgumentException("Faltan datos para crear el detalle.");
        }

        if (!Float.isFinite(importe) || importe < 0) {
            throw new IllegalArgumentException("El importe no es valido.");
        }

        int nuevoID = 1;

        for (DTODetalleCotizacion existente : almacen.getDetallesCotizacion()) {
            nuevoID = Math.max(nuevoID, existente.getID() + 1);
        }

        DTODetalleCotizacion detalle = new DTODetalleCotizacion();
        detalle.setID(nuevoID);
        detalle.setIdVersion(idVersion);
        detalle.setHorarioFases(horario);
        detalle.setInventario(inventario);
        detalle.setImporte(importe);

        DTODetalleCotizacion guardado = cotizacionBO.guardarDetalle(detalle);
        calcularImportesVersion(idVersion);

        return guardado;
    }

    public DTOVersionCotizacion consultarUltimaVersion(int idCotizacion) {
        if (cotizacionBO.consultar(idCotizacion) == null) {
            throw new IllegalArgumentException("La cotizacion indicada no existe.");
        }

        DTOVersionCotizacion ultimaVersion = null;

        for (DTOVersionCotizacion version : almacen.getVersionesCotizacion()) {
            if (version.getIdCotizacion() == idCotizacion) {
                if (ultimaVersion == null || version.getNumero() > ultimaVersion.getNumero()) {
                    ultimaVersion = version;
                }
            }
        }

        return ultimaVersion;
    }
    
    public DTOCotizacion obtenerOCrearCotizacion(DTOPropuesta propuesta) {
        if (propuesta == null || propuesta.getIdPropuesta() <= 0) {
            throw new IllegalArgumentException("La propuesta debe tener un ID valido.");
        }

        DTOCotizacion existente = consultarCotizacionPorPropuesta(propuesta.getIdPropuesta());

        if (existente != null) {
            return existente;
        }

        int nuevoID = 1;

        for (DTOCotizacion cotizacion : almacen.getCotizaciones()) {
            nuevoID = Math.max(nuevoID, cotizacion.getID() + 1);
        }

        DTOCotizacion nuevaCotizacion = new DTOCotizacion();
        nuevaCotizacion.setID(nuevoID);
        nuevaCotizacion.setIdPropuesta(propuesta.getIdPropuesta());
        nuevaCotizacion.setFecha(LocalDate.now());
        nuevaCotizacion.setEstadoGeneral("Pendiente");

        return cotizacionBO.guardar(nuevaCotizacion);
    }
    
    public boolean validarInventario(DTOInventario inventario) {
        if (inventario == null) {
            return false;
        }

        boolean tieneRecursos = false;

        if (inventario.getServicios() != null) {
            for (DTOServicio servicio : inventario.getServicios()) {
                if (servicio == null || servicio.getIdServicio() <= 0) {
                    return false;
                }
                if (servicio.isSeleccionado()) {
                    tieneRecursos = true;
                }
            }
        }

        if (inventario.getEquipos() != null) {
            for (DTOEquipo equipo : inventario.getEquipos()) {
                if (equipo == null || equipo.getIdEquipo() <= 0 || equipo.getCantidadSeleccionada() < 0) {
                    return false;
                }
                if (equipo.getCantidadSeleccionada() > equipo.getDisponibilidad()) {
                    return false;
                }
                if (equipo.getCantidadSeleccionada() > 0) {
                    tieneRecursos = true;
                }
            }
        }

        if (inventario.getPersonal() != null) {
            for (DTOPersonal personal : inventario.getPersonal()) {
                if (personal == null || personal.getIdEspecialidad() <= 0 || personal.getCantidad() < 0) {
                    return false;
                }
                if (personal.getHorasMontaje() < 0 || personal.getHorasOperacion() < 0
                        || personal.getHorasDesmontaje() < 0) {
                    return false;
                }
                if (personal.getCantidad() > 0) {
                    tieneRecursos = true;
                }
            }
        }

        return tieneRecursos;
    }

    public List<DTOVersionCotizacion> consultarVersionesCotizacion(int idCotizacion) {
        return cotizacionBO.consultarVersionesCotizacion(idCotizacion);
    }

}
