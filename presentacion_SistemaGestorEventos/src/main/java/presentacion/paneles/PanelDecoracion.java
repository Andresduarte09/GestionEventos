/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.paneles;

import presentacion.control.ControlPresentacion;

/**
 *
 * @author andres
 */
public class PanelDecoracion extends PanelCategoriaRecursos {

    public PanelDecoracion(ControlPresentacion control) {
        super(control, "Decoración", "decoracion", new String[][]{
            {"Mesas redondas", "Mobiliario"},
            {"Sillas Tiffany", "Mobiliario"},
            {"Centros de mesa", "Ambientación"},
            {"Globos", "Ambientación"},
            {"Mantelería", "Textiles"}
        });
    }
}
