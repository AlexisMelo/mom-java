package ir.mom.server.exception;

public class ApplicationAlreadySubscribedException extends MomException  {
    
    /**
    * Constructeur
    */
    public ApplicationAlreadySubscribedException(){
        super();
    }

    /**
    * Constructeur
    */
    public ApplicationAlreadySubscribedException(String msg){
        super(msg);
    }
}
