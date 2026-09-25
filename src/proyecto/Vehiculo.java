package proyecto;

public abstract class Vehiculo {
	private String patente;
	private String modelo;
	private boolean aceptaPasajero;
	private boolean aceptaCraga;
	private Servicio servicio;
	private Ubicacion ubicacion;
	
	public Vehiculo(String patente, String modelo, boolean aceptaPasajero, boolean aceptaCraga, Servicio servicio,
			Ubicacion ubicacion) {
		this.patente = patente;
		this.modelo = modelo;
		this.aceptaPasajero = aceptaPasajero;
		this.aceptaCraga = aceptaCraga;
		this.servicio = servicio;
		this.ubicacion = ubicacion;
	}
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public boolean isAceptaPasajero() {
		return aceptaPasajero;
	}
	public void setAceptaPasajero(boolean aceptaPasajero) {
		this.aceptaPasajero = aceptaPasajero;
	}
	public boolean isAceptaCraga() {
		return aceptaCraga;
	}
	public void setAceptaCraga(boolean aceptaCraga) {
		this.aceptaCraga = aceptaCraga;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patente == null) ? 0 : patente.hashCode());
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
		Vehiculo other = (Vehiculo) obj;
		if (patente == null) {
			if (other.patente != null)
				return false;
		} else if (!patente.equals(other.patente))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Vehiculo [patente=" + patente + ", modelo=" + modelo + ", aceptaPasajero=" + aceptaPasajero
				+ ", aceptaCraga=" + aceptaCraga + "]";
	}

	
}
