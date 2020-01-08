
package com.example.bloodbank.data.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private GeneralClientData client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public GeneralClientData getClient() {
        return client;
    }

    public void setClient(GeneralClientData client) {
        this.client = client;
    }

}
