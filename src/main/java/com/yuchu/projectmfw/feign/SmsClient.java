package com.yuchu.projectmfw.feign;

import com.yuchu.projectmfw.common.SmsResult;
import com.yuchu.projectmfw.domain.sms.SmsRequest;
import com.yuchu.projectmfw.domain.sms.SmsResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SmsService",url = "${url.smsApi}",fallback = SmsClientFallback.class)
public interface SmsClient {
    @RequestMapping(value = "/",method = RequestMethod.POST)
    SmsResult<SmsResponse> getCode(@RequestBody SmsRequest request);
}
class SmsClientFallback implements SmsClient{
    @Override
    public SmsResult<SmsResponse> getCode(SmsRequest request) {
        return null;
    }
}
