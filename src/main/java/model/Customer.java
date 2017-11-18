package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="cust_id")
    private String id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="last_updated")
    private Date lastUpdated;


    @OneToMany(mappedBy="customer")
    private List<Transaction> transactionList;

    @OneToMany(mappedBy="customer")
    private List<RewardTransaction> rtList;


    public Customer(){}

    public Customer(String firstName, String lastName, String gender, Date createDate, Date lastUpdated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }
}
