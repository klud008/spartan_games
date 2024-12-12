package group8.spartan_games_app.game;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameId;

    @Column(nullable = false)
    private int devId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Lob 
    @Column(name = "game_file", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @Lob
    @Column(name = "thumbnail", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] thumbnailData;

    @Column(name = "game_file_name", nullable = false)
    private String gameFileName;

    @Column(name = "thumbnail_file_name", nullable = false)
    private String thumbnailFileName;


    @CreatedDate
    @Column(nullable = false, updatable= false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private Double  rating;


    public Game() {
        
    }

    public Game(int gameId, int devId, String title, String description, byte[] fileData, byte[] thumbnailData,  String gameFileName, String thumbnailFileName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.gameId = gameId;
        this.devId = devId;
        this.title = title;
        this.description = description;
        this.fileData = fileData;
        this.thumbnailData = thumbnailData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
            this.devId = devId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public byte[] getGameFile() {
        return fileData;
    }

    public void setGameFile(byte[] fileData) {
        this.fileData = fileData;
    }

    @JsonIgnore
    public byte[] getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(byte[] thumbnailData) {
        this.thumbnailData = thumbnailData;
    }

    public String getGameFileName() {
        return gameFileName;
    }

    public void setGameFileName(String gameFileName) {
        this.gameFileName = gameFileName;
    }

    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
