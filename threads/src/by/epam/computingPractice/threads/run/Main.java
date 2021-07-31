package by.epam.computingPractice.threads.run;

import by.epam.computingPractice.threads.creator.*;
import by.epam.computingPractice.threads.entity.*;

/*
    Автобусные остановки. На маршруте несколько остановок. На одной
    остановке может останавливаться несколько автобусов одновременно, но
    не более заданного числа.
 */

public class Main {
    public static void main(String[] args) {
        Route[] routes = {RouteCreator.getFirstRoute(), RouteCreator.getSecondRoute()};
        RandomBusCreator randomBusCreator = new RandomBusCreator(3, routes);
        for (int i = 0; i < 10; i++) {
            randomBusCreator.getBus().start();
        }
    }
}
