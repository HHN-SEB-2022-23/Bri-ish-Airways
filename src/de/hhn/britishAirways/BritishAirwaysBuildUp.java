package de.hhn.britishAirways;

import java.util.Set;

public class BritishAirwaysBuildUp {
    Airline theAirline;
    Set<Pilot> thePilots;
    Flight outFlight;
    Flight inFlight;
    City frankfurt;
    City newYork;
    Plane plane;
    Airport frankfurtAirport;
    Airport newYorkAirport;

    public static void main(String[] args) {
        this.buildCity();
        this.buildAirport();
        this.buildFlight();
        this.buildAirline();
        this.buildPilot();
        this.buildPlane();
        this.buildBoardingPass();
    }

    private void buildAirline() {

    }


    /**
     * @author Henri Staudenrausch
     */
    private void buildAirport() {
        this.frankfurtAirport = new Airport("FRA", "Frankfurt Airport", new City[]{this.frankfurt});
        this.newYorkAirport = new Airport("JFK", "John F. Kennedy International Airport", new City[]{this.newYork});
    }

    private void buildCity() {

    }

    private void buildFlight() {

    }

    private void buildPilot() {

    }

    private void buildPlane() {

    }

    private void buildBoardingPass() {

    }
}
