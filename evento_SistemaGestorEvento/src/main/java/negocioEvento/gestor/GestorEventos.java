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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    
    //id -> cantidad
    private Map<Integer, Integer> seleccionRecursos = new LinkedHashMap<>();

    
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

    //expone el recurso a la ui
    public DTORecurso obtenerRecurso(int idRecurso) {
        return iRecursoBO.consultarRecurso(idRecurso);
    }
    //obtiene todos los recursos
    public List<DTORecurso> obtenerCatalogoRecursos() {
        return iRecursoBO.obtenerTodosLosRecursos();
    }
    //valida que existe para luego crear el DetalleSolicitud
    public DTODetalleSolicitud crearRecurso(DTODetalleSolicitud datos) {
        if (datos == null) return null;

        DTORecurso recursoValidado = iRecursoBO.consultarRecurso(datos.getIdRecurso());
        if (recursoValidado != null) {
            datos.setCategoriaSolicitada(recursoValidado.getTipoRecurso());
            datos.setDescripcionSolicitud(recursoValidado.getNombre());

            creaDetalleSolicitud(datos);
            return datos;
        }
        return null;
    }

    //guarda los recursos que seleccione el usuario en un map (cuando el asesor le da a -+)
    //falta que valide por cantidades del recurso en el almacen/bd, que no deje agregar mas recursos
    //de los que hay
    public void actualizarRecursosTemporal(String categoria, Map<Integer, Integer> cantidades) {
        seleccionRecursos.entrySet().removeIf(entry -> {
            DTORecurso r = iRecursoBO.consultarRecurso(entry.getKey());
            return r != null && r.getTipoRecurso().equalsIgnoreCase(categoria);
        });

        // Agregamos los nuevos IDs con sus cantidades
        seleccionRecursos.putAll(cantidades);
    }

    //si el usuario regresa a la UI, recupera el borrador y lo muestra
    public Map<Integer, Integer> obtenerRecursosSeleccionados() {
        return seleccionRecursos;
    }
    
    public void guardarRecursos(int idEvento) {
        for (Map.Entry<Integer, Integer> entry : seleccionRecursos.entrySet()) {
            DTODetalleSolicitud detalle = new DTODetalleSolicitud();
            detalle.setIdEvento(idEvento);
            detalle.setIdRecurso(entry.getKey());
            detalle.setCantidadEstimada(entry.getValue());

            crearRecurso(detalle);
        }
        seleccionRecursos.clear();
    }
    
    private void creaDetalleSolicitud(DTODetalleSolicitud datos) {
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