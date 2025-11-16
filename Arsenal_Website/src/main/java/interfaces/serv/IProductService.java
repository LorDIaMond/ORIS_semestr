package interfaces.serv;

import modules.Product;
import modules.User;

public interface IProductService {
    Product getProductById(long id);
    User getUserById(Long userId);
}
