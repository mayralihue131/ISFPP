package movilidad.dao.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import movilidad.dao.ViajeDAO;
import movilidad.modelo.EstadoViaje;
import movilidad.modelo.RegistroViaje;
import movilidad.modelo.RolUsuario;
import movilidad.modelo.Servicio;
import movilidad.modelo.TipoServicio;
import movilidad.modelo.TipoVehiculo;
import movilidad.modelo.Ubicacion;
import movilidad.modelo.Usuario;
import movilidad.modelo.Viaje;

public class ViajeDAOPostgres implements ViajeDAO {

    @Override
    public void guardar(Viaje viaje) {
        if (viaje == null || viaje.getCliente() == null
                || viaje.getOrigen() == null || viaje.getDestino() == null) {
            throw new IllegalArgumentException(
                    "El viaje, cliente, origen y destino son obligatorios");
        }
        if (viaje.getConductor() != null) {
            new UsuarioDAOPostgres().guardar(viaje.getConductor());
        }
        if (viaje.getVehiculo() != null) {
            new VehiculoDAOPostgres().guardar(viaje.getVehiculo());
        }
        if (viaje.getServicio() != null) {
            new ServicioDAOPostgres().guardar(viaje.getServicio());
        }
        String sql = """
                INSERT INTO mew_viajes (id, cliente_email, conductor_email,
                    vehiculo_patente, servicio_nombre, origen_latitud,
                    origen_longitud, destino_latitud, destino_longitud,
                    motivo_cancelacion, rol_cancela)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE SET conductor_email = EXCLUDED.conductor_email,
                    vehiculo_patente = EXCLUDED.vehiculo_patente,
                    servicio_nombre = EXCLUDED.servicio_nombre,
                    motivo_cancelacion = EXCLUDED.motivo_cancelacion,
                    rol_cancela = EXCLUDED.rol_cancela
                """;
        try (Connection conexion = ConexionPostgres.obtenerConexion()) {
            conexion.setAutoCommit(false);
            try {
                guardarUsuario(conexion, viaje.getCliente());
                try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
                    sentencia.setObject(1, viaje.getId());
                    sentencia.setString(2, viaje.getCliente().getEmail());
                    setString(sentencia, 3, email(viaje.getConductor()));
                    setString(sentencia, 4,
                            viaje.getVehiculo() == null ? null
                                    : viaje.getVehiculo().getPatente());
                    setString(sentencia, 5, viaje.getServicio() == null ? null
                            : viaje.getServicio().getNombre());
                    sentencia.setDouble(6, viaje.getOrigen().getLatitud());
                    sentencia.setDouble(7, viaje.getOrigen().getLongitud());
                    sentencia.setDouble(8, viaje.getDestino().getLatitud());
                    sentencia.setDouble(9, viaje.getDestino().getLongitud());
                    setString(sentencia, 10, viaje.getMotivoCancelacion());
                    setString(sentencia, 11, viaje.getRolCancela() == null ? null
                            : viaje.getRolCancela().name());
                    sentencia.executeUpdate();
                }
                guardarEstados(conexion, viaje);
                conexion.commit();
            } catch (SQLException | RuntimeException excepcion) {
                conexion.rollback();
                throw excepcion;
            } finally {
                conexion.setAutoCommit(true);
            }
        } catch (SQLException excepcion) {
            throw new DatabaseException("No se pudo guardar el viaje", excepcion);
        }
    }

    @Override
    public List<Viaje> listarTodos() {
        String sql = """
                SELECT v.id, v.origen_latitud, v.origen_longitud,
                       v.destino_latitud, v.destino_longitud,
                       v.motivo_cancelacion, v.rol_cancela,
                       c.nombre AS cliente_nombre, c.telefono AS cliente_telefono,
                       c.email AS cliente_email, h.fecha_hora, h.estado
                FROM mew_viajes v
                JOIN mew_usuarios c ON c.email = v.cliente_email
                LEFT JOIN mew_viaje_estados h ON h.viaje_id = v.id
                ORDER BY v.id, h.fecha_hora
                """;
        List<Viaje> viajes = new ArrayList<>();
        try (Connection conexion = ConexionPostgres.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {
            Viaje actual = null;
            while (resultado.next()) {
                UUID id = (UUID) resultado.getObject("id");
                if (actual == null || !actual.getId().equals(id)) {
                    Usuario cliente = new Usuario(
                            resultado.getString("cliente_nombre"),
                            resultado.getString("cliente_telefono"),
                            resultado.getString("cliente_email"));
                    actual = new Viaje(
                            new Ubicacion(resultado.getDouble("origen_latitud"),
                                    resultado.getDouble("origen_longitud")),
                            new Ubicacion(resultado.getDouble("destino_latitud"),
                                    resultado.getDouble("destino_longitud")),
                            cliente, null);
                    actual.setId(id);
                    actual.setMotivoCancelacion(
                            resultado.getString("motivo_cancelacion"));
                    String rol = resultado.getString("rol_cancela");
                    if (rol != null) {
                        actual.setRolCancela(RolUsuario.valueOf(rol));
                    }
                    viajes.add(actual);
                }
                Timestamp fecha = resultado.getTimestamp("fecha_hora");
                String estado = resultado.getString("estado");
                if (fecha != null && estado != null) {
                    actual.agregarRegistroPersistido(new RegistroViaje(
                            fecha.toLocalDateTime(), EstadoViaje.valueOf(estado)));
                }
            }
            return viajes;
        } catch (SQLException | IllegalArgumentException excepcion) {
            throw new DatabaseException("No se pudieron listar los viajes",
                    excepcion);
        }
    }

    private void guardarUsuario(Connection conexion, Usuario usuario)
            throws SQLException {
        String sql = """
                INSERT INTO mew_usuarios (email, nombre, telefono, rol_activo)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (email) DO NOTHING
                """;
        try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, usuario.getEmail());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getTelefono());
            sentencia.setString(4, usuario.getRolActivo().name());
            sentencia.executeUpdate();
        }
    }

    private void guardarEstados(Connection conexion, Viaje viaje)
            throws SQLException {
        try (PreparedStatement borrar = conexion.prepareStatement(
                "DELETE FROM mew_viaje_estados WHERE viaje_id = ?")) {
            borrar.setObject(1, viaje.getId());
            borrar.executeUpdate();
        }
        String sql = "INSERT INTO mew_viaje_estados (viaje_id, fecha_hora, estado) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement insertar = conexion.prepareStatement(sql)) {
            for (RegistroViaje registro : viaje.getRegistrosViaje()) {
                insertar.setObject(1, viaje.getId());
                insertar.setTimestamp(2,
                        Timestamp.valueOf(registro.getFechaHora()));
                insertar.setString(3, registro.getEstadoViaje().name());
                insertar.addBatch();
            }
            insertar.executeBatch();
        }
    }

    private String email(Usuario usuario) {
        return usuario == null ? null : usuario.getEmail();
    }

    private void setString(PreparedStatement sentencia, int indice, String valor)
            throws SQLException {
        if (valor == null) {
            sentencia.setNull(indice, java.sql.Types.VARCHAR);
        } else {
            sentencia.setString(indice, valor);
        }
    }
}
