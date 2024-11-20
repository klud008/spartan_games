package group8.spartan_games_app.report;

import group8.spartan_games_app.game.Game;
import group8.spartan_games_app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReportController.java.
 * Includes all REST API endpoint mappings for the Game object.
 */
@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/all")
    public List<Report> getAllReports() {
        return service.getAllReports();
    }

    /**
     * Create a new Report entry.
     * http://localhost:8080/reports
     *
     * @param report the new Report object.
     */
    @PostMapping("/new")
    public List<Report> addNewReport(@RequestBody Report report) {
        service.addNewReport(report);
        return service.getAllReports();
    }
}
