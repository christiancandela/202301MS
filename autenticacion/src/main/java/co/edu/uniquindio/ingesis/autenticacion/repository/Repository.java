package co.edu.uniquindio.ingesis.autenticacion.repository;

import java.util.Collection;
import java.util.Optional;
/**
 * Interfaz que define un elemento el conjunto de operaciones básicas de un repositorio para la gestión de datos.
 * @param <T> Tipo de dato a ser gestionado por el repositorio.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface Repository<T> {
    /**
     * Permite obtener todos los elementos registrados.
     * @return Collection con los elementos registrados.
     */
    Collection<T> getAll();

    /**
     * Permite guardar un elemento, si no existe lo registra, en caso de que el elemento ya haya sido registrado lo actualiza.
     * @param item Item a ser guardado.
     * @return El item guardado.
     */
    T save(T item);

    /**
     * Permite buscar un elemento basado en su id.
     * @param id Id del elemento a ser buscado.
     * @return Optional con el elemento correspondiente al id dado, en caso de no encontrar un elemento con dicho id se
     * retorna un Optional vacío.
     */
    Optional<T> findById(String id);

    /**
     * Permite eliminar un elemento a partir de us id
     * @param id Identificador del elemento que desea borrarse.
     */
    void deleteById(String id);
}