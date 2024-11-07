package group8.spartan_games_app.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService service;

    /**
     * Create a new Review entry.
     * http://localhost:8080/review
     *
     * @param review the new Review object.
     */
    @PostMapping("/review")
    public List<Review> addNewReview(Review review) {
        service.addNewReview(review);
        return service.getAllReviews();
    }
}
