/**
 * An enum of all possible result states from the airport security scanners.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public enum ScanResult {

    PASS( true ), FAIL( false );

    // Represents whether the scan passed or failed
    private boolean resultFlag;

    /**
     * The internal constructor for a ScanResult enum
     * 
     * @param resultFlag - true: pass, false: fail
     */
    private ScanResult( boolean resultFlag ) {
        this.resultFlag = resultFlag;
    }

    /**
     * Retrieve the scan result boolean from the enum
     * 
     * @return Scan result
     */
    public boolean value() {
        return resultFlag;
    }

}
