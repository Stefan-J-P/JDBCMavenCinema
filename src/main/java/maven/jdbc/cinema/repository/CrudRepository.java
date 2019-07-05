package maven.jdbc.cinema.repository;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T>
{
    void add(T t);
    void update(T t);
    void deleteOne(Integer id);
    void deleteAll();
    Optional<T> findOneById(Integer id);
    List<T> findAll();

}
