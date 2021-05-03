package ir.mom.server.view;

import com.google.gson.Gson;

public class TopicJson {

    private String title;

    public TopicJson(String title) {
        this.title = title;
    }

    public String getTitle() { 
        return this.title;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static TopicJson fromJson(String json) {
        return new Gson().fromJson(json, TopicJson.class);
    }

}