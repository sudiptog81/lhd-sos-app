package pro.ghosh.sosapp.models;

import com.google.gson.annotations.SerializedName;

public class ResponseObject {
    @SerializedName("message")
    private String message;

    public ResponseObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
