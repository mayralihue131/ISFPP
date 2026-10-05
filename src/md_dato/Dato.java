package md_dato;

import java.io.FileNotFoundException;
import java.util.List;

import md_modelo.Servicio;
import md_modelo.Usuario;
import md_modelo.Vehiculo;

public class Dato {

    /**
     * Lee vehiculos.txt y devuelve una lista de vehículos.
     * Formato:
     * patente;modelo;capacidad;tipoVehiculo;categoria;tipoServicio;...
     *
     * Ignora líneas vacías y comentarios que comienzan con #.
     */
    public static List<Vehiculo> cargarVehiculos(String fileName)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de vehículos.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Lee usuarios.txt y devuelve una lista de usuarios.
     * Formato:
     * nombre;telefono;email;licencia;patente;...
     *
     * Si hay licencia y patentes, da de alta el perfil de conductor.
     * Busca los vehículos en la lista previamente cargada.
     */
    public static List<Usuario> cargarUsuarios(
            String fileName, List<Vehiculo> vehiculos)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de usuarios.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Lee servicios.txt y devuelve una lista de servicios.
     * Formato:
     * nombre;tarifaBase;precioKm;precioMinuto;tipoVehiculo;categoria;tipoServicio
     *
     * Ignora líneas vacías y comentarios que comienzan con #.
     */
    public static List<Servicio> cargarServicios(String fileName)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de servicios.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Busca un vehículo por patente recorriendo la lista.
     * Devuelve null si no existe.
     */
    public static Vehiculo buscarVehiculo(
            String patente, List<Vehiculo> vehiculos) {

        // TODO: implementar búsqueda.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Busca un usuario por teléfono recorriendo la lista.
     * Devuelve null si no existe.
     */
    public static Usuario buscarUsuario(
            String telefono, List<Usuario> usuarios) {

        // TODO: implementar búsqueda.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}