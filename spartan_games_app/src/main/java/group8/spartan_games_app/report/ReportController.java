package group8.spartan_games_app.report;

import group8.spartan_games_app.game.Game;
import group8.spartan_games_app.user.User;
import group8.spartan_games_app.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReportController.java.
 * Includes all REST API endpoint mappings for the Game object.
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService service;

    @Autowired
    private UserController userController;

    @GetMapping("/admin/all")
    public String getAllReports(Model model) {

        userController.addUserAttributes(model); // Adds all the necessary attributes of the current user to the page
        // It adds the attributes 'user' and 'thumbnailData', so don't repeat them after using this.

        model.addAttribute("reportList", service.getAllReports());

        return "reports-list";
    }

    /**
     * Create a new Report entry.
     * http://localhost:8080/report/new
     *
     * @param report the new Report object.
     */
    @PostMapping("/new")
    public String addNewReport(@RequestParam("userId") int userId,
                               @RequestParam("contentId") int contentId,
                               @RequestParam("contentType") String contentType,
                               @RequestParam("reason") String reason) {

        Report report = new Report();

        report.setUserId(userId);
        report.setContentId(contentId);
        report.setContentType(contentType);
        report.setReason(reason);
        report.setStatus("PENDING");

        service.addNewReport(report);

        return "redirect:/games/" + contentId;
    }
}
