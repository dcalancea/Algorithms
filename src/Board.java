import java.util.Stack;

public class Board {
    private int[][] blocks;
    private int zeroPosI;
    private int zeroPosJ;
    private int manhattan = -1;
    private int hamming = -1;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        int n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroPosI = i;
                    zeroPosJ = j;
                }
            }
        }
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return blocks.length;
    }

    public int hamming()                   // number of blocks out of place
    {
        if(hamming == -1){
            hamming = computeHamming();
        }
        return hamming;
    }

    private int computeHamming(){
        int count = 0;
        int expectedValue = 1;
        int size = blocks.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != expectedValue) {
                    count++;
                }
                expectedValue++;
            }

        }
        return count;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        if(manhattan == -1){
            manhattan = computeManhattan();
        }

        return manhattan;
    }

    private int computeManhattan(){
        int count = 0;
        int size = blocks.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * size) + j + 1) {
                    int deltaI = java.lang.Math.abs(((blocks[i][j] - 1) / size) - i);
                    int deltaJ = java.lang.Math.abs(((blocks[i][j] - 1) % size) - j);

                    count += deltaI + deltaJ;
                }

            }
        }
        return count;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int n = blocks.length;
        int expectedIndex = 1;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(blocks[i][j] != 0 && blocks[i][j] != expectedIndex){
                    return false;
                }
                expectedIndex++;
            }
        }
        return true;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] blocksCopy = copyArray(blocks);
        mutateArray(blocksCopy);
        return new Board(blocksCopy);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (!(y instanceof Board)) {
            return false;
        }
        int[][] targetBlocks = ((Board) y).blocks;
        int n = targetBlocks.length;

        if (n != blocks.length) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (targetBlocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> boards = new Stack<>();
        if (zeroPosI != 0) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = neighbourBlock[zeroPosI - 1][zeroPosJ];
            neighbourBlock[zeroPosI - 1][zeroPosJ] = 0;
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosI != blocks.length - 1) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = neighbourBlock[zeroPosI + 1][zeroPosJ];
            neighbourBlock[zeroPosI + 1][zeroPosJ] = 0;
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosJ != 0) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = neighbourBlock[zeroPosI][zeroPosJ - 1];
            neighbourBlock[zeroPosI][zeroPosJ - 1] = 0;
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosJ != blocks.length - 1) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = neighbourBlock[zeroPosI][zeroPosJ + 1];
            neighbourBlock[zeroPosI][zeroPosJ + 1] = 0;
            boards.add(new Board(neighbourBlock));
        }

        return boards;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n", blocks.length));

        int n = blocks.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%3s", blocks[i][j]));
                if (j < n - 1) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
    }

    private int[][] copyArray(int[][] source) {
        int n = source.length;
        int[][] result = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = source[i][j];
            }
        }
        return result;
    }

    private void mutateArray(int[][] source) {
        int n = source.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (source[i][j] != 0 && source[i][j+1] != 0) {
                    int temp = source[i][j];
                    source[i][j] = source[i][j + 1];
                    source[i][j + 1] = temp;
                    return;
                }
            }
        }
    }
}
