package de.hhn.britishAirways;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jan-Niklas Jäger
 */
public class Seat {
    private final String location;
    private final Plane plane;
    private final Set<BoardingPass> reservations = new HashSet<>();

    Seat (String location, Plane plane){
        this.location = location;
        this.plane = plane;
        System.out.println(this + " was created");
    }
    public String getLocation(){
        return this.location;
    }

    public Plane getPlane() {
        return plane;
    }

    public void reserve(BoardingPass pass) {
        System.out.println(this + " is now reserved by " + pass.getPassenger() + " on " + pass.getFlight());
        reservations.add(pass);
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
