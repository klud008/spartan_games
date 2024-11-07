package group8.spartan_games_app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {
    List<User> findByRole(String role); // To retrieve users by role (e.g., "user", "developer")
}
