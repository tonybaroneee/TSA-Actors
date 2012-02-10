import java.util.LinkedList;
import java.util.List;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * This class represents the waiting queue before a passenger receives the OK to proceed
 * through the body scanner. A passenger's luggage is sent to the BagScanner
 * immediately when he/she reaches this point.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class ScanQueue extends UntypedActor {

    // Instance variables
    private final int position;
    private final ActorRef bagScanner;
    private final ActorRef bodyScanner;
    private final String INDENT = "  ";
    private List<Passenger> passengersWaiting = new LinkedList<Passenger>();

    /**
     * Constructor for a ScanQueue as part of a Line.
     * 
     * @param position - The line number as to which this ScanQueue belongs to
     * @param bagScanner - The baggage scanner belonging to this ScanQueue
     * @param bodyScanner - The body scanner belonging to this ScanQueue
     */
    public ScanQueue( int position, ActorRef bagScanner, ActorRef bodyScanner ) {
        this.position = position;
        this.bagScanner = bagScanner;
        this.bodyScanner = bodyScanner;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        //TODO figure out BodyScan shtuff
        //TODO disregard any new passengers after close message sent in (maybe a boolean)

        if ( msg instanceof Passenger ) {
        	System.out.println(INDENT + "Queue " + position + ": Passenger " + 
        			((Passenger) msg).getName() + " arrives in line");
            // If msg is a Passenger, immediately send their luggage off to BaggageScan 
            // and send them to the BodyScan if it's in a 'ready' state. Otherwise, add 
            // them to the FIFO wait queue to be notified when the BodyScan requests the 
            // next passenger to be scanned.
            bagScanner.tell( msg );
            if ( /*TODO check if bodyscan is ready*/true ) {
                bodyScanner.tell( msg );
            } else {
                passengersWaiting.add( 0, (Passenger)msg );
            }
        } else if ( msg instanceof NextMsg ) {
            // If msg is a NextMsg, the body scanner is marked ready if no passengers 
            // are waiting. Otherwise, the first passenger is sent into the scanner.
            if ( passengersWaiting.isEmpty() ) {
                //TODO mark body scan as 'ready'
            } else {
                Passenger p = passengersWaiting.remove( 0 );
                bodyScanner.tell( p );
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, message is relayed to the baggage scanner 
            // immediately, and to the body scanner if it is in the ready state. 
            // If the body scanner is still processing a passenger, and/or if passengers 
            // are waiting in this queue, the CloseMsg must be deferred until all 
            // passengers have been through the body scanner.
            bagScanner.tell( msg );
            if ( /*TODO check if bodyscan is not in ready state*/true || 
                    !passengersWaiting.isEmpty() ) {
                //TODO defer close message and let rest of passengers through that are waiting
            } else {
                bodyScanner.tell( msg );
            }
        }
    }

}