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
    
    public static boolean telefonoValido(String telefono) {
       if (telefono == null) 
           return false;
       String limpio = telefono.trim();
       if (limpio.length() != 10) 
           return false;
       for (int i = 0; i < limpio.length(); i++) {
           if (!Character.isDigit(limpio.charAt(i))) 
               return false;
       }
       return true;
   }
    
    
    public static boolean correoValido(String correo) {
        if (correo == null) 
            return false;
        return correo.trim().matches("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }



}