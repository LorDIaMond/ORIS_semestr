package interfaces.serv;

import jakarta.servlet.http.Part;
import modules.Category;
import modules.Product;

import java.util.List;

public interface IAdminService {
    List<Product> getAllProducts();
    List<Category> getAllCategories();
    void createProduct(String name, String description, double price, long categoryId, Part imagePart, String contextPath);
    void deleteProduct(long productId);
    Product getProductById(long id);
    void updateProduct(long id, String name, String description, double price, long categoryId);
}
