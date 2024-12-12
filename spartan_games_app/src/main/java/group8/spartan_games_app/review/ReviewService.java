package group8.spartan_games_app.review;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addNewReview(int userId, String username, int gameId, int rating, String comment) {

        Review review = new Review();

        review.setUserId(userId);
        review.setUsername(username);
        review.setGameId(gameId);
        review.setRating(rating);
        review.setComment(comment);
        
        if (review.getCreatedAt() == null) {
            review.setCreatedAt(LocalDateTime.now());
        }
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByGameId(int gameId) {
        return reviewRepository.findByGameId(gameId);
    }
}
