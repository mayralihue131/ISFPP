package movilidad.aplicacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargaParametros {

    private static String origenDatos;
    private static String archivoUsuarios;
    private static String archivoVehiculos;
    private static String archivoServicios;

    public static void parametros() throws IOException {
        Properties prop = new Properties();

        try (InputStream input = CargaParametros.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("No se encontró config.properties");
            }
            prop.load(input);
        }

        origenDatos = prop.getProperty("origen.datos", "TXT").trim()
                .toUpperCase();
        if (!origenDatos.equals("TXT") && !origenDatos.equals("BD")) {
            throw new IOException("origen.datos debe ser TXT o BD");
        }

        archivoUsuarios = prop.getProperty("usuario");
        archivoVehiculos = prop.getProperty("vehiculo");
        archivoServicios = prop.getProperty("servicio");

        if (!usarBaseDatos()
                && (archivoUsuarios == null || archivoUsuarios.isBlank()
                || archivoVehiculos == null || archivoVehiculos.isBlank()
                || archivoServicios == null || archivoServicios.isBlank())) {
            throw new IOException(
                    "Faltan las claves usuario, vehiculo o servicio "
                    + "en config.properties");
        }
    }

    public static boolean usarBaseDatos() {
        return "BD".equals(origenDatos);
    }

    public static String getArchivoUsuarios() {
        return archivoUsuarios;
    }

    public static String getArchivoVehiculos() {
        return archivoVehiculos;
    }

    public static String getArchivoServicios() {
        return archivoServicios;
    }
}