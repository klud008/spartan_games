package group8.spartan_games_app.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {


    List<Review> findByGameId(int gameId);
    Optional<Review> findByUserIdAndGameId(int userId, int gameId);

    
}
