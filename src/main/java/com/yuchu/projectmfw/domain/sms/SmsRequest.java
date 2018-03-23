package com.yuchu.projectmfw.domain.sms;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2018-03-23
 * Time: 11:05
 */
@Data
@ToString
@NoArgsConstructor
public class SmsRequest {
    /**
     * 本机号码
     */
    private String OWNNUMBER;
    /**
     * 发送者号码
     */
    private String SENDNUMBER;  //1069115392306518

    public SmsRequest(SmsRequestBuilder builder) {
        this.OWNNUMBER = builder.OWNNUMBER;
        this.SENDNUMBER = builder.SENDNUMBER;
    }


    public static class SmsRequestBuilder {
        private String OWNNUMBER;
        private String SENDNUMBER;

        public SmsRequestBuilder() {
        }

        public SmsRequestBuilder ownNumber(String ownNumber) {
            this.OWNNUMBER = ownNumber;
            return this;
        }

        public SmsRequestBuilder sendNumber(String sendNumber) {
            this.SENDNUMBER = sendNumber;
            return this;
        }

        public SmsRequest build() {
            return new SmsRequest(this);
        }
    }
}
