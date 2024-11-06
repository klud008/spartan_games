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

    /**
     * Add a new User to the database.
     *
     * @param user the new User to add.
     */
    public void addNewUser(User user) {
        userRepository.save(user);
    }
}
