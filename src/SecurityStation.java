import java.util.HashMap;
import java.util.Map;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * This actor handles the results from the BodyScan and the BagScan to determine
 * if a passenger is safe to let go or must be send to jail. The SecurityPoint
 * is the last station in the airport security line.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class SecurityStation extends UntypedActor {

    // Constants
    private static final String INDENT = "      ";

    // Instance variables
    private final int lineNumber;
    private final ActorRef jail;
    private final int numScannersPerLine = 2;
    private Map<Passenger, Report> pendingReports = new HashMap<Passenger, Report>();
    private int numCloseMsgsReceived;

    /**
     * Constructor for the SecurityStation
     * 
     * @param lineNumber the line number sending passengers to this security station
     * @param jail the jail to which to send detainees
     */
    public SecurityStation( int lineNumber, ActorRef jail ) {
        this.lineNumber = lineNumber;
        this.jail = jail;
    }

    @Override
    public void onReceive( final Object msg ) throws Exception {
        if ( msg instanceof Report ) {
            // If msg is a Report, check to see if we have received a report for
            // this passenger already. If so, make a decision whether to admit
            // through or not. Else, store the passenger & report in our reports
            // map for later.
            Report report = (Report) msg;
            boolean currentResult = report.getResult();
            printMsg("Passenger " + report.getPassenger().getName() +
                    " report received (" + (currentResult ? "pass" : "fail") + ")");
            if ( !pendingReports.containsKey( report.getPassenger() ) ) {
                pendingReports.put(report.getPassenger(), report);
                
            } else {
                Report previousReport = pendingReports.remove( report.getPassenger() );
                boolean previousResult = previousReport.getResult();
                if ( previousResult && currentResult) {
                    // Passenger passes, leaves system.
                    printMsg("Passenger " + report.getPassenger().getName() + 
                            " (pass/pass) released to airport");
                } else {
                    // Passenger has failed one or more scans, send to Jail.
                    printMsg("Passenger " + report.getPassenger().getName() + " (" +
                            (previousResult ? "pass" : "fail") + "/" +
                            (currentResult ? "pass" : "fail") +") sent to jail");
                    jail.tell( report.getPassenger() );
                }
            }
        } else if ( msg instanceof CloseMsg ) {
            // If msg is a CloseMsg, check to see if we have received CloseMsgs from
            // both scanners. If so, relay msg to Jail and terminate self.
            numCloseMsgsReceived++;
            printMsg("Close received (" + numCloseMsgsReceived + " of " +
                    numScannersPerLine + " scanners)");
            if ( numCloseMsgsReceived >= numScannersPerLine ) {
                jail.tell(msg);
                printMsg("Close sent to jail");
                getContext().stop();
                printMsg("Closed");
            }
        }
    }

    /**
     * Prints a properly indented and labeled message
     * 
     * @param msg the message to print
     */
    private void printMsg(String msg) {
        System.out.println(INDENT + "Security " + lineNumber + ": " + msg);
    }
}
