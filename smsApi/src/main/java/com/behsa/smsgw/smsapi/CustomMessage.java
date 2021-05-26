package com.behsa.smsgw.smsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomMessage {
    @JsonProperty("message")
    private String message;
    @JsonProperty("MSISDN")
    private String MSISDN;
    @JsonProperty("smsNo")
    private String smsNo;



    public CustomMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getMSISDN() {
        return MSISDN;
    }

    public String getSmsNo() {
        return smsNo;
    }



    public void setMessage(String message) {
        this.message = message;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }



    @Override
    public String toString() {
        return "CustomMessage{" +
                "message='" + message + '\'' +
                ", MSISDN='" + MSISDN + '\'' +
                ", smsNo='" + smsNo + '\'' +
                '}';
    }
}
