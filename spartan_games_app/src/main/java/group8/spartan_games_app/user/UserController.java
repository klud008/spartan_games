package group8.spartan_games_app.user;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import group8.spartan_games_app.game.GameService;

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

        addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        // Gets the user
        User user = userService.getUserById(profileUserId);
    
        System.out.println("CURRENT USER ID: " + currentUser.getUserId());
        System.out.println("USER ID: " + user.getUserId());
        if (user.getUserId() != currentUser.getUserId()) {
            return "403";
        }


        User profileUser = userService.getUserById(profileUserId);

        model.addAttribute("profileUser" , profileUser);

        if (profileUser.getThumbnailData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(profileUser.getThumbnailData());
            model.addAttribute("profileThumbnailData", base64Image);
        } else {
            model.addAttribute("profileThumbnailData", null);
        }

        return "edit-profile";
    }

    /**
     * Create a new User entry.
     * http://localhost:8080/api/users/new_user
     *
     */
    @PostMapping("/edit")
    public String editUser(
            @RequestParam("userId") int userId,
            @RequestParam("email") String email,
            @RequestParam("description") String description,
            @RequestParam("newThumbnailData") MultipartFile newThumbnailData
    ) throws IOException {
        // Gets the user
        User user = userService.getUserById(userId);

        user.setEmail(email);
        user.setDescription(description);

        // Update the thumbnail data if a file was uploaded
        if (newThumbnailData != null && !newThumbnailData.isEmpty()) {
            user.setThumbnailData(newThumbnailData);
        }

        userService.updateUser(userId, user);

        return "redirect:/user/" + userId;
    }

    @GetMapping("/{userId}/thumbnail")
    public ResponseEntity<byte[]> getUserThumbnail(@PathVariable int userId) throws IOException {
        User user = userService.getUserById(userId);

        if (user.getThumbnailData() == null) {
            InputStream defaultImageStream = getClass().getResourceAsStream("/static/images/placeholder.jpg");
            byte[] defaultImage = defaultImageStream.readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(defaultImage);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(user.getThumbnailData());
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
    @GetMapping("/delete/{profileUserId}")
    public String deleteUser(@PathVariable int profileUserId) {
        userService.deleteUser(profileUserId);
        return "redirect:/login";
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
