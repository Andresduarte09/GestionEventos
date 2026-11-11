/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;

import dto.DTORecurso;

/**
 *
 * @author Andres   
 */
public interface IRecursoBO {
    public DTORecurso crearRecurso(DTORecurso recurso);
    public DTORecurso actualizarRecurso(int idRecurso); 
    public DTORecurso consultarRecurso(int idRecurso);  
}
