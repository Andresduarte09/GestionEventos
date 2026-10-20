/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.equipogestioneventos.sistemagestoreventos;

/**
 *
 * @author lui
 */
import presentacion.JFrameInicioSesion;
public class SistemaGestorEventos {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(()-> {
            JFrameInicioSesion inicio = new JFrameInicioSesion();
            inicio.setVisible(true);
        });
    }
}
