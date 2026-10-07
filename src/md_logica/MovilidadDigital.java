package md_logica;

import java.time.Duration;
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
        if (cliente == null || servicio == null || origen == null || destino == null
                || fechaHora == null) {
            throw new IllegalArgumentException("Los datos del viaje no pueden ser nulos");
        }
        if (!usuarios.contains(cliente) || !servicios.contains(servicio)) {
            throw new IllegalArgumentException("El cliente y el servicio deben estar registrados");
        }
        Viaje viaje = new Viaje(origen, destino, cliente, servicio);
        viaje.solicitar(fechaHora);
        viajes.add(viaje);
        cliente.getCliente().agregarViaje(viaje);
        notificarSolicitud(viaje);
        return viaje;
    }

    // Buscar usuarios con conductores disponibles y compatibles.
    public List<Usuario> buscarConductoresDisponibles(Viaje viaje) {
        if (viaje == null || viaje.getServicio() == null) {
            return new ArrayList<>();
        }
        List<Usuario> disponibles = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            Conductor conductor = usuario.getConductor();
            if (conductor == null || conductor.getEstadoConductor() != EstadoConductor.DISPONIBLE
                    || conductor.getVehiculoActivo() == null
                    || !conductor.getVehiculoActivo().getTipoServicio()
                            .contains(viaje.getServicio().getTipoServicio())
                    || !categoriaCompatible(conductor.getCategoriaVehiculoActivo(),
                            viaje.getServicio().getCategoriaVehiculo())
                    || usuario.equals(viaje.getCliente())) {
                continue;
            }
            disponibles.add(usuario);
        }
        return disponibles;
    }

    // Obtener las solicitudes que un conductor puede aceptar.
    public List<Viaje> obtenerSolicitudesDisponibles(Usuario conductor) {
        if (conductor == null || conductor.getConductor() == null) {
            return new ArrayList<>();
        }
        List<Viaje> solicitudes = new ArrayList<>();
        for (Viaje viaje : viajes) {
            if (viaje.estadoActual() == EstadoViaje.SOLICITADO
                    && buscarConductoresDisponibles(viaje).contains(conductor)) {
                solicitudes.add(viaje);
            }
        }
        return solicitudes;
    }

    // Aceptar una solicitud todavía libre y comenzar el viaje al origen.
    public void aceptarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        Viaje viaje = viajeRequerido(idViaje);
        validarConductor(conductor);
        if (!buscarConductoresDisponibles(viaje).contains(conductor)) {
            throw new IllegalStateException("El conductor no está disponible o no es compatible");
        }
        viaje.aceptar(fechaHora, conductor);
        conductor.getConductor().setEstadoConductor(EstadoConductor.VIAJE_A_ORIGEN);
    }

    // Iniciar el traslado al destino con el conductor asignado.
    public void iniciarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        Viaje viaje = viajeRequerido(idViaje);
        validarConductorDelViaje(viaje, conductor);
        if (conductor.getConductor().getEstadoConductor() != EstadoConductor.VIAJE_A_ORIGEN) {
            throw new IllegalStateException("El conductor no está viajando al origen");
        }
        viaje.iniciar(fechaHora);
        conductor.getConductor().setEstadoConductor(EstadoConductor.VIAJE_A_DESTINO);
    }

    // Finalizar el traslado y dejar disponible al conductor.
    public void finalizarViaje(
            UUID idViaje,
            Usuario conductor,
            LocalDateTime fechaHora) {
        Viaje viaje = viajeRequerido(idViaje);
        validarConductorDelViaje(viaje, conductor);
        if (conductor.getConductor().getEstadoConductor() != EstadoConductor.VIAJE_A_DESTINO) {
            throw new IllegalStateException("El conductor no está trasladando al cliente");
        }
        viaje.finalizar(fechaHora, viaje.getCalificacionConductor(),
                viaje.getCalificacionCliente());
        conductor.getConductor().setEstadoConductor(EstadoConductor.DISPONIBLE);
    }

    // Registrar la calificación que el cliente da al conductor.
    public void calificarConductor(
            UUID idViaje,
            Usuario cliente,
            CalificacionViaje calificacion) {
        Viaje viaje = viajeRequerido(idViaje);
        validarCalificacion(viaje, cliente, viaje.getCliente(), calificacion);
        viaje.setCalificacionConductor(calificacion);
    }

    // Registrar la calificación que el conductor da al cliente.
    public void calificarCliente(
            UUID idViaje,
            Usuario conductor,
            CalificacionViaje calificacion) {
        Viaje viaje = viajeRequerido(idViaje);
        validarCalificacion(viaje, conductor, viaje.getConductor(), calificacion);
        viaje.setCalificacionCliente(calificacion);
    }

    // Calcular el cargo según quién cancela y el estado del viaje.
    // Antes de la aceptación, la cancelación del cliente cuesta cero.
    public double calcularCostoCancelacion(
            UUID idViaje,
            Usuario usuario,
            LocalDateTime fechaHora,
            double kilometrosRecorridos) {
        Viaje viaje = viajeRequerido(idViaje);
        if (usuario == null || fechaHora == null || kilometrosRecorridos < 0) {
            throw new IllegalArgumentException("Los datos de cancelación no son válidos");
        }
        if (!usuario.equals(viaje.getCliente()) && !usuario.equals(viaje.getConductor())) {
            throw new IllegalArgumentException("El usuario no participa en el viaje");
        }
        if (viaje.estadoActual() == EstadoViaje.SOLICITADO
                && usuario.equals(viaje.getCliente())) {
            return 0.0;
        }
        if (viaje.estadoActual() != EstadoViaje.ACEPTADO) {
            throw new IllegalStateException("El viaje no puede cancelarse en su estado actual");
        }
        return usuario.equals(viaje.getConductor()) ? 0.0
                : estimarCosto(viaje.getServicio(), viaje.getOrigen(), viaje.getDestino(),
                        estimarTiempo(viaje.getServicio(), viaje.getOrigen(), viaje.getDestino()));
    }

    // Cancelar, registrar el motivo y devolver el importe a cobrar.
    public double cancelarViaje(
            UUID idViaje,
            Usuario usuario,
            LocalDateTime fechaHora,
            String motivo,
            double kilometrosRecorridos) {
        Viaje viaje = viajeRequerido(idViaje);
        double costo = calcularCostoCancelacion(idViaje, usuario, fechaHora, kilometrosRecorridos);
        viaje.cancelar(fechaHora, usuario, motivo);
        if (viaje.getConductor() != null) {
            viaje.getConductor().getConductor().setEstadoConductor(EstadoConductor.DISPONIBLE);
        }
        notificarRechazo(viaje);
        return costo;
    }

    // Rechazar una solicitud pendiente cuyo tiempo de espera venció.
    public void rechazarViaje(
            UUID idViaje,
            LocalDateTime fechaHora) {
        Viaje viaje = viajeRequerido(idViaje);
        viaje.rechazar(fechaHora);
        if (viaje.getConductor() != null) {
            viaje.getConductor().getConductor().setEstadoConductor(EstadoConductor.DISPONIBLE);
        }
        notificarRechazo(viaje);
    }

    // Revisar las solicitudes y devolver las rechazadas en esta revisión.
    public List<Viaje> rechazarSolicitudesVencidas(LocalDateTime ahora) {
        if (ahora == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        List<Viaje> rechazadas = new ArrayList<>();
        for (Viaje viaje : viajes) {
            if (viaje.estadoActual() == EstadoViaje.SOLICITADO
                    && !viaje.getRegistrosViaje().isEmpty()
                    && Duration.between(viaje.getRegistrosViaje().get(0).getFechaHora(), ahora)
                            .toMinutes() >= 5) {
                rechazarViaje(viaje.getId(), ahora);
                rechazadas.add(viaje);
            }
        }
        return rechazadas;
    }

    // Buscar un viaje por su UUID.
    public Viaje buscarViajePorId(UUID idViaje) {
        if (idViaje == null) {
            return null;
        }
        for (Viaje viaje : viajes) {
            if (idViaje.equals(viaje.getId())) {
                return viaje;
            }
        }
        return null;
    }

    // Consultar los viajes del usuario como cliente.
    public List<Viaje> obtenerViajesCliente(Usuario usuario) {
        List<Viaje> resultado = new ArrayList<>();
        if (usuario == null) {
            return resultado;
        }
        for (Viaje viaje : viajes) {
            if (usuario.equals(viaje.getCliente())) {
                resultado.add(viaje);
            }
        }
        return resultado;
    }

    // Consultar los viajes del usuario como conductor.
    public List<Viaje> obtenerViajesConductor(Usuario usuario) {
        List<Viaje> resultado = new ArrayList<>();
        if (usuario == null) {
            return resultado;
        }
        for (Viaje viaje : viajes) {
            if (usuario.equals(viaje.getConductor())) {
                resultado.add(viaje);
            }
        }
        return resultado;
    }

    // Avisar a los conductores compatibles sobre una nueva solicitud.
    private void notificarSolicitud(Viaje viaje) {
        buscarConductoresDisponibles(viaje);
    }

    // Avisar al cliente que su solicitud fue rechazada.
    private void notificarRechazo(Viaje viaje) {
        if (viaje == null) {
            throw new IllegalArgumentException("El viaje no puede ser nulo");
        }
    }

    private Viaje viajeRequerido(UUID idViaje) {
        Viaje viaje = buscarViajePorId(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("No existe un viaje con ese identificador");
        }
        return viaje;
    }

    private void validarConductor(Usuario conductor) {
        if (conductor == null || conductor.getConductor() == null) {
            throw new IllegalArgumentException("El usuario no está habilitado como conductor");
        }
    }

    private void validarConductorDelViaje(Viaje viaje, Usuario conductor) {
        validarConductor(conductor);
        if (!conductor.equals(viaje.getConductor())) {
            throw new IllegalArgumentException("El conductor no está asignado al viaje");
        }
    }

    private void validarCalificacion(Viaje viaje, Usuario evaluador,
            Usuario evaluado, CalificacionViaje calificacion) {
        if (evaluador == null || !evaluador.equals(evaluado)) {
            throw new IllegalArgumentException("El usuario no corresponde al viaje");
        }
        if (calificacion == null || viaje.estadoActual() != EstadoViaje.FINALIZADO) {
            throw new IllegalStateException("El viaje debe estar finalizado y la calificación ser válida");
        }
    }

    private boolean categoriaCompatible(CategoriaVehiculo disponible,
            CategoriaVehiculo solicitada) {
        return disponible != null && solicitada != null
                && disponible.getCodigo() >= solicitada.getCodigo();
    }
}