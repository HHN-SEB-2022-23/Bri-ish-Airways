package de.hhn.britishAirways;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jannik Danecker
 */
public class Passenger {
    private final String name;
    private final Set<BoardingPass> bookings = new HashSet<>();


    public Passenger(String name) {
        this.name = name;
    }

    public BoardingPass[] getBookings() {
        return bookings.toArray(new BoardingPass[0]);
    }

    public String getName() {
        return name;
    }


    public void addBooking(BoardingPass pass){
        bookings.add(pass);
    }

    @Override
    public String toString() {
        return "Passenger " + name;
    }
}
