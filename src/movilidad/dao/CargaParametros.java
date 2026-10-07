package movilidad.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargaParametros {

    private static String archivoUsuarios;
    private static String archivoVehiculos;
    private static String archivoServicios;

    public static void parametros() throws IOException {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("src/config.properties")) {
            prop.load(input);
        }

        archivoUsuarios = prop.getProperty("usuario");
        archivoVehiculos = prop.getProperty("vehiculo");
        archivoServicios = prop.getProperty("servicio");

        if (archivoUsuarios == null || archivoUsuarios.isBlank()
                || archivoVehiculos == null || archivoVehiculos.isBlank()
                || archivoServicios == null || archivoServicios.isBlank()) {
            throw new IOException(
                    "Faltan las claves usuario, vehiculo o servicio "
                    + "en config.properties");
        }
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