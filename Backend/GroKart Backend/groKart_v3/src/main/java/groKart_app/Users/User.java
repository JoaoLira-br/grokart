package groKart_app.Users;

import groKart_app.Items.Item;
import groKart_app.Reports.Report;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//import org.hibernate.annotations.Table;

@Entity
@Table(name = "AppUser")
public class User {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;
    private String emailAdd;
    private String password;
    private String displayName;

    //privilege --> base_user = 0, store_admin = 1, app-admin = 2
    private int privilege;
    private String preferredStore;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reports;

    public User(String userName, String emailAdd, String password, String displayName, int privilege, String preferredStore) {
        this.userName = userName;
        this.emailAdd = emailAdd;
        this.password = password;
        this.displayName = displayName;
        this.privilege = privilege;
        this.preferredStore = preferredStore;
        reports = new ArrayList<>();
    }

    public User() {reports = new ArrayList<>();}

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getEmailAdd(){
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd){
        this.emailAdd = emailAdd;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getDisplayName() {return displayName;}

    public void setDisplayName(String displayName) {this.displayName = displayName;}

    public void setPrivilege(int privilege) { this.privilege = privilege; }

    public int getPrivilege() { return this.privilege; }

    public String getPreferredStore() {return preferredStore;}

    public void setPreferredStore(String preferredStore) {this.preferredStore = preferredStore;}

    public List<Report> getReports(){return reports;}

    public void setReports(List<Report> reports) {this.reports = reports;}

    public void removeReports(Report reports){this.reports.remove(reports);}

}
