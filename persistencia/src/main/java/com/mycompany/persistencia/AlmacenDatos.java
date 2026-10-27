/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import java.util.ArrayList;
import java.util.List;
import dto.*;
import java.time.LocalDate;

public class AlmacenDatos {

    private final List<DTOCliente> clientes = new ArrayList<>();
    private final List<DTODetalleSolicitud> detallesSolicitud = new ArrayList<>();
    private final List<DTOEvento> eventos = new ArrayList<>();
    private final List<DTOLevantamiento> levantamientos = new ArrayList<>();
    private final List<DTOLocalEvento> localesEvento = new ArrayList<>();
    private final List<DTORecurso> recursos = new ArrayList<>();
    private final List<DTOReserva> reservas = new ArrayList<>();
    private final List<dtoCotizacion> cotizaciones = new ArrayList<>();
    private final List<dtoDecisionCliente> decisionesCliente = new ArrayList<>();
    private final List<dtoDetalleCotizacion> detallesCotizacion = new ArrayList<>();
    private final List<dtoEquipo> equipos = new ArrayList<>();
    private final List<dtoInventario> inventario = new ArrayList<>();
    private final List<dtoPersonal> personal = new ArrayList<>();
    private final List<dtoServicio> servicios = new ArrayList<>();
    private final List<dtoVersionCotizacion> versionesCotizacion = new ArrayList<>();

    public AlmacenDatos() {
        cargarDatos();
    }

    private void cargarDatos() {
        DTOCliente cliente1 = new DTOCliente(1, "Mario Jesús S. Kennedy", "mario20@gmail.com", "6444238967", "6441155423");
        DTOCotizacion cotizacion = new DTOCotizacion(1, LocalDate.of(2026, 11, 15), "Pendiente");
        DTODecisionCliente decisionCliente = new DTODecisionCliente(1, LocalDate.of(2026, 11, 1), "Aceptada", "Cotización aprobada");
        DTODetalleCotizacion detalleCotizacion = new DTODetalleCotizacion();
        DTODetalleSolicitud detalleSolicitud = new DTODetalleSolicitud();
        DTOEquipo equipo = new DTOEquipo(1, "Silla", 100, 50);
        DTOEvento evento = new DTOEvento();
        DTOPersonal personal = new DTOPersonal(1, "Técnico de audio", 3, 2, 5, 1);
        DTOServicio servicio = new DTOServicio(1, "Audio", "Servicio de audio para evento", true, false, "Sin observaciones");
        List<DTOEquipo> equipos = new ArrayList<>(Arrays.asList(equipo));
        List<DTOPersonal> listaPersonal = new ArrayList<>(Arrays.asList(personal));
        List<DTOServicio> servicios = new ArrayList<>(Arrays.asList(servicio));
        DTOInventario inventario = new DTOInventario(equipos, listaPersonal, servicios, 1);
        DTOLevantamiento levantamiento = new DTOLevantamiento();
        DTOLocalEvento localEvento = new DTOLocalEvento(1, "Salón", "30 x 20 m", "Escenario y pista", "Acceso por entrada principal", "No fumar", "Se necesita conexión eléctrica");
        DTORecurso recurso = new DTORecurso(1, "Bocina", "Bocina amplificada", 100, 1500.0f, "personas", "Audio", "pieza", "Activo");
        DTOReserva reserva = new DTOReserva();
        DTOVersionCotizacion versionCotizacion = new DTOVersionCotizacion(1, 1, LocalDate.of(2026, 11, 15), "Vigente", "Versión inicial", 5.0f, 10000.0f, 11020.0f);
    }

    public List<DTOCliente> getClientes() {
        return clientes;
    }

    public List<DTODetalleSolicitud> getDetallesSolicitud() {
        return detallesSolicitud;
    }

    public List<DTOEvento> getEventos() {
        return eventos;
    }

    public List<DTOLevantamiento> getLevantamientos() {
        return levantamientos;
    }

    public List<DTOLocalEvento> getLocalesEvento() {
        return localesEvento;
    }

    public List<DTORecurso> getRecursos() {
        return recursos;
    }

    public List<DTOReserva> getReservas() {
        return reservas;
    }

    public List<dtoCotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public List<dtoDecisionCliente> getDecisionesCliente() {
        return decisionesCliente;
    }

    public List<dtoDetalleCotizacion> getDetallesCotizacion() {
        return detallesCotizacion;
    }

    public List<dtoEquipo> getEquipos() {
        return equipos;
    }

    public List<dtoInventario> getInventario() {
        return inventario;
    }

    public List<dtoPersonal> getPersonal() {
        return personal;
    }

    public List<dtoServicio> getServicios() {
        return servicios;
    }

    public List<dtoVersionCotizacion> getVersionesCotizacion() {
        return versionesCotizacion;
    }
}

