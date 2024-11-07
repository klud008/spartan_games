package group8.spartan_games_app.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        reportRepository.save(report);
    }
}
