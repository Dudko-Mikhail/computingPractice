package by.epam.computingPractice.threads.creator;

import by.epam.computingPractice.threads.constants.BusSpeed;
import by.epam.computingPractice.threads.constants.RouteDirection;
import by.epam.computingPractice.threads.entity.*;

import java.util.Random;

public class RandomBusCreator {
    private Route[] routes;
    private int     routeCountLimit;
    Random          random;

    public RandomBusCreator(int routeCountLimit, Route...routes) {
        this.routes = routes;
        if (routeCountLimit < 1) {
            throw new IllegalArgumentException("routeCountLimit cannot be less than 1");
        }
        this.routeCountLimit = routeCountLimit;
        random = new Random();
    }

    public Bus getBus() {
        Route route = routes[random.nextInt(routes.length)];
        RouteDirection direction = RouteDirection.values()[random.nextInt(RouteDirection.values().length)];
        int routeCount = random.nextInt(routeCountLimit) + 1;
        BusStop currentBusStop = route.getBusStop(random.nextInt(route.getRouteLength()));
        BusSpeed speed = BusSpeed.values()[random.nextInt(BusSpeed.values().length)];
        return new Bus(route, direction, routeCount, currentBusStop, speed);
    }
}
