/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioPropuesta.BO;

import dto.DTOInventario;
import dto.DTOPropuesta;

/**
 *
 * @author Andres
 */
public interface IPropuestaBO {
    public DTOPropuesta guardar(DTOPropuesta propuesta);
    
    public DTOPropuesta consultar(int idPropuesta);
    
    public DTOInventario consultarSeleccion(int idEvento);
    
    public void actualizarSeleccion(DTOInventario cambios);
}
