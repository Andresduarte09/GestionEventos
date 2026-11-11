package dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lui
 */
public class DTODetalleCotizacion {

    private int ID;
    private LocalTime horarioFases;
    List<DTOInventario> inventario = new ArrayList<>();
    private float importe;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalTime getHorarioFases() {
        return horarioFases;
    }

    public void setHorarioFases(LocalTime horarioFases) {
        this.horarioFases = horarioFases;
    }

    public List<DTOInventario> getInventario() {
        return inventario;
    }

    public void setInventario(List<DTOInventario> inventario) {
        this.inventario = inventario;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    private int idVersion;

    public int getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(int idVersion) {
        this.idVersion = idVersion;
        
    }

}
