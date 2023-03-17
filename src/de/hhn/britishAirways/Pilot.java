package de.hhn.britishAirways;

/**
 * @author Nico Vogel
 * @version 0.1
 */

public class Pilot {
    private final String name;
    private Airline employer;

    public Pilot(String name) {
        this.name = name;
        System.out.println(this + " created");
    }

    public String getName() {
        return this.name;
    }

    public Airline getEmployer() {
        return this.employer;
    }

    public void setEmployer(Airline employer) {
        this.employer = employer;
    }

    public void hire() {
        System.out.println(this + " is getting hired.");
    }

    public void fire() {
        System.out.println(this + " is getting fired.");
    }

    @Override
    public String toString() {
        return "Pilot " + name;
    }
}
