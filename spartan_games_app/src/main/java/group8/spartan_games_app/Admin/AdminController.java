package group8.spartan_games_app.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group8.spartan_games_app.game.GameService;
import group8.spartan_games_app.user.User;
import group8.spartan_games_app.user.UserService;

/**
 * AdminController.java
 * Handles administrative operations for the Spartan Games application.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public AdminController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("users", userService.getAllUsers());
        return "Adminhomepage"; // Admin dashboard Thymeleaf template
    }

    // Manage Users Section

    // GET all users
    @GetMapping("/users")
    public String manageUsers(Model model) {
        // Fetch the list of users and add it to the model
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "Manage-users"; // Thymeleaf template for managing users
    }

    // DELETE a user
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        // Delete the user with the given ID
        userService.deleteUser(id);
        return "redirect:/admin/users"; // Redirect back to the user management page
    }

    // Manage Games Section

    // GET all games
    @GetMapping("/games")
    public String manageGames(Model model) {
        // Fetch the list of games and add it to the model
        model.addAttribute("games", gameService.getAllGames());
        return "Manage-games"; // Thymeleaf template for managing games
    }

    // DELETE a game
    @PostMapping("/games/delete/{gameId}")
    public String deleteGame(@PathVariable int gameId) {
        // Delete the game with the given ID
        gameService.deleteGameById(gameId);
        return "redirect:/admin/games"; // Redirect back to the game management page
    }

    // Add more admin-specific operations below
}
