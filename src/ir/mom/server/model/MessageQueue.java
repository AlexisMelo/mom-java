package ir.mom.server.model;

import java.util.List;

public abstract class MessageQueue {
    private List<Message> messages;

    public void addMessage(Message message) {
    }

    public void removeMessage(Message message) {
    }

    public List<Message> getMessageToRead(Application reader) {
        return null;
    }
}