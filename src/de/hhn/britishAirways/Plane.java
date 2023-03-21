package de.hhn.britishAirways;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yagmur Simsek
 * @version 0.1
 */
public class Plane {

    public String model;
    public int serialNum;
    public int hoursFlown;
    public int tailNr;

    public Plane(String model, int serialNum, int hoursFlown, int tailNr) {

        this.model = model;
        this.serialNum = serialNum;
        this.hoursFlown = hoursFlown;
        this.tailNr = tailNr;
        System.out.println("Plane created: " + this);

    }

    public String getModel() {
        return model;

    }
    private final Set<Flight> flights = new HashSet<>();
    public Set<Flight> getFlights(){
        return flights;
    }

    public void setModel(String model) {
        this.model = model;

    }
    public void addFlight(Flight a) {
        flights.add(a);

    }
    public void remove(Flight flight) {
        flights.remove(flight);
    }

    public int getSerialNum() {
        return serialNum;

    }

    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public int getHoursFlown(int hoursFlown) {
        return hoursFlown;
    }

    public void setHoursFlown(int hoursFlown) {
        this.hoursFlown = hoursFlown;
    }

    public int getTailNr() {
        return tailNr;
    }

    public void setTailNr(int tailNr) {
        this.tailNr = tailNr;
    }

    public void refuel() {
        System.out.println(this + "is refueling");
    }

     public void reserve() {
        System.out.println(this + "is reserving");
    }

    public void deice() {
        System.out.println(this + "is deicing");
    }

    public String toString() {
        return "Plane" + "Model: " +  model + " SerialNum: " + serialNum + " Hoursflown: " +  hoursFlown + " TailNum: " + tailNr;
    }
}


