package group8.spartan_games_app.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides the actual database transactions.
 */
@Repository
public interface GameRepository  extends JpaRepository<Game, Integer> {

    // Finds names based on if they include the keyword.
    @Query(value = "SELECT * FROM games WHERE games.title LIKE %:keyword%", nativeQuery = true)
    List<Game> getGamesByName(@Param("keyword") String keyword);
}
