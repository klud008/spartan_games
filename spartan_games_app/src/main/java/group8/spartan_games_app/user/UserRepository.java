package group8.spartan_games_app.user;

import group8.spartan_games_app.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
    List<User> findByRole(String role); // To retrieve users by role (e.g., "user", "developer")

    Optional<User> getUserByUsername(String username);
}
