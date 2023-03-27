package de.hhn.britishAirways;

import java.time.LocalDateTime;

public class BritishAirwaysBuildUp {
    Airline theAirline;
    Pilot captain;
    Pilot coPilot;
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
        britishAirwaysBuildUp.buildPilot();
        britishAirwaysBuildUp.buildAirline();
        britishAirwaysBuildUp.buildFlight();
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
        this.theAirline.hire(captain);
        this.theAirline.hire(coPilot);
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
        this.frankfurt = new City("Frankfurt");
        this.newYork = new City("New York");

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

        outFlight.addPilot(captain);
        outFlight.addPilot(coPilot);
        outFlight.setPlane(plane);
        outFlight.addOrigin(frankfurtAirport);
        outFlight.addDestination(newYorkAirport);
        outFlight.setDeparture(LocalDateTime.of(2023, 3, 27, 18, 0));
        outFlight.setArrival(LocalDateTime.of(2023, 3, 27, 20, 0));

        inFlight.addPilot(captain);
        inFlight.addPilot(coPilot);
        inFlight.setPlane(plane);
        inFlight.addOrigin(newYorkAirport);
        inFlight.addDestination(frankfurtAirport);
        inFlight.setDeparture(LocalDateTime.of(2023, 3, 28, 20, 0));
        inFlight.setArrival(LocalDateTime.of(2023, 3, 28, 22, 0));
    }

    /**
     * @author Nico Vogel
     */
    private void buildPilot() {
        captain = new Pilot("Hans Maier");
        coPilot = new Pilot("Kevin Müller");
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
        Seat seat1 = new Seat("34B", this.plane);
        Seat seat2 = new Seat("34C", this.plane);

        BoardingPass boardingPass1 = new BoardingPass(passenger1, this.inFlight, seat1);
        BoardingPass boardingPass2 = new BoardingPass(passenger2, this.inFlight, seat2);
        BoardingPass boardingPass3 = new BoardingPass(passenger1, this.outFlight, seat1);

        this.inFlight.addBoardingPass(boardingPass1);
        this.inFlight.addBoardingPass(boardingPass2);
        this.outFlight.addBoardingPass(boardingPass3);
    }
}
