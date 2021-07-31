package by.epam.computingPractice.threads.entity;

import java.util.concurrent.TimeUnit;
import by.epam.computingPractice.threads.constants.*;

public class Bus extends Thread {
    private long           busNumber;
    private Route          route;
    private RouteDirection direction;
    private int            routeCount; // 1 в routeCount это движение до конечной остановки
    private BusStop        currentBusStop;
    private BusSpeed       speed;

    public Bus(Route route, RouteDirection direction, int routeCount, BusSpeed speed) {
        this.route = route;
        this.direction = direction;
        this.routeCount = routeCount;
        if (routeCount <= 0) {
            throw new IllegalArgumentException("routeCount cannot be less than 1");
        }
        this.speed = speed;
        currentBusStop = (direction == RouteDirection.DIRECT_ROUTE)
                ? route.getBusStop(0)
                : route.getBusStop(route.getRouteLength() - 1);
    }

    public Bus(Route route, RouteDirection direction, int routeCount, BusStop currentBusStop, BusSpeed speed) {
        this.route = route;
        this.direction = direction;
        this.routeCount = routeCount;
        if (routeCount <= 0) {
            throw new IllegalArgumentException("routeCount cannot be less than 0");
        }
        this.currentBusStop = currentBusStop;
        this.speed = speed;
    }

    public Bus(Route route, RouteDirection direction, int routeCount, int startBusStopIndex, BusSpeed speed) {
        this.route = route;
        this.direction = direction;
        this.routeCount = routeCount;
        if (routeCount <= 0) {
            throw new IllegalArgumentException("routeCount cannot be less than 0");
        }
        currentBusStop = route.getBusStop(startBusStopIndex);
        this.speed = speed;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    int getRouteNumber() {
        return route.routeNumber();
    }

    public RouteDirection getDirection() {
        return direction;
    }

    public void setDirection(RouteDirection direction) {
        this.direction = direction;
    }

    public BusStop getCurrentBusStop() {
        return currentBusStop;
    }

    public long getBusNumber() {
        return busNumber;
    }

    @Override
    public void run() {
        busNumber = this.getId();
        while (routeCount > 0) {
            move();
        }
        System.out.printf("Автобус %d окончил движение по маршруту %d\n", busNumber, route.routeNumber());
    }

    private void move() {
        if (route.hasNextBusStop(direction, currentBusStop)) {
            System.out.printf("Остановка %s. Номер маршрута %d. Автобус -> %d\n",
                    currentBusStop.getName(), route.routeNumber(), busNumber);
        }
        else {
            System.out.printf("Остановка %s (конечная). Номер маршрута %d. Автобус -> %d\n",
                    currentBusStop.getName(), route.routeNumber(), busNumber);
            rotate();
            routeCount--;
        }
        currentBusStop.acceptBus(this);
        if (routeCount == 0) {
            return;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(speed.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentBusStop = route.getNextBusStop(direction, currentBusStop);
    }

    private void rotate() {
        direction = (direction == RouteDirection.DIRECT_ROUTE) ? RouteDirection.RETURN_ROUTE
                : RouteDirection.DIRECT_ROUTE;
    }

}
