package by.epam.computingPractice.threads.entity;

import by.epam.computingPractice.threads.constants.RouteDirection;
import java.util.Collections;
import java.util.List;

public record Route(int routeNumber, List<BusStop> route) {
    public Route(int routeNumber, List<BusStop> route) {
        this.routeNumber = routeNumber;
        this.route = Collections.unmodifiableList(route);
        if (route.size() <= 1) {
            throw new IllegalArgumentException("Route length cannot be less than 2");
        }
    }

    public int getRouteLength() {
        return route.size();
    }

    public BusStop getBusStop(int index) {
        return route.get(index);
    }

    public boolean hasNextBusStop(RouteDirection direction, BusStop currentBusStop) {
        return (direction == RouteDirection.DIRECT_ROUTE && route.indexOf(currentBusStop) != route.size() - 1)
                || (direction == RouteDirection.RETURN_ROUTE && route.indexOf(currentBusStop) != 0);
    }

    public BusStop getNextBusStop(RouteDirection direction, BusStop currentBusStop) {
        if (hasNextBusStop(direction, currentBusStop)) {
            return (direction == RouteDirection.DIRECT_ROUTE)
                    ? route.get(route.indexOf(currentBusStop) + 1)
                    : route.get(route.indexOf(currentBusStop) - 1);
        } else {
            return (direction == RouteDirection.DIRECT_ROUTE)
                    ? route.get(route.size() - 2)
                    : route.get(1);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("routeNumber=").append(routeNumber);
        sb.append(", route=").append(route);
        sb.append('}');
        return sb.toString();
    }
}
