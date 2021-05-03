package ir.mom.server.view;

import java.util.List;

import ir.mom.server.controller.MomDao;
import ir.mom.server.model.Message;

import static spark.Spark.get;
import spark.Request;
import spark.Response;
import spark.Route;

public class MomService {

    private final MomDao dao;

    public MomService(MomDao dao) {
        this.dao = dao;
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