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
    private List<DTOConceptoCotizacion> conceptos = new ArrayList<>();

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

    public List<DTOConceptoCotizacion> getConceptos() {
        return java.util.Collections.unmodifiableList(conceptos);
    }

    public void setConceptos(List<DTOConceptoCotizacion> conceptos) {
        if (conceptos == null || conceptos.isEmpty()) {
            throw new IllegalArgumentException("El desglose económico no puede estar vacío.");
        }

        double suma = 0;

        for (DTOConceptoCotizacion concepto : conceptos) {
            if (concepto == null || concepto.getCantidad() <= 0
                    || !Float.isFinite(concepto.getImporte()) || concepto.getImporte() < 0) {
                throw new IllegalArgumentException("Existe un concepto económico inválido.");
            }

            suma += concepto.getImporte();
        }

        if (!Double.isFinite(suma) || Math.abs(suma - importe) > 0.01) {
            throw new IllegalArgumentException("El desglose no coincide con el importe del detalle.");
        }

        this.conceptos = new ArrayList<>(conceptos);
    }

}
