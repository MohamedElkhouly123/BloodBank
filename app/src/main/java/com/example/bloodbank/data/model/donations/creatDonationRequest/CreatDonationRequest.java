
package com.example.bloodbank.data.model.donations.creatDonationRequest;

import com.example.bloodbank.data.model.donations.DonationRequests.GeneralDonationRequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatDonationRequest {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private GeneralDonationRequestData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GeneralDonationRequestData getData() {
        return data;
    }

    public void setData(GeneralDonationRequestData data) {
        this.data = data;
    }

}
