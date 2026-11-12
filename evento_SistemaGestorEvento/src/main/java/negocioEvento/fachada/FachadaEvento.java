/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.fachada;

import dto.*;
import java.util.List;
import java.util.Map;
import negocioEvento.gestor.GestorEventos;
import negocioEvento.interfaces.ISubsistemaEvento;


/**
 *
 * @author Andres
 */
public class FachadaEvento implements ISubsistemaEvento {
    
    private GestorEventos gestor;

    public FachadaEvento() {
        this.gestor = new GestorEventos();
    }

    @Override
    public DTOCliente ingresarTelefono(String telefono) {
        return gestor.obtenerCliente(telefono);
    }

    @Override
    public DTOCliente registrarCliente(DTOCliente datos) {
        return gestor.crearCliente(datos);
    }

    @Override
    public DTOEvento registrarDatosEvento(DTOEvento datos) {
        return gestor.crearEvento(datos);
    }

    @Override
    public DTOEvento consultarInformacionEvento(int idEvento) {
        return gestor.solicitarInformacionEvento(idEvento);
    }

    @Override
    public DTOLevantamiento registrarLevantamiento(DTOLevantamiento datos) {
        return gestor.registrarLevantamiento(datos);
    }
    @Override
    public List<DTORecurso> obtenerCatalogoRecursos() {
        return gestor.obtenerCatalogoRecursos();
    }

    @Override
public void guardarRecursos(int idEvento) {
    System.out.println("DEBUG: Llegué a la Fachada. Mandando al Gestor...");
    gestor.guardarRecursos(idEvento);
}
    
    @Override
    public DTODetalleSolicitud registrarRecurso(DTODetalleSolicitud datos) {
        return gestor.crearRecurso(datos);
    }

    @Override
    public DTORecurso consultarRecurso(int idRecurso) {
        return gestor.obtenerRecurso(idRecurso);
    }

    @Override
    public DTOLocalEvento consultarLocal(int idLocalEvento) {
        return gestor.consultarLocal(idLocalEvento);
    }
    
    @Override
    public List<DTOLocalEvento> obtenerLocalesDisponibles(DTOEvento evento, String tipoLocal) {
        return gestor.obtenerLocalesDisponibles(evento, tipoLocal);
    }
    
    @Override
    public void actualizarRecursosTemporal(String categoria, Map<Integer, Integer> cantidades) {
        gestor.actualizarRecursosTemporal(categoria, cantidades);
    }

    @Override
    public Map<Integer, Integer> obtenerRecursosSeleccionados() {
        return gestor.obtenerRecursosSeleccionados();
    }
}