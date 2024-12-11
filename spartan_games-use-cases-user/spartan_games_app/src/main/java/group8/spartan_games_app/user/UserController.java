package group8.spartan_games_app.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

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
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * Create a new User entry.
     * http://localhost:8080/api/users/new_user
     *
     */
    @PostMapping("/new_user")
    public String addNewUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role,
                             @RequestParam("email") String email,
                             @RequestParam("accountStatus") String accountStatus) {
        // Add the new user
        userService.addNewUser(username, password, role, email, accountStatus);

        // Redirect to /newusers
        return "redirect:/admin/users";
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

    @GetMapping("/api/users/new_user")
    public String showNewUserForm() {
        return "Manage-users";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("admin", userService.getAllUsers());
        return "Manage-users"; // This maps to a Thymeleaf template (Manage-users.html)
    }

    // GET all users by role
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }
}