package model;

import javax.persistence.*;

@Entity
@Table(name="reward")
public class Reward {

    @Id
    @Column(name = "reward_id")
    private String id;

    @Column(name = "text")
    private String text;

    @Column(name = "points_cost")
    private String pointsCost;


    @ManyToOne
    @JoinTable(name="rest_reward_xref",
            joinColumns = @JoinColumn(name = "rest_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id"))
    private Restaurant restaurant;

    public Reward() {
    }

    public Reward(String id, String text, String pointsCost) {
        this.id = id;
        this.text = text;
        this.pointsCost = pointsCost;
    }
}


