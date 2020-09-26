package pl.coderslab.entity;

import pl.coderslab.BCrypt;
import pl.coderslab.DBUtil_2;

import java.sql.*;


public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username,email, password) VALUES (?,?,?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String SELECT_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String REMOVE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DBUtil_2.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Błąd");
            return null;
        }
    }

    public void update(User user) {
        try (Connection conn = DBUtil_2.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User read(int userId) {

        try (Connection conn = DBUtil_2.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(SELECT_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User[] readAll() {

        try (Connection conn = DBUtil_2.connect()) {
            User[] users = new User[0];
            PreparedStatement statement =
                    conn.prepareStatement(SELECT_USER_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
               // users = addToArray(user, users); - dokończ metodę addToArray
            }
                return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  //  public String[] addToArray (User user, User[] users) {}

    public void remove(int userID) {
        try (Connection conn = DBUtil_2.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(REMOVE_USER_QUERY);
            statement.setInt(1, userID);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}