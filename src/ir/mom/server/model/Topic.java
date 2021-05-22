package ir.mom.server.model;

import java.util.List;

import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;

import java.util.ArrayList;

public class Topic extends MessageQueue {
    private String title;
    private List<Application> subscribers;

    public Topic(String title) {
        this.title = title;
        this.subscribers = new ArrayList<Application>();
    }

    public void addSubscriber(Application subscriber) throws ApplicationAlreadySubscribedException {

        if(this.getSubscribers().contains(subscriber)) 
            throw new ApplicationAlreadySubscribedException();

        this.getSubscribers().add(subscriber);
        this.getMessages().forEach((msg) -> {
            try {
                msg.addReader(subscriber);
            } catch (CantAddWriterOfMessageToReadersException e) {
            }
        });
    }

    public void removeSubscriber(Application subscriber) {
        this.getSubscribers().remove(subscriber);
        this.getMessages().forEach((msg) -> {
            msg.removeReader(subscriber);
        });
    }

    public String getTitle(){
        return this.title;
    }

    @Override
    public void addMessage(Message message) throws CantAddWriterOfMessageToReadersException {
        super.addMessage(message);
        for(Application value : this.getSubscribers()){
            try {
                message.addReader(value);
            } catch (CantAddWriterOfMessageToReadersException e) {
            }
        }
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