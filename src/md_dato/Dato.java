package md_dato;

import java.io.FileNotFoundException;
import java.util.Map;

import md_modelo.Servicio;
import md_modelo.Usuario;
import md_modelo.Vehiculo;

public class Dato {

    /**
     * Lee vehiculos.txt y crea los vehículos.
     * Formato:
     * patente;modelo;capacidad;tipoVehiculo;categoria;tipoServicio;...
     *
     * Ignora líneas vacías y comentarios que comienzan con #.
     * Devuelve un mapa de vehículos indexado por patente.
     */
    public static Map<String, Vehiculo> cargarVehiculos(String fileName)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de vehículos.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Lee usuarios.txt y crea los usuarios.
     * Formato:
     * nombre;telefono;email;licencia;patente;...
     *
     * Si tiene licencia y vehículos, da de alta su perfil de conductor.
     * Busca las patentes en el mapa de vehículos previamente cargado.
     * Devuelve un mapa de usuarios indexado por teléfono.
     */
    public static Map<String, Usuario> cargarUsuarios(
            String fileName, Map<String, Vehiculo> vehiculos)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de usuarios.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }

    /**
     * Lee servicios.txt y crea los servicios.
     * Formato:
     * nombre;tarifaBase;precioKm;precioMinuto;tipoVehiculo;categoria;tipoServicio
     *
     * Devuelve un mapa de servicios indexado por nombre.
     */
    public static Map<String, Servicio> cargarServicios(String fileName)
            throws FileNotFoundException {

        // TODO: implementar lectura y creación de servicios.
        throw new UnsupportedOperationException("Pendiente de implementar");
    }
}