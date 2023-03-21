package de.hhn.britishAirways;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jannik Danecker
 */
public class Passenger {
    private final String name;


    private final Map<Flight, Seat> bookings = new HashMap<>();


    public Passenger(String name) {
        this.name = name;
    }

    public Flight[] getBookings() {
        return bookings.keySet().toArray(new Flight[0]);
    }

    public String getName() {
        return name;
    }

    public Seat getSeat(Flight flight) {
        return bookings.get(flight);
    }

    public void addBooking(Flight flight, Seat seat){
        bookings.put(flight, seat);
    }

    public void removeBooking(Flight flight){
        bookings.remove(flight);
    }

    @Override
    public String toString() {
        return "Passenger " + name;
    }
}
