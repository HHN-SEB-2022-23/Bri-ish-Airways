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
    private Optional<Plane> plane = Optional.empty();
    private final Set<Airport> origins = new HashSet<>(1);
    private final Set<Airport> destinations = new HashSet<>(1);
    private final Set<BoardingPass> boardingPasses = new HashSet<>();
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
        this.departure = Optional.ofNullable(departure);
    }

    public void setDeparture(Optional<LocalDateTime> departure) {
        this.departure = departure;
    }

    public Optional<LocalDateTime> getArrival() {
        return this.arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = Optional.ofNullable(arrival);
    }

    public void setArrival(Optional<LocalDateTime> arrival) {
        this.arrival = arrival;
    }

    public Optional<Plane> getPlane() {
        return this.plane;
    }

    public void setPlane(Plane plane) {
        this.plane = Optional.ofNullable(plane);
    }

    public void setPlane(Optional<Plane> vehicle) {
        this.plane = vehicle;
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
        return this.boardingPasses
            .stream()
            .map(BoardingPass::getPassenger)
            .collect(Collectors.toUnmodifiableSet());
    }

    public BoardingPass addPassenger(Seat seat, Passenger passenger) throws IllegalStateException {
        for (var bp : this.boardingPasses) {
            if (bp.getSeat().equals(seat)) {
                throw new IllegalStateException("Seat is already occupied");
            }

            if (bp.getPassenger().equals(passenger)) {
                throw new IllegalStateException("Passenger is already on board");
            }
        }

        var bp = new BoardingPass(passenger, this, seat);
        this.boardingPasses.add(bp);
        return bp;
    }

    public BoardingPass addBoardingPass(BoardingPass bp) {
        this.boardingPasses.add(bp);
        return bp;
    }

    public boolean removePassenger(Passenger passenger) {
        return this.boardingPasses.removeIf(bp -> bp.getPassenger().equals(passenger));
    }

    /**
     * @return true if the passenger is present, false otherwise
     */
    public boolean hasPassenger(Passenger passenger) {
        for (var bp : this.boardingPasses) {
            if (bp.getPassenger().equals(passenger)) {
                return true;
            }
        }

        return false;
    }

    public void clearPassengers() {
        this.boardingPasses.clear();
    }

    /**
     * Check if this Flight instance has enough information to be scheduled.
     */
    private boolean isReady() {
        return this.pilots.size() >= 2 &&
            this.plane.isPresent() &&
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
        if (this.getState() != State.SCHEDULED) {
            throw new Error("Flight is not scheduled");
        }

        if (!this.isReady()) {
            throw new Error("Flight has not enough information to be scheduled");
        }

        this.state = State.IN_FLIGHT;

        System.out.printf("Flight %s is taking off%n", this.flightNumber);
    }

    public void land() {
        if (this.getState() != State.IN_FLIGHT) {
            throw new Error("Flight is not in flight");
        }

        this.state = State.LANDED;

        System.out.printf("Flight %s has landed%n", this.flightNumber);
    }

    /**
     * ["a", "b", "c"] -> "a, b and c"
     * @return a formatted string of a list of strings
     */
    private static String format(Collection<?> list) {
        var text = new StringBuilder();
        var last = Optional.empty();
        for (var item : list) {
            last.ifPresent(x -> {
                if ( ! text.isEmpty()) {
                    text.append(", ");
                }
                text.append(x);
            });

            last = Optional.of(item);
        }

        last.ifPresent(x -> {
            if ( ! text.isEmpty()) {
                text.append(" and ");
            }
            text.append(x);
        });

        return text.toString();
    }

    public void show() {
        if (this.departure.isPresent()) {
            System.out.printf("Flight %s on %s%n", this.flightNumber, this.departure.get().toLocalDate());
        }
        else {
            System.out.printf("Flight %s%n", this.flightNumber);
        }

        System.out.printf("Offered by %s (%s)%n", this.provider.getName(), this.provider.getIATACode());

        if ( ! this.pilots.isEmpty()) {
            System.out.printf(
                "Flown by %s%n",
                Flight.format(this.pilots)
            );
        }

        try {
            var origin = this.origins.iterator().next();
            var time = this.departure
                .map(x -> x.toLocalDate() + " " + x.toLocalTime())
                .orElse("unknown time");

            try {
                var city = origin.getCities().iterator().next();
                System.out.printf(
                    "departing %s (%s) near %s at %s%n",
                    origin.getName(),
                    origin.getIATACode(),
                    city,
                    time
                );

            }
            catch (NoSuchElementException ignore) {
                System.out.printf(
                    "departing %s (%s) at %s%n",
                    origin.getName(),
                    origin.getIATACode(),
                    time
                );
            }
        }
        catch (NoSuchElementException ignore) {
            System.out.println("departing unknown");
        }

        try {
            var destination = this.destinations.iterator().next();
            var time = this.arrival
                .map(x -> x.toLocalDate() + " " + x.toLocalTime())
                .orElse("unknown time");

            try {
                var city = destination.getCities().iterator().next();
                System.out.printf(
                    "arriving %s (%s) near %s at %s%n",
                    destination.getName(),
                    destination.getIATACode(),
                    city,
                    time
                );
            }
            catch (NoSuchElementException ignore) {
                System.out.printf(
                    "arriving %s (%s) at %s%n",
                    destination.getName(),
                    destination.getIATACode(),
                    time
                );
            }
        }
        catch (NoSuchElementException ignore) {
            System.out.println("arriving unknown");
        }

        this.plane.ifPresent(plane -> System.out.printf(
            "using %s (%s)%n",
            plane.getTailNr(),
            plane.getModel()
        ));

        if ( ! this.boardingPasses.isEmpty()) {
            System.out.printf(
                "carrying passengers %s%n",
                Flight.format(this.boardingPasses.stream()
                    .map(x -> x.getPassenger().getName())
                    .collect(Collectors.toList())
                )
            );
        }
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
                o instanceof Flight otherFlight
                && Objects.equals(this.flightNumber, otherFlight.flightNumber)
                && Objects.equals(this.pilots, otherFlight.pilots)
                && Objects.equals(this.plane, otherFlight.plane)
                && Objects.equals(this.departure, otherFlight.departure)
                && Objects.equals(this.arrival, otherFlight.arrival)
                && Objects.equals(this.origins, otherFlight.origins)
                && Objects.equals(this.destinations, otherFlight.destinations)
                && Objects.equals(this.boardingPasses, otherFlight.boardingPasses)
                && this.getState() == otherFlight.getState()
            );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.flightNumber,
            this.pilots,
            this.plane,
            this.departure,
            this.arrival,
            this.origins,
            this.destinations,
            this.boardingPasses
        );
    }

    public enum State {
        NOT_READY, SCHEDULED, CANCELLED, DELAYED, IN_FLIGHT, LANDED
    }
}
