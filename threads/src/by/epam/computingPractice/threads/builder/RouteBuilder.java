package by.epam.computingPractice.threads.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import by.epam.computingPractice.threads.entity.*;

public class RouteBuilder {
    private ArrayList<BusStop> busStops;
    private int                routeNumber;

    public RouteBuilder(int routeNumber) {
        this.routeNumber = routeNumber;
        busStops = new ArrayList<>();

    }

    public RouteBuilder(int routeNumber, BusStop...busStops) {
        this.routeNumber = routeNumber;
        this.busStops = new ArrayList<>(Arrays.asList(busStops));
    }

    public RouteBuilder(int routeNumber, Collection<BusStop> busStops) {
        this.routeNumber = routeNumber;
        this.busStops = new ArrayList<>(busStops);
    }

    public RouteBuilder append(BusStop busStop) {
        busStops.add(busStop);
        return this;
    }

    public RouteBuilder append(String busStopName, int capacity) {
        busStops.add(new BusStop(capacity, busStopName));
        return this;
    }

    public RouteBuilder insert(int index, BusStop busStop) {
        busStops.add(index, busStop);
        return this;
    }

    public RouteBuilder insert(int index, String busStopName, int capacity) {
        busStops.add(index, new BusStop(capacity, busStopName));
        return this;
    }

    public Route getRoute() {
        if (busStops.size() <= 1) {
            throw new IllegalArgumentException("Route length cannot be less than 2");
        }
        return new Route(routeNumber, Collections.unmodifiableList(busStops));
    }

    public boolean removeStop(BusStop busStop) {
        return busStops.remove(busStop);
    }

    public void removeAllStops() {
        busStops.clear();
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

}
