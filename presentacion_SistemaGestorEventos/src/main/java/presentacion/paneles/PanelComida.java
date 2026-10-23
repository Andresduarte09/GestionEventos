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
public class PanelComida extends PanelCategoriaRecursos {

    public PanelComida(controlPresentacion control) {
        super(control, "Comida", "comida", new String[][]{
            {"Sopa fría", "Entrada"},
            {"Ensalada", "Entrada"},
            {"Pasta", "Plato fuerte"},
            {"Pollo asado", "Plato fuerte"},
            {"Pastel", "Postre"},
            {"Gelatina", "Postre"},
            {"Agua de jamaica", "Bebida"},
            {"Refresco", "Bebida"}
        });
    }
}
