package movilidad.dao.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movilidad.dao.VehiculoDAO;
import movilidad.modelo.CategoriaVehiculo;
import movilidad.modelo.TipoServicio;
import movilidad.modelo.TipoVehiculo;
import movilidad.modelo.Ubicacion;
import movilidad.modelo.Vehiculo;

public class VehiculoDAOPostgres implements VehiculoDAO {

    @Override
    public void guardar(Vehiculo vehiculo) {
        validar(vehiculo);
        String sql = """
                INSERT INTO mew_vehiculos (patente, modelo, capacidad_pasajeros,
                    tipo_vehiculo, categoria_vehiculo,
                    ubicacion_latitud, ubicacion_longitud)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (patente) DO UPDATE SET modelo = EXCLUDED.modelo,
                    capacidad_pasajeros = EXCLUDED.capacidad_pasajeros,
                    tipo_vehiculo = EXCLUDED.tipo_vehiculo,
                    categoria_vehiculo = EXCLUDED.categoria_vehiculo,
                    ubicacion_latitud = EXCLUDED.ubicacion_latitud,
                    ubicacion_longitud = EXCLUDED.ubicacion_longitud
                """;
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, vehiculo.getPatente());
            sentencia.setString(2, vehiculo.getModelo());
            sentencia.setInt(3, vehiculo.getCapacidadPasajeros());
            sentencia.setString(4, vehiculo.getTipoVehiculo().name());
            sentencia.setString(5, vehiculo.getCategoriaVehiculo().name());
            if (vehiculo.getUbicacion() == null) {
                sentencia.setNull(6, java.sql.Types.DOUBLE);
                sentencia.setNull(7, java.sql.Types.DOUBLE);
            } else {
                sentencia.setDouble(6, vehiculo.getUbicacion().getLatitud());
                sentencia.setDouble(7, vehiculo.getUbicacion().getLongitud());
            }
            sentencia.executeUpdate();
            guardarTiposServicio(conexion, vehiculo);
        } catch (SQLException excepcion) {
            throw new DatabaseException("No se pudo guardar el vehículo",
                    excepcion);
        }
    }

    @Override
    public Vehiculo buscarPorPatente(String patente) {
        if (patente == null || patente.isBlank()) {
            return null;
        }
        String sql = consulta() + " WHERE v.patente = ?";
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, patente);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? crearVehiculo(resultado) : null;
            }
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudo buscar el vehículo",
                    excepcion);
        }
    }

    @Override
    public List<Vehiculo> listarTodos() {
        String sql = consulta() + " ORDER BY v.patente";
        List<Vehiculo> vehiculos = new ArrayList<>();
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                vehiculos.add(crearVehiculo(resultado));
            }
            return vehiculos;
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudieron listar los vehículos",
                    excepcion);
        }
    }

    private String consulta() {
        return """
                SELECT v.patente, v.modelo, v.capacidad_pasajeros,
                       v.tipo_vehiculo, v.categoria_vehiculo,
                       v.ubicacion_latitud, v.ubicacion_longitud,
                       COALESCE(string_agg(vs.tipo_servicio, ','), '') AS tipos
                FROM mew_vehiculos v
                LEFT JOIN mew_vehiculo_servicios vs ON vs.patente = v.patente
                GROUP BY v.patente, v.modelo, v.capacidad_pasajeros,
                    v.tipo_vehiculo, v.categoria_vehiculo,
                    v.ubicacion_latitud, v.ubicacion_longitud
                """;
    }

    private void guardarTiposServicio(Connection conexion, Vehiculo vehiculo)
            throws SQLException {
        try (PreparedStatement borrar = conexion.prepareStatement(
                "DELETE FROM mew_vehiculo_servicios WHERE patente = ?")) {
            borrar.setString(1, vehiculo.getPatente());
            borrar.executeUpdate();
        }
        String sql = "INSERT INTO mew_vehiculo_servicios (patente, tipo_servicio) "
                + "VALUES (?, ?)";
        try (PreparedStatement insertar = conexion.prepareStatement(sql)) {
            for (TipoServicio tipo : vehiculo.getTipoServicio()) {
                insertar.setString(1, vehiculo.getPatente());
                insertar.setString(2, tipo.name());
                insertar.addBatch();
            }
            insertar.executeBatch();
        }
    }

    private Vehiculo crearVehiculo(ResultSet resultado) throws SQLException {
        Ubicacion ubicacion = null;
        if (resultado.getObject("ubicacion_latitud") != null) {
            ubicacion = new Ubicacion(resultado.getDouble("ubicacion_latitud"),
                    resultado.getDouble("ubicacion_longitud"));
        }
        Vehiculo vehiculo = new Vehiculo(resultado.getString("patente"),
                resultado.getString("modelo"),
                resultado.getInt("capacidad_pasajeros"),
                TipoVehiculo.valueOf(resultado.getString("tipo_vehiculo")),
                CategoriaVehiculo.valueOf(
                        resultado.getString("categoria_vehiculo")),
                ubicacion);
        String tipos = resultado.getString("tipos");
        if (tipos != null && !tipos.isBlank()) {
            for (String tipo : tipos.split(",")) {
                vehiculo.agregarTipoServicio(TipoServicio.valueOf(tipo));
            }
        }
        return vehiculo;
    }

    private void validar(Vehiculo vehiculo) {
        if (vehiculo == null || vehiculo.getPatente() == null
                || vehiculo.getPatente().isBlank()) {
            throw new IllegalArgumentException(
                    "El vehículo y su patente son obligatorios");
        }
    }
}
