package ir.mom.server.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private Application sender;
    private String content;
    private Map<Application, Boolean> hasRead;

    public Message(Application sender, String content, Application receiver) {
        this.sender = sender;
        this.content = content;
        this.hasRead = new HashMap<Application, Boolean>();
        this.hasRead.put(receiver, false);
    }

    public String getContent() {
        return null;
    }

    public Application getSender() {
        return null;
    }

    public void addReader(Application reader) {
    }

    public void addReader(List<Application> readers) {
    }

    public void removeReader(Application reader) {
    }

    public List<Application> getReaders() {
        return null;
    }

    public void read(Application reader) {
    }

    public String toJson() {
        return null;
    }

    public boolean erasable() {
        return false;
    }
}