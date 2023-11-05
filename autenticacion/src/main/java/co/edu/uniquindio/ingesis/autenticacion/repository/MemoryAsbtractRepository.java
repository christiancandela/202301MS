package co.edu.uniquindio.ingesis.autenticacion.repository;

import lombok.extern.java.Log;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Clase que implementa de forma general un repositorio con soporte de {@link Stream} almacenando los elementos en memoria.
 * @param <T> Tipo de elementos a ser gestionados por el repositorio.
 * @author Alexandra Ruiz Gaona
 * @author Christian A. Candela-Uribe
 * @author Luis E. Sepúlveda-Rodríguez
 * @since 2023
 * <p>
 * (<a href="https://raw.githubusercontent.com/grid-uq/poo/main/LICENSE">Licencia GNU/GPL V3.0</a>)
 */
@Log
public abstract class MemoryAsbtractRepository<T extends Entity> implements StreamRepository<T>{
    private final Map<String, T> data;

    public MemoryAsbtractRepository() {
        this.data = new HashMap<>();
    }

    /**
     * @see Repository#getAll()
     */
    @Override
    public Collection<T> getAll() {
        return data.values();
    }
    /**
     * @see Repository#save(Object) 
     */
    @Override
    public T save(T item) {
        Objects.requireNonNull(item,"El elemento a almacenar no puede ser nulo");
        data.put(item.id(),item);
        log.info("Se adicionó elemento"+item);
        return item;
    }
    /**
     * @see Repository#findById(String) 
     */
    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable( data.get(id) );
    }
    /**
     * @see Repository#deleteById(String) 
     */
    @Override
    public void deleteById(String id) {
        data.remove(id);
    }
    /**
     * @see StreamRepository#find(Predicate) 
     */
    @Override
    public Collection<T> find(Predicate<T> predicate) {
        return stream().filter(predicate).toList();
    }
    /**
     * @see StreamRepository#find(Predicate, Comparator)   
     */
    @Override
    public Collection<T> find(Predicate<T> predicate, Comparator<T> comparator) {
        return stream().filter(predicate).sorted(comparator).toList();
    }
    /**
     * @see StreamRepository#findAny(Predicate)
     */
    @Override
    public Optional<T> findAny(Predicate<T> predicate) {
        return stream().filter(predicate).findAny();
    }
    /**
     * @see StreamRepository#findFirs(Predicate)
     */
    @Override
    public Optional<T> findFirs(Predicate<T> predicate) {
        return stream().filter(predicate).findFirst();
    }
    /**
     * @see StreamRepository#stream()
     */
    @Override
    public Stream<T> stream() {
        return data.values().stream();
    }
}
