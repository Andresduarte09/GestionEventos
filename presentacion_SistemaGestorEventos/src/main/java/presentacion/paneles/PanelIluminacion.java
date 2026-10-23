/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import presentacion.control.controlPresentacion;

/**
 *
 * @author andres
 */
public class PanelIluminacion extends PanelCategoriaRecursos {

    public PanelIluminacion(controlPresentacion control) {
        super(control, "Iluminación", "iluminacion", new String[][]{
            {"Lámparas colgantes", "Interior"},
            {"Luces LED", "Interior"},
            {"Reflectores", "Escenario"},
            {"Luces robóticas", "Escenario"},
            {"Máquina de humo", "Efectos"}
        });
    }
}
