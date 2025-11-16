package repositoryes;

import database.DatabaseConnection;
import interfaces.repo.IOrdersRepository;
import modules.Product;

import java.sql.*;
import java.util.*;

public class OrdersRepository implements IOrdersRepository {

    // Добавить в заказы
    @Override
    public void addToOrders(Long userId, Long productId, int quantity) {
        System.out.println("🔍 Checking if product " + productId + " is in cart for user " + userId);
        String sql = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                "ON CONFLICT (user_id, product_id) DO UPDATE SET quantity = orders.quantity + EXCLUDED.quantity";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding to cart", e);
        }
    }

    // Удалить из заказов
    @Override
    public void removeFromOrders(Long userId, Long productId) {
        System.out.println("🔍 Checking if product " + productId + " is in cart for user " + userId);
        String sql = "DELETE FROM orders WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error removing from cart", e);
        }
    }

    // Проверить, заказан ли товар
    @Override
    public boolean isInOrders(Long userId, Long productId) {
        System.out.println("🔍 Checking if product " + productId + " is in cart for user " + userId);
        String sql = "SELECT 1 FROM orders WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking cart", e);
        }
    }

    // Получить все заказанные товары
    @Override
    public List<Product> getOrderedProducts(Long userId) {
        List<Product> cartItems = new ArrayList<>();
        String sql = "SELECT p.* FROM orders o JOIN products p ON o.product_id = p.id WHERE o.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cartItems.add(new Product(
                        rs.getLong("id"),
                        rs.getLong("category_id"),
                        rs.getString("image_url"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching cart items", e);
        }
        return cartItems;
    }
}