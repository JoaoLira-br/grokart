package groKart_app.Reports;

import groKart_app.Items.Item;
import groKart_app.Users.User;
import groKart_app.Users.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="ReportController", description = "REST APIs for the entire Report class controllers")
@RestController
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL REPORTS
     * @return
     */
    @ApiOperation(value="Get List of All Reports in Database", response=Iterable.class, tags="ReportController")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Success|OK"),
            @ApiResponse(code=401, message = "Not Authorized"),
            @ApiResponse(code=403, message = "Forbidden!"),
            @ApiResponse(code=404, message = "Error!"),
            @ApiResponse(code=500, message = "Server Not Found")
    })
    @GetMapping(path = "/reports")
    List<Report> getAllReports() { return reportRepository.findAll(); }

    /**
     * GET REPORT BY TITLE
     * @return
     */
    @GetMapping(path = "/reports/{reportTitle}")
    Report getSpecificReport (@PathVariable String reportTitle){
        return reportRepository.findByReportTitle(reportTitle);
    }

    /**
     * GET REPORTS OF AN USER
     * @return
     */
    @GetMapping(path = "/reports/{userName}")
    List <Report> getAllUserReports(@PathVariable String userName){
        User owner = userRepository.findByUserName(userName);
        return owner.getReports();
    }

    /**
     * ASSIGN REPORT TO AN USER
     * @return
     */
    @PutMapping(path = "/reports/{userName}/{reportTitle}")
    String assignReport(@PathVariable String userName, @PathVariable String reportTitle){
        User owner = userRepository.findByUserName(userName);
        Report report = reportRepository.findByReportTitle(reportTitle);
        owner.addReports(report);
        userRepository.save(owner);
        return success;
    }

    /**
     * CREATE REPORT
     * @param report
     * @return
     */
    @ApiOperation(value="Create a New Report", response=Iterable.class, tags="ReportController")
    @PostMapping(path = "/reports")
    String createReport(@RequestBody Report report){
        if(report == null){return failure;}
        reportRepository.save(report);
        return success;
    }

    /**
     * DELETE REPORT
     * @param reportId
     * @return
     */
    @ApiOperation(value="Delete a Report", response=Iterable.class, tags="ReportController")
    @DeleteMapping(path = "/reports/{reportTitle}/{reportId}")
    String deleteReport(@PathVariable int reportId, @PathVariable String reportTitle) {
        reportRepository.deleteByReportTitleAndId(reportTitle,reportId);
        return success;
    }

    /**
     * GET REPORT STATUS
     * @param reportId
     * @return
     */
    @ApiOperation(value="Get the Report Status", response=Iterable.class, tags="ReportController")
    @GetMapping(path = "/reports/{reportId}/status")
    String getStatus(@PathVariable int reportId){
        Report report = reportRepository.findById(reportId);
        return report.getReportStatus();
    }

}
