import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] queue;
    private int initialSize = 2;
    private int head = 1;
    private int tail = 0;

    public Deque()                           // construct an empty deque
    {
        queue = (Item[]) new Object[initialSize];
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return size() == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return tail - head + 1;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (head == 0) {
            queue = doubleArray(queue, head, tail);
        }

        queue[--head] = item;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (tail == queue.length - 1) {
            queue = doubleArray(queue, head, tail);
        }

        queue[++tail] = item;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item removed = queue[head];
        head++;

        if (size() > 1 && queue.length / size() > 4) {
            queue = halfArray(queue, head, tail);
        }

        return removed;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item removed = queue[tail];
        tail--;

        if (size() > 0 && queue.length / size() > 4) {
            queue = halfArray(queue, head, tail);
        }

        return removed;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    public static void main(String[] args)   // unit testing (optional)
    {

    }

    private Item[] doubleArray(Item[] array, int start, int end) {
        Item[] newArray = (Item[]) new Object[array.length * 2];

        int copyStart = (newArray.length - (end - start)) / 2;
        int copyOffset = copyStart - start;
        for (int i = copyStart; i <= end + copyOffset; i++) {
            newArray[i] = array[i - copyOffset];
        }

        head = copyStart;
        tail = end + copyOffset;
        return newArray;
    }

    private Item[] halfArray(Item[] array, int start, int end) {
        if (end - start > array.length / 2) {
            return array;
        }

        int newLength = array.length % 2 == 0 ? array.length / 2 : array.length / 2 + 1;
        Item[] newArray = (Item[]) new Object[newLength];

        int copyStart = (newArray.length - (end - start)) / 2;
        int copyOffset = copyStart - start;
        for (int i = copyStart; i <= end + copyOffset; i++) {
            newArray[i] = array[i - copyOffset];
        }

        head = copyStart;
        tail = end + copyOffset;
        return newArray;
    }

    private class DequeIterator implements Iterator<Item> {
        private int current = head-1;
        private int localTail = tail;

        public boolean hasNext() {
            return current != localTail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return queue[++current];
        }
    }

//    private Item[] resetArray(Item[] array, int start, int end){
//        for(int i=0; i<= end-start; i++){
//            array[i] = array[i+start];
//        }
//
//        return array;
//    }
}