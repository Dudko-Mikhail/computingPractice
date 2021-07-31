package by.epam.computingPractice.collections.task5A;

import java.util.Stack;

/**
 * 5. Задать два стека, поменять информацию местами.
 */

public class Main {
    public static void main(String[] args) {
        Stack<Integer> first = new Stack<>();
        Stack<Integer> second = new Stack<>();
        StackUtils.fillWithRandomIntegers(first, 10, -30, -20);
        StackUtils.fillWithRandomIntegers(second, 5, 100, 200);
        System.out.println("first " + first);
        System.out.println("second " + second);
        StackUtils.exchange(first, second);
        System.out.println("first " + first);
        System.out.println("second " + second);
    }
}
