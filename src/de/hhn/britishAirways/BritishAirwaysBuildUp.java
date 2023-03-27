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

        System.out.println("---");
        britishAirwaysBuildUp.inFlight.show();
        System.out.println("---");
        britishAirwaysBuildUp.outFlight.show();
    }

    /**
     * @author René Ott
     */

    private void buildAirline() {
        this.theAirline = new Airline("BA", "British Airways");
        for (Pilot pilot : this.thePilots) {
            this.theAirline.hire(pilot);
        }
    }

    /**
     * @author Henri Staudenrausch
     */
    private void buildAirport() {
        this.frankfurtAirport = new Airport("FRA", "Frankfurt Airport", new City[]{this.frankfurt});
        this.newYorkAirport = new Airport("JFK", "John F. Kennedy International Airport", new City[]{this.newYork});
    }

    /**
     * @author Felix Marzioch
     */
    private void buildCity() {
        this.frankfurt = new City("Frankfurt, Germany");
        this.newYork = new City ("New York, USA");

        frankfurt.addAirport(frankfurtAirport);
        newYork.addAirport(newYorkAirport);

    }

    /**
     * @author Frank Mayer
     */
    private void buildFlight() {
        this.inFlight = new Flight(this.theAirline, "ABC123");
        this.newYorkAirport.addArrivingFlight(this.inFlight);
        this.frankfurtAirport.addDepartingFlight(this.inFlight);

        this.outFlight = new Flight(this.theAirline, "ABC345");
        this.frankfurtAirport.addArrivingFlight(this.outFlight);
        this.newYorkAirport.addDepartingFlight(this.outFlight);
    }

    /**
     * @author Nico Vogel
     */
    private void buildPilot() {
        var captain = new Pilot("Hans Maier");
        var coPilot = new Pilot("Kevin Müller");
        this.thePilots = Set.of(captain, coPilot);
    }

    /**
     * @author Yagmur Simsek
     */

    private void buildPlane() {
        this.plane = new Plane("Airbus", 321, 4, 930);

    }

    private void buildBoardingPass() {
        Passenger passenger1 = new Passenger("Hans Maier");
        Passenger passenger2 = new Passenger("Kevin Müller");
        Seat seat1 = new Seat("34B", plane);
        Seat seat2 = new Seat("34C", plane);

        BoardingPass boardingPass1 = new BoardingPass(passenger1, inFlight, seat1);
        BoardingPass boardingPass2 = new BoardingPass(passenger2, inFlight, seat2);
        BoardingPass boardingPass3 = new BoardingPass(passenger1, outFlight, seat1);



    }
}
