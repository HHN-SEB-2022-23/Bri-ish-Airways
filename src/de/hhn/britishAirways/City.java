package de.hhn.britishAirways;

/**
 * @author Felix Marzioch
 */

import java.util.HashSet;

public class City {

    private final String name;
    private final HashSet<Airport> airports = new HashSet<>();

    public City(String name) {
        this.name = name;
        System.out.println(this + " created");
    }

    public String getName() {
        return name;
    }

    public void addAirport (Airport airport){
        airports.add(airport);

    }
    public Airport[] getAirports() {
        return airports.toArray(Airport[]::new);
    }

    @Override
    public String toString() {
        return "City " + name;
    }
}
