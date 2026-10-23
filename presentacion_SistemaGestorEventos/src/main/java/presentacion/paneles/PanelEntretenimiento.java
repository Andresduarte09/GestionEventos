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
public class PanelEntretenimiento extends PanelCategoriaRecursos {

    public PanelEntretenimiento(controlPresentacion control) {
        super(control, "Entretenimiento", "entretenimiento", new String[][]{
            {"DJ", "Música"},
            {"Banda en vivo", "Música"},
            {"Payaso", "Infantil"},
            {"Brincolín", "Infantil"},
            {"Mago", "Show"}
        });
    }
}
