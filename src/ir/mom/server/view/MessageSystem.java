package ir.mom.server.view;
import com.google.gson.Gson;

public class MessageSystem {
    private String message;

    public MessageSystem(String message) {
        this.message = message;
    }

    public String getTitle() { 
        return this.message;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static MessageSystem fromJson(String json) {
        return new Gson().fromJson(json, MessageSystem.class);
    }
}
