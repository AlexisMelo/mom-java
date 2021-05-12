package ir.mom.server.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Application extends MessageQueue implements Serializable{
    private String token;
    private List<Topic> supscriptions;

    public Application(String token) {
        this.token = token;
        this.supscriptions = new ArrayList();
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
    public void addMessage(Message message){
        super.addMessage(message);
        message.addReader(this);
    }

    public List<Message> getPrivateMessagesFrom(Application application) {
        List<Message> messagesToRead = new ArrayList<Message>();

        for (Message msg : this.getMessages()) {

            Boolean hasRead = msg.hasRead(this);
            if (hasRead != null && !hasRead && msg.getSender().equals(application)) {
                messagesToRead.add(msg);
                msg.read(this);
            }
        }

        return messagesToRead;
    }

    public void addSubscription(Topic topic) {
        this.supscriptions.add(topic);
        topic.addSubscriber(this);
    }

    public void removeSubscrption(Topic topic) {
        this.supscriptions.remove(topic);
        topic.removeSubscriber(this);
    }

    public String getToken(){
        return this.token;
    }

    public List<Topic> getSupscriptions(){
        return this.supscriptions;
    }

    public String toString() {
        return this.getToken();
    }
}
