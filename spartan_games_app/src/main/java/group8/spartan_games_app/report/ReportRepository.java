package group8.spartan_games_app.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides the actual database transactions.
 */
@Repository

public interface ReportRepository extends JpaRepository<Report, Integer>  {

    

}
