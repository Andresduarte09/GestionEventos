/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioCotizacion.gestor;

import dto.DTOCotizacion;
import dto.DTODetalleCotizacion;
import dto.DTOVersionCotizacion;
import com.mycompany.persistencia.AlmacenDatos;
import negocioCotizacion.BO.CotizacionBO;


/**
 *
 * @author lui
 */
public class GestorCotizacion {
    private final AlmacenDatos almacen;
    private final CotizacionBO cotizacionBO;
    
    public GestorCotizacion(){
        this.almacen = AlmacenDatos.getInstance();
        this.cotizacionBO = new CotizacionBO(this.almacen);
        
    }
    
    private DTOVersionCotizacion versionActual;

    public DTOCotizacion calcularCotizacion(int idPropuesta) {
        return cotizacionBO.calcularCotizacion(idPropuesta);
    }

    public DTOVersionCotizacion crearNuevaVersion(int idCotizacion) {
        return null;
    }

    public DTODetalleCotizacion crearDetalleCotizacion() {
        return null;
    }
}
