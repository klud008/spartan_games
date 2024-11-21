package group8.spartan_games_app.user;

import group8.spartan_games_app.game.Game;
import group8.spartan_games_app.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    GameService gameService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users (for viewing user list)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET user by ID
    @GetMapping("/{userId}")
    public String getUserById(@PathVariable int userId, Model model) {

        User currentUser = userService.getUserById(userId);

        model.addAttribute("user" , currentUser);

        if (currentUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(currentUser.getThumbnailData());
            model.addAttribute("thumbnailData", base64Image);
        } else {
            model.addAttribute("thumbnailData", null);
        }

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("gamesList", gameService.getGameByDev(userId));

        return "profile";
    }

    @GetMapping("/sign-up")
    public String showSignUp(Model model) {
        return "sign-up";
    }

    /**
     * Create a new User entry.
     * http://localhost:8080/api/users/new_user
     *
     */
    @PostMapping("/new-user")
    public String addNewUser(String username, String password, String email ) {
        userService.addNewUser(username, password, email);
        return "redirect:/user/login";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable int userId, Model model) {

        User currentUser = userService.getUserById(userId);

        model.addAttribute("user" , currentUser);

        if (currentUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(currentUser.getThumbnailData());
            model.addAttribute("thumbnailData", base64Image);
        } else {
            model.addAttribute("thumbnailData", null);
        }

        return "edit-profile";
    }

    /**
     * Create a new User entry.
     * http://localhost:8080/api/users/new_user
     *
     */
    @PostMapping("/edit")
    public String editUser(User user, @RequestParam("file") MultipartFile file) throws IOException {
        // Process the byte[] to database
        user.setThumbnailData(file);
        userService.updateUser(user.getUserId(), user);
        return "redirect:/user/" + user.getUserId();
    }

    // PUT update a user's role or account status (e.g., ban or unban)
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable int userId,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "role", required = false) String role,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "accountStatus", required = false) String accountStatus) {

        userService.updateUser(userId, username, password, role, email, accountStatus);

        return userService.getUserById(userId);
    }

    // DELETE remove a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    // GET all users by role
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }
}
