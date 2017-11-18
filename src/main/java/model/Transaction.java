package model;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="points")
public class Transaction {

    @Id
    @Column(name="trans_id")
    private String id;

    @Column(name="date")
    private Date date;

    @Column(name="amount")
    private double amount;

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

    public Transaction(String id, Date date, double amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }
}

