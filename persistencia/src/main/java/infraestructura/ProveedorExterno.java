/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;

import dto.DTOEquipo;
import java.util.ArrayList;
import java.util.List;

/**
 * Almacén del proveedor externo: solo guarda equipo.
 *
 * @author Andre
 */
public class ProveedorExterno {

    private final List<DTOEquipo> equipos = new ArrayList<>();

    public ProveedorExterno() {
        cargarEquipos();
    }

    // id, nombre, disponibilidad, cantidad seleccionada
    private void cargarEquipos() {
        equipos.add(new DTOEquipo(101, "Generador", 4, 0));
        equipos.add(new DTOEquipo(102, "Escenario modular", 2, 0));
        equipos.add(new DTOEquipo(103, "Carpa 10x10", 6, 0));
        equipos.add(new DTOEquipo(104, "Pista de baile LED", 3, 0));
        equipos.add(new DTOEquipo(105, "Sistema de sonido line array", 2, 0));
        equipos.add(new DTOEquipo(106, "Pantalla gigante", 2, 0));
        equipos.add(new DTOEquipo(107, "Planta de luz", 3, 0));
    }

    public List<DTOEquipo> getEquipos() {
        return equipos;
    }
}