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
        BritishAirwaysBuildUp britishAirwaysBuildUp = new BritishAirwaysBuildUp();
        britishAirwaysBuildUp.buildCity();
        britishAirwaysBuildUp.buildAirport();
        britishAirwaysBuildUp.buildFlight();
        britishAirwaysBuildUp.buildAirline();
        britishAirwaysBuildUp.buildPilot();
        britishAirwaysBuildUp.buildPlane();
        britishAirwaysBuildUp.buildBoardingPass();
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

    /**
     * @author Frank Mayer
     */
    private void buildFlight() {
        this.inFlight = new Flight(this.theAirline, "ABC123");
        this.frankfurtAirport.addArrivingFlight(this.inFlight);

        this.outFlight = new Flight(this.theAirline, "ABC345");
        this.newYorkAirport.addDepartingFlight(this.outFlight);
    }

    private void buildPilot() {

    }

    private void buildPlane() {

    }

    private void buildBoardingPass() {

    }
}
