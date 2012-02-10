/**
 * The Report that is passed through the system which holds a specific passenger
 * and a boolean representing whether or not the scan check has passed or failed.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class Report {
    
    // Instance variables
    final Passenger passenger;
    final ScanResult result;
    
    /**
     * Constructor for a message
     * @param p - The passenger
     * @param r - The result of the particular scan this Report was passed from
     */
    public Report( Passenger p, ScanResult r ) {
        this.passenger = p;
        this.result = r;
    }
    
}
