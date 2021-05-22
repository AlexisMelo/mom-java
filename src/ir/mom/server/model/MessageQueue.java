package ir.mom.server.model;

import java.util.LinkedList;
import java.io.Serializable;
import java.util.List;

import ir.mom.server.exception.CantAddWriterOfMessageToReadersException;

public class MessageQueue implements Serializable{
    private List<Message> messages; 

    public MessageQueue() {
        this.messages = new LinkedList<Message>();
    }
    public List<Message> getMessages(){
        return this.messages;
    }

    public void addMessage(Message message) throws CantAddWriterOfMessageToReadersException {
        this.messages.add(message);
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
    }

    /**
     * 
     * @param reader Application dont on souhaite check les messages non-lus
     * @return Liste des messages que le reader n'a pas lu
     */
    public List<Message> getMessageToRead(Application reader) {
        List<Message> messagesToRead = new LinkedList<Message>();

        for (Message msg : this.messages) {
            Boolean hasRead = msg.hasRead(reader);
            // boolean isSender = msg.getSender().equals(reader);

            if (hasRead != null && !hasRead) {
                messagesToRead.add(msg);
                msg.read(reader);
                if (msg.erasable()) {
                    this.removeMessage(msg);
                }
            }
        }
        System.gc();
        return messagesToRead;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i < this.messages.size(); i++) {
            sb.append("\n(Message n°" + (i+1) + ")\n");
            sb.append(this.messages.get(i) + "\n");
        }

        return sb.toString();
    }
    public static void main(String[] args) throws CantAddWriterOfMessageToReadersException {
        Application app1 = new Application("PC d'Alexis");
        Application app2 = new Application("Gameboy de Léo");
        Application app3 = new Application("Table de pingpong de Benji");

        Message msg = new Message(app1, "Salut", app2);
        msg.addReader(app3);

        Message msg2 = new Message(app3, "Salut", app2);

        MessageQueue msgq = new MessageQueue();

        System.out.println("\nTest 1 - Ajout msg ------------------------------");
        msgq.addMessage(msg);
        msgq.addMessage(msg2);
        System.out.println(msgq);

        System.out.println("\nTest 2 - Suppression msg ------------------------");
        msgq.removeMessage(msg);
        System.out.println(msgq);

        System.out.println("\nTest 3 - Messages to read : Benji ------------------------");
        System.out.println(msgq.getMessageToRead(app3));

        System.out.println("\nTest 4 - Messages to read : Léo ------------------------");
        System.out.println(msgq.getMessageToRead(app2));

    }
}