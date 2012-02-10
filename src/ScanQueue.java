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
    final int position;
    final ActorRef bagScanner;
    final ActorRef bodyScanner;
    List<Passenger> passengersWaiting = new LinkedList<Passenger>();
    
    /**
     * Constructor for a ScanQueue as part of a Line.
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
        // If msg is a Passenger, immediately send their luggage off to BaggageScan and
        // send them to the BodyScann if it's in a 'ready' state. Otherwise, add them
        // to the FIFO wait queue to be notified when the BodyScan requests the next
        // passenger to be scanned.
    }
    
}
