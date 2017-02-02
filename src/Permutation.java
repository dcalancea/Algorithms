import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        for (String s:StdIn.readString().split(" ")) {
            queue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            String element = queue.dequeue();
            System.out.println(element);
        }
    }
}