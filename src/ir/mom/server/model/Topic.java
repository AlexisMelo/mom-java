package ir.mom.server.model;

import java.util.List;

public class Topic extends MessageQueue {
    private String title;
    private List<Application> subscribers;

    public Topic(String title) {
        this.title = title;
        this.subscribers = new List<Application>();
    }

    public void addSubscriber(Application subscriber) {
        this.subscribers.add(subscriber);
    }

    public void removeSubscriber(Application subscriber) {
        this.remove.add(subscriber);
    }
}