/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.gestor;


import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCliente;
import dto.DTODetalleSolicitud;
import dto.DTOEvento;
import dto.DTOLevantamiento;
import dto.DTOLocalEvento;
import dto.DTORecurso;
import dto.DTOReserva;
import java.util.ArrayList;
import java.util.List;
import negocioEvento.BO.*;
import negocioEvento.interfaces.*;


/**
 *
 * @author Andres
 */
public class GestorEventos {
   
    private AlmacenDatos almacen;

    private IEventoBO iEventoBO;
    private IClienteBO iClienteBO;
    private IRecursoBO iRecursoBO;
    private ILocalEventoBO iLocalEventoBO;
    private ILevantamientoBO iLevantamientoBO;
    private IDetalleSolicitudBO iDetalleSolicitudBO;
    private IReservaBO iReservaBO;

    public GestorEventos() {
        //se hizo una inyeccion de dependencias con singleton aqui, de esta manera cuando se cambie la BD, solo
        //se ocupa cambiar el constructor y no todos los BO
        this.almacen = AlmacenDatos.getInstance();
        
        this.iEventoBO = new EventoBO(this.almacen);
        this.iClienteBO = new ClienteBO(this.almacen);
            this.iRecursoBO = new RecursoBO(this.almacen);
            this.iLocalEventoBO = new LocalEventoBO(this.almacen);
            this.iLevantamientoBO = new LevantamientoBO(this.almacen);
            this.iDetalleSolicitudBO = new DetalleSolicitudBO(this.almacen);
            this.iReservaBO = new ReservaBO(this.almacen);
        }

    public DTOCliente obtenerCliente(String telefono) {
        return iClienteBO.consultarPorTelefono(telefono);
    }

    public DTOCliente crearCliente(DTOCliente datos) {
        return iClienteBO.guardar(datos);
    }

    public DTOEvento crearEvento(DTOEvento datos) {
        if (validarDatosEvento(datos)) {
            return iEventoBO.guardar(datos);
        }
        return null;
    }

    public boolean validarDatosEvento(DTOEvento datos) {
        return datos != null && datos.getNumAsistentes() > 0;
    }

    public DTOEvento consultarEvento(int idEvento) {
        return iEventoBO.consultar(idEvento);
    }

    public DTOEvento solicitarInformacionEvento(int idEvento) {
        return iEventoBO.consultar(idEvento);
    }

    public DTORecurso obtenerRecurso(int idRecurso) {
        return iRecursoBO.consultarRecurso(idRecurso);
    }

    public DTODetalleSolicitud crearRecurso(DTODetalleSolicitud datos) {
        return iDetalleSolicitudBO.crearDetalleSolicitud(datos);
    }

    public void creaDetalleSolicitud(DTODetalleSolicitud datos) {
        iDetalleSolicitudBO.crearDetalleSolicitud(datos);
    }

    public DTOLevantamiento registrarLevantamiento(DTOLevantamiento datos) {
        return iLevantamientoBO.guardar(datos);
    }

    public DTOLocalEvento consultarLocal(int idLocalEvento) {
        return iLocalEventoBO.consultar(idLocalEvento);
    }

    public boolean validarDatosLocal(DTOLocalEvento datos) {
        return datos != null && datos.getCapacidad() > 0;
    }
    
   
    public void guardarRecursos(List<DTODetalleSolicitud> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return;
        }

        for (DTODetalleSolicitud detalle : detalles) {
            if (validarRecursos(detalle)) {
                iDetalleSolicitudBO.crearDetalleSolicitud(detalle);
            }
        }
    }

    //por el momento solo valida que exista
    private boolean validarRecursos(DTODetalleSolicitud detalle) {
        if (detalle == null) {
            return false;
        }

        DTORecurso recursoValidado = iRecursoBO.consultarRecurso(detalle.getIdRecurso());
        
        return recursoValidado != null;
    }
    
    public List<DTOLocalEvento> obtenerLocalesDisponibles(DTOEvento evento, String tipoLocal) {
        if (evento == null || tipoLocal == null) return new ArrayList<>();
        
        List<DTOLocalEvento> localesCandidatos = iLocalEventoBO.obtenerLocalFiltrado(tipoLocal, evento.getNumAsistentes());
        List<DTOReserva> reservasDelDia = iReservaBO.consultar(evento.getFechaEvento());

        return crearListaLocalEventos(localesCandidatos, reservasDelDia);
    }

    private List<DTOLocalEvento> crearListaLocalEventos(List<DTOLocalEvento> localesCandidatos, List<DTOReserva> reservasDelDia) {
        List<DTOLocalEvento> listaFinal = new ArrayList<>();
        for (DTOLocalEvento local : localesCandidatos) {
            boolean estaOcupado = false;
            for (DTOReserva reserva : reservasDelDia) {
                if (reserva.getIdLocalEvento() == local.getIdLocalEvento()) {
                    estaOcupado = true;
                    break;
                }
            }
            if (!estaOcupado) {
                listaFinal.add(local);
            }
        }
        return listaFinal;
    }
}