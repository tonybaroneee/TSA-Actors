import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * The body scanner as part of a line in the airport security system. A passenger
 * is passed in and is sent away with either a pass or fail flag depending on the
 * results of the body scan.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class BodyScan extends UntypedActor {

    // Instance variables
    private final int lineNumber;
    private final ActorRef scanQueue;
    private final ActorRef securityStation;

    /**
     * Constructor for a body scanner.
     * 
     * @param lineNumber - Which line this scanner is in
     * @param scanQueue - The scan queue before this (for sending NextMsgs to)
     * @param securityStation - The security station after this (for sending Reports to)
     */
    public BodyScan( int lineNumber, ActorRef scanQueue, ActorRef securityStation ) {
        this.lineNumber = lineNumber;
        this.scanQueue = scanQueue;
        this.securityStation = securityStation;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        //TODO printouts
        //TODO deal with sending NextMsg and also storing this object's state
        //     (initial state = ready, otherwise busy and can't accept new passengers)

        if ( msg instanceof Passenger ) {
            // If msg is a Passenger, perform the body scan.
            if ( ( Math.random()*100 ) <= TestBedConstants.BODY_SCAN_FAIL_PERCENTAGE ) {
                // Body scan failed, send a fail Report to security station
                securityStation.tell( new Report( (Passenger)msg, ScanResult.FAIL ) );
            } else {
                // Body scan approved, send a pass Report to security station
                securityStation.tell( new Report( (Passenger)msg, ScanResult.PASS ) );
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, relay to the security station and terminate self.
            securityStation.tell( msg );
            this.getContext().stop();
        }
    }
}
