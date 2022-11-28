package groKart_app.Reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import jdk.internal.vm.compiler.collections.EconomicMap;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.*;
import groKart_app.Users.User;

@Entity
@Table(name = "Reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes="Report ID", name="reportId", value="1")
    private int id;
    @ApiModelProperty(notes="Report Title", name="reportTitle", value="Wrong Store Pricing")
    private String reportTitle;
    @ApiModelProperty(notes="Report Description", name="description", value="<Full Description of the Report>")
    private String description;
    @ApiModelProperty(notes="StoreName of the Report being launched on", name="storeName", value="Walmart")
    private String storeName;

    @ApiModelProperty(notes="Report Status", name="reportStatus", value="Under Review")
    private String reportStatus;

    @ApiModelProperty(notes="Report Comment", name="comments", value="Problem Solved")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Report(int id, String reportTitle, String description, String storeName, String reportStatus, String comments) {
        this.id = id;
        this.reportTitle = reportTitle;
        this.description = description;
        this.storeName = storeName;
        this.reportStatus = reportStatus;
        this.comments = comments;
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

    public String getComments() {return comments;}

    public void setComments(String comments) {this.comments = comments;}
}
