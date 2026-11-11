/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;

import dto.DTOLevantamiento;

/**
 *
 * @author Andres
 */
public interface ILevantamientoBO {
    public DTOLevantamiento guardar(DTOLevantamiento levantamiento);
    public DTOLevantamiento consultarPorEvento(int idEvento);
}
