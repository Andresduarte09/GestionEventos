/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

/**
 *
 * @author lui
 */
public class controlPresentacion {
    JFrameRecursosEvento recursosEvento = new JFrameRecursosEvento();
    
    public void iniciarRegistrarEvento(){
        JFrameRegistrarDatosEvento registrarEvento = new JFrameRegistrarDatosEvento();
        registrarEvento.setVisible(true);
    }
    
    public void cancelarRegistrarEvento(){
        JFrameInicioSesion inicioSesion = new JFrameInicioSesion();
        inicioSesion.setVisible(true);
    }
    
    public void iniciarRecursosEvento(){
        recursosEvento.setVisible(true);
    }
    
}
