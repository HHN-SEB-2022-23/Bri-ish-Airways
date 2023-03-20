package de.hhn.britishAirways;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Airport str = new Airport("STR", "Stuttgart", null);
        Airport lhr = new Airport("LHR", "London Heathrow", null);
        str.deice();
        Airline ba = new Airline("BA", "British Airways");
        Flight strToLhr = new Flight(ba, "BA123");
        strToLhr.setDeparture(LocalDateTime.of(2019, 1, 1, 10, 0));
        strToLhr.setArrival(LocalDateTime.of(2019, 1, 1, 12, 0));
    }
}
