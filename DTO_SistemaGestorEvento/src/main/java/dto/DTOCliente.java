/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Andres
 */
public class DTOCliente {
    int idCliente;
    String nombre;
    String correo;
    String telefono;
    String telefonoAlt;

    public DTOCliente(int idCliente, String nombre, String correo, String telefono, String telefonoAlt) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.telefonoAlt = telefonoAlt;
    }

    public DTOCliente() {
    }

    
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoAlt() {
        return telefonoAlt;
    }

    public void setTelefonoAlt(String telefonoAlt) {
        this.telefonoAlt = telefonoAlt;
    }
    
    
}
