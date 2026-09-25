package proyecto;

public class Auto extends Vehiculo{
	private int capacidadPasajeros;

	public Auto(String patente, String modelo, boolean aceptaPasajero, boolean aceptaCraga, Servicio servicio,
			Ubicacion ubicacion, int capacidadPasajeros) {
		super(patente, modelo, aceptaPasajero, aceptaCraga, servicio, ubicacion);
		this.capacidadPasajeros = capacidadPasajeros;
	}

	public int getCapacidadPasajeros() {
		return capacidadPasajeros;
	}

	public void setCapacidadPasajeros(int capacidadPasajeros) {
		this.capacidadPasajeros = capacidadPasajeros;
	}

	@Override
	public String toString() {
		return "Auto [capacidadPasajeros=" + capacidadPasajeros + "]";
	}
	
}
