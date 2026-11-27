package subsistemaInfraestructura.cliente;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;
import subsistemaInfraestructura.excepciones.InfraestructuraException;
import subsistemaInfraestructura.interfaces.IProveedorExterno;

public class ProveedorExternoHTTP implements IProveedorExterno {
    private final String urlBase;
    private final HttpClient cliente = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10)).build();

    public ProveedorExternoHTTP(String urlBase) {
        URI uri = URI.create(urlBase);
        if (!("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
                || uri.getHost() == null || uri.getQuery() != null || uri.getFragment() != null) {
            throw new IllegalArgumentException("URL del proveedor inválida");
        }
        this.urlBase = urlBase.replaceAll("/+$", "");
    }

    @Override
    public List<DTOEquipoProveedor> consultarEquipos() {
        return consultar("/api/equipos", "idEquipo\tidRecurso\tnombre\tdisponibilidad", 4,
                c -> new DTOEquipoProveedor(Integer.parseInt(c[0]), Integer.parseInt(c[1]),
                        c[2], Integer.parseInt(c[3])), DTOEquipoProveedor::idEquipo);
    }

    @Override
    public List<DTORecursoProveedor> consultarRecursos() {
        return consultar("/api/recursos", "idRecurso\tnombre\tdescripcion\tcapacidad\tprecioReferencia\tunidadCapacidad\ttipoRecurso\tunidadMedida\testadoEnCatalogo", 9,
                c -> new DTORecursoProveedor(Integer.parseInt(c[0]), c[1], c[2],
                        Integer.parseInt(c[3]), Float.parseFloat(c[4]), c[5], c[6], c[7], c[8]),
                DTORecursoProveedor::idRecurso);
    }

    // Se negocia TSV para no necesitar bibliotecas JSON en el cliente Java.
    private <T> List<T> consultar(String ruta, String encabezado, int columnas,
            Function<String[], T> convertir, ToIntFunction<T> id) {
        HttpRequest peticion = HttpRequest.newBuilder(URI.create(urlBase + ruta))
                .timeout(Duration.ofSeconds(90))
                .header("Accept", "text/tab-separated-values").GET().build();
        try {
            HttpResponse<String> respuesta = cliente.send(peticion,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (respuesta.statusCode() != 200) {
                throw new IOException("HTTP " + respuesta.statusCode());
            }
            String[] filas = respuesta.body().split("\\R");
            if (filas.length == 0 || !encabezado.equals(filas[0])) {
                throw new IOException("Encabezado del catálogo inválido");
            }
            List<T> resultado = new ArrayList<>();
            Set<Integer> ids = new HashSet<>();
            for (int i = 1; i < filas.length; i++) {
                if (!filas[i].isBlank()) {
                    String[] campos = filas[i].split("\t", -1);
                    if (campos.length != columnas) {
                        throw new IOException("Fila del catálogo inválida");
                    }
                    T elemento = convertir.apply(campos);
                    if (!ids.add(id.applyAsInt(elemento))) {
                        throw new IOException("ID duplicado en el catálogo");
                    }
                    resultado.add(elemento);
                }
            }
            return List.copyOf(resultado);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InfraestructuraException("Consulta interrumpida", e);
        } catch (IOException | IllegalArgumentException e) {
            throw new InfraestructuraException("No se pudo consultar " + ruta, e);
        }
    }
}
