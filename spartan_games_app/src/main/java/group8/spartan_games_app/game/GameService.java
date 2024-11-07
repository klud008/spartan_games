package group8.spartan_games_app.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GameService.java
 * Centralizes data access to the Game Database.
 */
@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


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
     * Fetch all games whose title matches the search term.
     *
     * @param keyword the search key.
     * @return the list of matching Games.
     */
    public List<Game> getGamesByName(String keyword) {
        return gameRepository.getGamesByName(keyword);
    }

    /**
     * Add a new Game to the database.
     *
     * @param game the new Game to add.
     */
    public void addNewGame(Game game) {
        gameRepository.save(game);
    }

    /**
     * Update an existing Game.
     *
     * @param gameId the unique Game Id.
     * @param game   the new Game details.
     */
    public void updateGame(int gameId, Game game) {
        Game existing = getGameById(gameId);
        existing.setTitle(game.getTitle());
        existing.setDescription(game.getDescription());
        existing.setFileUrl(game.getFileUrl());
        existing.setThumbnailUrl(game.getThumbnailUrl());

        //Technically the 4 lines above are not necessary because the save method merges by default.
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
