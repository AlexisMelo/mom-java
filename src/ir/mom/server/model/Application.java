package ir.mom.server.model;

import java.util.List;

import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;

import java.util.LinkedList;
import java.io.Serializable;

public class Application extends MessageQueue implements Serializable {
    private String token;
    private List<Topic> subscriptions;

    public Application(String token) {
        this.token = token;
        this.subscriptions = new LinkedList<Topic>();
    }

    public List<Message> getAllPrivateMessages() {
        return this.getMessageToRead(this);
    }

    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Application)) return false;
        Application app = (Application) o;

        if(app.getToken() == this.getToken()) return true; // il ne faut pas tester les Topic sinon il y aura une boucle infinie.
        return false;
    }

    @Override
    public void addMessage(Message message) throws CantAddWriterOfMessageToReadersException {
        super.addMessage(message);
        message.addReader(this);
    }

    public List<Message> getPrivateMessagesFrom(Application application) {
        List<Message> messagesToRead = new LinkedList<Message>();

        for (Message msg : this.getMessages()) {

            Boolean hasRead = msg.hasRead(this);
            if (hasRead != null && !hasRead && msg.getSender().equals(application)) {
                messagesToRead.add(msg);
                msg.read(this);
            }
        }

        return messagesToRead;
    }

    public void addSubscription(Topic topic) throws ApplicationAlreadySubscribedException {
        
        topic.addSubscriber(this);

        if(this.getSupscriptions().contains(topic)) 
            throw new ApplicationAlreadySubscribedException("");

        this.subscriptions.add(topic);
    }

    public void removeSubscrption(Topic topic) {
        this.subscriptions.remove(topic);
        topic.removeSubscriber(this);
    }

    public String getToken(){
        return this.token;
    }

    public List<Topic> getSupscriptions(){
        return this.subscriptions;
    }

    public String toString() {
        return this.getToken();
    }
}
