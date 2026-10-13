package org.iesalixar.daw2.saulrodriguez.daos;

import org.iesalixar.daw2.saulrodriguez.entities.User;

import java.util.List;

/**
 * Interfaz DAO para gestionar usuarios.
 *
 * @author Saúl Rodríguez
 */
public interface UserDAO {

    void insert(User user);

    User findById(Long id);

    List<User> findAll();

    void update(User user);

    void delete(Long id);

    boolean existsByUsername(String username);
}