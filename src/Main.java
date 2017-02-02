public class Main {
    public static void main(String[] args)   // test client (optional)
    {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        System.out.println("Please enter: " + count + " numbers: ");
        try {

            for (int i = 0; i < count; i++) {
                queue.enqueue(StdIn.readString());
            }
        } catch (Exception e) {

        }

        for (String i : queue) {
            for (String j : queue)
            System.out.print(j + " ");
            System.out.println();
        }

//        int size = queue.size();
//        for (int i = 0; i < size; i++) {
//            String element = queue.removeLast();
//            System.out.println(element);
//        }


//        PercolationStats ps = new PercolationStats(1000, 100);
//        System.out.println("mean: "+ ps.mean());
//        System.out.println("stddev: " + ps.stddev());
//        System.out.println("95% confidence interval: " + ps.confidenceLo() + " " + ps.confidenceHi());
    }
}