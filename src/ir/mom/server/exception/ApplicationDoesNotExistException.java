package ir.mom.server.exception;

public class ApplicationDoesNotExistException extends MomException {

    /**
    * Constructeur
    */
    public ApplicationDoesNotExistException(){
        super();
    }

    /**
    * Constructeur
    */
    public ApplicationDoesNotExistException(String msg){
        super(msg);
    }

}
