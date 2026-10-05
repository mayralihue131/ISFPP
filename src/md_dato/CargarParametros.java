package md_dato;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargarParametros {

    private final Properties parametros;

    /**
     * Lee el archivo de configuración con los nombres de los TXT
     * y los límites geográficos.
     */
    public CargarParametros(String rutaArchivo) throws IOException {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new IllegalArgumentException(
                    "La ruta del archivo no puede estar vacía");
        }

        this.parametros = new Properties();

        try (InputStream archivo = new FileInputStream(rutaArchivo)) {
            parametros.load(archivo);
        }
    }

    /**
     * Obtiene un parámetro obligatorio.
     */
    private String obtenerParametro(String nombre) {
        String valor = parametros.getProperty(nombre);

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(
                    "Falta el parámetro: " + nombre);
        }

        return valor.trim();
    }

    /**
     * Convierte un parámetro a double.
     */
    private double obtenerNumero(String nombre) {
        try {
            return Double.parseDouble(obtenerParametro(nombre));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "El parámetro " + nombre + " debe ser numérico", e);
        }
    }

    public String getArchivoUsuarios() {
        return obtenerParametro("usuario");
    }

    public String getArchivoVehiculos() {
        return obtenerParametro("vehiculo");
    }

    public String getArchivoServicios() {
        return obtenerParametro("servicio");
    }

    public double getLatitud1() {
        return obtenerNumero("latitud1");
    }

    public double getLongitud1() {
        return obtenerNumero("longitud1");
    }

    public double getLatitud2() {
        return obtenerNumero("latitud2");
    }

    public double getLongitud2() {
        return obtenerNumero("longitud2");
    }
}