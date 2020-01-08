
package com.example.bloodbank.data.model.donations.DonationRequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllDonationRequests {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private GetAllDonationRequestsPagination data;

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

    public GetAllDonationRequestsPagination getData() {
        return data;
    }

    public void setData(GetAllDonationRequestsPagination data) {
        this.data = data;
    }

}
