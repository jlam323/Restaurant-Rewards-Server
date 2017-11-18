package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="points")
public class Points {

    @Id
    @Expose
    @Column(name="points_id")
    private String id;

    @Expose
    @Column(name="rest_name")
    private int amount;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @ManyToOne
    @Column(name="cust_id")
    private Customer customer;


    public Points(){}

    public Points(String id, int amount, Customer customer, Date createDate, Date lastUpdated) {
        this.id = id;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

