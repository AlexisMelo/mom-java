package ir.mom.server.view;

import ir.mom.server.controller.MomDao;
import ir.mom.server.model.Message;

import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import ir.mom.server.exception.ApplicationNotSubscribedException;
import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;
import ir.mom.server.exception.TopicDoesNotExistException;

import java.io.EOFException;
import java.util.List;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.path;
import static spark.Spark.before;
import spark.Request;
import spark.Response;

public class MomService {

    private final MomDao dao;

    public MomService(MomDao dao) {
        this.dao = dao;
    }

    private void save(){
        try{
            this.dao.save("./tmp/momdao.ser");
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
        String tokenClient = req.headers("token");
        String content = req.body();
        ResponseServer response = null;

        if(content == null || content == "" || tokenClient == null || tokenClient == "" || topicName == null || topicName == ""){
            response = new ResponseServer("Un des paramètres obligatoires est nul.", true);
            return response.toJson();
        }

        try {
            this.dao.sendMessageToTopic(tokenClient, topicName, content);
            response = new ResponseServer("Le message a bien été envoyé", false);
        } catch (ApplicationNotSubscribedException e) {            
            response = new ResponseServer("L'application "+tokenClient+" n'est pas abonnée au topic "+topicName, true);

        } catch (TopicDoesNotExistException e) {
            response = new ResponseServer("Le topic "+topicName+" n'existe pas.", true);
        }
        
        this.save();
        return response.toJson();
    }


    public String getMessageFromTopic(Request req, Response res) {
        String topicName = req.params(":topic_name");
        String tokenClient = req.headers("token");
        
        ResponseServer response = null;
        try {
            List<Message> lstMessage = this.dao.getMessageFromTopic(tokenClient,topicName);
            response = new ResponseServer("", false, lstMessage);
        }
        catch(ApplicationNotSubscribedException e) {
            response = new ResponseServer("L'application "+tokenClient+" n'est pas abonnée au topic "+topicName, true);
        } catch (TopicDoesNotExistException e) {
            response = new ResponseServer("Le topic "+topicName+" n'existe pas.", true);
        }
        
        this.save();
        return response.toJson();

    }

    public String getMessagesApplication(Request req, Response res) {
        String tokenOther = req.params(":token_other");
        String tokenClient = req.headers("token");
        
        ResponseServer response = null;
        List<Message> lstMessage = this.dao.getMessageFromApplication(tokenClient,tokenOther);
        response = new ResponseServer("", false, lstMessage);
        
        this.save();
        return response.toJson();
    }

    public String getMessageFromAllApplication(Request req, Response res) {
        String tokenClient = req.headers("token");

        List<Message> lstMessage = this.dao.getAllMessageFromApplication(tokenClient);
        ResponseServer response = new ResponseServer("", false, lstMessage);

        this.save();
        return response.toJson();
    }

    public String sendMessageToApplication(Request req, Response res) {

        String tokenOther = req.params(":token_other");
        String tokenClient = req.headers("token");
        String content = req.body();
        System.out.println(content);

        if(content == null || content == "" || tokenClient == null || tokenClient == "" || tokenOther == null || tokenOther == ""){
            ResponseServer m = new ResponseServer("Un des paramètres obligatoires est nul.",true);
            return m.toJson();
        }
        ResponseServer message = null;
        try {
            this.dao.sendMessageToApplication(tokenClient,tokenOther,content);
            message = new ResponseServer("Le message a bien été envoyé.", false); 
        } catch (CantAddWriterOfMessageToReadersException e) {
            message = new ResponseServer("Il n'est pas possible de s'envoyer sois même un message.", true); 
        }
        
        this.save();
         
        return message.toJson();
    }

    public String subscribeToTopic(Request req, Response res){
        String topicName = req.params(":topic_name");
        String tokenClient = req.headers("token");

        
        ResponseServer message;

        try{
            this.dao.subscribeToTopic(tokenClient,topicName);
            message = new ResponseServer("L'utilisateur "+tokenClient+" vient de s'abonner au topic "+topicName, false);

        }
        
        catch(ApplicationAlreadySubscribedException e){
            message = new ResponseServer("L'utilisateur "+tokenClient+" est déjà abonné à "+topicName, true);
        }

        this.save();
        return message.toJson();
    }

    public static void main(String[] args) {

        MomDao m = null;

        try{
            m = MomDao.load("./tmp/momdao.ser");
 
        }
        catch(Exception e){
            m = new MomDao();
        }

        MomService service = new MomService(m);
        

        get("/ping", service::pong);

        path("/topic", () -> {
            before("*", (q, a) -> System.out.println("Received topic call"));

            get("/subscribe/:topic_name", service::subscribeToTopic);
            get("/:topic_name", service::getMessageFromTopic);
            post("/:topic_name", service::sendMessageToTopic);

        });

        path("/p2p", () -> {
            before("*", (q, a) -> System.out.println("Received topic call"));

            get("", service::getMessageFromAllApplication);
            get("/:token_other", service::getMessagesApplication);
            post("/:token_other", service::sendMessageToApplication);
        });

    }
}