package services;

import interfaces.repo.IFavoritesRepository;
import interfaces.repo.IOrdersRepository;
import interfaces.repo.IUserRepository;
import interfaces.serv.IProfileService;
import modules.Product;
import modules.User;

import java.util.List;

public class ProfileService implements IProfileService {

    private final IUserRepository userRepository;
    private final IFavoritesRepository favoritesRepository;
    private final IOrdersRepository ordersRepository;

    public ProfileService(IUserRepository userRepository, IFavoritesRepository favoritesRepository, IOrdersRepository ordersRepository) {
        this.userRepository = userRepository;
        this.favoritesRepository = favoritesRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<Product> getFavoriteProducts(Long userId) {
        return favoritesRepository.findFavoritesByUserId(userId);
    }

    @Override
    public List<Product> getOrderedProducts(Long userId) {
        return ordersRepository.getOrderedProducts(userId);
    }
}
