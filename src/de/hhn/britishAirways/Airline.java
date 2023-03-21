package de.hhn.britishAirways;

import java.util.HashSet;
import java.util.Set;

/**
 * @author René Ott
 */
public class Airline {

    private String IATACode;

    private String name;

    private final HashSet<Pilot> pilots = new HashSet<>(50);    //Approx. no. of pilots employed by BA is 3200. No. is too high for initial capacity of the HashSet though.

    private final HashSet<Flight> flights= new HashSet<>(50);

    private final HashSet<Plane> planes= new HashSet<>(20);     //No. of planes operated by BA is 261. No. is too high for initial capacity of the HashSet though.

    public Airline(String IATACode, String name){
        this.IATACode = IATACode;
        this.name = name;
        System.out.println(this + " created");
    }

    public String getIATACode(){
        return IATACode;
    }

    public void setIATACode(String IATACode){
        this.IATACode = IATACode;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "Airline " + name + " (" + IATACode + ")";
    }

    public void hire(Pilot pilot){
        System.out.println(pilot + " is hired");
    }

    public void fire(Pilot pilot){
        System.out.println(pilot + " is fired");
    }

    public boolean addPilot(Pilot pilot){
        return this.pilots.add(pilot);
    }

    public boolean removePilot(Pilot pilot){
        return this.pilots.remove(pilot);
    }

    public boolean hasPilot(Pilot pilot){
        return this.pilots.contains(pilot);
    }

    public void clearPilots(){
        this.pilots.clear();
    }

    public boolean addFlight(Flight flight){
        return this.flights.add(flight);
    }

    public boolean removeFlight(Flight flight){
        return this.flights.remove(flight);
    }

    public boolean hasFlight(Flight flight){
        return this.flights.contains(flight);
    }

    public void clearFlights(){
        this.flights.clear();
    }

    public boolean addPlane(Plane plane){
        return this.planes.add(plane);
    }

    public boolean removePlane(Plane plane){
        return this.planes.remove(plane);
    }

    public boolean hasPlane(Plane plane){
        return this.planes.contains(plane);
    }

    public void clearPlanes(){
        this.planes.clear();
    }
}
