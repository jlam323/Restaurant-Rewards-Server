package model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="reward_trans")
public class RewardTransaction {

    @Id
    @Column(name="reward_id")
    private String id;

    @Column(name="date")
    private Date date;

    @Column(name="points_cost")
    private String pointsCost;


    @ManyToOne
    @JoinTable(name="cust_rt_xref",
            joinColumns = @JoinColumn(name = "cust_id"),
            inverseJoinColumns = @JoinColumn(name = "rt_id"))
    private Customer customer;


    public RewardTransaction(){}

    public RewardTransaction(String id, Date date, String pointsCost) {
        this.id = id;
        this.date = date;
        this.pointsCost = pointsCost;
    }
}


