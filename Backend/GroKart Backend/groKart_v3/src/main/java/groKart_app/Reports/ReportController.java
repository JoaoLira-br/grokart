package groKart_app.Reports;

import groKart_app.Items.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * GET ALL REPORTS
     * @return
     */
    @GetMapping(path = "/reports")
    List<Report> getAllReports() { return reportRepository.findAll(); }

    /**
     * CREATE REPORT
     * @param report
     * @return
     */
    @PostMapping(path = "/reports")
    String createReport(@RequestBody Report report){
        if(report == null){
            return failure;
        } else if (reportRepository.existsByReportTitleAndStoreName(report.getReportTitle(),report.getStoreName())) {
            report.setCount(report.getCount()+1);
            reportRepository.save(report);
            return success;
        }
        reportRepository.save(report);
        return success;
    }

    /**
     * DELETE REPORT
     * @param reportId
     * @return
     */
    @DeleteMapping(path = "/reports/{reportTitle}/{reportId}")
    String deleteReport(@PathVariable int reportId, @PathVariable String reportTitle) {
        reportRepository.deleteByReportTitleAndId(reportTitle,reportId);
        return success;
    }
}
