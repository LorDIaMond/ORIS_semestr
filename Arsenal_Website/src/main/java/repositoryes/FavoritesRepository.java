package repositoryes;

import database.DatabaseConnection;
import interfaces.repo.IFavoritesRepository;
import modules.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository implements IFavoritesRepository {

    // Добавить в избранное
    @Override
    public void addToFavorites(Long userId, Long productId) {
        String sql = "INSERT INTO favorites (user_id, product_id) VALUES (?, ?) " +
                "ON CONFLICT DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось добавить в избранное", e);
        }
    }

    // Удалить из избранного
    @Override
    public void removeFromFavorites(Long userId, Long productId) {
        String sql = "DELETE FROM favorites WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось убрать из избранного", e);
        }
    }

    // Проверить, в избранном ли товар
    @Override
    public boolean isFavorite(Long userId, Long productId) {
        String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не получилось проверить, лежит ли товар в избранном", e);
        }
    }

    // По названию все понятно
    @Override
    public List<Product> findFavoritesByUserId(Long userId) {
        List<Product> favorites = new ArrayList<>();
        String sql = "SELECT p.id, p.category_id, p.image_url, p.price, p.description, p.name " +
                "FROM favorites f " +
                "JOIN products p ON f.product_id = p.id " +
                "WHERE f.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favorites.add(new Product(
                        rs.getLong("id"),
                        rs.getLong("category_id"),
                        rs.getString("image_url"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching favorite products", e);
        }
        return favorites;
    }
}