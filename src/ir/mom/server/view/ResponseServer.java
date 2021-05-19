package ir.mom.server.view;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import ir.mom.server.model.Message;

public class ResponseServer {

    private String message;
    private boolean erreur;
    private List<MessageJson> listMessage;

    public ResponseServer(String message, boolean erreur, List<Message> messages) {

        this.listMessage = new LinkedList<MessageJson>();
        for(int i = 0; i< messages.size(); i++){
            Message msg = messages.get(i);
            this.listMessage.add(new MessageJson(msg.getSender().getToken(), msg.getContent()));
        }

        this.message = message;
        this.erreur = erreur;
    }

    public ResponseServer(String message, boolean erreur) {
        this.message = message;
        this.erreur = erreur;
        this.listMessage = null;
    }

    public String getMessage() { 
        return this.message;
    }
    
    public List<MessageJson> getListMessage() {
        return this.listMessage;
    }

    public boolean getErreur(){
        return this.erreur;
    }


    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ResponseServer fromJson(String json) {
        return new Gson().fromJson(json, ResponseServer.class);
    }
}
