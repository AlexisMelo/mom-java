package ir.mom.server.exception;

public class TopicDoesNotExistException extends MomException {
    /**
     * Constructeur
     */
    public TopicDoesNotExistException(){
        super();
    }

    /**
    * Constructeur
    */
    public TopicDoesNotExistException(String msg){
        super(msg);
    }
}
