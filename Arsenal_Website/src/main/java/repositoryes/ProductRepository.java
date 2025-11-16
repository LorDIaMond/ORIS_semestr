package repositoryes;

import database.DatabaseConnection;
import interfaces.repo.IProductRepository;
import modules.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getLong("category_id"),
                        rs.getString("image_url"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("name")
                        );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public Product findById(Long id) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getLong("id"),
                            rs.getLong("category_id"),
                            rs.getString("image_url"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public List<Product> findByFilter(String[] categoryIds, String[] priceRanges) {
        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Фильтр по категориям
        if (categoryIds != null && categoryIds.length > 0) {
            sql.append(" AND category_id IN (");
            for (int i = 0; i < categoryIds.length; i++) {
                sql.append("?");
                if (i < categoryIds.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            for (String id : categoryIds) {
                params.add(Long.parseLong(id));
            }
        }

        // Фильтр по цене
        if (priceRanges != null && priceRanges.length > 0) {
            sql.append(" AND (");
            List<String> prices = new ArrayList<>();
            for (String range : priceRanges) {
                switch (range) {
                    case "under_20":
                        prices.add("price < 20");
                        break;
                    case "20_50":
                        prices.add("price BETWEEN 20 AND 50");
                        break;
                    case "50_100":
                        prices.add("price BETWEEN 50 AND 100");
                        break;
                    case "100_250":
                        prices.add("price BETWEEN 100 AND 250");
                        break;
                    case "250_500":
                        prices.add("price BETWEEN 250 AND 500");
                        break;
                }
            }
            sql.append(String.join(" OR ", prices));
            sql.append(")");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(new Product(
                        rs.getLong("id"),
                        rs.getLong("category_id"),
                        rs.getString("image_url"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("name")
                ));
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    // Сохранить новый товар
    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products (category_id, image_url, price, name, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, product.getCategoryId());
            stmt.setString(2, product.getImageUrl());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getName());
            stmt.setString(5, product.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не получилось сохранить товар", e);
        }
    }

    // Удалить товар по ID
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Не получилось удалить товар", e);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET category_id = ?, image_url = ?, price = ?, name = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, product.getCategoryId());
            stmt.setString(2, product.getImageUrl());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getName());
            stmt.setString(5, product.getDescription());
            stmt.setLong(6, product.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

}
