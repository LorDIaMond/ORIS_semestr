package repositoryes;

import interfaces.repo.IUserRepository;
import modules.User;
import database.DatabaseConnection;

import java.sql.*;

public class UserRepository implements IUserRepository {

    // Сохранить нового пользователя
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (email, password_hash, name, is_admin) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getName());
            stmt.setBoolean(4, user.getIsAdmin());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пользователя", e);
        }
    }

    // Найти пользователя по email
    @Override
    public User findByEmail(String email) {
        String sql = "SELECT id, email, password_hash, name, is_admin FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("name"),
                            rs.getBoolean("is_admin")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти польователя", e);
        }
        return null;
    }

    // Найти пользователя по ID
    @Override
    public User findById(Long id) {
        String sql = "SELECT id, email, password_hash, name, is_admin FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("name"),
                            rs.getBoolean("is_admin")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти пользователя", e);
        }
        return null;
    }

}
