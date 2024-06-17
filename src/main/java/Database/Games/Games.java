package Database.Games;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Games {
    private static final String URL = "jdbc:postgresql://localhost:5432/yourdatabase";
    private static final String USERNAME = "yourusername";
    private static final String PASSWORD = "yourpassword";

    // Method untuk membuat permainan baru
    public static void createGame(int player1Id, int player2Id) {
        String sql = "INSERT INTO Games (player1_id, player2_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, player1Id);
            stmt.setInt(2, player2Id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mengupdate hasil permainan
    public static void updateGameResult(int gameId, String result) {
        String sql = "UPDATE Games SET result = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, result);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mendapatkan daftar permainan
    public static void getGames() {
        // Implementasi untuk mendapatkan data permainan dari database
    }
}
