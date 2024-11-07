package group8.spartan_games_app.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    /**
     * Create a new Review entry.
     * http://localhost:8080/reviews/create
     *
     * @param review the new Review object.
     */
    @PostMapping("/create")
    public List<Review> addNewReview(@RequestBody Review review) {
        service.addNewReview(review);
        return service.getAllReviews();
    }
}
