/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocioEvento.interfaces;
import dto.DTOCliente;
import dto.DTODetalleSolicitud;
import dto.DTOEvento;
import dto.DTOLevantamiento;
import dto.DTOLocalEvento;
import dto.DTORecurso;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Andres
 */
public interface ISubsistemaEvento {
    public DTOCliente ingresarTelefono(String telefono);
    public DTOCliente registrarCliente(DTOCliente datos);
    public DTOEvento registrarDatosEvento(DTOEvento datos);
    public DTOEvento consultarInformacionEvento(int idEvento);
    public DTOLevantamiento registrarLevantamiento(DTOLevantamiento datos);
    public List<DTORecurso> obtenerCatalogoRecursos();
    public DTODetalleSolicitud registrarRecurso(DTODetalleSolicitud datos);
    public void guardarRecursos(int idEvento);
    public DTORecurso consultarRecurso(int idRecurso);
    public DTOLocalEvento consultarLocal(int idLocalEvento);
    public List<DTOLocalEvento> obtenerLocalesDisponibles(DTOEvento evento, String tipoLocal);
    public void actualizarRecursosTemporal(String categoria, Map<Integer, Integer> cantidades);
    public Map<Integer, Integer> obtenerRecursosSeleccionados();
}
