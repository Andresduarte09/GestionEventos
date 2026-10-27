/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.persistencia;

import java.util.ArrayList;
import java.util.List;
import dto.*;

public class AlmacenDatos {

    private final List<DTOCliente> clientes = new ArrayList<>();
    private final List<DTODetalleSolicitud> detallesSolicitud = new ArrayList<>();
    private final List<DTOEvento> eventos = new ArrayList<>();
    private final List<DTOLevantamiento> levantamientos = new ArrayList<>();
    private final List<DTOLocalEvento> localesEvento = new ArrayList<>();
    private final List<DTORecurso> recursos = new ArrayList<>();
    private final List<DTOReserva> reservas = new ArrayList<>();
    private final List<DTOCotizacion> cotizaciones = new ArrayList<>();
    private final List<DTODecisionCliente> decisionesCliente = new ArrayList<>();
    private final List<DTODetalleCotizacion> detallesCotizacion = new ArrayList<>();
    private final List<DTOEquipo> equipos = new ArrayList<>();
    private final List<DTOInventario> inventario = new ArrayList<>();
    private final List<DTOPersonal> personal = new ArrayList<>();
    private final List<DTOServicio> servicios = new ArrayList<>();
    private final List<DTOVersionCotizacion> versionesCotizacion = new ArrayList<>();

    public AlmacenDatos() {
        cargarDatos();
    }

    private void cargarDatos() {
        DTOCliente cliente1 = new DTOCliente(1, "Mario Jesús S. Kennedy", "mario20@gmail.com", "6444238967", "6441155423");
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

    public List<DTOCotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public List<DTODecisionCliente> getDecisionesCliente() {
        return decisionesCliente;
    }

    public List<DTODetalleCotizacion> getDetallesCotizacion() {
        return detallesCotizacion;
    }

    public List<DTOEquipo> getEquipos() {
        return equipos;
    }

    public List<DTOInventario> getInventario() {
        return inventario;
    }

    public List<DTOPersonal> getPersonal() {
        return personal;
    }

    public List<DTOServicio> getServicios() {
        return servicios;
    }

    public List<DTOVersionCotizacion> getVersionesCotizacion() {
        return versionesCotizacion;
    }
}

