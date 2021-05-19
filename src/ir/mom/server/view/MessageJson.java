package ir.mom.server.view;

import com.google.gson.Gson;

public class MessageJson {

    private String sender;
    private String content;

    public MessageJson(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public String getSender() {
        return this.sender;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}