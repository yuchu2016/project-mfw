package com.yuchu.projectmfw.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 13:18
 */
@Data
@Table(name = "tb_mfw_passenger")
@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long Id;

    private String name;

    private String IdNum;

//    @ManyToOne
//    @JoinColumn(name = "orderId",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private String orderId;

}
