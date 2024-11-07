package group8.spartan_games_app.report;

import group8.spartan_games_app.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ReportController.java.
 * Includes all REST API endpoint mappings for the Game object.
 */
@RestController
public class ReportController {

    @Autowired
    private ReportService service;

    /**
     * Create a new Report entry.
     * http://localhost:8080/reports
     *
     * @param report the new Report object.
     */
    @PostMapping("/report")
    public List<Report> addNewReport(@RequestBody Report report) {
        service.addNewReport(report);
        return service.getAllReports();
    }
}
