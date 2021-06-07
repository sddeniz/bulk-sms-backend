package com.behsa.smsgw.smsapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("message")
    private String message;
    @JsonProperty("MSISDN")
    private String msisdn;
    @JsonProperty("smsNo")
    private String smsNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSmsNo() {
        return smsNo;
    }

    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }

    @Override
    public String toString() {
        return "SmsMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", smsNo='" + smsNo + '\'' +
                '}';
    }
}
