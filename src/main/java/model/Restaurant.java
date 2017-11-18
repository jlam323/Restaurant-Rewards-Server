package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="restaurant")
public class Restaurant {

    @Id
    @Expose
    @Column(name="rest_id")
    private String id;

    @Expose
    @Column(name="rest_name")
    private String name;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @OneToMany(mappedBy="restaurant")
    private List<Transaction> transactionList;

    @OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reward> rewardsList;


    public Restaurant(){}

    public Restaurant(String id, String name, Date createDate, Date lastUpdated) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Reward> getRewardsList() {
        return rewardsList;
    }

    public void setRewardsList(List<Reward> rewardsList) {
        this.rewardsList = rewardsList;
    }
}
