/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.interfaces;
import dto.DTOReserva;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author Andres
 */
public interface IReservaBO {
    public List<DTOReserva> consultar(LocalDate fecha);
    public void guardar(DTOReserva reserva);
}
    