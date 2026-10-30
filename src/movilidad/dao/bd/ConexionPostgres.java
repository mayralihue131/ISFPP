package movilidad.dao.bd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexionPostgres {

    private static final String ARCHIVO_CONFIGURACION = "jdbc.properties";

    private ConexionPostgres() {
    }

    public static Connection obtenerConexion() throws SQLException {
        Properties propiedades = cargarPropiedades();
        String driver = obligatorio(propiedades, "driver");
        String url = obligatorio(propiedades, "url");
        String usuario = obligatorio(propiedades, "usr");
        String password = obligatorio(propiedades, "pwd");
        String esquema = obligatorio(propiedades, "schema");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException excepcion) {
            throw new SQLException("No se encontró el driver JDBC " + driver,
                    excepcion);
        }

        Connection conexion = DriverManager.getConnection(url, usuario, password);
        conexion.setSchema(esquema);
        return conexion;
    }

    private static Properties cargarPropiedades() throws SQLException {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionPostgres.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIGURACION)) {
            if (entrada == null) {
                throw new SQLException("No se encontró " + ARCHIVO_CONFIGURACION
                        + " en el classpath");
            }
            propiedades.load(entrada);
            return propiedades;
        } catch (IOException excepcion) {
            throw new SQLException("No se pudo leer " + ARCHIVO_CONFIGURACION,
                    excepcion);
        }
    }

    private static String obligatorio(Properties propiedades, String clave)
            throws SQLException {
        String valor = propiedades.getProperty(clave);
        if (valor == null || valor.isBlank()) {
            throw new SQLException("Falta la propiedad " + clave
                    + " en " + ARCHIVO_CONFIGURACION);
        }
        return valor.trim();
    }
}
