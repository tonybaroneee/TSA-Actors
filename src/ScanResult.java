/**
 * An enum of all possible result states from the airport security scanners.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public enum ScanResult {
    
	PASS( true ), 
	FAIL( false );
	
	// Represents whether the scan passed or failed
    private boolean resultFlag;
    
    private ScanResult( boolean resultFlag ) {
        this.resultFlag = resultFlag;
    }
    
    public boolean value() {
    	return resultFlag;
    }
    
}
