/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;

import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOEvento;
import negocioEvento.interfaces.IEventoBO;


/**
 *
 * @author Ale
 */
public class EventoBO implements IEventoBO {

    private final AlmacenDatos almacen;

    public EventoBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTOEvento guardar(DTOEvento evento) {
        int nuevoId = almacen.getEventos().size() + 1;
        evento.setIdEvento(nuevoId);
        almacen.registrarEvento(evento);
        return evento;
    }

    @Override
    public DTOEvento consultar(int idEvento) {
        for (DTOEvento e : almacen.getEventos()) {
            if (e.getIdEvento() == idEvento) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void actualizar(DTOEvento evento) {
        for (int i = 0; i < almacen.getEventos().size(); i++) {
            if (almacen.getEventos().get(i).getIdEvento() == evento.getIdEvento()) {
                almacen.getEventos().set(i, evento);
                return;
            }
        }
    }
}
