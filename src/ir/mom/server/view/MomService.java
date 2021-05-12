package ir.mom.server.view;

import ir.mom.server.controller.MomDao;
import ir.mom.server.model.Message;
import ir.mom.server.model.Application;
import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import java.io.EOFException;
import java.util.List;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.path;
import static spark.Spark.before;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class MomService {

    private final MomDao dao;

    public MomService(MomDao dao) {
        this.dao = dao;
    }

    private MomDao getMomDao(){
        MomDao m = null;
        try{
            m = MomDao.load("./tmp/momdao.ser");
 
        }
        catch(EOFException e){
            m = new MomDao();
        }
        catch(Exception e){
            System.out.println("Problème lors de la save des datas");
            System.out.println(e);
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

    public String pong(Request req, Response res) {
        return "pong!";
    }

    public String sendMessageToTopic(Request req, Response res) {
        String topicName = req.params(":topic_name");
        String tokenClient = req.params(":token");
        String content = req.params(":content");

        if(content == null || content == "" || tokenClient == null || tokenClient == "" || topicName == null || topicName == ""){
            MessageSystem m = new MessageSystem("Un des paramètres obligatoires est nul.");
            return m.toJson();
        }

        MomDao mom = this.getMomDao();

        mom.sendMessageToTopic(tokenClient,topicName,content);
        
        this.save(mom);
        MessageSystem message= new MessageSystem("Le message a bien été envoyé");
        return message.toJson();
    }


    public String getMessageTopic(Request req, Response res) {
        String topicName = req.params(":topic_name");
        String tokenClient = req.params(":token");
        MomDao mom = this.getMomDao();
        ListMessage messages=null;
        try{
            List<Message> lstMessage = mom.getMessageFromTopic(tokenClient,topicName);
            messages = new ListMessage(lstMessage);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        this.save(mom);
        return new Gson().toJson(messages);

    }

    public String getMessagesApplication(Request req, Response res) {
        String tokenOther = req.params(":token_other");
        String tokenClient = req.params(":token");
        MomDao mom = this.getMomDao();
        ListMessage messages=null;
        try{
            List<Message> lstMessage = mom.getMessageFromApplication(tokenClient,tokenOther);
            messages = new ListMessage(lstMessage);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        this.save(mom);
        return new Gson().toJson(messages);
    }

    public String getAllMessageFromApplication(Request req, Response res) {
        String tokenClient = req.params(":token");
        MomDao mom = this.getMomDao();
        ListMessage messages=null;
        try{
            List<Message> lstMessage = mom.getAllMessageFromApplication(tokenClient);
            messages = new ListMessage(lstMessage);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        this.save(mom);
        return new Gson().toJson(messages);
    }

    public String sendMessageToApplication(Request req, Response res) {

        String tokenOther = req.params(":token_other");
        String tokenClient = req.params(":token");
        String content = req.params(":content");

        if(content == null || content == "" || tokenClient == null || tokenClient == "" || tokenOther == null || tokenOther == ""){
            MessageSystem m = new MessageSystem("Un des paramètres obligatoires est nul.");
            return m.toJson();
        }

        MomDao mom = this.getMomDao();

        mom.sendMessageToApplication(tokenClient,tokenOther,content);
        
        this.save(mom);
        MessageSystem message= new MessageSystem("Le message a bien été envoyé");
        return message.toJson();
    }

    public String subscribe(Request req, Response res){
        String topicName = req.params(":topic_name");
        String tokenClient = req.params(":token");

        MomDao mom = this.getMomDao();
        MessageSystem message;

        try{
            mom.subscribeToTopic(tokenClient,topicName);
            message = new MessageSystem("L'utilisateur "+tokenClient+" vient de s'abonner au topic "+topicName);

        }
        catch(ApplicationAlreadySubscribedException e){
            message = new MessageSystem("L'utilisateur "+tokenClient+" est déjà abonné à "+topicName);
        }
        this.save(mom);
        return message.toJson();
    }

    public static void main(String[] args) {

        MomService service = new MomService(new MomDao());

        get("/ping", service::pong);

        path("/topic", () -> {
            before("/*", (q, a) -> System.out.println("Received topic call"));

            // get("/test/:name", (req,res) -> {
            //     return "Querry:"+req.queryMap()+";Req:"+req.params();
            // });
            get("/subscribe/:token/:topic_name", service::subscribe);
            get("/:token/:topic_name", service::getMessageTopic);
            //post("/:token/:topic_name/:content", service::sendMessageToTopic);
            get("/post/:token/:topic_name/:content", service::sendMessageToTopic);

            // get("/:name", (req, res) -> {
            //     TopicJson topic = new TopicJson(req.params(":name"));
            //     return topic.toJson();
            // });

        });

        path("/p2p", () -> {
            before("/*", (q, a) -> System.out.println("Received topic call"));

            get("/:token", service::getAllMessageFromApplication);
            get("/:token/:token_other", service::getMessagesApplication);
            get("/post/:token/:token_other/:content", service::sendMessageToApplication);
            

        });


    }
}