package model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="reward")
public class Reward {

    @Id
    @Expose
    @Column(name = "reward_id")
    private String id;

    @Expose
    @Column(name = "text")
    private String text;

    @Expose
    @Column(name = "points_cost")
    private String pointsCost;

    @Expose
    @Column(name="create_date")
    private Date createDate;

    @Expose
    @Column(name="last_updated")
    private Date lastUpdated;


    @ManyToOne
    @JoinTable(name="rest_reward_xref",
            joinColumns = @JoinColumn(name = "rest_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id"))
    private Restaurant restaurant;

    public Reward() { }

    public Reward(String id, String text, String pointsCost, Restaurant restaurant, Date createDate, Date lastUpdated) {
        this.id = id;
        this.text = text;
        this.pointsCost = pointsCost;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}


