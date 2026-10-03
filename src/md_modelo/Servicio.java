package md_modelo;

public class Servicio {

    private String nombre;
    private double tarifaBase;
    private double precioKm;
    private double precioMinuto;
    private CategoriaVehiculo categoriaVehiculo;
    private TipoVehiculo tipoVehiculo;
    private TipoServicio tipoServicio;

    public Servicio() {
    }

    public Servicio(String nombre, double tarifaBase, double precioKm, double precioMinuto,
            CategoriaVehiculo categoriaVehiculo, TipoVehiculo tipoVehiculo, TipoServicio tipoServicio) {
        this.nombre = nombre;
        this.tarifaBase = tarifaBase;
        this.precioKm = precioKm;
        this.precioMinuto = precioMinuto;
        this.categoriaVehiculo = categoriaVehiculo;
        this.tipoVehiculo = tipoVehiculo;
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

    public double getPrecioKm() {
        return precioKm;
    }

    public void setPrecioKm(double precioKm) {
        this.precioKm = precioKm;
    }

    public double getPrecioMinuto() {
        return precioMinuto;
    }

    public void setPrecioMinuto(double precioMinuto) {
        this.precioMinuto = precioMinuto;
    }

    public CategoriaVehiculo getCategoriaVehiculo() {
        return categoriaVehiculo;
    }

    public void setCategoriaVehiculo(CategoriaVehiculo categoriaVehiculo) {
        this.categoriaVehiculo = categoriaVehiculo;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public double calcularCosto(double km, double minutos) {
        // TODO: Implementar cálculo de costo usando tarifaBase, precioKm y precioMinuto
        return 0.0;
    }

    @Override
    public String toString() {
        return "Servicio{" + "nombre='" + nombre + '\'' + ", tarifaBase=" + tarifaBase + ", precioKm=" + precioKm
                + ", precioMinuto=" + precioMinuto + ", categoriaVehiculo=" + categoriaVehiculo + ", tipoVehiculo="
                + tipoVehiculo + ", tipoServicio=" + tipoServicio + '}';
    }
}
