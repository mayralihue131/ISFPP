package md_modelo;

public class Ubicacion {

    private double latitud;
    private double longitud;

    public Ubicacion() {
    }

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * Calcula la distancia respecto a otra ubicación.
     * Esqueleto sin implementar.
     */
    public double calcularDistancia(Ubicacion ubicacion) {
        // TODO: Implementar cálculo de distancia (ej. fórmula de Haversine)
        return 0.0;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
