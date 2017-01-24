import java.util.Iterator;

import static edu.princeton.cs.algs4.StdRandom.uniform;

public class RandomizedQueue<Item> implements Iterable<Item> {
    Item[] queue;
    private int initialSize = 10;
    private int tail = -1;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        queue = (Item[])new Object[initialSize];
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return size() < 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return tail + 1;
    }

    public void enqueue(Item item)           // add the item
    {
        if(item == null){
            throw new java.lang.NullPointerException();
        }

        if(tail+1 == queue.length){
            queue = doubleArray(queue);
        }
        queue[tail++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }

        int removeId = uniform(tail);

        Item removed = queue[removeId];
        if(tail != removeId) {
            queue[removeId] = queue[tail];
        }

        tail--;

        if(size() == queue.length/4){
            queue = halfArray(queue);
        }

        return removed;
    }

    public Item sample()                     // return (but do not remove) a random item
    {
        return queue[uniform(tail)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomQueueIterator();
    }

    public static void main(String[] args)   // unit testing (optional)
    {

    }

    private Item[] doubleArray(Item[] array){
        Item[] newArray = (Item[])new Object[array.length*2];

        for(int i=0; i < array.length; i++){
            newArray[i] = array[i];
        }

        return newArray;
    }

    private Item[] halfArray(Item[] array){
        if(size() > array.length / 2){
            return array;
        }

        int newLength = array.length % 2 == 0 ? array.length/2 : array.length/2 + 1;
        Item[] newArray = (Item[])new Object[newLength];

        for(int i=0; i < array.length; i++){
            newArray[i] = array[i];
        }

        return newArray;
    }
    private class RandomQueueIterator implements Iterator<Item>{
        private int current = 0;

        public boolean hasNext(){ return current != tail; }
        public void remove(){}
        public Item next(){ return queue[current++];}
    }
}