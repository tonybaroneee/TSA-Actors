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
