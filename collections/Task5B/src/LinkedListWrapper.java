import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWrapper<E extends Number> {
    private LinkedList<E> data;

    LinkedListWrapper() {
        data = new LinkedList<>();
    }

    LinkedListWrapper(Collection<? extends E> c) {
        data = new LinkedList(c);
    }

    public boolean add(E element) {
        return data.add(element);
    }

    public E remove(int index) {
        return data.remove(index);
    }

    public boolean remove(Object o) {
        return data.remove(o);
    }

    public void clear() {
        data.clear();
    }

    public E findNearest(E element) {
        if (data.size() == 0) {
            throw new NoSuchElementException("empty list");
        }
        int index = 0;
        ListIterator<E> it = data.listIterator();
        if (element instanceof Double || element instanceof Float) {
            double minDifference = data.get(0).doubleValue();
            while (it.hasNext()) {
                double currentDifference = findDifference(element.doubleValue(), it.next().doubleValue());
                if (currentDifference < minDifference) {
                    minDifference = currentDifference;
                    index = it.nextIndex() - 1;
                }
            }
        }
        else if (element instanceof Byte || element instanceof Short
                || element instanceof  Integer || element instanceof Long) {
            long minDifference = data.get(0).longValue();
            while (it.hasNext()) {
                long currentDifference = findDifference(element.longValue(), it.next().longValue());
                if (currentDifference < minDifference) {
                    minDifference = currentDifference;
                    index = it.nextIndex() - 1;
                }
            }
        }
        else {
            throw new UnsupportedOperationException("Unsupported Number inheritor");
        }
        return data.get(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinkedListWrapper{");
        sb.append("data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    private double findDifference(double a, double b) {
        return Math.abs(Math.abs(a) - Math.abs(b));
    }

    private long findDifference(long a, long b) {
        return Math.abs(Math.abs(a) - Math.abs(b));
    }

}
