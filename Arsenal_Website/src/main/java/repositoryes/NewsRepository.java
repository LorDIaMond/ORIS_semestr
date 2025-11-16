package repositoryes;

import database.DatabaseConnection;
import interfaces.repo.INewsRepository;
import modules.News;

import java.sql.*;

public class NewsRepository implements INewsRepository {

    @Override
    public News findLatestVisibleNews() {
        String sql = "SELECT * FROM news ORDER BY published_at DESC LIMIT 1";
        System.out.println("Executing SQL: " + sql);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Проверка
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            System.out.println("Columns in result: " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " + meta.getColumnName(i));
            }

            if (rs.next()) {
                return new News(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("image_url"),
                rs.getString("external_url"),
                rs.getTimestamp("published_at")
                );
            }
            else {
                System.out.println("No news found in database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
