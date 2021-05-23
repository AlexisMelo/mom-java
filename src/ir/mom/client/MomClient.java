package ir.mom.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

/**
 * Classe qui gère le client du MOM.
 * @author Alexis Melo da Silva, Alexandre Vigneron
 */
public class MomClient {
    private String token;
    private Client jaxClient;
    private String serviceUrl;

    /**
     * Constructeur
     * @param le token que le client a choisi
     */
    public MomClient(String tokenString, String serviceUrl) {
        this.token = tokenString;
        this.jaxClient = ClientBuilder.newClient();
        this.serviceUrl = serviceUrl;
    }

    /**
     * Recevoir les messages de toutes les autres applications
     * @return Response la réponse
     */
    public Response getMessagesPeerToPeerAllApps(){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/p2p");
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }

    /**
     * Recevoir les messages d'une application en particulier
     * @param String tokenOtherApp token de l'application dont on veut recevoir les messages
     * @return Response la réponse
     */
    public Response getMessagesPeerToPeerOneApp(String tokenOtherApp){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/p2p/{token_other_app}")
                                 .resolveTemplate("token_other_app",tokenOtherApp);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }

    /**
     * Ajouter un message à un topic
     * @param String topicId l'id du topic
     * @param String message le message a envoyer
     * @return Response la réponse
     */
    public Response sendMessageToTopic(String topicId, String message){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/topic/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(message));
        
        return response;
    }

    /**
     * Envoyer un message à une autre application en mode point à point
     * @param String tokenOtherApp le token de l'application destinataire
     * @param String message le message a envoyer
     * @return Response la réponse
     */
    public Response sendMessageToApplication(String tokenOtherApp, String message){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/p2p/{token_other_app}")
                                 .resolveTemplate("token_other_app",tokenOtherApp);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(message));
        
        return response;
    }

    /**
     * Recevoir tous les messages d'un topic
     * @param String topicId l'id du topic
     * @return Response la réponse
     */
    public Response getMessagesTopic(String topicId){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/topic/{topic_id}")
                                 .resolveTemplate("topic_id", topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        return response;
    }
        
    /**
     * S'abonner à un topic 
     * @param String topicId l'id du topic
     * @return Response la réponse
     */
    public Response subscribeTopic(String topicId){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/topic/subscribe/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        // System.out.println(target.getUri().toString());
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        
        return response;
    }

    /**
     * Se désabonner d'un topic 
     * @param String topicId l'id du topic
     * @return Response la réponse
     */
    public Response unsubscribeTopic(String topicId){
        WebTarget target = this.jaxClient.target(serviceUrl)
                                 .path("/topic/unsubscribe/{topic_id}")
                                 .resolveTemplate("topic_id",topicId);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .get();
        
        return response;
    }
}
