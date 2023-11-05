package co.edu.uniquindio.ingesis.autenticacion.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
/**
 * Interfaz que define un elemento el conjunto de operaciones básicas de un repositorio con soporte de streams.
 * @param <T> Tipo de dato a ser gestionado por el repositorio.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
public interface StreamRepository<T> extends Repository<T>{
    /**
     * Permite obtener el conjunto de elementos que cumpla con el predicado dado.
     * @param predicate Predicado (condición) a ser usada para filtrar el conjunto de elementos.
     * @return Collection de elementos que cumplen con el {@link Predicate} dado.
     */
    Collection<T> find(Predicate<T> predicate);
    /**
     * Permite obtener el conjunto de elementos que cumpla con el predicado dado ordenados según el {@link Comparator}.
     * @param predicate Predicado (condición) a ser usada para filtrar el conjunto de elementos.
     * @param comparator Comparetor usado para ordenar el conjunto de elementos.
     * @return Collection de elementos que cumplen con el {@link Predicate} dado ordenados por el {@link Comparator} dado.
     */
    Collection<T> find(Predicate<T> predicate, Comparator<T> comparator);
    /**
     * Permite obtener un elemento del conjunto de elementos registrados que cumpla con el predicado dado.
     * @param predicate Predicado (condición) a ser usado como condición para encontrar un elemento que lo cumpla.
     * @return Optional con un elemento que cumplen con el {@link Predicate} dado. En caso de no encontrar un elemento
     * que cumpla con él {@link Predicate}, retorna un {@link Optional} vacío.
     */
    Optional<T> findAny(Predicate<T> predicate);
    /**
     * Permite obtener el primer elemento del conjunto de elementos registrados que cumpla con el predicado dado.
     * @param predicate Predicado (condición) a ser usado como condición para encontrar el primer elemento que lo cumpla.
     * @return Optional con el primer elemento que cumplen con el {@link Predicate} dado. En caso de no encontrar un elemento
     * que cumpla con él {@link Predicate}, retorna un {@link Optional} vacío.
     */
    Optional<T> findFirs(Predicate<T> predicate);

    /**
     * Permite obtener un Stream basado en el conjunto de elementos registrados.
     * @return Stream del conjunto de elementos registrados.
     */
    Stream<T> stream();
}