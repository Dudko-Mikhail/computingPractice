package by.epam.computingPractice.threads.creator;

import by.epam.computingPractice.threads.builder.RouteBuilder;
import by.epam.computingPractice.threads.entity.Route;
import java.util.Random;

public class RouteCreator {

    private static String[] firstRoute = {"Компрессорная", "Кутузова", "Тормасова", "Ленина", "Королёва"};
    private static String[] secondRoute = {"Орловская", "CШ №3", "Московская", "Советская"};
    private static RouteBuilder builder;
    private static Random random;

    static  {
        random = new Random();
    }

    private RouteCreator() {}

    public static Route getFirstRoute() {
        builder = new RouteBuilder(1);
        for (int i = 0; i < firstRoute.length; i++) {
            builder.append(firstRoute[i], random.nextInt(3) + 1);
        }
        return builder.getRoute();
    }

    public static Route getSecondRoute() {
        builder = new RouteBuilder(2);
        for (int i = 0; i < secondRoute.length; i++) {
            builder.append(secondRoute[i], random.nextInt(3) + 1);
        }
        return builder.getRoute();
    }

}
