package ir.mom.server.exception;

public class ApplicationNotSubscribedException extends MomException {

    /**
    * Constructeur
    */
    public ApplicationNotSubscribedException(){
        super();
    }

    /**
    * Constructeur
    */
    public ApplicationNotSubscribedException(String msg){
        super(msg);
    }
}
