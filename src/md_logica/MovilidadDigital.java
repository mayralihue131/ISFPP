package md_logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import md_modelo.*;

public class MovilidadDigital {

    private List<Usuario> usuarios;
    private List<Viaje> viajes;
    private List<Servicio> servicios;

    public MovilidadDigital() {
        this.usuarios = new ArrayList<>();
        this.viajes = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public List<Viaje> getViajes() {
        return new ArrayList<>(viajes);
    }

    public List<Servicio> getServicios() {
        return new ArrayList<>(servicios);
    }

    // Registrar un usuario y evitar duplicados.
    public void agregarUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Buscar un usuario registrado por teléfono.
    public Usuario buscarUsuarioPorTelefono(String telefono) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Habilitar al usuario como conductor con su primer vehículo.
    public void registrarConductor(
            Usuario usuario,
            String licencia,
            Vehiculo vehiculo) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Agregar un vehículo al conductor del usuario.
    public void agregarVehiculo(Usuario usuario, Vehiculo vehiculo) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Agregar PASAJEROS o ENVIOS a un vehículo del conductor.
    public void agregarTipoServicioVehiculo(
            Usuario usuario,
            Vehiculo vehiculo,
            TipoServicio tipoServicio) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Elegir un vehículo propio cuando no haya un viaje en curso.
    public void seleccionarVehiculoActivo(
            Usuario usuario,
            Vehiculo vehiculo) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Definir hasta qué categoría menor acepta solicitudes.
    public void definirCategoriaAceptada(
            Usuario usuario,
            CategoriaVehiculo categoria) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Validar conductor, licencia y vehículo activo.
    public void ponerDisponible(Usuario usuario) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Dejar de recibir solicitudes si no tiene un viaje en curso.
    public void ponerFueraDeServicio(Usuario usuario) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Cambiar el rol activo del usuario.
    public void cambiarRolActivo(Usuario usuario, RolUsuario rol) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Registrar un servicio y evitar duplicados.
    public void agregarServicio(Servicio servicio) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Buscar servicios compatibles con lo solicitado.
    public List<Servicio> consultarServicios(
            TipoServicio tipoServicio,
            CategoriaVehiculo categoria) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Estimar los minutos de recorrido usando parámetros configurados.
    public double estimarTiempo(
            Servicio servicio,
            Ubicacion origen,
            Ubicacion destino) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Calcular el precio estimado mediante la tarifa del servicio.
    public double estimarCosto(
            Servicio servicio,
            Ubicacion origen,
            Ubicacion destino,
            double minutosEstimados) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Crear la solicitud y asociarla al cliente.
    public Viaje solicitarViaje(
            Usuario cliente,
            Servicio servicio,
            Ubicacion origen,
            Ubicacion destino,
            LocalDateTime fechaHora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Buscar usuarios con conductores disponibles y compatibles.
    public List<Usuario> buscarConductoresDisponibles(Viaje viaje) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Obtener las solicitudes que un conductor puede aceptar.
    public List<Viaje> obtenerSolicitudesDisponibles(Usuario conductor) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Aceptar una solicitud todavía libre y comenzar el viaje al origen.
    public void aceptarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Iniciar el traslado al destino con el conductor asignado.
    public void iniciarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Finalizar el traslado y dejar disponible al conductor.
    public void finalizarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Registrar la calificación que el cliente da al conductor.
    public void calificarConductor(
            UUID idViaje,
            Usuario cliente,
            CalificacionViaje calificacion) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Registrar la calificación que el conductor da al cliente.
    public void calificarCliente(
            UUID idViaje,
            Usuario conductor,
            CalificacionViaje calificacion) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Calcular el cargo según quién cancela y el estado del viaje.
    // Antes de la aceptación, la cancelación del cliente cuesta cero.
    public double calcularCostoCancelacion(
            UUID idViaje,
            Usuario usuario,
            LocalDateTime fechaHora,
            double kilometrosRecorridos) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Cancelar, registrar el motivo y devolver el importe a cobrar.
    public double cancelarViaje(
            UUID idViaje,
            Usuario usuario,
            LocalDateTime fechaHora,
            String motivo,
            double kilometrosRecorridos) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Rechazar una solicitud pendiente cuyo tiempo de espera venció.
    public void rechazarViaje(
            UUID idViaje,
            LocalDateTime fechaHora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Revisar las solicitudes y devolver las rechazadas en esta revisión.
    public List<Viaje> rechazarSolicitudesVencidas(LocalDateTime ahora) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Buscar un viaje por su UUID.
    public Viaje buscarViajePorId(UUID idViaje) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Consultar los viajes del usuario como cliente.
    public List<Viaje> obtenerViajesCliente(Usuario usuario) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Consultar los viajes del usuario como conductor.
    public List<Viaje> obtenerViajesConductor(Usuario usuario) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Avisar a los conductores compatibles sobre una nueva solicitud.
    private void notificarSolicitud(Viaje viaje) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    // Avisar al cliente que su solicitud fue rechazada.
    private void notificarRechazo(Viaje viaje) {
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}