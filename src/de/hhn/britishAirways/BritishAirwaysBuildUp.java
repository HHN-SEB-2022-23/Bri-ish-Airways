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
        this.newYorkAirport.addArrivingFlight(this.inFlight);
        this.frankfurtAirport.addDepartingFlight(this.inFlight);

        this.outFlight = new Flight(this.theAirline, "ABC345");
        this.frankfurtAirport.addArrivingFlight(this.outFlight);
        this.newYorkAirport.addDepartingFlight(this.outFlight);
    }

    private void buildPilot() {

    }

    private void buildPlane() {

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
