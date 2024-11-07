package group8.spartan_games_app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(int userId, User updatedUser) {
        User user = getUserById(userId);
        user.setAccountStatus(updatedUser.getAccountStatus());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
