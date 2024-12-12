package group8.spartan_games_app.report;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    /**
     * Fetch all Reports.
     *
     * @return the list of all Reports.
     */
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * Add a new Report to the database.
     *
     * @param report the new Report to add.
     */
    public void addNewReport(Report report) {
        report.setCreatedAt(LocalDateTime.now());
        reportRepository.save(report);
    }
}
