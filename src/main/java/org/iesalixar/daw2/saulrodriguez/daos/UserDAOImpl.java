package org.iesalixar.daw2.saulrodriguez.daos;

import org.iesalixar.daw2.saulrodriguez.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para la tabla users.
 *
 * @author Saúl Rodríguez
 */
public class UserDAOImpl implements UserDAO {

    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(User user) {

        String sql = """
                INSERT INTO users (
                    username,
                    passwordHash,
                    active,
                    accountNonLocked,
                    lastPasswordChange,
                    passwordExpiresAt,
                    failedLoginAttempts,
                    emailVerified,
                    mustChangePassword
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.getActive());
            stmt.setBoolean(4, user.getAccountNonLocked());
            stmt.setTimestamp(5, Timestamp.valueOf(user.getLastPasswordChange()));
            stmt.setTimestamp(6, Timestamp.valueOf(user.getPasswordExpiresAt()));
            stmt.setInt(7, user.getFailedLoginAttempts());
            stmt.setBoolean(8, user.getEmailVerified());
            stmt.setBoolean(9, user.getMustChangePassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(Long id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY id";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void update(User user) {

        String sql = """
                UPDATE users
                SET username = ?,
                    passwordHash = ?,
                    active = ?,
                    accountNonLocked = ?,
                    lastPasswordChange = ?,
                    passwordExpiresAt = ?,
                    failedLoginAttempts = ?,
                    emailVerified = ?,
                    mustChangePassword = ?
                WHERE id = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.getActive());
            stmt.setBoolean(4, user.getAccountNonLocked());
            stmt.setTimestamp(5, Timestamp.valueOf(user.getLastPasswordChange()));
            stmt.setTimestamp(6, Timestamp.valueOf(user.getPasswordExpiresAt()));
            stmt.setInt(7, user.getFailedLoginAttempts());
            stmt.setBoolean(8, user.getEmailVerified());
            stmt.setBoolean(9, user.getMustChangePassword());
            stmt.setLong(10, user.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String username) {

        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private User mapUser(ResultSet rs) throws SQLException {

        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("passwordHash"),
                rs.getBoolean("active"),
                rs.getBoolean("accountNonLocked"),
                rs.getTimestamp("lastPasswordChange").toLocalDateTime(),
                rs.getTimestamp("passwordExpiresAt").toLocalDateTime(),
                rs.getInt("failedLoginAttempts"),
                rs.getBoolean("emailVerified"),
                rs.getBoolean("mustChangePassword")
        );
    }
}