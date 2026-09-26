package proyecto;

public class Cliente extends Usuario{
	private static int limiteCalificaciones = 100;

	public Cliente(String nombre, String documento, String telefono, String email, int totalCalificaciones) {
		super(nombre, documento, telefono, email, totalCalificaciones);
	}

	public static int getLimiteCalificaciones() {
		return limiteCalificaciones;
	}

	public static void setLimiteCalificaciones(int limiteCalificaciones) {
		Cliente.limiteCalificaciones = limiteCalificaciones;
	}

			
	
}
