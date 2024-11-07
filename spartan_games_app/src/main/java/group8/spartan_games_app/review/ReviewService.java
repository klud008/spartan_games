package group8.spartan_games_app.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Fetch all Reviews.
     *
     * @return the list of all Reviews.
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Add a new Review to the database.
     *
     * @param review the new Review to add.
     */
    public void addNewReview(Review review) {
        reviewRepository.save(review);
    }
}
