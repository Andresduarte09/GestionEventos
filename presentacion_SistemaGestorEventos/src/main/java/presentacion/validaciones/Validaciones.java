/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.validaciones;

import javax.swing.JTextField;

/**
 *
 * @author Andre
 */
public class Validaciones {
    
    public Validaciones(){
        
    }
    
    
    public static boolean soloNumeros(JTextField txt) {
        String texto = txt.getText();
        for (int i = 0; i < texto.length(); i++){
            if(!Character.isDigit(texto.charAt(i))){
                return false;
            }  
        }
        return true;  
    }
    
    public static boolean soloLetras(JTextField txt) {

        String texto = txt.getText();

        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isLetter(texto.charAt(i)) && texto.charAt(i) != ' ') { // texto.charAt(i) != ' ' permite espacios entre palabras
                return false;
            }
        }

        return true;
    }
    
    public static boolean sonDiezDigitos(JTextField txt){
        String texto = txt.getText();
        return soloNumeros(txt) && texto.length()==10;
    }
    
    
    public static boolean correoElectronico(JTextField txt) {
        String correo = txt.getText().trim();
        return correo.matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }



}