package de.hhn.britishAirways;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Henri Staudenrausch
 * @version 1, 20.03.2023
 */

public class Airport {
    private final Set<City> cities = new HashSet<>(3);
    private final Set<Flight> arrivingFlights = new HashSet<>();
    private final Set<Flight> departingFlights = new HashSet<>();
    private String IATACode;
    private String name;

    public Airport(String IATACode, String name, City[] cities) {
        this.IATACode = IATACode;
        this.name = name;
        if (cities != null) {
            if(cities.length > 3){
                throw new IllegalStateException("Airport can only have 3 cities or less");
            }
            for (City c : cities) {
                addCity(c);
            }
        }
        System.out.println("Airport created: " + this);
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCity(City city) {
        if(cities.size() >= 3){
            throw new IllegalStateException("Airport can only have 3 cities or less");
        }
        cities.add(city);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }

    public void addDepartingFlight(Flight flight) {
        departingFlights.add(flight);
    }

    public void removeDepartingFlight(Flight flight) {
        departingFlights.remove(flight);
    }

    public void addArrivingFlight(Flight flight) {
        arrivingFlights.add(flight);
    }

    public void removeArrivingFlight(Flight flight) {
         arrivingFlights.remove(flight);
    }

    public Set<City> getCities() {
        return cities;
    }

    public Set<Flight> getDepartingFlights() {
        return departingFlights;
    }

    public Set<Flight> getArrivingFlights() {
        return arrivingFlights;
    }

    @Override
    public String toString() {
        return "Airport " + name + "(" + IATACode + ")";
    }

    public void deice() {
        System.out.println(this + " is deicing");
    }
}
