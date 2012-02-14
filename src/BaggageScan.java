import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * The baggage scanner as part of a line in the airport security system. A passenger
 * is passed in (which symbolizes the luggage of the passenger) and is sent away with 
 * either a pass or fail flag depending on the results of the bag scan.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class BaggageScan extends UntypedActor {

    // Constants
    private static final String INDENT = "    ";

    // Instance variables
    private final int lineNumber;
    private final ActorRef securityStation;

    /**
     * Constructor for a body scanner.
     * 
     * @param lineNumber Which line this scanner is in
     * @param securityStation The security station after this (for sending Reports to)
     */
    public BaggageScan( int lineNumber, ActorRef securityStation ) {
        this.lineNumber = lineNumber;
        this.securityStation = securityStation;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        if ( msg instanceof Passenger ) {
            Passenger p = (Passenger)msg;
            printMsg("Passenger " + p.getName() + " baggage enters");
            // If msg is a Passenger, perform the baggage check.
            if ( ( Math.random()*100 ) < TestBedConstants.BAG_SCAN_FAIL_PERCENTAGE ) {
                // Baggage check failed, send a fail Report to security station
                printMsg("Passenger " + p.getName() + " baggage fails");
                securityStation.tell( new Report( p, false ) );
            } else {
                // Baggage check approved, send a pass Report to security station
            	printMsg("Passenger " + p.getName() + " baggage passes");
                securityStation.tell( new Report( p, true ) );
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, relay to the security station and terminate self.
            printMsg("Close received");
            securityStation.tell( msg );
            printMsg("Close sent to security");
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
        System.out.println(INDENT + "Baggage Scan " + lineNumber + ": " + msg);
    }
}
