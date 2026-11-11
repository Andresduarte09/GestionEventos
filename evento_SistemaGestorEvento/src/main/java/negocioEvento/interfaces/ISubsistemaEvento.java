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
    public DTODetalleSolicitud registrarRecurso(DTODetalleSolicitud datos);
    public DTORecurso consultarRecurso(int idRecurso);
    public DTOLocalEvento consultarLocal(int idLocalEvento);
}
