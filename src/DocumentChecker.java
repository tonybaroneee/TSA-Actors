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
     * @param airportLines All of the scan queues in the airport
     */
    public DocumentChecker( ArrayList<ActorRef> airportLines, ActorRef jail ) {
        this.airportLines = airportLines;
        this.jail = jail;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        if ( msg instanceof Passenger ) {
            Passenger p = (Passenger)msg;
            printMsg("Passenger " + p.getName() + " arrives");
            // If msg is a Passenger, perform the document check.
            if ( ( Math.random()*100 ) < TestBedConstants.DOC_CHECK_FAIL_PERCENTAGE ) {
                // Document check failed, send passenger to jail!
                printMsg("Passenger " + p.getName() + " turned away");
                jail.tell( msg );
            } else {
                // Document check passed, passenger free to advance to ScanQueue
                airportLines.get( currentLineChoice % airportLines.size() ).tell( msg );
                printMsg("Passenger " + p.getName() + " sent to line " + 
                        ( currentLineChoice % airportLines.size() + 1 ) );
                currentLineChoice++;
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, relay to all ScanQueues and terminate self.
            printMsg("Close sent");
            for (ActorRef line : airportLines) {
                line.tell(msg);
            }
            getContext().stop();
            printMsg("Closed");
        }
    }

    /**
     * Prints a properly indented and labeled message
     * 
     * @param msg the message to print
     */
    private void printMsg(String msg) {
        System.out.println("Document Check: " + msg);
    }
}
