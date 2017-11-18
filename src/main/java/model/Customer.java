package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
@Table(name="customer")
public class Customer {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cust_id")
    private String id;

    @Expose
    @Column(name="first_name")
    private String firstName;

    @Expose
    @Column(name="last_name")
    private String lastName;

    @Expose
    @Column(name="gender")
    private String gender;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Points> points;

    @OneToMany(mappedBy="customer")
    private List<Transaction> transactionList;

    @OneToMany(mappedBy="customer")
    private List<RewardTransaction> rtList;


    public Customer(){}

    public Customer(String id, String firstName, String lastName, String gender, Date createDate, Date lastUpdated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void setPoints(List<Points> points) {
        this.points = points;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<RewardTransaction> getRtList() {
        return rtList;
    }

    public void setRtList(List<RewardTransaction> rtList) {
        this.rtList = rtList;
    }
}
