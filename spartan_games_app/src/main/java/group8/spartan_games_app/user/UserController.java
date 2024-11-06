package group8.spartan_games_app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Create a new User entry.
     * http://localhost:8080/new_user
     *
     * @param user the new Review object.
     */
    @PostMapping("/new_user")
    public List<User> addNewUser(User user) {
        service.addNewUser(user);
        return service.getAllUsers();
    }
}
