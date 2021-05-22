package ir.mom.server.view;

import ir.mom.server.controller.MomDao;
import ir.mom.server.model.Message;

import ir.mom.server.exception.ApplicationAlreadySubscribedException;
import ir.mom.server.exception.ApplicationNotSubscribedException;
import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;
import ir.mom.server.exception.TopicDoesNotExistException;

import java.util.List;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.path;
import spark.Request;
import spark.Response;

public class MomService {

    private final MomDao dao;
    private final String path;

    public MomService(MomDao dao, String path) {
        this.dao = dao;
        this.path = path;
    }

    private MomDao getDao() {
        return this.dao;
    }

    public String getPath() {
        return this.path;
    }

    private void save(){
        try{
            this.getDao().save(this.path);
        }
        catch(Exception e){
            System.out.println("Problème lors de la save des datas");
            System.out.println(e);
        }
    }

    public String pong(Request req, Response res) {
        ResponseServer message = new ResponseServer("pong", false);
        return message.toJson();
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
            this.getDao().sendMessageToTopic(tokenClient, topicName, content);
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
            List<Message> lstMessage = this.getDao().getMessageFromTopic(tokenClient,topicName);
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
        List<Message> lstMessage = this.getDao().getMessageFromApplication(tokenClient,tokenOther);
        response = new ResponseServer("", false, lstMessage);
        
        this.save();
        return response.toJson();
    }

    public String getMessageFromAllApplication(Request req, Response res) {
        String tokenClient = req.headers("token");

        List<Message> lstMessage = this.getDao().getMessageFromAllApplication(tokenClient);
        ResponseServer response = new ResponseServer("", false, lstMessage);

        this.save();
        return response.toJson();
    }

    public String sendMessageToApplication(Request req, Response res) {

        String tokenOther = req.params(":token_other");
        String tokenClient = req.headers("token");
        String content = req.body();

        if(content == null || content == "" || tokenClient == null || tokenClient == "" || tokenOther == null || tokenOther == ""){
            ResponseServer message = new ResponseServer("Un des paramètres obligatoires est nul.",true);
            return message.toJson();
        }

        ResponseServer message = null;
        try {
            this.getDao().sendMessageToApplication(tokenClient,tokenOther,content);
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
            this.getDao().subscribeToTopic(tokenClient,topicName);
            message = new ResponseServer("L'utilisateur "+tokenClient+" vient de s'abonner au topic "+topicName, false);
        }
        catch(ApplicationAlreadySubscribedException e){
            message = new ResponseServer("L'utilisateur "+tokenClient+" est déjà abonné à "+topicName, true);
        }

        this.save();
        return message.toJson();
    }

    public static void main(String[] args) {

        MomDao dao = null;
        String daoPath = "./tmp/momdao.ser";

        try{
            dao = MomDao.load(daoPath);
 
        }
        catch(Exception e){
            dao = new MomDao();
        }

        MomService service = new MomService(dao, daoPath);
        

        get("/ping", service::pong);

        path("/topic", () -> {
            get("/subscribe/:topic_name", service::subscribeToTopic);
            get("/:topic_name", service::getMessageFromTopic);
            post("/:topic_name", service::sendMessageToTopic);
        });

        path("/p2p", () -> {
            get("", service::getMessageFromAllApplication);
            get("/:token_other", service::getMessagesApplication);
            post("/:token_other", service::sendMessageToApplication);
        });

    }
}