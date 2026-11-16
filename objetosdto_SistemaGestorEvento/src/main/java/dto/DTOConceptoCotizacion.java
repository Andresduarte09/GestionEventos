package dto;

/**
 *
 * @author lui
 */
public class DTOConceptoCotizacion {

    private String tipo;
    private String nombre;
    private int cantidad;
    private float precioUnitario;
    private float importe;

    public DTOConceptoCotizacion() {
    }

    public DTOConceptoCotizacion(String tipo, String nombre, int cantidad, float precioUnitario) {
        if (tipo == null || tipo.isBlank() || nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El concepto debe tener tipo y nombre.");
        }

        if (cantidad <= 0 || !Float.isFinite(precioUnitario) || precioUnitario < 0) {
            throw new IllegalArgumentException("La cantidad o tarifa del concepto no es válida.");
        }

        double calculo = (double) cantidad * precioUnitario;

        if (!Double.isFinite(calculo) || calculo > Float.MAX_VALUE) {
            throw new IllegalArgumentException("El importe del concepto excede el límite.");
        }

        this.tipo = tipo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.importe = (float) calculo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public float getImporte() {
        return importe;
    }
}
