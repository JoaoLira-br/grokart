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
     *
     * @return
     */
    @ApiOperation(value = "Get List of All Reports in Database", response = Iterable.class, tags = "ReportController")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 403, message = "Forbidden!"),
            @ApiResponse(code = 404, message = "Error!"),
            @ApiResponse(code = 500, message = "Server Not Found")
    })
    @GetMapping(path = "/reports")
    List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * GET REPORT BY TITLE AND STORE NAME
     *
     * @return
     */
    @GetMapping(path = "/reports/{reportTitle}/{storeName}")
    Report getSpecificReport(@PathVariable String reportTitle, @PathVariable String storeName) {
        return reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
    }

    /**
     * GET REPORTS OF AN USER
     *
     * @return
     */
    @GetMapping(path = "/reports/{userName}")
    List<Report> getAllUserReports(@PathVariable String userName) {
        User owner = userRepository.findByUserName(userName);
        return owner.getReports();
    }

    /**
     * GET REPORTS FOR A STORE
     * @return
     */
    @GetMapping(path = "/reports/store/{storeName}")
    List<Report> getAllStoreReports(@PathVariable String storeName) {
        return reportRepository.findAllByStoreName(storeName);
    }


    /**
     * ASSIGN REPORT TO AN USER
     *
     * @return
     */
    @PutMapping(path = "/reports/{reportTitle}/{storeName}/assignTo/{userName}")
    String assignReport(@PathVariable String userName, @PathVariable String reportTitle, @PathVariable String storeName) {
        User owner = userRepository.findByUserName(userName);
        Report report = reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
        if (report == null || owner == null) {return failure;}
        owner.addReports(report);
        userRepository.save(owner);
        report.setUser(owner);
        reportRepository.save(report);
        return success;
    }

    /**
     * CREATE REPORT
     *
     * @param report
     * @return
     */
    @ApiOperation(value = "Create a New Report", response = Iterable.class, tags = "ReportController")
    @PostMapping(path = "/reports")
    String createReport(@RequestBody Report report) {
        if (report == null || reportRepository.existsByReportTitle(report.getReportTitle())) {
            return failure;
        }
        reportRepository.save(report);
        return success;
    }

    /**
     * DELETE REPORT
     *
     * @param reportTitle
     * @param storeName
     * @return
     */
    @ApiOperation(value = "Delete a Report", response = Iterable.class, tags = "ReportController")
    @DeleteMapping(path = "/reports/{reportTitle}/{storeName}")
    String deleteReport(@PathVariable String storeName, @PathVariable String reportTitle) {
        Report delReport = reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
        if (delReport == null) {
            return failure;
        }
        reportRepository.deleteByReportTitleAndId(reportTitle, delReport.getId());
        return success;
    }

    /**
     * GET REPORT STATUS
     *
     * @param reportTitle
     * @param storeName
     * @return
     */
    @ApiOperation(value = "Get the Report Status", response = Iterable.class, tags = "ReportController")
    @GetMapping(path = "/reports/{reportTitle}/{storeName}/status")
    String getStatus(@PathVariable String reportTitle, @PathVariable String storeName) {
        Report report = reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
        if (report == null) {
            return failure;
        }
        return report.getReportStatus();
    }

    /**
     * ALTER REPORT STATUS
     *
     * @param
     * @return
     */
    @ApiOperation(value = "Alter the Report Status", response = Iterable.class, tags = "ReportController")
    @PutMapping(path = "/reports/{reportTitle}/{storeName}/status/{status}")
    String alterStatus(@PathVariable String reportTitle, @PathVariable String storeName, @PathVariable String status) {
        Report report = reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
        if (report == null) {
            return failure;
        }
        report.setReportStatus(status);
        reportRepository.save(report);
        return report.getReportStatus();
    }

    /**
     * ALTER COMMENTS FOR REPORT
     *
     * @param
     * @return
     */
    @ApiOperation(value = "Alter the Comments for Report", response = Iterable.class, tags = "ReportController")
    @PutMapping(path = "/reports/{reportTitle}/{storeName}/comments/{comments}")
    String alterComments(@PathVariable String reportTitle, @PathVariable String storeName, @PathVariable String comments){
        Report report = reportRepository.findByReportTitleAndStoreName(reportTitle, storeName);
        if (report == null) {
            return failure;
        }
        report.setComments(comments);
        reportRepository.save(report);
        return success;
    }



}
