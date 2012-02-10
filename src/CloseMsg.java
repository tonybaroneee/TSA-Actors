/**
 * Message propagated to close the system for the day.
 * All passengers in the system must make it through before closing.
 * Actors with more than one source cannot pass on the Close message nor close 
 * themselves until they receive a Close message from all their sources.
 *  
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class CloseMsg {
    // Empty implementation - class itself represents the request.
}
