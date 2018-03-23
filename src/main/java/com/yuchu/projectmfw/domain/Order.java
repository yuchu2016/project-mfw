package com.yuchu.projectmfw.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 9:35
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Table(name = "tb_mfw_order")
public class Order {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String Id; //主键

    private String contactPhone; //联系人手机号

    private String contactName; //联系人姓名

    private String contactEmail; //联系人电话

    private String departCity;  //出发地城市

    private String departCityCode;  //出发地三字码

    private String desCity; //目的地城市

    private String desCityCode; //目的地三字码

    private String date; //行程日期

    private String level; //舱位等级

    private String flightNo; //航班号

    private String account; //登录账号

    private String password; //登录密码

    @OneToMany(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "orderId", referencedColumnName = "id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private List<Passenger> passengers;

    @CreatedDate
    private Date createdTime; //创建时间

    @LastModifiedDate
    private Date updatedTime;  //修改时间

}


