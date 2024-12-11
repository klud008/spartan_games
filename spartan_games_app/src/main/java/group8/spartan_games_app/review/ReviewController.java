package group8.spartan_games_app.review;

import java.util.List;

import group8.spartan_games_app.report.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    /*@GetMapping("/all")
    public List<Review> getAllReviews() {
        return service.getAllReviews();
    }*/

    @GetMapping("/{gameId}")
    public List<Review> getAllReviews(@PathVariable int gameId) {
        return service.getReviewsByGameId(gameId);
    }

    @PostMapping("/add")
    public String addReview(@RequestParam("userId") int userId,
                            @RequestParam("username") String username,
                            @RequestParam("gameId") int gameId,
                            @RequestParam("rating") int rating,
                            @RequestParam("review-text") String comment) {

        Review review = new Review();
        review.setUserId(userId);
        review.setUsername(username);
        review.setGameId(gameId);
        review.setRating(rating);
        review.setComment(comment);

        service.addNewReview(review);

        service.getReviewsByGameId(gameId);
        return "redirect:/games/" + gameId;
    }

    /*
    /**
     * Create a new Review entry.
     * http://localhost:8080/reviews/create
     *
     * @param review the new Review object.
     * /
    @PostMapping("/create")
    public List<Review> addNewReview(@RequestBody Review review) {
        service.addNewReview(review);
        return service.getAllReviews();
    }*/
}
