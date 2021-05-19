package ir.mom.server.exception;

public class CantAddWriterOfMessageToReadersException extends MomException  {
    
    /**
    * Constructeur
    */
    public CantAddWriterOfMessageToReadersException(){
        super();
    }

    /**
    * Constructeur
    */
    public CantAddWriterOfMessageToReadersException(String msg){
        super(msg);
    }
}
