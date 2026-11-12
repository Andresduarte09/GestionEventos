/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;
import dto.DTORecurso;
import java.util.List;


/**
 *
 * @author Andres   
 */
public interface IRecursoBO {
    public DTORecurso crearRecurso(DTORecurso recurso); 
    public DTORecurso consultarRecurso(int idRecurso); 
    public List<DTORecurso> obtenerTodosLosRecursos(); 
}
