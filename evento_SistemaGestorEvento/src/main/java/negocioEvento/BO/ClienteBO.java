/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTOCliente;
import negocioEvento.interfaces.IClienteBO;


/**
 *
 * @author Ale
 */
public class ClienteBO implements IClienteBO {

    private final AlmacenDatos almacen;

    public ClienteBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTOCliente guardar(DTOCliente cliente) {
        int nuevoId = almacen.getClientes().size() + 1;
        cliente.setIdCliente(nuevoId);
        almacen.registrarCliente(cliente);
        return cliente;
    }

    @Override
    public DTOCliente consultarPorTelefono(String telefono) {
        for (DTOCliente c : almacen.getClientes()) {
            if (c.getTelefono() != null && c.getTelefono().equals(telefono)) {
                return c;
            }
        }
        return null;
    }
}

