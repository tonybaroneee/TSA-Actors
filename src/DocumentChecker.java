import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * The first step of the security system where a passenger's documents are checked 
 * for validity and the passenger is either admitted through or sent to Jail.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class DocumentChecker extends UntypedActor {

    // Instance variables
    private final ArrayList<ActorRef> airportLines;
    private final ActorRef jail;
    private int currentLineChoice = 0;

    /**
     * Constructor for the DocumentChecker
     * 
     * @param airportLines - All of the scan queues in the airport
     */
    public DocumentChecker( ArrayList<ActorRef> airportLines, ActorRef jail ) {
        this.airportLines = airportLines;
        this.jail = jail;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        if ( msg instanceof Passenger ) {
            // If msg is a Passenger, perform the document check.
            if ( ( Math.random()*100 ) <= TestBedConstants.DOC_CHECK_FAIL_PERCENTAGE ) {
                // Document check failed, send passenger to jail!
                // TODO printout
                jail.tell( msg );
            } else {
                // Document check passed, passenger free to advance to ScanQueue
                // TODO printout
                airportLines.get( currentLineChoice % airportLines.size() ).tell( msg );
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, relay to all ScanQueues and terminate self.
            for (ActorRef line : airportLines) {
                line.tell(msg);
            }
            this.getContext().stop();
        }
    }

}
