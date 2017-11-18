package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="reward_trans")
public class RewardTransaction {

    @Id
    @Expose
    @Column(name="reward_id")
    private String id;

    @Expose
    @Column(name="date")
    private Date date;

    @Expose
    @Column(name="points_cost")
    private String pointsCost;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @ManyToOne
    @JoinTable(name="cust_rt_xref",
            joinColumns = @JoinColumn(name = "cust_id"),
            inverseJoinColumns = @JoinColumn(name = "rt_id"))
    private Customer customer;


    public RewardTransaction(){}

    public RewardTransaction(String id, Date date, String pointsCost, Customer customer, Date createDate,
                             Date lastUpdated) {
        this.id = id;
        this.date = date;
        this.pointsCost = pointsCost;
        this.customer = customer;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPointsCost() {
        return pointsCost;
    }

    public void setPointsCost(String pointsCost) {
        this.pointsCost = pointsCost;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}


