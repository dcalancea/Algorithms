import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int zeroPosI;
    private int zeroPosJ;

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
        int count = 0;
        int size = blocks.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((blocks[i][j] == 0 && i == size - 1 && j == i)
                        || (blocks[i][j] == (i * size) + j + 1)) {
                    continue;
                }
                count++;
            }
        }
        return count;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int count = 0;
        int size = blocks.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((blocks[i][j] == 0 && i == size - 1 && j == i)
                        || (blocks[i][j] == (i * size) + j + 1)) {
                    continue;
                }
                int targetI, targetJ = 0;
                if (blocks[i][j] == 0) {
                    targetI = targetJ = size - 1;
                } else {
                    targetI = java.lang.Math.abs((blocks[i][j] / size) - i);
                    targetJ = java.lang.Math.abs((blocks[i][j] % size) - 1 - j);
                }

                count += targetI + targetJ;
            }
        }
        return count;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return zeroPosI == blocks.length - 1 && zeroPosJ == blocks.length - 1;
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
        ArrayList<Board> boards = new ArrayList<>();
        if (zeroPosI != 0) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = blocks[zeroPosI - 1][zeroPosJ];
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosI != blocks.length - 1) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = blocks[zeroPosI + 1][zeroPosJ];
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosJ != 0) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = blocks[zeroPosI][zeroPosJ - 1];
            boards.add(new Board(neighbourBlock));
        }

        if (zeroPosJ != blocks.length - 1) {
            int[][] neighbourBlock = copyArray(blocks);
            neighbourBlock[zeroPosI][zeroPosJ] = blocks[zeroPosI][zeroPosJ + 1];
            boards.add(new Board(neighbourBlock));
        }

        return boards;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d\n", blocks.length));

        int n = blocks.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(blocks[i][j]);
                if (j < n - 1) {
                    sb.append('\n');
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

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (i != zeroPosI && j != zeroPosJ && i + 1 != zeroPosI && j + 1 != zeroPosJ) {
                    result[i][j] = source[i][j];
                }
            }
        }
        return result;
    }

    private void mutateArray(int[][] source) {
        int n = source.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (i != zeroPosI && j != zeroPosJ && i + 1 != zeroPosI && j + 1 != zeroPosJ) {
                    int temp = source[i][j];
                    source[i][j] = source[i][j + 1];
                    source[i][j + 1] = temp;
                    return;
                }
            }
        }
    }
}
