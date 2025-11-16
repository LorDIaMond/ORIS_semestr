package interfaces.repo;

public interface ICrudRepository<T, ID> {
    T findById(ID id);
    void save(T entity);
}