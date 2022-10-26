package groKart_app.Reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.*;
import groKart_app.Users.User;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reportTitle;
    private String description;
    private String storeName;
    private String reportStatus;
    private int count;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }
}
