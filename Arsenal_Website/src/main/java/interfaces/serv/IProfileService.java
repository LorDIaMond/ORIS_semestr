package interfaces.serv;

import modules.Product;
import modules.User;

import java.util.List;

public interface IProfileService {
    public User getUserById(Long userId);
    public List<Product> getFavoriteProducts(Long userId);
    public List<Product> getOrderedProducts(Long userId);
}
