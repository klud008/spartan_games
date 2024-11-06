package group8.spartan_games_app.review;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long gameId;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String comment;

    @CreatedDate
    @Column(nullable = false)
    private Date createdAt;


    public Review(long reviewId, long userId, long gameId, int rating, String comment, Date createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.gameId = gameId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
