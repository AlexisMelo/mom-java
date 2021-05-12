package ir.mom.server.view;

import com.google.gson.Gson;
import java.util.List;
import ir.mom.server.model.Message;

public class ListMessage{
    
    private String[] lstMessage;

    public ListMessage(List<Message> messages) {
        this.lstMessage = new String[messages.size()];
        for(int i = 0; i< messages.size(); i++){
            this.lstMessage[i] = messages.get(i).getContent();
        }
    }

    public String[] getMessages() { 
        return this.lstMessage;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ListMessage fromJson(String json) {
        return new Gson().fromJson(json, ListMessage.class);
    }

}