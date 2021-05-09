package ir.mom.server.controller;

import java.util.List;
import java.util.Map;

import ir.mom.server.model.Topic;
import ir.mom.server.model.Application;
import ir.mom.server.model.Message;
import java.util.Map;
import java.util.TreeMap;

public class MomDao {
    private Map<String,Topic> lstTopic;
    private Map<String,Application> lstApplication;

    public MomDao() {
        this.lstTopic = new TreeMap<String,Topic>();
        this.lstApplication = new TreeMap<String,Application>();
    }

    public static MomDao load(String path) {
        return null;
    }

    public void save() {
    }

    public void subApplicationToTopic(String token, String topicTitle) {
        Application client = this.getApplicationFromString(token);
        Topic topic = this.getTopicFromString(topicTitle);

        client.addSubscription(topic);
    }

    public void sendMessageToTopic(String sender, String idTopic, String msg) {
        Application senderApp = this.getApplicationFromString(sender);
        Topic topic = this.getTopicFromString(idTopic);
        new Message(senderApp,msg,topic); // le message se met tout seul dans la MessageQueue
    }

    public void sendMessageToApplication(String sender, String tokenReceiver, String msg) {
        Application senderApp = this.getApplicationFromString(sender);
        Application receiverApp = this.getApplicationFromString(tokenReceiver);
        new Message(senderApp,msg,receiverApp); // le message se met tout seul dans la MessageQueue
    }

    public List<Message> getMessageFromApplication(String tokenClient) { 
        Application client = this.getApplicationFromString(tokenClient);
        return client.getMessageToRead(client);
        //TODO A revoir est ce qu'il ne manquerait pas un paramètre ?
    }

    public List<Message> getMessageFromTopic(String topicTitle) {
        return null;
    }

    public List<Message> getAllMessageFromApplication() {
        return null;
    }

    public Topic getTopicFromString(String topicTitle) {
        return this.lstTopic.get(topicTitle);
    }

    public Application getApplicationFromString(String ApplicationToken) {
        return this.lstApplication.get(ApplicationToken);
    }
}