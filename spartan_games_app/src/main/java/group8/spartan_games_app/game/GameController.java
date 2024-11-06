package group8.spartan_games_app.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GameController.java.
 * Includes all REST API endpoint mappings for the Game object.
 */
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService service;

    /**
     * Get a list of all Animals in the database.
     * http://localhost:8080/games/all
     *
     * @return a list of Games  objects.
     *
     */
    @GetMapping("/all")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    /**
     * Get a specific Game by Id.
     * http://localhost:8080/games/2
     *
     * @param gameId the unique Id for a Game.
     * @return One Game object.
     *
     */
    @GetMapping("/{gameId}")
    public Game getOneGame(@PathVariable int gameId) {
        return service.getGameById(gameId);
    }

    /**
     * Get a list of Games based on the searched name.
     * http://localhost:8080/games/title_search?name=
     *
     * @param keyword the search key.
     * @return A list of Game objects matching the search key.
     */
    @GetMapping("/title_search")
    public List<Game> getGamesByTitle(@RequestParam(name = "title", defaultValue = "") String keyword) {
        return service.getGamesByName(keyword);
    }

    /**
     * Create a new Game entry.
     * http://localhost:8080/games/upload_game
     *
     * @param game the new Game object.
     */
    @PostMapping("/upload_game")
    public List<Game> addNewGame(Game game) {
        service.addNewGame(game);
        return service.getAllGames();
    }

    /**
     * Performs the update
     * @param game
     * @return
     */
    @PutMapping("/update/{gameId}")
    public List<Game> updateGame(@PathVariable int gameId, @RequestBody Game game) {
        service.updateGame(gameId, game);
        return service.getAllGames();
    }

    /**
     * Delete a Game object.
     * http://localhost:8080/games/delete/2
     *
     * @param gameId the unique Game Id.
     * @return the updated list of Games.
     */
    @DeleteMapping("/delete/{gameId}")
    public List<Game> deleteGameById(@PathVariable int gameId) {
        service.deleteGameById(gameId);
        return service.getAllGames();
    }
}
