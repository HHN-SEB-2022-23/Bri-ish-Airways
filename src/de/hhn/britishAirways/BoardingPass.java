package de.hhn.britishAirways;

import java.util.Objects;

public class BoardingPass {
    private Passenger passenger;
    private Flight flight;
    private Seat seat;

    public BoardingPass(Passenger passenger, Flight flight, Seat seat) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Boarding Pass " + flight + " " + seat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seat, passenger);
    }
}
