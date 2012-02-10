import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * This actor handles the results from the BodyScan and the BagScan to determine if a
 * passenger is safe to let go or must be send to jail. The SecurityPoint is the last
 * station in the airport security line.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class SecurityStation extends UntypedActor {
    
    // Instance variables
    final int lineNumber;
    final ActorRef jail;
    
    /**
     * Constructor for the SecurityStation
     * @param lineNumber
     */
    public SecurityStation( int lineNumber, ActorRef jail ) {
        this.lineNumber = lineNumber;
        this.jail = jail;
    }
    
    @Override
    public void onReceive(Object arg0) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
