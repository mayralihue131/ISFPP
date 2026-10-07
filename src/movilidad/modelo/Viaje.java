package movilidad.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Viaje {

    private UUID id;
    private String motivoCancelacion;
    private Ubicacion origen;
    private Ubicacion destino;
    private Usuario cliente;
    private Usuario conductor;
    private Vehiculo vehiculo;
    private Servicio servicio;
    private RolUsuario rolCancela;
    private CalificacionViaje calificacionConductor;
    private CalificacionViaje calificacionCliente;
    private List<RegistroViaje> registrosViaje;

    public Viaje() {
        this.id = UUID.randomUUID();
        this.registrosViaje = new ArrayList<>();
    }

    public Viaje(Ubicacion origen, Ubicacion destino, Usuario cliente, Servicio servicio) {
        this.id = UUID.randomUUID();
        this.origen = origen;
        this.destino = destino;
        this.cliente = cliente;
        this.servicio = servicio;
        this.registrosViaje = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getConductor() {
        return conductor;
    }

    public void setConductor(Usuario conductor) {
        this.conductor = conductor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public RolUsuario getRolCancela() {
        return rolCancela;
    }

    public void setRolCancela(RolUsuario rolCancela) {
        this.rolCancela = rolCancela;
    }

    public CalificacionViaje getCalificacionConductor() {
        return calificacionConductor;
    }

    public void setCalificacionConductor(CalificacionViaje calificacionConductor) {
        this.calificacionConductor = calificacionConductor;
    }

    public CalificacionViaje getCalificacionCliente() {
        return calificacionCliente;
    }

    public void setCalificacionCliente(CalificacionViaje calificacionCliente) {
        this.calificacionCliente = calificacionCliente;
    }

    public List<RegistroViaje> getRegistrosViaje() {
        return new ArrayList<>(registrosViaje);
    }

    public void solicitar(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y la hora de la solicitud no pueden ser nulas");
        }

        if (this.registrosViaje == null) {
            this.registrosViaje = new ArrayList<>();
        }

        // un viaje solo puede ser solicitado si aún no tiene registros
        if (!this.registrosViaje.isEmpty()) {
            throw new IllegalStateException("El viaje ya ha sido solicitado previamente");
        }

        RegistroViaje registro = new RegistroViaje(fechaHora, EstadoViaje.SOLICITADO);
        this.registrosViaje.add(registro);
    }

    public void aceptar(LocalDateTime fechaHora, Usuario conductor) {

        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
        }
        if (conductor.getConductor() == null) {
            throw new IllegalArgumentException("El usuario no esta habilitado como conductor");
        }

        if (this.estadoActual() != EstadoViaje.SOLICITADO) {
            throw new IllegalStateException("El viaje solo puede ser aceptado si se encuentra en estado SOLICITADO");
        }

        if (this.cliente != null && this.cliente.equals(conductor)) {
            throw new IllegalStateException("El conductor no puede ser el mismo usuario que solicito el viaje");
        }

        if (conductor.getConductor().getVehiculoActivo() != null) {
            this.vehiculo = conductor.getConductor().getVehiculoActivo();
        }

        this.conductor = conductor;
        RegistroViaje registro = new RegistroViaje(fechaHora, EstadoViaje.ACEPTADO);
        this.registrosViaje.add(registro);
    }
    
    //:))
    /**
     * Inicia el viaje cuando el pasajero sube al vehículo.
     * Esqueleto sin implementar.
     */
    public void iniciar(LocalDateTime fechaHora) {
    		if(fechaHora == null) {
    			throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
    		}
    		
    		if (conductor == null || conductor.getConductor() == null) {
    		    throw new IllegalStateException("El viaje no tiene un conductor habilitado");
    		}
    		if (this.estadoActual() == EstadoViaje.INICIADO) {
            throw new IllegalStateException("El viaje ya está iniciado");
        }
    		if (this.estadoActual() != EstadoViaje.ACEPTADO) {
    		    throw new IllegalStateException("El viaje no está en condiciones de iniciarse");
    		}
        RegistroViaje registro = new RegistroViaje(fechaHora, EstadoViaje.INICIADO);
        this.registrosViaje.add(registro);
        
    }
    //;(((
    /**
     * Finaliza el viaje y asienta las calificaciones mutuas.
     * Esqueleto sin implementar.
     */
    public void finalizar(LocalDateTime fechaHora, CalificacionViaje calificacionConductor,
            CalificacionViaje calificacionCliente) {
    	
    		if(fechaHora == null) {
    			throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
    		}
    		if(this.estadoActual() != EstadoViaje.INICIADO) {
            throw new IllegalStateException("El viaje solo puede finalizarse si está INICIADO");
    		}
    		if (conductor == null || conductor.getConductor() == null) {
    		    throw new IllegalArgumentException("El usuario no está habilitado como conductor");
    		}
    		
    		this.calificacionConductor = calificacionConductor;
    		this.calificacionCliente = calificacionCliente;
    		
    		RegistroViaje registro = new RegistroViaje(fechaHora, EstadoViaje.FINALIZADO);
        this.registrosViaje.add(registro);
      
    }

    	//:(
    /**
     * Cancela el viaje indicando el usuario y motivo.
     * Esqueleto sin implementar.
     */
    public void cancelar(LocalDateTime fechaHora, Usuario usuario, String motivo) {
    	
    		if(fechaHora == null) {
    			throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");  			
    		}

    		if(usuario == null) {
    			throw new IllegalArgumentException("El usuario no puede ser nulo");  			

    		}
    		if(motivo == null || motivo.isBlank()) {
    			throw new IllegalArgumentException("El motivo tiene que ser explicado");  			

    		}
    		if(this.estadoActual() != EstadoViaje.ACEPTADO) {
    			throw new IllegalStateException("Se cancela un viaje cuando fue aceptado");  			

    		}
    		boolean esCliente = usuario.equals(this.cliente);
    		boolean esConductor = usuario.equals(this.conductor);
    		
    		if(!esCliente && !esConductor) {
    			throw new IllegalArgumentException("Solo puede cancelar el cliente o el conudctor el viaje");  			

    		}
    		   		
    		this.motivoCancelacion = motivo;
    		
    		if(esCliente) {
    			this.rolCancela = RolUsuario.CLIENTE;
    		}else{
    			this.rolCancela = RolUsuario.CONDUCTOR;
    		}
    		RegistroViaje registro = new RegistroViaje(fechaHora, EstadoViaje.CANCELADO);
    		this.registrosViaje.add(registro);
    }

    /**
     * El conductor rechaza el viaje ofrecido.
     * Esqueleto sin implementar.
     */
    
    //:)
    public void rechazar(LocalDateTime fechaHora) {
    		if(fechaHora == null) {
    			throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
    		}
    		if(this.estadoActual() != EstadoViaje.ACEPTADO) {
    			throw new IllegalStateException("El viaje puede ser rechazado si esta aceptado");
    		}
    		
    		if(this.conductor == null || this.conductor.getConductor() == null) {
    			throw new IllegalStateException("El viaje tiene que tener un conductor");

    		}
    		       
    		RegistroViaje registro = new RegistroViaje(fechaHora,EstadoViaje.RECHAZADO);
    		this.registrosViaje.add(registro);
      
    }
    
    /**
     * Retorna el estado actual del viaje según el último registro histórico.
     */
    public EstadoViaje estadoActual() {
        if (registrosViaje == null || registrosViaje.isEmpty()) {
            return null;
        }
        return registrosViaje.get(registrosViaje.size() - 1).getEstadoViaje();
    }

    @Override
    public String toString() {
        return "Viaje{" + "id=" + id + ", motivoCancelacion='" + motivoCancelacion + '\'' + ", origen=" + origen
                + ", destino=" + destino + ", cliente=" + (cliente != null ? cliente.getNombre() : "null")
                + ", conductor=" + (conductor != null ? conductor.getNombre() : "null") + ", vehiculo="
                + (vehiculo != null ? vehiculo.getPatente() : "null") + ", servicio="
                + (servicio != null ? servicio.getNombre() : "null") + ", rolCancela=" + rolCancela
                + ", calificacionConductor=" + calificacionConductor + ", calificacionCliente=" + calificacionCliente
                + '}';
    }
}