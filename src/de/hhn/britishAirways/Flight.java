package de.hhn.britishAirways;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A flight is a journey from one airport to another.
 * It has a flight number, a departure time and an arrival time.
 * The flight number is a string of the form "BA123".
 * The departure and arrival times are in the local time zone.
 *
 * @author Frank Mayer
 */
public class Flight {
    private static final Pattern FLIGHTNUM = Pattern.compile("^[A-Z]{2,3}[0-9]{1,4}$");
    private final String flightNumber;
    private final Airline provider;
    private final Set<Pilot> pilots = new HashSet<>(2);
    private Optional<LocalDateTime> departure = Optional.empty();
    private Optional<LocalDateTime> arrival = Optional.empty();
    private Optional<Plane> vehicle = Optional.empty();
    private final Set<Airport> origins = new HashSet<>(1);
    private final Set<Airport> destinations = new HashSet<>(1);
    private final Map<Seat, Optional<Passenger>> seats = new HashMap<>(100);
    private State state = State.NOT_READY;

    public Flight(Airline provider, String flightNumber) throws IllegalArgumentException {
        if (provider == null) {
            throw new IllegalArgumentException("Airline must not be null");
        }

        if (flightNumber == null) {
            throw new IllegalArgumentException("Flight number must not be null");
        }

        if (!Flight.FLIGHTNUM.matcher(flightNumber).matches()) {
            throw new IllegalArgumentException("Flight number must be of the form AB123");
        }

        this.provider = provider;
        this.flightNumber = flightNumber;

        System.out.printf("%s created%n", this);
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public Airline getProvider() {
        return this.provider;
    }

    /**
     * @return an unmodifiable set of the pilots
     */
    public Set<Pilot> getPilots() {
        return Collections.unmodifiableSet(this.pilots);
    }

    /**
     * @return true if the pilot is newly assigned to this flight, false if he was already assigned
     */
    public boolean addPilot(Pilot pilot) {
        return this.pilots.add(pilot);
    }

    /**
     * @return true if the pilot was assigned to this flight, false if not
     */
    public boolean removePilot(Pilot pilot) {
        return this.pilots.remove(pilot);
    }

    /**
     * @return true if the pilot is assigned to this flight, false if not
     */
    public boolean hasPilot(Pilot pilot) {
        return this.pilots.contains(pilot);
    }

    public void clearPilots() {
        this.pilots.clear();
    }

    public Optional<LocalDateTime> getDeparture() {
        return this.departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = Optional.of(departure);
    }

    public void setDeparture(Optional<LocalDateTime> departure) {
        this.departure = departure;
    }

    public Optional<LocalDateTime> getArrival() {
        return this.arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = Optional.of(arrival);
    }

    public void setArrival(Optional<LocalDateTime> arrival) {
        this.arrival = arrival;
    }

    public Optional<Plane> getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Plane vehicle) {
        this.vehicle = Optional.of(vehicle);
    }

    public void setVehicle(Optional<Plane> vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * @return an unmodifiable set of the destinations
     */
    public Set<Airport> getOrigins() {
        return Collections.unmodifiableSet(this.origins);
    }

    /**
     * @return true if the origin was added, false if it was already present
     */
    public boolean addOrigin(Airport origin) {
        return this.origins.add(origin);
    }

    /**
     * @return true if the origin was removed, false if it was not present
     */
    public boolean removeOrigin(Airport origin) {
        return this.origins.remove(origin);
    }

    /**
     * @return true if the origin is present, false otherwise
     */
    public boolean hasOrigin(Airport origin) {
        return this.origins.contains(origin);
    }

    public void clearOrigins() {
        this.origins.clear();
    }

    /**
     * @return an unmodifiable set of the destinations
     */
    public Set<Airport> getDestinations() {
        return Collections.unmodifiableSet(this.destinations);
    }

    /**
     * @return true if the destination was added, false if it was already present
     */
    public boolean addDestination(Airport destination) {
        return this.destinations.add(destination);
    }

    /**
     * @return true if the destination was removed, false if it was not present
     */
    public boolean removeDestination(Airport destination) {
        return this.destinations.remove(destination);
    }

    /**
     * @return true if the destination is present, false otherwise
     */
    public boolean hasDestination(Airport destination) {
        return this.destinations.contains(destination);
    }

    public void clearDestinations() {
        this.destinations.clear();
    }

    /**
     * @return an unmodifiable collection of the passengers
     */
    public Collection<Passenger> getPassengers() {
        return this.seats.values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toUnmodifiableSet());
    }

    public void setSeats(Set<? extends Seat> seats) {
        this.seats.keySet().removeIf(seat -> !seats.contains(seat));

        for (Seat seat : seats) {
            if (!this.seats.containsKey(seat)) {
                this.seats.put(seat, Optional.empty());
            }
        }
    }

    public boolean addPassenger(Seat seat, Passenger passenger) {
        if (!this.seats.containsKey(seat)) {
            return false;
        }

        if (this.seats.get(seat).isPresent()) {
            return false;
        }

        for (Map.Entry<Seat, Optional<Passenger>> entry : this.seats.entrySet()) {
            var seatPassenger = entry.getValue();
            if (seatPassenger.isPresent() && seatPassenger.get().equals(passenger)) {
                return false;
            }
        }

        this.seats.put(seat, Optional.ofNullable(passenger));
        return true;
    }

    public boolean removePassenger(Passenger passenger) {
        for (Map.Entry<Seat, Optional<Passenger>> entry : this.seats.entrySet()) {
            var seatPassenger = entry.getValue();
            if (seatPassenger.isPresent() && seatPassenger.get().equals(passenger)) {
                this.seats.put(entry.getKey(), Optional.empty());
                return true;
            }
        }

        return false;
    }

    /**
     * @return true if the passenger is present, false otherwise
     */
    public boolean hasPassenger(Passenger passenger) {
        return this.seats.values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .anyMatch(passenger::equals);
    }

    public void clearPassengers() {
        this.seats.replaceAll((s, v) -> Optional.empty());
    }

    /**
     * Check if this Flight instance has enough information to be scheduled.
     */
    private boolean isReady() {
        return this.pilots.size() >= 2 &&
            this.vehicle.isPresent() &&
            this.departure.isPresent() &&
            this.arrival.isPresent() &&
            ! this.origins.isEmpty() &&
            ! this.destinations.isEmpty() &&
            this.arrival.get().isAfter(this.departure.get());
    }

    public State getState() {
        // check if the flight state should be updated automatically
        if (this.state == State.NOT_READY) {
            if (this.isReady()) {
                this.state = State.SCHEDULED;
            }
        }
        else if (
            this.state == State.SCHEDULED &&
            this.departure.isPresent() &&
            this.departure.get().isBefore(LocalDateTime.now())
        ) {
                this.state = State.DELAYED;
        }

        return this.state;
    }

    public boolean setState(State newState) {
        if (
            ( newState == State.SCHEDULED ||
              newState == State.DELAYED ||
              newState == State.IN_FLIGHT ||
              newState == State.LANDED
            ) && ! this.isReady()
        ) {
            return false;
        }

        this.state = newState;
        return true;
    }

    public void refuel() {
        System.out.printf("Flight %s is refueling%n", this.flightNumber);
    }

    public void reserve() {
        System.out.printf("Flight %s is reserved%n", this.flightNumber);
    }

    public void takeoff() {
        if (this.state != State.SCHEDULED) {
            throw new Error("Flight is not scheduled");
        }

        if (!this.isReady()) {
            throw new Error("Flight has not enough information to be scheduled");
        }

        this.state = State.IN_FLIGHT;

        System.out.printf("Flight %s is taking off%n", this.flightNumber);
    }

    public void land() {
        if (this.state != State.IN_FLIGHT) {
            throw new Error("Flight is not in flight");
        }

        this.state = State.LANDED;

        System.out.printf("Flight %s has landed%n", this.flightNumber);
    }

    @Override
    public String toString() {
        return String.format(
            "%s %s",
            this.getClass().getSimpleName(),
            this.flightNumber
        );
    }

    @Override
    public boolean equals(Object o) {
        return o == this
            || (
                o instanceof Flight flight
                && Objects.equals(this.flightNumber, flight.flightNumber)
                && Objects.equals(this.pilots, flight.pilots)
                && Objects.equals(this.vehicle, flight.vehicle)
                && Objects.equals(this.departure, flight.departure)
                && Objects.equals(this.arrival, flight.arrival)
                && Objects.equals(this.origins, flight.origins)
                && Objects.equals(this.destinations, flight.destinations)
                && Objects.equals(this.seats, flight.seats)
                && this.state == flight.state
            );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.flightNumber,
            this.pilots,
            this.vehicle,
            this.departure,
            this.arrival,
            this.origins,
            this.destinations,
            this.seats,
            this.state
        );
    }

    public enum State {
        NOT_READY, SCHEDULED, CANCELLED, DELAYED, IN_FLIGHT, LANDED
    }
}
