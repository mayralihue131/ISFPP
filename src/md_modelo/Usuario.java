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
	
	public void altaConductor(String licencia, Vehiculo vehiculo) {
		return;
	}
	public void cambiarRolActivo(RolUsuario rolNuevo) {
		return ;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + ", conductor=" + conductor
				+ ", cliente=" + cliente + ", rolActivo=" + rolActivo + "]";
	}
	
}
