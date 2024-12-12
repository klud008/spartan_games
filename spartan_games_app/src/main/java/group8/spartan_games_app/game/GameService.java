package group8.spartan_games_app.game;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import group8.spartan_games_app.review.Review;
import group8.spartan_games_app.review.ReviewService;

/**
 * GameService.java
 * Centralizes data access to the Game Database.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ReviewService reviewService;


    /**
     * Fetch all Games.
     *
     * @return the list of all Games.
     */
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Fetch a unique Game.
     *
     * @param gameId the unique Game id.
     * @return a unique Game object.
     */
    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    /**
     * Fetch games by given developer
     * 
     */

     public List<Game> getGameByDev(int devId) {
        return gameRepository.findByDevId(devId);
    }

    /**
     * Fetch all games whose title matches the search term.
     *
     * @param keyword the search key.
     * @return the list of matching Games.
     */
    public List<Game> getGamesByName(String keyword) {
        return gameRepository.getGamesByName(keyword);
    }

    public List<Game> getGamesByNewest() {
        return gameRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Game::getCreatedAt).reversed())
                .collect(Collectors.toList()) ;
    }

    /**
     * Add a new Game to the database.
     *
     * @param game the new Game to add.
     */
    public void addNewGame(String title, String description, int devId, MultipartFile gameFile, MultipartFile thumbnailFile, String gameFileName, String thumbnailFileName) throws IOException {

        Game game = new Game();


        game.setTitle(title);
        game.setDescription(description);
        game.setDevId(devId);

        game.setCreatedAt(LocalDateTime.now());
        game.setUpdatedAt(LocalDateTime.now());
        game.setGameFileName(gameFileName);  
        game.setThumbnailFileName(thumbnailFileName);

        if (gameFile != null && !gameFile.isEmpty()) {
            game.setGameFile(gameFile.getBytes());
        }

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            game.setThumbnailData(thumbnailFile.getBytes());
        }

        gameRepository.save(game);
    }

    /**
     * Update an existing Game.
     *
     * @param gameId the unique Game Id.
     * @param game   the new Game details.
     */
    public void updateGame(int gameId, String title, String description, MultipartFile gameFile, MultipartFile thumbnailFile) throws IOException  {
        
        Game existing = getGameById(gameId);
        if (existing == null) {
            throw new IllegalArgumentException("Game with ID " + gameId + " doesn't exist");
        }

        if (title != null && !title.isEmpty()) {
            existing.setTitle(title);
        }

        if (description != null && !description.isEmpty()) {
            existing.setDescription(description);
        }

        if (gameFile != null && !gameFile.isEmpty()) {
            existing.setGameFile(gameFile.getBytes());
            existing.setGameFileName(gameFile.getOriginalFilename());
        }

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            existing.setThumbnailData(thumbnailFile.getBytes());
            existing.setThumbnailFileName(thumbnailFile.getOriginalFilename());
        }

        existing.setUpdatedAt(LocalDateTime.now());

        gameRepository.save(existing);
    }

    public void updateGameRating(int gameId) {

    Game existing = getGameById(gameId);

    // get all reviews for the game
    List<Review> reviews = reviewService.getReviewsByGameId(gameId);
    
    double totalRating = 0;
    for (Review review : reviews) {
        totalRating += review.getRating();
        System.out.println(review.getComment() + ":  " + review.getRating());
    }
    
    double averageRating = totalRating / reviews.size(); 
    
    existing.setRating((Double)averageRating);
    
    gameRepository.save(existing);
}

    /**
     * Delete a unique Game.
     *
     * @param gameId the unique Game Id.
     */
    public void deleteGameById(int gameId) {
        gameRepository.deleteById(gameId);
    }

}
