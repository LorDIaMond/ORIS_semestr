package interfaces.serv;

import modules.Product;
import modules.Category;
import java.util.List;
import java.util.Map;

public interface IShopService {
    List<Product> getFilteredProducts(String[] categoryIds, String[] priceRanges);
    List<Category> getAllCategories();
    Map<Long, Boolean> getFavoriteStatus(Long userId, List<Product> products);
    Map<Long, Boolean> getCartStatus(Long userId, List<Product> products);
}
