package group8.spartan_games_app.user;

import group8.spartan_games_app.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch all Users.
     *
     * @return the list of all Users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Add a new User to the database.
     *
     * @param user the new User to add.
     */
    public void addNewUser(String username, String password, String role, String email, String accountStatus) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEmail(email);
        user.setAccountStatus(accountStatus);

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public User updateUser(int userId, String username, String password, String role, String email, String accountStatus) {

        User existing = getUserById(userId);
        if (existing == null) {
            throw new IllegalArgumentException("User with ID " + userId + " doesn't exist");
        }

        if (username != null && !username.isEmpty()) {
            existing.setUsername(username);
        }

        if (password != null && !password.isEmpty()) {
            existing.setPassword(password);
        }

        if (role != null && !role.isEmpty()) {
            existing.setRole(role);
        }

        if (email != null && !email.isEmpty()) {
            existing.setEmail(email);
        }

        if (accountStatus != null && !accountStatus.isEmpty()) {
            existing.setAccountStatus(accountStatus);
        }

        return userRepository.save(existing);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
