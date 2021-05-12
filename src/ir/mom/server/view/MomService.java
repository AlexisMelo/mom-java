package ir.mom.server.view;

import ir.mom.server.controller.MomDao;
import ir.mom.server.exception.ApplicationAlreadySubscribedException;

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

    private MomDao getMomDao(){
        MomDao m = null;
        try{
            m = MomDao.load("./tmp/momdao.ser");
 
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(m == null){
            m = new MomDao();
        }
        return m;
    }

    private void save(MomDao mom){
        try{
            mom.save("./tmp/momdao.ser");
        }
        catch(Exception e){
            System.out.println("Problème lors de la save des datas");
            System.out.println(e);
        }
    }

    public String subscribe(Request req, Response res){
        String topicName = req.params(":topicName");
        String tokenClient = req.params(":tokenclient");

        MomDao mom = this.getMomDao();
        MessageSystem message;
        try{
            mom.subscribeToTopic(tokenClient,topicName);
            mom.save("./tmp/momdao.ser");
            message = new MessageSystem("L'utilisateur "+tokenClient+" vient de s'abonner au topic "+topicName);

        }
        catch(ApplicationAlreadySubscribedException e){
            message = new MessageSystem("L'utilisateur "+tokenClient+" est déjà abonné à "+topicName);

        }
        catch(Exception e){
            System.out.println(e);
            message = new MessageSystem("Problème lors de la save des data");
        }
        return message.toJson();
    }

    public static void main(String[] args) {

        MomService service = new MomService(new MomDao());

        get("/ping", service::pong);

        path("/topic", () -> {
            before("/*", (q, a) -> System.out.println("Received topic call"));
            get("/ping", service::pong);
            get("/subscribe/:tokenclient/:topicName", service::subscribe);
            get("/:name", (req, res) -> {
                TopicJson topic = new TopicJson(req.params(":name"));
                return topic.toJson();
            });

        });

    }
}