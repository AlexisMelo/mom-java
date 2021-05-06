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
                menu();
                break;
        }
    }
    
    private void studyTheTopic(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vous avez choisi d'étudier les topics.");
        sb.append("\nQue voulez-vous faire ?");
        sb.append("\n1) S'abonner à un topic");
        sb.append("\n2) Se désabonner d'un topic");
    }

    public void printMessagesPeerToPeer() {
        StringBuilder sb = new StringBuilder();
        List<String> lstMessages = this.client.getMessagesPeerToPeer();
        for(String message : lstMessages){
            sb.append("token:");
            sb.append("\n-message1");
            sb.append("\n-message2");
        }
    }

    public void printTopicMessage(int idTopic) {
        StringBuilder sb = new StringBuilder();
        List<String> lstMessages = this.client.getMessagesTopic(idTopic);
        for(String message : lstMessages){
            sb.append("sujetdutopic:");
            sb.append("\n-message1");
            sb.append("\n-message2");
        }
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
        List<String> lstTopics = this.client.getTopics();
        StringBuilder sb = new StringBuilder();
        sb.append("Dans quel topic souhaitez-vous envoyer un message ?");
        for(String topic: lstTopics){
             sb.append("\n1)"+topic);
        }
        int answer = this.getIntBetweenRange(1,lstTopics.size());
        int idTopic = 2;
        String message = this.getMessage();
        this.client.addMessageToTopic(idTopic, message);
    }  

    public void subscribeTopic() {
    }

    public void unsubscribeTopic() {
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

    public static void main(String[] args) {
        
        MomClientTerminal clt = new MomClientTerminal(null);
        clt.menu();
        //clt.communicateWithOtherApp();
        //clt.sendMessageTo();
        //String lol = clt.getMessage();
    }
}
