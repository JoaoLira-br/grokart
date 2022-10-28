package groKart_app.Reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReportRepository extends JpaRepository<Report, Long>{
    Report findById(int id);

    Report findByReportTitle(String reportTitle);

    boolean existsByReportTitleAndStoreName(String reportTitle, String storeName);

    @Transactional
    void deleteByReportTitleAndId(String reportTitle, int id);
}
