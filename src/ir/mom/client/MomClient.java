package ir.mom.client;

import java.util.List;

public class MomClient {
    private String token;
    private List<String> lstTopic;

    public MomClient(String tokeString) {
        this.token = tokeString;
    }

    public void addMessageToTopic(int idTopic){

    }

    public void sendMessageTo(int token){

    }

    public List<String> getMessagesTopic(int idTopic){
        return null;
    }

    public List<String> getMessagesPeerToPeer(){
        return null;
    }
}
