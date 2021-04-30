package ir.mom.server.view;

import java.util.List;

import ir.mom.server.controller.MomDao;
import ir.mom.server.model.Message;

public class MomService {

    private MomDao dao;

    public MomService() {
        this.dao = new MomDao();
    }

    public String sendMessageToTopic(String topic_name, String token_sender, String message) {
        return null;
    }

    public String sendMessageTo(String token_receiver, String token_sender, String message) {
        return null;
    }

    public List<Message> getMessageTopic(String topic_name) {
        return null;
    }

    public List<Message> getMessagesPeerToPeer(String token) {
        return null;
    }

    public List<Message> getAllMessageFromApplication() {
        return null;
    }
}