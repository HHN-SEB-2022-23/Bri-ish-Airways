package de.hhn.britishAirways;

import java.util.HashSet;

/**
 * @author Nico Vogel
 * @version 0.1
 */
public class Pilot {
    private final String name;
    private final HashSet<Airline> employer = new HashSet<>();
    private final HashSet<Flight> tasks = new HashSet<>();

    public Pilot(String name) {
        this.name = name;
        System.out.println(this + " created");
    }

    public String getName() {
        return this.name;
    }

    public Airline[] getEmployer() {
        return this.employer.toArray(new Airline[0]);
    }

    public HashSet<Flight> getTasks() {
        return this.tasks;
    }

    public void addFlight(Flight flight){
        tasks.add(flight);
    }

    public void hire(Airline airline) {
        if (this.employer.contains(airline))
            return;
        this.employer.add(airline);
        System.out.println(this + " is getting hired by " + airline + ".");
    }

    public void fire(Airline airline) {
        if (!this.employer.contains(airline))
            return;
        this.employer.remove(airline);
        System.out.println(this + " is getting fired from " + airline + ".");
    }

    @Override
    public String toString() {
        return "Pilot " + name;
    }
}
