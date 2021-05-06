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
    private String REST_SERVICE_URL = "http://.../"; //à compléter quand le serv sera lancé

    public MomClient(String tokenString) {
        this.token = tokenString;
        this.momTerm = new MomClientTerminal(this);
        this.jaxClient = ClientBuilder.newClient();
    }

    public Response addMessageToTopic(int idTopic, String message){
        WebTarget target = this.jaxClient.target(REST_SERVICE_URL)
                                 .path("topics/{idTopic}")
                                 .resolveTemplate("idTopic",idTopic);
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                                  .header("token",this.token)
                                  .post(Entity.json(message));
        
        return response;
    }

    public void sendMessageTo(String token, String message){

    }

    public List<String> getMessagesTopic(int idTopic){
        return null;
    }

    public List<String> getMessagesPeerToPeer(){
        return null;
    }

    public List<String> getTopics(){
        List<String> lst = new ArrayList<String>();
        lst.add("Cinema");
        lst.add("Sports");
        lst.add("Jeux-vidéos");
        return lst;
    }
}
