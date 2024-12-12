package group8.spartan_games_app.user;

import group8.spartan_games_app.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(()
                -> new RuntimeException("User not found"));
    }

    /**
     * Add a new User to the database.
     *
     * @param user the new User to add.
     */
    public void addNewUser(String username, String password, String email) {

        User user = new User();

        user.setUsername(username);
        user.setPassword( passwordEncoder.encode(password) );
        user.setEmail(email);

        if (user.getRole() == null) {
            user.setRole("USER");
        }
        if (user.getAccountStatus() == null) {
            user.setAccountStatus("ACTIVE");
        }

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public void updateUser(int userId, User user) {

        User existing = getUserById(userId);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setDescription(user.getDescription());

        //Technically the lines above are not necessary because the save method merges by default.
        userRepository.save(existing);
    }

    public void addNewUser(User user) {
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
