package group8.spartan_games_app;

import group8.spartan_games_app.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AppController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
