import java.util.List;

/*
    5. На базе коллекций реализовать структуру хранения чисел с поддержкой
    следующих операций:
    • добавление/удаление числа;
    • поиск числа, наиболее близкого к заданному (т.е. модуль разницы минимален).
 */

public class Main {
    public static void main(String[] args) {
        LinkedListWrapper<Integer> list = new LinkedListWrapper<>(List.of(69, 87, 96, 35, 14));
        list.add(-94);
        list.add(9);
        System.out.println(list);
        list.remove(Integer.valueOf(69));
        System.out.println(list);
        System.out.println(list.findNearest(78));
    }
}
