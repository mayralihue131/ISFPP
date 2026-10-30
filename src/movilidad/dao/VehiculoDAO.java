package movilidad.dao;

import java.util.List;
import movilidad.modelo.Vehiculo;

public interface VehiculoDAO {

    void guardar(Vehiculo vehiculo);

    Vehiculo buscarPorPatente(String patente);

    List<Vehiculo> listarTodos();
}
