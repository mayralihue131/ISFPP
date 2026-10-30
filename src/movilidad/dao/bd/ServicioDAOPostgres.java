package movilidad.dao.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movilidad.dao.ServicioDAO;
import movilidad.modelo.CategoriaVehiculo;
import movilidad.modelo.Servicio;
import movilidad.modelo.TipoServicio;
import movilidad.modelo.TipoVehiculo;

public class ServicioDAOPostgres implements ServicioDAO {

    @Override
    public void guardar(Servicio servicio) {
        if (servicio == null || servicio.getNombre() == null
                || servicio.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El servicio y su nombre son obligatorios");
        }
        String sql = """
                INSERT INTO mew_servicios (nombre, tarifa_base, precio_km,
                    precio_minuto, tipo_vehiculo, categoria_vehiculo, tipo_servicio)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (nombre) DO UPDATE SET tarifa_base = EXCLUDED.tarifa_base,
                    precio_km = EXCLUDED.precio_km,
                    precio_minuto = EXCLUDED.precio_minuto,
                    tipo_vehiculo = EXCLUDED.tipo_vehiculo,
                    categoria_vehiculo = EXCLUDED.categoria_vehiculo,
                    tipo_servicio = EXCLUDED.tipo_servicio
                """;
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, servicio.getNombre());
            sentencia.setDouble(2, servicio.getTarifaBase());
            sentencia.setDouble(3, servicio.getPrecioKm());
            sentencia.setDouble(4, servicio.getPrecioMinuto());
            sentencia.setString(5, servicio.getTipoVehiculo().name());
            sentencia.setString(6, servicio.getCategoriaVehiculo().name());
            sentencia.setString(7, servicio.getTipoServicio().name());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw new DatabaseException("No se pudo guardar el servicio",
                    excepcion);
        }
    }

    @Override
    public List<Servicio> listarTodos() {
        String sql = "SELECT nombre, tarifa_base, precio_km, precio_minuto, "
                + "tipo_vehiculo, categoria_vehiculo, tipo_servicio "
                + "FROM mew_servicios ORDER BY nombre";
        List<Servicio> servicios = new ArrayList<>();
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                servicios.add(new Servicio(resultado.getString("nombre"),
                        resultado.getDouble("tarifa_base"),
                        resultado.getDouble("precio_km"),
                        resultado.getDouble("precio_minuto"),
                        CategoriaVehiculo.valueOf(
                                resultado.getString("categoria_vehiculo")),
                        TipoVehiculo.valueOf(
                                resultado.getString("tipo_vehiculo")),
                        TipoServicio.valueOf(
                                resultado.getString("tipo_servicio"))));
            }
            return servicios;
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudieron listar los servicios",
                    excepcion);
        }
    }
}
