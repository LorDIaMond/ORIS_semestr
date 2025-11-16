package services;

import interfaces.repo.ICategoryRepository;
import interfaces.repo.IProductRepository;
import interfaces.serv.IShopService;
import modules.Category;
import modules.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopService implements IShopService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public ShopService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getFilteredProducts(String[] categoryIds, String[] priceRanges) {
        return productRepository.findByFilter(categoryIds, priceRanges);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Map<Long, Boolean> getFavoriteStatus(Long userId, List<Product> products) {
        return new HashMap<>();
    }

    @Override
    public Map<Long, Boolean> getCartStatus(Long userId, List<Product> products) {
        return new HashMap<>();
    }
}
