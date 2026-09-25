package proyectopartecris;

/**
 * Representa la configuración de tarifas y tipo de servicio ofrecido.
 */
public class Servicio {

    private String nombre;
    private double tarifaBase;
    private double precioPorKm;
    private double precioPorMinuto;
    private TipoServicio tipoServicio;

    public Servicio() {
    }

    public Servicio(String nombre, double tarifaBase, double precioPorKm, double precioPorMinuto, TipoServicio tipoServicio) {
        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.precioPorKm = precioPorKm;
        this.precioPorMinuto = precioPorMinuto;
        this.tipoServicio = tipoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getPrecioPorKm() {
        return precioPorKm;
    }

    public void setPrecioPorKm(double precioPorKm) {
        this.precioPorKm = precioPorKm;
    }

    public double getPrecioPorMinuto() {
        return precioPorMinuto;
    }

    public void setPrecioPorMinuto(double precioPorMinuto) {
        this.precioPorMinuto = precioPorMinuto;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "nombre='" + nombre + '\'' +
                ", tarifaBase=" + tarifaBase +
                ", precioPorKm=" + precioPorKm +
                ", precioPorMinuto=" + precioPorMinuto +
                ", tipoServicio=" + tipoServicio +
                '}';
    }
}
