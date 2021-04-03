package pro.ghosh.sosapp.models;

import com.google.gson.annotations.SerializedName;

public class RequestObject {
    @SerializedName("message")
    private String message;

    @SerializedName("to")
    private String to;

    public RequestObject(String message, String to) {
        this.message = message;
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
