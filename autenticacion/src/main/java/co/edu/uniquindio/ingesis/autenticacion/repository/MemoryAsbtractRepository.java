package co.edu.uniquindio.ingesis.autenticacion.repository;

import lombok.extern.java.Log;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Log
public abstract class MemoryAsbtractRepository<T extends Entity> implements StreamRepository<T>{
    private final Map<String, T> data;

    public MemoryAsbtractRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public Collection<T> getAll() {
        return data.values();
    }

    @Override
    public T save(T item) {
        Objects.requireNonNull(item,"El elemento a almacenar no puede ser nulo");
        data.put(item.id(),item);
        log.info("Se adicionó elemento"+item);
        return item;
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable( data.get(id) );
    }

    @Override
    public void deleteById(String id) {
        data.remove(id);
    }

    @Override
    public Collection<T> find(Predicate<T> predicate) {
        return stream().filter(predicate).toList();
    }

    @Override
    public Collection<T> find(Predicate<T> predicate, Comparator<T> comparator) {
        return stream().filter(predicate).sorted(comparator).toList();
    }

    @Override
    public Optional<T> findAny(Predicate<T> predicate) {
        return stream().filter(predicate).findAny();
    }

    @Override
    public Optional<T> findFirs(Predicate<T> predicate) {
        return stream().filter(predicate).findFirst();
    }

    @Override
    public Stream<T> stream() {
        return data.values().stream();
    }
}
