/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOReserva;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import negocioEvento.interfaces.IReservaBO;


/**
 *
 * @author Andres
 */
public class ReservaBO implements IReservaBO {
    
    private final AlmacenDatos almacen;

    public ReservaBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public List<DTOReserva> consultar(LocalDate fecha) {
        List<DTOReserva> reservasEncontradas = new ArrayList<>();
        for (DTOReserva r : almacen.getReservas()) {
            if (r.getFecha() != null && r.getFecha().equals(fecha)) {
                reservasEncontradas.add(r);
            }
        }
        return reservasEncontradas;
    }

    @Override
    public void guardar(DTOReserva reserva) {
        almacen.getReservas().add(reserva);
    }
}
