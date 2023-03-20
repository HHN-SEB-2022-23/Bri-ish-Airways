package de.hhn.britishAirways;

/**
 * @author jan-niklas jäger
 */
public class Seat {
    private final String location;

    Seat (String location){
        this.location=location;
        System.out.println(this + " was created");
    }
    public void reserve(){
        System.out.println(this+" reserve");
    }
    @Override
    public String toString(){
        return "Seat " + location ;
    }
    @Override
    public int hashCode() {
        return location.hashCode();
    }

}
