package ir.mom.server.controller;

import java.util.List;
import java.util.Map;

import ir.mom.server.model.Topic;
import ir.mom.server.model.Application;
import ir.mom.server.model.Message;

public class MomDao {
    private Map lstTopic;
    private Map lstApplication;

    public MomDao() {
    }

    public static MomDao load(String path) {
        return null;
    }

    public void save() {
    }

    public void subApplicationToTopic(String token, String topicTitle) {
    }

    public void sendMessageToTopic(String sender, String idTopic, String msg) {
    }

    public void sendMessageToApplication(String sender, String tokenReceiver, String msg) {
    }

    public List<Message> getMessageFromApplication(String tokenClient) {
        return null;
    }

    public List<Message> getMessageFromTopic(String topicTitle) {
        return null;
    }

    public List<Message> getAllMessageFromApplication() {
        return null;
    }

    public Topic getTopicFromString(String topicTitle) {
        return null;
    }

    public Application getApplicationFromString(String ApplicationToken) {
        return null;
    }
}