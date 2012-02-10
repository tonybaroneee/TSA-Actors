/**
 * A passenger that flows through the security system.
 * 
 * @author Anthony Barone
 * @author Ryan Clough
 * @author Rebecca Dudley
 * @author Ryan Mentley
 */
public class Passenger {

    // Instance variables
    private String name;

    /**
     * Constructor for a passenger
     * 
     * @param name - The name of the passenger
     */
    public Passenger(String name) {
        this.name = name;
    }

    /**
     * Fetch the name of this passenger
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

}
