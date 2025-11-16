package services;

import interfaces.repo.IFavoritesRepository;
import interfaces.serv.IFavoritesService;

public class FavoritesService implements IFavoritesService {

    private final IFavoritesRepository favoritesRepository;

    public FavoritesService(IFavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public boolean toggleFavorite(Long userId, Long productId) {
        boolean isFavorite = favoritesRepository.isFavorite(userId, productId);
        if (isFavorite) {
            favoritesRepository.removeFromFavorites(userId, productId);
        } else {
            favoritesRepository.addToFavorites(userId, productId);
        }
        return !isFavorite;
    }

    @Override
    public boolean isFavorite(Long userId, Long productId) {
        return favoritesRepository.isFavorite(userId, productId);
    }
}
