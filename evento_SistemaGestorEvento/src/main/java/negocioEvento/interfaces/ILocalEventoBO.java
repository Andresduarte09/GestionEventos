/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;
import dto.DTOLocalEvento;
import java.util.List;


/**
 *
 * @author Andres
 */
public interface ILocalEventoBO {
    //faltan?
    public DTOLocalEvento guardar(DTOLocalEvento local);
    public DTOLocalEvento consultar(int idLocalEvento);
    public List<DTOLocalEvento> obtenerLocalFiltrado(String tipoLocal, int asistentes);
}
