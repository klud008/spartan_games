package group8.spartan_games_app.game;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import group8.spartan_games_app.review.Review;
import group8.spartan_games_app.review.ReviewService;
import group8.spartan_games_app.user.User;
import group8.spartan_games_app.user.UserService;


/**
 * GameController.java.
 * Includes all REST API endpoint mappings for the Game object.
 */
@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService service;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    /*
    http://localhost:8080/games/home

    */
    @GetMapping("/home")
    public String homePage(Model model){
        return "new-home";
    }


    /**
     * Get a list of all Games in the database.
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
    public String getOneGame(@PathVariable int gameId, Model model) {

        Game game = service.getGameById(gameId);
        List<Review> reviews = reviewService.getReviewsByGameId(gameId);

        model.addAttribute("game", game);
        //get reviews
        model.addAttribute("reviews", reviews);
        return "gamepage";
    }

    @GetMapping("/developer/{devId}")
    public String  getByDeveloper(@PathVariable int devId, Model model) {

        User developer = userService.getUserById(devId);

        List<Game> developerGames = service.getGameByDev(devId);

        model.addAttribute("games", developerGames);
        model.addAttribute("developer", developer);

        return "profile";
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

    @GetMapping("/upload_game")
    public String showUploadForm(Model model) {
        return "upload";
    }

    /**
     * Create a new Game entry.
     * http://localhost:8080/games/upload_game
     *
     * @param game the new Game object.
     * @throws IOException
     */
    @PostMapping("/upload_game")
    public String  addNewGame(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("devId") int devId,
                              @RequestParam("gameFile") MultipartFile gameFile,
                              @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException{


        String gameFileName = gameFile.getOriginalFilename();
        String thumbnailFileName = thumbnailFile.getOriginalFilename();

        service.addNewGame(title, description, devId, gameFile, thumbnailFile, gameFileName, thumbnailFileName);


        return "redirect:/games/developer/" + devId;
    }



    /**
     * Updates
     * http://localhost:8080/games/update/{gameId}
     *
     * @param game the new Game object.
     * @throws IOException
     */

    @GetMapping("/update/{gameId}")
    public String showUpdateForm(@PathVariable int gameId, Model model) {
        model.addAttribute("game", service.getGameById(gameId));
        return "updategame";
    }

    /**
     * Performs the update
     * @param game
     * @return
     */
    @PostMapping("/update")
    public String updateGame(@RequestParam("gameId") int gameId,
                             @RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "gameFile", required = false) MultipartFile gameFile,
                             @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile) throws IOException{

        Game game = new Game();
        game.setGameId(gameId);

        if (title != null && !title.isEmpty()) {
            game.setTitle(title);
        }
        if (description != null && !description.isEmpty()) {
            game.setDescription(description);
        }



        service.updateGame(game.getGameId(), game.getTitle(), game.getDescription(), gameFile, thumbnailFile);

        return "redirect:/games/" + game.getGameId();
    }




    @GetMapping("/download/{gameId}")
    public ResponseEntity<Resource> downloadGameFile(@PathVariable int gameId) {
        // Fetch the game by its ID
        Game game = service.getGameById(gameId);
        if (game.getGameFile() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] gameFileData = game.getGameFile();

        ByteArrayResource gameFile = new ByteArrayResource(gameFileData);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + game.getGameFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(gameFileData.length));


        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(gameFileData.length)
                .body(gameFile);

    }

    @GetMapping("/reviews/{gameId}")
    public List<Review> getMethodName(@PathVariable long gameId) {

        return reviewService.getReviewsByGameId(gameId);
    }



    /**
     * Delete a Game object.
     * http://localhost:8080/games/delete/2
     *
     * @param gameId the unique Game Id.
     * @return the updated list of Games.
     */

    @GetMapping("/delete/{gameId}")
    public String deleteGameById(@PathVariable int gameId) {
        Game game = service.getGameById(gameId);
        int devId = game.getDevId();
        service.deleteGameById(gameId);
        return "redirect:/games/developer/" + devId;
    }
}