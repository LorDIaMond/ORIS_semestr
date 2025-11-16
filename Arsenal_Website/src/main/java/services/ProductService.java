package services;

import interfaces.repo.IProductRepository;
import interfaces.repo.IUserRepository;
import interfaces.serv.IProductService;
import modules.Product;
import modules.User;

public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final IUserRepository userRepository;

    public ProductService(IProductRepository productRepository, IUserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public User getUserById(Long userId) {
        return userId != null ? userRepository.findById(userId) : null;
    }
}
