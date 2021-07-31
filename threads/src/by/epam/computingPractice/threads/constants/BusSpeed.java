package by.epam.computingPractice.threads.constants;

public enum BusSpeed {
    LOW(1500),
    MEDIUM(1000),
    HIGH(500);

    int value;
    BusSpeed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
