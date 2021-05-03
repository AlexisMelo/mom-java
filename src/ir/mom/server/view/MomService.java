package ir.mom.server.view;

import ir.mom.server.controller.MomDao;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.before;
import spark.Request;
import spark.Response;

public class MomService {

    private final MomDao dao;

    public MomService(MomDao dao) {
        this.dao = dao;
    }

    public String pong(Request req, Response res) {
        return "pong!";
    }

    public Response sendMessageToTopic(Request req, Response res) {
        return null;
    }

    public Response sendMessageToApplication(Request req, Response res) {
        return null;
    }

    public Response getMessageTopic(Request req, Response res) {
        return null;
    }

    public Response getMessagesApplication(Request req, Response res) {
        return null;
    }

    public Response getAllMessageFromApplication(Request req, Response res) {
        return null;
    }

    public static void main(String[] args) {

        MomService service = new MomService(new MomDao());

        get("/ping", service::pong);

        path("/topic", () -> {
            before("/*", (q, a) -> System.out.println("Received topic call"));
            get("/ping", service::pong);
            get("/:name", (req, res) -> {
                TopicJson topic = new TopicJson(req.params(":name"))
                return topic.toJson();
            });

        });

    }
}