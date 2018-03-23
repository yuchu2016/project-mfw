package com.yuchu.projectmfw.common;

import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 11:26
 */
@Data
@ToString
public class SmsResult<T> {

    private Integer code;

    private String msg;

    private  T data;
}
