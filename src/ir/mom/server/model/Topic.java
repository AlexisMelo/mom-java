package ir.mom.server.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Topic extends MessageQueue implements Serializable{
    private String title;
    private List<Application> subscribers;

    public Topic(String title) {
        this.title = title;
        this.subscribers = new ArrayList<Application>();
    }

    public void addSubscriber(Application subscriber) {
        this.getSubscribers().add(subscriber);
    }

    public void removeSubscriber(Application subscriber) {
        this.getSubscribers().remove(subscriber);
    }

    public String getTitle(){
        return this.title;
    }

    public List<Application> getSubscribers(){
        return this.subscribers;
    }

    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Topic)) return false;
        Topic app = (Topic) o;
        return app.getTitle() == this.getTitle() && this.getSubscribers().equals(app.getSubscribers()) ;

    }
}