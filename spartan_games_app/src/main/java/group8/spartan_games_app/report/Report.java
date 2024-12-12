package group8.spartan_games_app.report;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reportId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int contentId;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status;

    @CreatedDate
    @Column(nullable = false, updatable= false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private String resolvedAt;


    public Report() {
        this.createdAt = LocalDateTime.now();
    }

    public Report(int reviewId, int userId, int contentId, String contentType, String reason, String status, LocalDateTime createdAt, String resolvedAt) {
        this.reportId = reviewId;
        this.userId = userId;
        this.contentId = contentId;
        this.contentType = contentType;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(String resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

}
