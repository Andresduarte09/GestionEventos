/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOLocalEvento;
import java.util.ArrayList;
import java.util.List;
import negocioEvento.interfaces.ILocalEventoBO;


/**
 *
 * @author Ale
 */
public class LocalEventoBO implements ILocalEventoBO {
    
    private final AlmacenDatos almacen;

    public LocalEventoBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTOLocalEvento guardar(DTOLocalEvento local) {
        almacen.getLocalesEvento().add(local);
        return local;
    }

    @Override
    public DTOLocalEvento consultar(int idLocalEvento) {
        for (DTOLocalEvento l : almacen.getLocalesEvento()) {
            if (l.getIdLocalEvento() == idLocalEvento) {
                return l;
            }
        }
        return null;
    }

    @Override
    public List<DTOLocalEvento> obtenerLocalFiltrado(String tipoLocal, int asistentes) {
        List<DTOLocalEvento> filtrados = new ArrayList<>();
        for (DTOLocalEvento l : almacen.getLocalesEvento()) {
            
            //POR EL MOMENTO SOLO HAY 2 FILTROS, PERO CONSIDERAR AGREGAR MAS
            if (l.getTipoLocal().equalsIgnoreCase(tipoLocal) && l.getCapacidad() >= asistentes) {
                filtrados.add(l);
            }
        }
        return filtrados;
    }
}
