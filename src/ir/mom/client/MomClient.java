package ir.mom.client;

import ir.mom.server.model.Message;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    public void addMessageToTopic(int idTopic, String message){
        //Message message = this.momentClientTerminal.sendMessageToTopic();
        
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
        return null;
    }
}
