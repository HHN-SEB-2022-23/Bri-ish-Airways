package de.hhn.britishAirways;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

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
    private final Set<Pilot> pilots = new HashSet<>(4);
    private Optional<LocalDateTime> departure = Optional.empty();
    private Optional<LocalDateTime> arrival = Optional.empty();
    private Optional<Plane> vehicle = Optional.empty();
    private final Set<Airport> origins = new HashSet<>(1);
    private final Set<Airport> destinations = new HashSet<>(1);
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

    public void addPilot(Pilot pilot) {
        this.pilots.add(pilot);
    }

    public void removePilot(Pilot pilot) {
        this.pilots.remove(pilot);
    }

    public void hasPilot(Pilot pilot) {
        this.pilots.contains(pilot);
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
        if (departure == null) {
            throw new IllegalArgumentException("Departure time must not be null");
        }

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

    public Result<State, Error> setState(State newState) {
        if (
            ( newState == State.SCHEDULED ||
              newState == State.DELAYED ||
              newState == State.IN_FLIGHT ||
              newState == State.LANDED
            ) && !this.isReady()
        ) {
            return Result.err(new Error("Flight is not ready"));
        }

        this.state = newState;
        return Result.ok(newState);
    }

    public void refuel() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void reserve() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Result<Flight, Error> takeoff() {
        if (this.state != State.SCHEDULED) {
            return Result.err(new Error("Flight is not scheduled"));
        }

        if (!this.isReady()) {
            return Result.err(new Error("Flight has not enough information to be scheduled"));
        }

        // unknown takeoff logic...

        this.state = State.IN_FLIGHT;

        return Result.ok(this);
    }

    public Result<Flight, Error> land() {
        if (this.state != State.IN_FLIGHT) {
            return Result.err(new Error("Flight is not in flight"));
        }

        // unknown landing logic...

        this.state = State.LANDED;

        return Result.ok(this);
    }

    public enum State {
        NOT_READY, SCHEDULED, CANCELLED, DELAYED, IN_FLIGHT, LANDED
    }
}
