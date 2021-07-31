package by.epam.computingPractice.threads.entity;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BusStop {

    private int                     capacity;
    private String                  name;
    private ArrayBlockingQueue<Bus> transportQueue;

    public BusStop(int capacity, String name) {
        this.capacity = capacity;
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        this.name = name;
        transportQueue = new ArrayBlockingQueue<>(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void acceptBus(Bus bus) {
        try {
            while (!transportQueue.offer(bus,1000, TimeUnit.MILLISECONDS)) {
                System.out.printf("Попытка занять остановку %s. Номер маршрута %d. Автобус -> %d\n",
                        name, bus.getRouteNumber(), bus.getBusNumber());
            }
            System.out.printf("Остановка %s. Посадка/высадка пассажиров. Номер маршрута %d. Автобус -> %d\n",
                    name, bus.getRouteNumber(), bus.getBusNumber());
            Thread.sleep(1000);
            releaseBus();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseBus() {
        Bus bus = transportQueue.poll();
        System.out.printf("Остановка %s. Посадка/высадка завершена. Номер маршрута %d. Автобус -> %d\n",
                name,  bus.getRouteNumber(), bus.getBusNumber());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusStop{");
        sb.append("capacity=").append(capacity);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}