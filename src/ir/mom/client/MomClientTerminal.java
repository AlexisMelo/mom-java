package ir.mom.client;

import java.util.InputMismatchException;
import  java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MomClientTerminal {

    private MomClient client;

    public MomClientTerminal(MomClient client) {
        this.client = client;
    }

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

    private void communicateWithOtherApp() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vous avez choisi de communiquer avec d'autres applications.");
        sb.append("\nQue voulez-vous faire ?");
        sb.append("\n1) Envoyer un message à une application");
        sb.append("\n2) Recevoir tous les messages");
        sb.append("\n3) Retour");

        System.out.println(sb.toString());

        int answer = this.getIntBetweenRange(1,3);

        switch(answer) {
            case 1:
                this.sendMessageTo();
                break;
            case 2:
                this.printMessagesPeerToPeer();
                break;
            default:
                this.menu();
                break;
        }
    }
    
    private void studyTheTopic(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vous avez choisi d'étudier les topics.");
        sb.append("\nQue voulez-vous faire ?");
        sb.append("\n1) Voir tous les topics");
        sb.append("\n2) S'abonner à un topic");
        sb.append("\n3) Se désabonner d'un topic");
        sb.append("\n4) Publier un message sur un topic");
        sb.append("\n5) Retour");
        System.out.println(sb.toString());
        int answer = this.getIntBetweenRange(1,5);
        switch(answer) {
            case 1:
                this.viewTopics();
                break;
            case 2:
                this.subscribeTopic();
                break;
            case 3:
                this.unsubscribeTopic();
                break;
            case 4:
                this.sendMessageToTopic();
                break;                
            default:
                this.menu();
                break;
        }
    }

    public void printMessagesPeerToPeer() {
        StringBuilder sb = new StringBuilder();
        List<String> lstMessages = this.client.getMessagesPeerToPeer();
        for(String message : lstMessages){
            sb.append("token:");
            sb.append("\n-message1");
            sb.append("\n-message2");
        }
        System.out.println(sb.toString());
    }

    public void printTopicMessage(int idTopic) {
        StringBuilder sb = new StringBuilder();
        List<String> lstMessages = this.client.getMessagesTopic(idTopic);
        for(String message : lstMessages){
            sb.append("sujetdutopic:");
            sb.append("\n-message1");
            sb.append("\n-message2");
        }
        System.out.println(sb.toString());
    }

    public void sendMessageTo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel est le token de l'application destinataire ?");
        String token = sc.nextLine();
        System.out.println(token);
        String message = this.getMessage();
        this.client.sendMessageTo(token, message);
    }

    public void sendMessageToTopic() {
        /*List<String> lstTopics = this.client.getTopicsSubscribe();
        StringBuilder sb = new StringBuilder();
        sb.append("Dans quel topic souhaitez-vous envoyer un message ?");
        for(String topic: lstTopics){
             sb.append("\n1)"+topic);
        }
        System.out.println(sb.toString());*/
        int idTopic = this.getIdTopic();
        String message = this.getMessage();
        this.client.addMessageToTopic(idTopic, message);
    }  

    public void subscribeTopic() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vous souhaitez vous abonner à un topic.");
        sb.append("A quel topic souhaitez-vous vous abonner ?");
        System.out.println(sb.toString());
        int idTopic = this.getIdTopic();
        /*sb.append("\n Dans quel topic souhaitez-vous vous abonner ?");
        List<String> lstTopics = this.client.getTopics();
        for(String topic: lstTopics){
             sb.append("\n1)"+topic);
        }*/
        System.out.println("Vous êtes désormais abonné à ce topic.");
        this.studyTheTopic();
    }

    public void unsubscribeTopic() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vous souhaitez vous désabonner à un topic.");
        sb.append("\n De quel topic souhaitez-vous vous désabonner ?");
        System.out.println(sb.toString());
        int idTopic = this.getIdTopic();
        /*List<String> lstTopics = this.client.getTopicsSubscribe();
        for(String topic: lstTopics){
             sb.append("\n1)"+topic);
        }*/
        System.out.println("Vous êtes désormais désabonné de ce topic.");
        this.studyTheTopic();
    }

    public void viewTopics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vous souhaitez voir tous les topics crées.");
        List<String> lstTopics = this.client.getTopics();
        
        for(String topic: lstTopics){
             sb.append("\n - " + topic);
        }

        System.out.println(sb.toString());
        
        this.studyTheTopic();
    }

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
            }
            
        } while(!answerValid);

        return answer;
    }

    private String getMessage(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Votre message :");
        String message = sc.nextLine();
        return message;
    }

    private int getIdTopic(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Id du topic:");
        int idTopic = sc.nextInt();
        return idTopic;
    }

    public static void main(String[] args) {
        
        MomClient momclt = new MomClient("token7889952");
        MomClientTerminal clt = new MomClientTerminal(momclt);
        clt.menu();
    }
}
