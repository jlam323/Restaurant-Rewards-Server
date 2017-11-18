package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="restaurant")
public class Restaurant {

    @Id
    @Column(name="rest_id")
    private String id;

    @Column(name="rest_name")
    private String name;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="last_updated")
    private Date lastUpdated;


    @OneToMany(mappedBy="restaurant")
    private List<Transaction> transactionList;

    @OneToMany(mappedBy="restaurant")
    private List<Reward> rewardsList;


    public Restaurant(){}

    public Restaurant(String id, String name, Date createDate, Date lastUpdated) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }
}
