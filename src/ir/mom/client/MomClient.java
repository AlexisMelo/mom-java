package ir.mom.client;

import ir.mom.server.model.Message;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

public class MomClient {
    private String token;
    private List<String> lstTopic;
    private Client jaxClient;

    private MomClientTerminal momTerm;
    private String REST_SERVICE_URL = "http://localhost/"; //à compléter quand le serv sera lancé

    public MomClient(String tokenString) {
        this.token = tokenString;
        this.momTerm = new MomClientTerminal(this);
        this.jaxClient = ClientBuilder.newClient();
    }

    /**
     * Recevoir les messages de toutes les autres applications
     */
    public Response getMessagesPeerToPeer(){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("p2p");
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }

    /**
     * Ajouter un message à un topic
     */
    public Response addMessageToTopic(int topicId, String message){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(message));
        
        return response;
    }

    /**
     * Envoyer un message à une autre application en mode point à point
     */
    public Response sendMessageTo(String tokenOtherApp, String message){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("p2p/{token_other_app}")
                                 .resolveTemplate("token_other_app",tokenOtherApp);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(message));
        
        return response;
    }

    /**
     * Recevoir tous les messages d'un topic
     */
    public Response getMessagesTopic(int topicId){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }

    /**
     *  Recevoir les ID et noms de tous les topics
     */
    public Response getTopics(){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics");
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }
        
    /**
     *    S'abonner à un topic 
     */
    public Response subscribeTopic(int topicId){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics/subscribe/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(""));
        
        return response;
    }

    /**
     *    Se désabonner d'un topic 
     */
    public Response unsubscribeTopic(int topicId){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics/unsubscribe/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(""));
        
        return response;
    }
}
