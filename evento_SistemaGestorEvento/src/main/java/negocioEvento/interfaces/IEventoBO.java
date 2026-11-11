/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;

import dto.DTOEvento;

/**
 *
 * @author Andres
 */
public interface IEventoBO {
    public DTOEvento guardar(DTOEvento evento);
    public DTOEvento consultar(int idEvento);
    public void actualizar(DTOEvento evento);
}
