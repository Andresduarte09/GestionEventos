/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import presentacion.control.controlPresentacion;

/**
 *
 * @author lui
 */
public class PanelPantallas extends PanelCategoriaRecursos {

    public PanelPantallas(controlPresentacion control) {
        super(control, "Pantallas", "pantallas", new String[][]{
            {"Pantalla LED", "LED"},
            {"Proyector", "Proyección"},
            {"Pantalla de proyección", "Proyección"},
            {"Televisión", "Televisión"}
        });
    }
}
