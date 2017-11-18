package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="points")
public class Points {

    @Id
    @Column(name="points_id")
    private String id;

    @Column(name="rest_name")
    private int amount;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="last_updated")
    private Date lastUpdated;



    public Points(){}

    public Points(String id, int amount, Date createDate, Date lastUpdated) {
        this.id = id;
        this.amount = amount;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }
}

