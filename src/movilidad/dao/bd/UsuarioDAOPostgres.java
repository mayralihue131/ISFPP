package movilidad.dao.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import movilidad.dao.UsuarioDAO;
import movilidad.modelo.CategoriaVehiculo;
import movilidad.modelo.Conductor;
import movilidad.modelo.EstadoConductor;
import movilidad.modelo.RolUsuario;
import movilidad.modelo.TipoVehiculo;
import movilidad.modelo.Ubicacion;
import movilidad.modelo.Usuario;
import movilidad.modelo.Vehiculo;

public class UsuarioDAOPostgres implements UsuarioDAO {

    @Override
    public void guardar(Usuario usuario) {
        validar(usuario);
        try (Connection conexion = ConexionPostgres.obtenerConexion()) {
            conexion.setAutoCommit(false);
            try {
                upsertUsuario(conexion, usuario);
                if (usuario.getConductor() != null) {
                    guardarConductor(conexion, usuario);
                }
                conexion.commit();
            } catch (SQLException | RuntimeException excepcion) {
                conexion.rollback();
                throw excepcion;
            } finally {
                conexion.setAutoCommit(true);
            }
        } catch (SQLException excepcion) {
            throw new DatabaseException("No se pudo guardar el usuario", excepcion);
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        String sql = consultaUsuarios() + " WHERE lower(u.email) = lower(?)";
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, email);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? crearUsuario(resultado) : null;
            }
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudo buscar el usuario", excepcion);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = consultaUsuarios() + " ORDER BY u.email";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                usuarios.add(crearUsuario(resultado));
            }
            return usuarios;
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudieron listar los usuarios",
                    excepcion);
        }
    }

    private String consultaUsuarios() {
        return """
                SELECT u.email, u.nombre, u.telefono, u.rol_activo,
                       c.licencia, c.estado, c.categoria_activa,
                       v.patente, v.modelo, v.capacidad_pasajeros,
                       v.tipo_vehiculo, v.categoria_vehiculo,
                       v.ubicacion_latitud, v.ubicacion_longitud
                FROM mew_usuarios u
                LEFT JOIN mew_conductores c ON c.email = u.email
                LEFT JOIN mew_conductor_vehiculos cv ON cv.conductor_email = u.email
                    AND cv.vehiculo_activo = TRUE
                LEFT JOIN mew_vehiculos v ON v.patente = cv.patente
                """;
    }

    private void upsertUsuario(Connection conexion, Usuario usuario)
            throws SQLException {
        String sql = """
                INSERT INTO mew_usuarios (email, nombre, telefono, rol_activo)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (email) DO UPDATE SET
                    nombre = EXCLUDED.nombre,
                    telefono = EXCLUDED.telefono,
                    rol_activo = EXCLUDED.rol_activo
                """;
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, usuario.getEmail());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getTelefono());
            sentencia.setString(4, usuario.getRolActivo().name());
            sentencia.executeUpdate();
        }
    }

    private void guardarConductor(Connection conexion, Usuario usuario)
            throws SQLException {
        Conductor conductor = usuario.getConductor();
        String sql = """
                INSERT INTO mew_conductores (email, licencia, estado, categoria_activa)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (email) DO UPDATE SET licencia = EXCLUDED.licencia,
                    estado = EXCLUDED.estado,
                    categoria_activa = EXCLUDED.categoria_activa
                """;
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, usuario.getEmail());
            sentencia.setString(2, conductor.getLicenciaConducir());
            sentencia.setString(3, conductor.getEstadoConductor().name());
            sentencia.setString(4, conductor.getCategoriaVehiculoActivo().name());
            sentencia.executeUpdate();
        }

        for (Vehiculo vehiculo : conductor.getVehiculos()) {
            guardarVehiculo(conexion, vehiculo);
            String relacion = """
                    INSERT INTO mew_conductor_vehiculos
                        (conductor_email, patente, vehiculo_activo)
                    VALUES (?, ?, ?)
                    ON CONFLICT (conductor_email, patente) DO UPDATE SET
                        vehiculo_activo = EXCLUDED.vehiculo_activo
                    """;
            try (PreparedStatement sentencia = conexion.prepareStatement(relacion)) {
                sentencia.setString(1, usuario.getEmail());
                sentencia.setString(2, vehiculo.getPatente());
                sentencia.setBoolean(3,
                        vehiculo.equals(conductor.getVehiculoActivo()));
                sentencia.executeUpdate();
            }
        }
    }

    private void guardarVehiculo(Connection conexion, Vehiculo vehiculo)
            throws SQLException {
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
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
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
        }
    }

    private Usuario crearUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario(resultado.getString("nombre"),
                resultado.getString("telefono"), resultado.getString("email"));
        String licencia = resultado.getString("licencia");
        String patente = resultado.getString("patente");
        if (licencia != null && patente != null) {
            Ubicacion ubicacion = null;
            if (resultado.getObject("ubicacion_latitud") != null) {
                ubicacion = new Ubicacion(resultado.getDouble("ubicacion_latitud"),
                        resultado.getDouble("ubicacion_longitud"));
            }
            Vehiculo vehiculo = new Vehiculo(patente,
                    resultado.getString("modelo"),
                    resultado.getInt("capacidad_pasajeros"),
                    TipoVehiculo.valueOf(resultado.getString("tipo_vehiculo")),
                    CategoriaVehiculo.valueOf(
                            resultado.getString("categoria_vehiculo")),
                    ubicacion);
            usuario.altaConductor(licencia, vehiculo);
            if (RolUsuario.CONDUCTOR.name().equals(
                    resultado.getString("rol_activo"))) {
                usuario.cambiarRolActivo(RolUsuario.CONDUCTOR);
            }
            usuario.getConductor().setEstadoConductor(
                    EstadoConductor.valueOf(resultado.getString("estado")));
            usuario.getConductor().setCategoriaVehiculoActivo(
                    CategoriaVehiculo.valueOf(
                            resultado.getString("categoria_activa")));
        }
        return usuario;
    }

    private void validar(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null
                || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "El usuario y su email son obligatorios");
        }
    }
}
