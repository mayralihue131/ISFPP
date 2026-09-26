package proyecto;

public class Conductor extends Usuario{
	private boolean disponible;
	private static int limiteCalificaciones = 200;
	public Conductor(String nombre, String documento, String telefono, String email, int totalCalificaciones,
			boolean disponible) {
		super(nombre, documento, telefono, email, totalCalificaciones);
		this.disponible = disponible;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public static int getLimiteCalificaciones() {
		return limiteCalificaciones;
	}
	public static void setLimiteCalificaciones(int limiteCalificaciones) {
		Conductor.limiteCalificaciones = limiteCalificaciones;
	}
	@Override
	public String toString() {
		return "Conductor [disponible=" + disponible + "]";
	}
	
	
	
}
