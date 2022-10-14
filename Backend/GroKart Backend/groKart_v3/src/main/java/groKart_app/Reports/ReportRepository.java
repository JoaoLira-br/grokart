package groKart_app.Reports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReportRepository extends JpaRepository<Report, Long>{
    Report findById(int id);

    Report findByTitle(String reportTitle);

    boolean existsByTitleAndStoreName(String reportTitle, String storeName);

    @Transactional
    void deleteByTitleAndId(String reportTitle, int id);
}
