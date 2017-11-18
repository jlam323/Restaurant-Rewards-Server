package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="points")
public class Transaction {

    @Id
    @Expose
    @Column(name="trans_id")
    private String id;

    @Expose
    @Column(name="date")
    private Date date;

    @Expose
    @Column(name="amount")
    private double amount;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @ManyToOne
    @JoinTable(name="cust_trans_xref",
            joinColumns = @JoinColumn(name = "cust_id"),
            inverseJoinColumns = @JoinColumn(name = "trans_id"))
    private Customer customer;

    @ManyToOne
    @JoinTable(name="rest_trans_xref",
            joinColumns = @JoinColumn(name = "rest_id"),
            inverseJoinColumns = @JoinColumn(name = "trans_id"))
    private Restaurant restaurant;



    public Transaction(){}

    public Transaction(String id, Date date, double amount, Customer customer, Restaurant restaurant,
                       Date createDate, Date lastUpdated) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.customer = customer;
        this.restaurant = restaurant;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

