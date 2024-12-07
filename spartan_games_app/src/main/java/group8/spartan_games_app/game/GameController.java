package group8.spartan_games_app.game;


import java.io.IOException;
import java.util.Base64;
import java.util.List;

import group8.spartan_games_app.user.User;
import group8.spartan_games_app.user.UserService;
import group8.spartan_games_app.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.multipart.MultipartFile;

import group8.spartan_games_app.review.Review;
import group8.spartan_games_app.review.ReviewService;


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
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserController userController;

    /**
     * Get a list of all Games in the database.
     * http://localhost:8080/games/all
     *
     * @return a list of Games  objects.
     *
     */
    @GetMapping("/all")
    //public List<Game> getAllGames() {
    public String getAllGames(Model model) {
        //return service.getAllGames();

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        model.addAttribute("gamesList", service.getAllGames());


        return "home";

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

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        Game game = service.getGameById(gameId);
        List<Review> reviews = reviewService.getReviewsByGameId(gameId);

        model.addAttribute("game", game);
        //get reviews
        model.addAttribute("reviews", reviews);
        return "gamepage";
    }

    @GetMapping("/developer/{devId}")
    public String  getByDeveloper(@PathVariable int devId, Model model) {

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

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

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        return "upload";
    }

    /**
     * Create a new Game entry.
     * http://localhost:8080/games/upload_game
     *
     * @param game the new Game object.
     * @throws IOException 
     */
    @PostMapping("/upload_game_complete")
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
     * Performs the update
     * @param game
     * @return
          * @throws IOException 
          */
         @PutMapping("/update/{gameId}")
         public Game updateGame(@PathVariable int gameId,
         @RequestParam(value = "title", required = false) String title,
         @RequestParam(value = "description", required = false) String description,
         @RequestParam(value = "gameFile", required = false) MultipartFile gameFile,
         @RequestParam(value = "thumbnailFile", required = false) MultipartFile thumbnailFile) throws IOException{
        

        service.updateGame(gameId, title, description, gameFile, thumbnailFile);

        return service.getGameById(gameId);
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

    @DeleteMapping("/delete/{gameId}")
    public List<Game> deleteGameById(@PathVariable int gameId) {
        service.deleteGameById(gameId);
        return service.getAllGames();
    }
}
