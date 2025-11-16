package repositoryes;

import database.DatabaseConnection;
import interfaces.repo.IMatchRepository;
import modules.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchRepository implements IMatchRepository {

    @Override
    public Match findCurrentMatch() {
        String sql = "SELECT * FROM matches " +
                "WHERE match_date >= NOW() - INTERVAL '2 days' " +
                "ORDER BY " +
                "  CASE " +
                "    WHEN match_date >= NOW() THEN 0 " +
                "    ELSE 1 " +
                "  END, " +
                "  match_date DESC " +
                "LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return new Match(
                    resultSet.getLong("id"),
                    resultSet.getString("home_team"),
                    resultSet.getString("away_team"),
                    resultSet.getString("competition"),
                    resultSet.getString("score"),
                    resultSet.getTimestamp("match_date"),
                    resultSet.getString("stadium"),
                    resultSet.getString("preview_image_url"),
                    resultSet.getString("home_team_logo_url"),
                    resultSet.getString("away_team_logo_url")
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
