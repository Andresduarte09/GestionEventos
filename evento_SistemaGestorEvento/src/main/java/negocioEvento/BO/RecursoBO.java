/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTORecurso;
import negocioEvento.interfaces.IRecursoBO;


/**
 *
 * @author Andres
 */
public class RecursoBO implements IRecursoBO {

    private final AlmacenDatos almacen;

    public RecursoBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTORecurso crearRecurso(DTORecurso recurso) {
        almacen.getRecursos().add(recurso);
        return recurso;
    }

    @Override
    public DTORecurso consultarRecurso(int idRecurso) {
        for (DTORecurso r : almacen.getRecursos()) {
            if (r.getIdRecurso() == idRecurso) {
                return r;
            }
        }
        return null;
    }
}
