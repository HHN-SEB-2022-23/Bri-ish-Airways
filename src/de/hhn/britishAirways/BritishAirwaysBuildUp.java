package de.hhn.britishAirways;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BritishAirwaysBuildUp {

    private void buildAirline() {

    }

    private void buildAirport() {

    }

    private void buildCity() {

    }

    private void buildFlight() {

    }

    private void buildPassenger() {

    }

    private void buildPilot() {

    }

    private void buildPlane() {

    }

    private void buildSeat() {

    }

    private void buildBoardingPass() {

    }

    public static void main(String[] args) {
        Airport str = new Airport("STR", "Stuttgart", null);
        Airport lhr = new Airport("LHR", "London Heathrow", null);
        str.deice();
        Airline ba = new Airline("BA", "British Airways");
        Flight strToLhr = new Flight(ba, "BA123");
        Plane plane = new Plane("Boeing 747", 526, 0, 123);
        Pilot fritz = new Pilot("Fritz Müller");
        ba.addPilot(fritz);
        fritz.hire(ba);
        Pilot hans = new Pilot("Hans Müller");
        ba.addPilot(hans);
        hans.hire(ba);
        Set<Seat> seats = new HashSet<>();
        seats.add(new Seat("A12"));
        seats.add(new Seat("B24"));
        seats.add(new Seat("C36"));
        strToLhr.setSeats(seats);
        strToLhr.addPilot(fritz);
        strToLhr.addPilot(hans);
        strToLhr.addOrigin(str);
        strToLhr.addDestination(lhr);
        strToLhr.setDeparture(LocalDateTime.of(2019, 1, 1, 10, 0));
        strToLhr.setArrival(LocalDateTime.of(2019, 1, 1, 12, 0));
        strToLhr.setVehicle(plane);
        strToLhr.takeoff();
        strToLhr.land();
        System.out.println("---");
        strToLhr.show();
    }
}
