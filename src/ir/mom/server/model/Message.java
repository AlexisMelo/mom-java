package ir.mom.server.model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

public class Message {

    private Application sender;
    private String content;
    private Map<Application, Boolean> hasRead;

    public Message(Application sender, String content, Application receiver) {
        this.sender = sender;
        this.content = content;
        this.hasRead = new HashMap<Application, Boolean>();
        this.hasRead.put(receiver, false);
    }

    public String getContent() {
        return this.content;
    }

    public Application getSender() {
        return this.sender;
    }

    public void addReader(Application reader) {

        //check si le reader n'est pas déjà dans la liste
        if (!this.hasRead.containsKey(reader)) {
            this.hasRead.put(reader, false);
        }
    }

    public void addReader(List<Application> readers) {
        /*for (Application app: readers) {
            this.addReader(app);            
        }*/
    }

    public void removeReader(Application reader) {

        //check si le reader est bien dans la liste des reader
        if (this.hasRead.containsKey(reader)) {
            this.hasRead.remove(reader);
        }
    }

    public List<Application> getReaders() {
        return new ArrayList<Application>(this.hasRead.keySet());
    }

    public void read(Application reader) {
        
        //check pour ne pas l'ajouter si le reader en paramètre n'est pas dans les readers de base
        if (this.hasRead.containsKey(reader)) {
            this.hasRead.put(reader, true);
        }
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    /**
     * Retourne vrai si tous les readers ont lu le message
     */
    public boolean erasable() {
        for (Map.Entry<Application, Boolean> hasRead : this.hasRead.entrySet()) {
            if (!hasRead.getValue()) {
                return false;
            } 
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sender : " + this.sender);
        sb.append("\nContent : " + this.content);
        sb.append("\nReaders : ");
        for (Application app : this.hasRead.keySet()) {
            sb.append("\n - " + app);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        
        Application app1 = new Application("supertoken");
        Application app2 = new Application("gigatoken");
        Application app3 = new Application("ultratoken");

        Message msg = new Message(app1, "Salut", app2);

        System.out.println("\nTest 1 - Constructeur & ToString");
        System.out.println(msg);

        System.out.println("\nTest 2 - Getters");
        System.out.println("Get sender : " + msg.getSender());
        System.out.println("Get content : " + msg.getContent());
        System.out.println("Get readers : " + msg.getReaders());
    }
}