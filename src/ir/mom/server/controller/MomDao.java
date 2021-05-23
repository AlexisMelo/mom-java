package ir.mom.server.controller;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

import ir.mom.server.model.Topic;
import ir.mom.server.model.Application;
import ir.mom.server.model.Message;
import ir.mom.server.exception.ApplicationNotSubscribedException;
import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;
import ir.mom.server.exception.TopicDoesNotExistException;
import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import java.util.TreeMap;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MomDao implements Serializable {
    private Map<String,Topic> lstTopic;
    private Map<String,Application> lstApplication;
    private static final long serialVersionUID = 1350092881346723535L;

    public MomDao() {
        this.lstTopic = new TreeMap<String,Topic>();
        this.lstApplication = new TreeMap<String,Application>();
    }

    public static MomDao load(String path) throws FileNotFoundException, IOException, ClassNotFoundException {
        File fichier = new File(path) ;
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
        MomDao m = (MomDao) ois.readObject() ;
        ois.close();
        return m;
    }

    public void save(String path) throws FileNotFoundException, IOException{
        File fichier =  new File(path) ;
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
        oos.writeObject(this);
        oos.close();
    }

    public void sendMessageToTopic(String sender, String idTopic, String msg) throws ApplicationNotSubscribedException, TopicDoesNotExistException {
        Application senderApp = this.getApplicationFromString(sender);
        Topic topic = this.getTopicFromString(idTopic);
        
        if (!topic.getSubscribers().contains(senderApp)) {
            throw new ApplicationNotSubscribedException("L'application n'est pas abonnée à ce topic");
        }
        try {
            new Message(senderApp,msg,topic);
        } catch (CantAddWriterOfMessageToReadersException e) {
        }
    }

    public void sendMessageToApplication(String sender, String tokenReceiver, String msg) throws CantAddWriterOfMessageToReadersException {
        Application senderApp = this.getApplicationFromString(sender);
        Application receiverApp = this.getApplicationFromString(tokenReceiver);
        new Message(senderApp,msg,receiverApp);
    }

    public List<Message> getMessageFromApplication(String tokenClient, String tokenTarget) { 
        Application client = this.getApplicationFromString(tokenClient);
        Application target = this.getApplicationFromString(tokenTarget);
        return client.getPrivateMessagesFrom(target);
    }

    public List<Message> getMessageFromTopic(String tokenClient, String topicTitle) throws ApplicationNotSubscribedException, TopicDoesNotExistException {
        
        Application client = this.getApplicationFromString(tokenClient);
        Topic topic = this.getTopicFromString(topicTitle);

        if(!topic.getSubscribers().contains(client))
            throw new ApplicationNotSubscribedException("L'application n'est pas abbonée à ce topic");

        return topic.getMessageToRead(client);
    }
    
    public List<Message> getMessageFromAllApplication(String tokenClient) {
        Application client = this.getApplicationFromString(tokenClient);
        return client.getAllPrivateMessages();
    }

    public void subscribeToTopic(String tokenClient, String topicTitle) throws ApplicationAlreadySubscribedException {
        Application client = this.getApplicationFromString(tokenClient);
        Topic topic;

        try {
            topic = this.getTopicFromString(topicTitle);
        } catch (TopicDoesNotExistException e) {
            topic = new Topic(topicTitle);
            this.lstTopic.put(topicTitle, topic);
        }

        client.addSubscription(topic);
    }

    public void unsubscribeToTopic(String tokenClient, String topicTitle) throws TopicDoesNotExistException, ApplicationNotSubscribedException {
        Application client = this.getApplicationFromString(tokenClient);
        Topic topic = this.getTopicFromString(topicTitle);

        client.removeSubscription(topic);
    }

    public Topic getTopicFromString(String topicTitle) throws TopicDoesNotExistException {
        Topic topic = this.lstTopic.get(topicTitle);
        if(topic == null){
            throw new TopicDoesNotExistException("Le topic "+topicTitle+" n'existe pas.");
        }
        return topic;
    }

    public Application getApplicationFromString(String ApplicationToken) {
        Application app = this.lstApplication.get(ApplicationToken);
        if(app == null){
            app = new Application(ApplicationToken);
            lstApplication.put(ApplicationToken,app);
        }
        return app;
    }
}
