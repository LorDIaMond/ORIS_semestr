package interfaces.repo;

import modules.Product;

import java.util.List;

public interface IProductRepository extends ICrudRepository<Product, Long> {
    List<Product> findByFilter(String[] categoryIds, String[] priceRanges);
    public void delete(Long id);
    public void update(Product product);
    public List<Product> findAll();
}
