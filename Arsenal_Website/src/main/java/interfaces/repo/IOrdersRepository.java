package interfaces.repo;

import modules.Product;

import java.util.List;

public interface IOrdersRepository {
    public void addToOrders(Long userId, Long productId, int quantity);
    public void removeFromOrders(Long userId, Long productId);
    public boolean isInOrders(Long userId, Long productId);
    public List<Product> getOrderedProducts(Long userId);
}
