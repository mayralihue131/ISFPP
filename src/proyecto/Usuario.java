package proyecto;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	private String nombre;
	private String documento;
	private String telefono;
	private String email;
	private int totalCalificaciones; //readOnly
	private List<Calificacion> calificaciones;
	private List<Viaje> viajes;
	
	public Usuario(String nombre, String documento, String telefono, String email) {
		this.nombre = nombre;
		this.documento = documento;
		this.telefono = telefono;
		this.email = email;
		this.totalCalificaciones = 0;
		this.calificaciones = new ArrayList<Calificacion>();
		this.viajes = new ArrayList<Viaje>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTotalCalificaciones() {
		return totalCalificaciones;
	}
	
	public List<Calificacion> getCalificaciones() {
		return new ArrayList<>(calificaciones);
	}
	
	public List<Viaje> getViajes() {
		return new ArrayList<>(viajes);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documento == null) ? 0 : documento.hashCode());
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
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		return true;
	}

	public  void calificarViaje(Calificacion calificacion) {
		if (calificacion == null) {
			return; //  Eligió "skip": no se guarda ni se cuenta	    
		}
		 calificaciones.add(calificacion);
		 totalCalificaciones++;
	}
	

	public  double promedioCalificacion(){
		if(calificaciones.isEmpty()) {
			return 0;
		}
		
		double suma = 0;
		
		for(Calificacion calificacion: calificaciones) {
			suma = suma + calificacion.ordinal() + 1;
		}
		
		return suma / calificaciones.size();
	}
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", documento=" + documento + ", telefono=" + telefono + ", email=" + email
				+ ", totalCalificaciones=" + totalCalificaciones + "]";
	}
	
}
