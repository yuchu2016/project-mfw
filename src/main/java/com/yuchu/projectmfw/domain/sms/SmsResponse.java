package com.yuchu.projectmfw.domain.sms;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 11:06
 */
@Data
@ToString
public class SmsResponse {
    /**
     * 本机号码
     */
    private String OWNNUMBER;
    /**
     *发送者号码
     */
    private String SENDNUMBER;
    /**
     * 接收时间
     */
    private Date RECEIVEDATE;
    /**
     * 短信内容
     */
    private String CONTENT;
}
