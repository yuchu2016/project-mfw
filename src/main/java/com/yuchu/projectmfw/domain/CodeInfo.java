package com.yuchu.projectmfw.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-22
 * Time: 9:50
 */
@Data
@Table(name = "tb_mfw_codeinfo")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CodeInfo {
    @Id
    @GeneratedValue
    private Integer id;

    private String cityName;

    private String fourCode;

    private String threeCode;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;
}
