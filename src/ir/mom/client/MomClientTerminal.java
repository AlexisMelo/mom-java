package ir.mom.client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.core.Response;

import ir.mom.server.view.ResponseServer;
import ir.mom.server.view.MessageJson;

/**
 * Classe qui gère l'affichage du client du MOM.
 * @author Alexis Melo da Silva, Alexandre Vigneron
 */
public class MomClientTerminal {

    private MomClient client;

    /**
     * Constructeur.
     * @param MomClient client le client lié à cette IHM
     */
    public MomClientTerminal(MomClient client) {
        this.client = client;
    }

    /**
     * Affichage du menu.
     */
    public void menu() {
        StringBuilder menu = new StringBuilder();

        menu.append("Que voulez-vous faire ?");
        menu.append("\n1) Communiquer avec une autre application");
        menu.append("\n2) Etudier les topics");
        menu.append("\n3) Quitter");

        System.out.println(menu.toString());

        int answer = this.getIntBetweenRange(1,3);

        switch(answer) {
            case 1:
                this.communicateWithOtherApp();
                break;
            case 2:
                this.studyTheTopic();
                break;
            default:
                System.out.println("Au revoir !");
                System.exit(0);
        }
    }


    /**
        Afficher tous les messages de toutes les applications
     */
    public void getMessagesPeerToPeerAllApps() {

        ResponseServer response = this.getResponseServerObject(this.client.getMessagesPeerToPeerAllApps());

        if (response.getErreur()) {
            System.out.println(response.getMessage());
        } else {
            List<MessageJson> messages = response.getListMessage();

            if (!messages.isEmpty()) {
                for (MessageJson message : messages) {
                    System.out.println(message.getSender() + " : " + message.getContent());
                }  
            }
            else {
                System.out.println("Vous n'avez aucun nouveau message !");
            }
        }
    }

    /**
        Afficher les messages reçus d'une autre application
     */
    public void getMessagesPeerToPeerOneApp() {

        System.out.println("De quelle application voulez-vous recevoir un message ?");
        String token = this.getIdTopicOrTokenApplication();

        ResponseServer response = this.getResponseServerObject(this.client.getMessagesPeerToPeerOneApp(token));

        if (response.getErreur()) {
            System.out.println(response.getMessage());
        } else {
            List<MessageJson> messages = response.getListMessage();

            if (!messages.isEmpty()) {
                System.out.println("Voici tous les nouveaux messages de " + token);
                for (MessageJson message : messages) {
                    System.out.println(message.getContent());
                }
            }
            else {
                System.out.println("Vous n'avez aucun nouveau message de " + token + " !");
            }
        }
    }

    /**
     * Envoyer un message à un topic
     */
    public void sendMessageToTopic() {

        System.out.println("A quel topic souhaitez-vous envoyer un message ?");
        String idTopic = this.getIdTopicOrTokenApplication(); //id du topic à qui envoyer le msg

        System.out.println("Quel message souhaitez-vous envoyer ?");
        String message = this.getMessage(); //message que l'on doit envoyer

        ResponseServer response = this.getResponseServerObject(this.client.sendMessageToTopic(idTopic, message));

        System.out.println(response.getMessage()); //que le message soit bien envoyé ou non dans tous les cas le message de la réponse affichera ce qu'il s'est passé
    }  

    /**
        Envoyer un message à une application
     */
    public void sendMessageToApplication() {

        System.out.println("Quel est le token de l'application destinataire ?");
        String token = this.getIdTopicOrTokenApplication();

        System.out.println("Quel message souhaitez-vous envoyer ?");
        String message = this.getMessage();

        ResponseServer response = this.getResponseServerObject(this.client.sendMessageToApplication(token, message));

        System.out.println(response.getMessage()); //que le message soit bien envoyé ou non dans tous les cas le message de la réponse affichera ce qu'il s'est passé
    }

    /**
        Afficher les messages d'un topic en particulier
     */
    public void getMessagesTopic() {

        System.out.println("De quel topic souhaitez-vous recevoir les messages ?");
        String idTopic = this.getIdTopicOrTokenApplication(); //identifiant du topic dont on doit obtenir la réponse

        ResponseServer response = this.getResponseServerObject(this.client.getMessagesTopic(idTopic));

        if (response.getErreur()) {
            System.out.println(response.getMessage());
        } else {
            List<MessageJson> messages = response.getListMessage();

            if (!messages.isEmpty()) {
                System.out.println("Voici tous les nouveaux messages du topic " + idTopic);
                for (MessageJson message : messages) {
                    System.out.println(message.getContent());
                }
            }
            else {
                System.out.println("Il n'y a aucun nouveau message dans le topic " + idTopic + " !");
            }
        }
    }

    /**
        S'abonner à un topic
     */
    public void subscribeTopic() {

        System.out.println("A quel topic souhaitez-vous vous abonner ?");
        String idTopic = this.getIdTopicOrTokenApplication(); //id du topic à qui envoyer le msg

        ResponseServer response = this.getResponseServerObject(this.client.subscribeTopic(idTopic));

        System.out.println(response.getMessage()); //qu'il y ai une erreur ou non, le serveur affiche le résultat dans l'attribut message, on peut donc directement l'afficher
    }

    /**
        Se désabonner d'un topic
     */
    public void unsubscribeTopic() {

        System.out.println("\n De quel topic souhaitez-vous vous désabonner ?");
        String idTopic = this.getIdTopicOrTokenApplication();

        ResponseServer response = this.getResponseServerObject(this.client.unsubscribeTopic(idTopic));

        System.out.println(response.getMessage()); //qu'il y ai une erreur ou non, le serveur affiche le résultat dans l'attribut message, on peut donc directement l'afficher
    }

    /**
     * Demander à l'utilisateur un nombre entre un min et un max
     * @param int min le minimum du nombre
     * @param int max le maximum du nombre
     * @return int le nombre choisi par l'utilisateur
     */
    private int getIntBetweenRange(int min, int max) {
        
        Scanner answerScanner = new Scanner(System.in);
        int answer = 0;
        boolean answerValid = false;
        String msgErreur = String.format("[Erreur] Veuillez entrer un chiffre entre %d et %d", min, max);
        do {

            try {
                answer = answerScanner.nextInt();
            
                if (answer < min || answer > max) {
                    System.out.println(msgErreur);
                }
                else {
                    answerValid = true;
                }
            } 
            catch(InputMismatchException | NumberFormatException e) {
                System.out.println(msgErreur);
                answerScanner.nextLine();
            }
        } while(!answerValid);
        return answer;
    }

    /**
     * Demande à l'utilisateur de rentrer une chaine de caractère au clavier
     * @return String le message
     */
    private String getMessage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Votre message :");
        String message = sc.nextLine();
        return message;
    }

    /**
     * Demande à l'utilisateur de rentrer un token ou un id d'une application au clavier
     * @return String le token
     */
    private String getIdTopicOrTokenApplication(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Id du topic:");
        String idTopic = sc.nextLine();
        return idTopic;
    }
    
    /**
     * Retourne la réponse du serveur au format ResponseServer
     * @param httpResponse la réponse du serveur
     * @return ResponseServer la réponse au format ResponseServer
     */
    private ResponseServer getResponseServerObject(Response httpResponse){
        String response = httpResponse.readEntity(String.class);

        try {
            return ResponseServer.fromJson(response);
        } catch(Exception e) {
            return new ResponseServer("Le résultat du serveur ne semble pas interpretable...", true);
        }

    }

    /**
     * Menu pour p2p
     */
    private void communicateWithOtherApp() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vous avez choisi de communiquer avec d'autres applications.");
        sb.append("\nQue voulez-vous faire ?");
        sb.append("\n1) Envoyer un message à une application");
        sb.append("\n2) Recevoir tous les messages");
        sb.append("\n3) Recevoir les messages d'une application en particulier");
        sb.append("\n4) Retour");

        System.out.println(sb.toString());

        int answer = this.getIntBetweenRange(1,4);

        switch(answer) {
            case 1:
                this.sendMessageToApplication();
                break;
            case 2:
                this.getMessagesPeerToPeerAllApps();
                break;
            case 3:
                this.getMessagesPeerToPeerOneApp();
            default:
                this.menu();
                break;
        }
    }
    
    /**
     * Menu pour la gestion des topics
     */
    private void studyTheTopic(){
        StringBuilder sb = new StringBuilder();

        sb.append("Vous avez choisi d'étudier les topics.");
        sb.append("\nQue voulez-vous faire ?");
        sb.append("\n1) S'abonner à un topic");
        sb.append("\n2) Se désabonner d'un topic");
        sb.append("\n3) Publier un message sur un topic");
        sb.append("\n4) Recevoir les messages d'un topic");
        sb.append("\n5) Retour");

        System.out.println(sb.toString());

        int answer = this.getIntBetweenRange(1,5);

        switch(answer) {
            case 1:
                this.subscribeTopic();
                break;
            case 2:
                this.unsubscribeTopic();
                break;
            case 3:
                this.sendMessageToTopic();
                break;      
            case 4:
                this.getMessagesTopic();
                break;  
            default:
                this.menu();
                break;
        }
    }

    public static void main(String[] args) {

        if (args.length == 0){
            System.out.println("Il faut donner le token du client dans les arguments de la commande");
            System.exit(1);
        }
        String url = "http://localhost:4567/";
        if (args.length >= 2)
            url = args[1];
        MomClient momclt = new MomClient(args[0], url);
        MomClientTerminal clt = new MomClientTerminal(momclt);

        while(true) { // à enlever et mettre les bons retours au bon endroit dans les fcts si on veut améliorer
            clt.menu();
        }
    }

}
