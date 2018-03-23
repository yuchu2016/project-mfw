package com.yuchu.projectmfw.controller;

import com.yuchu.projectmfw.common.SmsResult;
import com.yuchu.projectmfw.domain.sms.SmsRequest;
import com.yuchu.projectmfw.domain.sms.SmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 13:16
 */
@RestController
@RequestMapping("/sms")
public class SmsTestController {
    private static final Logger log = LoggerFactory.getLogger(SmsTestController.class);
    @PostMapping
    public SmsResult<SmsResponse> getCode(@RequestBody SmsRequest request){
        log.info(request.toString());
        SmsResponse response = new SmsResponse();
        response.setCONTENT("290776");
        response.setOWNNUMBER(request.getOWNNUMBER());
        response.setSENDNUMBER(request.getSENDNUMBER());
        response.setRECEIVEDATE(new Date());
        SmsResult<SmsResponse> smsResult= new SmsResult<>();
        smsResult.setCode(1);
        smsResult.setMsg("请求成功");
        smsResult.setData(response);
        return smsResult;
    }
}
