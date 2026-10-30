package movilidad.dao;

import java.util.List;
import movilidad.modelo.Servicio;

public interface ServicioDAO {

    void guardar(Servicio servicio);

    List<Servicio> listarTodos();
}
