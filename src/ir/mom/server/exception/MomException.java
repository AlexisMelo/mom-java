package ir.mom.server.exception;

public abstract class MomException extends Exception {
    
    /**
    * Constructeur
    */
    public MomException(){
        super();
    }

    /**
    * Constructeur
    */
    public MomException(String msg){
        super(msg);
    }

}
