import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args)   // test client (optional)
    {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();

        System.out.println("Please enter: " + count + " numbers: ");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {

            for (int i = 0; i < count; i++) {
                randomQueue.enqueue(buffer.readLine());
            }
        } catch (Exception e) {

        }

        for (String i : randomQueue) {
            System.out.print(i + " ");
        }


//        PercolationStats ps = new PercolationStats(1000, 100);
//        System.out.println("mean: "+ ps.mean());
//        System.out.println("stddev: " + ps.stddev());
//        System.out.println("95% confidence interval: " + ps.confidenceLo() + " " + ps.confidenceHi());
    }
}