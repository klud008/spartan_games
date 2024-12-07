package group8.spartan_games_app.user;

import group8.spartan_games_app.game.Game;
import group8.spartan_games_app.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

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
    @GetMapping("/admin/all")
    public String getAllUsers(Model model) {

        addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        model.addAttribute("userList", userService.getAllUsers());


        return "users-list";

    }

    // GET user by ID
    @GetMapping("/{profileUserId}")
    public String getUserById(@PathVariable int profileUserId, Model model) {

        addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        User profileUser = userService.getUserById(profileUserId);

        model.addAttribute("profileUser" , profileUser);

        if (profileUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(profileUser.getThumbnailData());
            model.addAttribute("profileThumbnailData", base64Image);
        } else {
            model.addAttribute("profileThumbnailData", null);
        }

        model.addAttribute("username", profileUser.getUsername());
        model.addAttribute("gamesList", gameService.getGameByDev(profileUserId));

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

    @GetMapping("/edit/{profileUserId}")
    public String editUser(@PathVariable int profileUserId, Model model) {

        User profileUser = userService.getUserById(profileUserId);

        model.addAttribute("user" , profileUser);

        if (profileUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(profileUser.getThumbnailData());
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
    @PutMapping("/{profileUserId}")
    public User updateUser(@PathVariable int profileUserId,
                           @RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "role", required = false) String role,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "accountStatus", required = false) String accountStatus) {



        userService.updateUser(profileUserId, username, password, role, email, accountStatus);

        return userService.getUserById(profileUserId);
    }

    // DELETE remove a user
    @DeleteMapping("/{profileUserId}")
    public void deleteUser(@PathVariable int profileUserId) {
        userService.deleteUser(profileUserId);
    }

    // GET all users by role
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    // Used to pass the necessary information on the current user to the html pages.
    // Massively reduced redundancy this way, since this code gets repeated for nearly
    // every single page on the site.
    public void addUserAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User currentUser = userService.getUserByUsername(username);
        int currentUserId = currentUser.getUserId();


        if (currentUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(currentUser.getThumbnailData());
            model.addAttribute("thumbnailData", base64Image);
        } else {
            model.addAttribute("thumbnailData", null);
        }

        model.addAttribute("user", currentUser);
    }

}
