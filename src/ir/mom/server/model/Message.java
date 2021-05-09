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

    public Message(Application sender, String content, MessageQueue receiver) {
        this.sender = sender;
        this.content = content;
        this.hasRead = new HashMap<Application, Boolean>();
        receiver.addMessage(this); //C'est la MessageQueure qui gère a quelles applications elle envoie le message //cf diagramme d'interaction
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
        for (Application app: readers) {
            this.addReader(app);            
        }
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
        for (Map.Entry<Application, Boolean> hasRead : this.hasRead.entrySet()) {
            sb.append("\n - " + hasRead.getKey() + " : " + hasRead.getValue());
        }
        return sb.toString();
    }

    public Boolean hasRead(Application potentialReader){
        return this.hasRead.get(potentialReader); //null si potentialReader not inside
    }
    public static void main(String[] args) {
        
        Application app1 = new Application("PC d'Alexis");
        Application app2 = new Application("Gameboy de Léo");
        Application app3 = new Application("Wii de Louis");
        Application app4 = new Application("AppleWatch d'Alexandre");
        Application app5 = new Application("Frigo connecté de Malandain");

        Message msg = new Message(app1, "Salut", app2);

        System.out.println("\nTest 1 - Constructeur & ToString ------------------------------");
        System.out.println(msg);

        System.out.println("\nTest 2 - Getters ----------------------------------------------");
        System.out.println("Get sender : " + msg.getSender());
        System.out.println("Get content : " + msg.getContent());
        System.out.println("Get readers : " + msg.getReaders());

        System.out.println("\nTest 3 - Ajout 1 reader ---------------------------------------");
        System.out.println("Avant ajout : " + msg.getReaders());
        msg.addReader(app3);
        System.out.println("Après ajout : " + msg.getReaders());

        System.out.println("\nTest 4 - Ajout 2 readers --------------------------------------");
        System.out.println("Avant ajout : " + msg.getReaders());
        List<Application> readers = new ArrayList<Application>();
        readers.add(app4);
        readers.add(app5);
        msg.addReader(readers);
        System.out.println("Après ajout : " + msg.getReaders());

        System.out.println("\nTest 5 - Suppression reader -----------------------------------");
        System.out.println("Avant suppression : " + msg.getReaders());
        msg.removeReader(app3);
        System.out.println("Après suppression : " + msg.getReaders());

        System.out.println("\nTest 6 - Read -------------------------------------------------");
        System.out.println("\n" + msg);
        msg.read(app1);
        msg.read(app4);
        System.out.println("\n" + msg);

        System.out.println("\nTest 7 - Json -------------------------------------------------");
        System.out.println(msg.toJson());
        
    }
}