package group8.spartan_games_app;

import group8.spartan_games_app.user.UserController;
import group8.spartan_games_app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AppController {

    @Autowired
    private UserController userController;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String _403(Model model) {

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        return "403";
    }

}
