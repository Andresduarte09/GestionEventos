/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;

import dto.DTOCliente;

/**
 *
 * @author Andres
 */
public interface IClienteBO {
    public DTOCliente guardar(DTOCliente cliente);
    public DTOCliente consultarPorTelefono(String telefono);
}
