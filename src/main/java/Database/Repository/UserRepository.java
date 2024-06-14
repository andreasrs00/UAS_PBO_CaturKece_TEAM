package Database.Repository;

import Database.DatabaseConnection;
import Database.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;



    public class UserRepository {
        public void saveUser(User user) throws SQLException {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, user.getUsername());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword()); // Note: you should hash the password
                statement.executeUpdate();
            }
        }

        public Optional<User> findByEmailAndPassword(String email, String password) throws SQLException {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                    user.setId(resultSet.getLong("id"));
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }
        }
    }


