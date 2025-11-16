package interfaces.repo;

import modules.Product;

import java.util.List;

public interface IFavoritesRepository {
    void addToFavorites(Long userId, Long productId);
    void removeFromFavorites(Long userId, Long productId);
    boolean isFavorite(Long userId, Long productId);
    List<Product> findFavoritesByUserId(Long userId);
}
