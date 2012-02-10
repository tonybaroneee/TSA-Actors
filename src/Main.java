import static akka.actor.Actors.actorOf;

import java.util.ArrayList;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;

/**
 * The main execution point for our TSA Airport Security simulation.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class Main {
    public static void main( String[] args ) {
    	// Make the jail
        final ActorRef jail = actorOf(
                new UntypedActorFactory() {
                    @Override
                    public Actor create() {
                        return new Jail();
                    }
                });
        
        // Create the lines
    	final ArrayList<ActorRef> lines = new ArrayList<ActorRef>();
        for (int lineNum = 1; lineNum <= TestBedConstants.NUM_LINES; lineNum++) {
        	final int lineNumber = lineNum;

            // Make the security station
            final ActorRef securityStation = actorOf(
                    new UntypedActorFactory() {
                        @Override
                        public Actor create() {
                            return new SecurityStation(lineNumber, jail);
                        }
                    });
            
            // Make the bag scanner
            final ActorRef bagScanner = actorOf(
                    new UntypedActorFactory() {
                        @Override
                        public Actor create() {
                            return new BaggageScan(lineNumber, securityStation);
                        }
                    });
            
            final ActorRef bodyScanner = actorOf(
            		new UntypedActorFactory() {
						@Override
						public Actor create() {
							return new BodyScan(lineNumber, securityStation);
						}
					});
            
            // Make the queue
            ActorRef queue = actorOf(
                    new UntypedActorFactory() {
                        @Override
                        public Actor create() {
                            return new ScanQueue(lineNumber, bagScanner, bodyScanner);
                        }
                    });
            lines.add(queue);
        }

        // Make the document checker
        ActorRef documentChecker = actorOf(
                new UntypedActorFactory() {
                    @Override
                    public Actor create() {
                        return new DocumentChecker(lines, jail);
                    }
                });

        // Create the passengers
        for (int passengerNum = 1; passengerNum <= TestBedConstants.NUM_PASSENGERS;
             passengerNum++) {
            Passenger passenger = new Passenger(
                    passengerNum < TestBedConstants.PASSENGER_NAMES.length ?
                    TestBedConstants.PASSENGER_NAMES[passengerNum] :
                        "Passenger #" + passengerNum);
            documentChecker.tell(passenger);
        }
    }
}
