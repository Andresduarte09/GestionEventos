/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioOrden.interfaces;

import java.time.LocalDate;

/**
 *
 * @author Andres
 */
import dto.DTOOrdenServicio;
import dto.DTOServicio;
import java.util.List;

public interface IOrdenServicioBO {
    void generarOrdenServicio();
    void registrarCotizacionEnAlmacen();
    DTOOrdenServicio consultarOrdenPorId(int idOrden);
    List<DTOOrdenServicio> consultarTodasOrdenes();
}

