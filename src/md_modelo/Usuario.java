package md_modelo;

public class Usuario {
	private String nombre;
	private String telefono;
	private String email;
	private Conductor conductor;
	private Cliente cliente;
	private RolUsuario rolActivo;
			
	public Usuario(String nombre, String telefono, String email) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.conductor = null;
		this.cliente = new Cliente();
		this.rolActivo = RolUsuario.CLIENTE;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}//preguntar si es necesario si es un identificador inmutable

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Conductor getConductor() {
		return conductor;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public RolUsuario getRolActivo() {
		return rolActivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
	/**
	 * Da de alta el perfil de conductor del usuario con una licencia
	 * y un vehículo inicial.
	 * La licencia no puede ser null, vacía ni contener solo espacios.
	 * El vehículo no puede ser null y el usuario no debe tener
	 * un perfil de conductor previamente.
	 */
	public void altaConductor(String licencia, Vehiculo vehiculo) {
	
		if(vehiculo == null) {
	        throw new IllegalArgumentException("El vehículo no puede ser null");
		}
		
		if (licencia == null || licencia.isBlank()) {
	        throw new IllegalArgumentException("La licencia no puede estar vacía");
	    }
		
		if(this.conductor == null) {
			
			this.conductor = new Conductor( licencia,  EstadoConductor.FUERA_DE_SERVICIO, vehiculo.getCategoriaVehiculo(),  vehiculo);
		
		}else {
		    throw new IllegalStateException("El usuario ya tiene un perfil de conductor");	
		}

	}
	/**
	 * Cambia el rol activo del usuario a CLIENTE o CONDUCTOR.
	 * El nuevo rol no puede ser null.
	 * Para activar el rol CONDUCTOR, el usuario debe tener
	 * previamente ese perfil.
	 */
	public void cambiarRolActivo(RolUsuario rolNuevo) {
		if(rolNuevo == null) {
			throw new IllegalArgumentException("El nuevo rol no puede ser null");
		}
		
		if(rolNuevo == RolUsuario.CONDUCTOR && this.conductor == null) {
			throw new IllegalStateException("El usuario no tiene un perfil de conductor");
		}
		
		this.rolActivo = rolNuevo;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + ", conductor=" + conductor
				+ ", cliente=" + cliente + ", rolActivo=" + rolActivo + "]";
	}
	
}
