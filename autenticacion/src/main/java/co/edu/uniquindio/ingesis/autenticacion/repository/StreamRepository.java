package co.edu.uniquindio.ingesis.autenticacion.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StreamRepository<T> extends Repository<T>{
    Collection<T> find(Predicate<T> predicate);
    Collection<T> find(Predicate<T> predicate, Comparator<T> comparator);

    Optional<T> findAny(Predicate<T> predicate);

    Optional<T> findFirs(Predicate<T> predicate);

    Stream<T> stream();
}