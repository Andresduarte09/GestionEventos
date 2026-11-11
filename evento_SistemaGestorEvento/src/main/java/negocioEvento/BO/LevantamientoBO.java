/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOLevantamiento;
import negocioEvento.interfaces.ILevantamientoBO;


/**
 *
 * @author Ale
 */
public class LevantamientoBO implements ILevantamientoBO {

    private final AlmacenDatos almacen;

    public LevantamientoBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTOLevantamiento guardar(DTOLevantamiento levantamiento) {
        almacen.getLevantamientos().add(levantamiento);
        return levantamiento;
    }

    @Override
    public DTOLevantamiento consultarPorEvento(int idEvento) {
        for (DTOLevantamiento l : almacen.getLevantamientos()) {
            return l; 
        }
        return null;
    }
}
