package backend.dbms.Service;

import java.util.List;
import java.util.Optional;

import backend.dbms.models.User;

public interface UserService {
    Optional<User> getByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void createUser(User user);

    long count();

    List<User> getAllUsers();
}
