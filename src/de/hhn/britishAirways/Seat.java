package de.hhn.britishAirways;

/**
 * @author Jan-Niklas Jäger
 */
public class Seat {
    private final String location;

    Seat (String location){
        this.location=location;
        System.out.println(this + " was created");
    }
    public String getLocation(){
        return this.location;
    }

    public void reserve(){
        System.out.println(this+" reserve");
    }

    @Override
    public String toString(){
        return "Seat " + this.location ;
    }

    @Override
    public int hashCode() {
        return this.location.hashCode();
    }
}
