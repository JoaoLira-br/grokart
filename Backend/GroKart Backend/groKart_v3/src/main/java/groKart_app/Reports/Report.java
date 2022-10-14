package groKart_app.Reports;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.*;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //TODO: Need to map the User to this class

    private String reportTitle;
    private String description;
    private String storeName;
    private int count;

    public Report(int id, String reportTitle, String description, String storeName) {
        this.id = id;
        this.reportTitle = reportTitle;
        this.description = description;
        this.storeName = storeName;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
