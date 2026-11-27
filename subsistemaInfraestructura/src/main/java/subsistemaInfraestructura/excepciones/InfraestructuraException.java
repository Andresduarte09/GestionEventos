package subsistemaInfraestructura.excepciones;

public class InfraestructuraException extends RuntimeException {
    public InfraestructuraException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
