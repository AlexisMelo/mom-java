package ir.mom.server.model;

import java.util.List;

public class Topic extends MessageQueue {
    private String title;
    private List<Application> subscribers;

    public Topic(String title) {
        this.title = title;
    }

    public void addSubscriber(Application subscriber) {
    }

    public void removeSubscriber(Application subscriber) {
    }
}