package de.hhn.britishAirways;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        var str = new Airport("STR", "Stuttgart");
        var lhr = new Airport("LHR", "London Heathrow");
        var ba = new Airline("BA", "British Airways"); // <- bitte konstruktor korrigieren (falls nötig), ist nur geraten
        var strToLhr = new Flight(ba, "BA123");
        strToLhr.setDeparture(LocalDateTime.of(2019, 1, 1, 10, 0));
    }
}
