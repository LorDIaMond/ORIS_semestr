package interfaces.serv;

public interface IFavoritesService {
    boolean toggleFavorite(Long userId, Long productId);
    boolean isFavorite(Long userId, Long productId);
}
