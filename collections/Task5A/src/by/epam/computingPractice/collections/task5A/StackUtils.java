package by.epam.computingPractice.collections.task5A;

import java.util.ArrayList;
import java.util.Stack;

public class StackUtils {

    private StackUtils() {}

    public static <E> void exchange(Stack<E> first, Stack<E> second) {
        ArrayList<E> temp = new ArrayList<>(first);
        first.clear();
        first.addAll(second);
        second.clear();
        second.addAll(temp);
    }

    /**
     * Clears all the elements of the specified stack and fills it with
     * amount random integers in the interval [min, max]
     * @param stack an empty stack
     * @param amount number of elements to fill
     * @param min min value of integer
     * @param max max value of integer
     * @throws IllegalArgumentException if {@code min} > {@code max} || {@code amount} < 0
     */
    public static void fillWithRandomIntegers(Stack<Integer> stack, int amount, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min > max");
        }
        else if (amount < 0) {
            throw new IllegalArgumentException("amount < 0");
        }
        stack.clear();
        for (int i = 0; i < amount; i++) {
            stack.add((int) (Math.random() * (max - min + 1)) + min);
        }
    }
}
