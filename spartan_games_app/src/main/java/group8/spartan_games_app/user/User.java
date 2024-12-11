package group8.spartan_games_app.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = false, updatable= false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String accountStatus = "ACTIVE";

    @Lob
    @Column(name = "thumbnail", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] thumbnailData;

    @Column(nullable = true)
    private String description = "";


    public User() {}

    // Used for the actual sign-up
    public User(String username, String password, String email) {
        //this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        //this.createdAt = createdAt;
    }

    public User(int userId, String username, String password, String role, String email, LocalDateTime createdAt, String accountStatus, byte[] thumbnailData) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.createdAt = createdAt;
        this.accountStatus = accountStatus;
        this.thumbnailData = thumbnailData;
    }

    /*public User(String username, String password, String role, String email, String accountStatus) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.accountStatus = accountStatus;
    }*/

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountStatus() { return accountStatus; }

    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }

    @JsonIgnore
    public byte[] getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(byte[] thumbnailData) {
        this.thumbnailData = thumbnailData;
    }

    public void setThumbnailData(MultipartFile thumbnailData) throws IOException {
        this.setThumbnailData( thumbnailData.getBytes() );
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
