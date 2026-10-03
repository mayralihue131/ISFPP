package md_modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	private List<Viaje> viajes;

	public Cliente(List<Viaje> viajes) {
		this.viajes = new ArrayList<Viaje>();
	}

	public List<Viaje> getViajes() {
		return new ArrayList<>(viajes);
	}	
	public void agregarViaje(Viaje viaje) {
		viajes.add(viaje);
	}
	public boolean enViaje() {
		for(Viaje viaje: viajes) {
			if(viaje.estadoActual() == EstadoViaje.INICIADO )
				return true;
		}
	}
	
	
}
