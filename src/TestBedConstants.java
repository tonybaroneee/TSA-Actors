/**
 * The configuration variables for the TSA application.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class TestBedConstants {
	
	/**
	 * The number of passengers that will arrive at the airport and go through security.
	 */
    public static final int NUM_PASSENGERS = 20;
    
    /**
     * The list of the first passenger names.  Any additional passengers will be numbered.
     */
    public static final String[] PASSENGER_NAMES = { "Alvin", "Bob", "Charlie", "Dave",
        "Ellen", "Frank", "George", "Harold", "Isaac", "John", "Kelly", "Leo", "Mark",
        "Norbert", "Oliver", "Peter", "Quentin", "Ryan", "Steve", "Tom", "Ursula",
        "Vince", "Walter", "Xavier", "Yvonne", "Zach"
        };
    
    /**
     * The number of lines to have in the airport security system.
     */
    public static final int NUM_LINES = 5;

    /**
     * The probability (in %) that a passenger will be rejected at the document checker.
     */
    public static final int DOC_CHECK_FAIL_PERCENTAGE = 20;

    /**
     * The probability (in %) that a passenger will be rejected at the baggage scanner.
     */
    public static final int BAG_SCAN_FAIL_PERCENTAGE = 20;

    /**
     * The probability (in %) that a passenger will be rejected at the body scanner.
     */
    public static final int BODY_SCAN_FAIL_PERCENTAGE = 20;

}
