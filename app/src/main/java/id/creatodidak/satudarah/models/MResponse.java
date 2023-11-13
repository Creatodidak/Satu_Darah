package id.creatodidak.satudarah.models;

import com.google.gson.annotations.SerializedName;

public class MResponse {
    @SerializedName("status")
    private boolean isStatus;

    @SerializedName("msg")
    private String msg;

    public boolean isStatus() {
        return isStatus;
    }

    public String getMsg() {
        return msg;
    }
}
