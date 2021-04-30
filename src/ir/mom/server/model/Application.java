package ir.mom.server.model;

import java.util.List;

public class Application extends MessageQueue {
    private String token;
    private List<Topic> supscriptions;

    public Application(String token) {
        this.token = token;
    }

    public List<Message> getAllPrivateMessages() {
        return null;
    }

    public List<Message> getPrivateMessagesFrom(Application application) {
        return null;
    }

    public void addSubscription(Topic topic) {
    }

    public void removeSubscrption(Topic topic) {
    }
}
