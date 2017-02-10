import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private Board initialBoard;
    private Board twinBoard;
    private Board solutionBoard;
    private int iteration = 0;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) {
            throw new NullPointerException();
        }

        initialBoard = initial;
        twinBoard = initial.twin();
        solutionBoard = generateSolutionBoard(initial.dimension());
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return moves() != -1;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        Iterable<Board> boards = solution();

        if (boards == null) {
            return -1;
        }

        int count = 0;
        for (Board board : solution()) {
            count++;
        }
        return count;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        MinPQ originalQueue = new MinPQ(new SearchNodeComparator());
        MinPQ twinQueue = new MinPQ(new SearchNodeComparator());

        SearchNode originalSearchNode = new SearchNode(initialBoard, null, 0);
        SearchNode twinSearchNode = new SearchNode(twinBoard, null, 0);

        ArrayList<Board> originalSearchNodes = new ArrayList<>();
        ArrayList<Board> twinSearchNodes = new ArrayList<>();
//        originalSearchNodes.add(originalSearchNode.board);
//        twinSearchNodes.add(twinSearchNode.board);
        originalQueue.insert(originalSearchNode);
        twinQueue.insert(twinSearchNode);

        while (!originalSearchNode.getBoard().equals(solutionBoard)
                && !twinSearchNode.getBoard().equals(solutionBoard)) {
            originalSearchNode = (SearchNode) originalQueue.delMin();
            twinSearchNode = (SearchNode) twinQueue.delMin();

            originalSearchNodes.add(originalSearchNode.board);
            twinSearchNodes.add(twinSearchNode.board);

            for (Board originalBoard : originalSearchNode.board.neighbors()) {
                originalQueue.insert(new SearchNode(originalBoard, originalSearchNode, originalSearchNode.iteration + 1));
            }

            for (Board twinBoard : twinSearchNode.board.neighbors()) {
                twinQueue.insert(new SearchNode(twinBoard, twinSearchNode, twinSearchNode.iteration + 1));
            }
        }

        if(originalSearchNode.getBoard().equals(solutionBoard)){
            return originalSearchNodes;
        }
        return null;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    }

    private Board generateSolutionBoard(int size) {
        int[][] resultBlocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                resultBlocks[i][j] = (i * size) + j + 1;
            }
        }

        return new Board(resultBlocks);
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode n1, SearchNode n2) {
            Board b1 = n1.board;
            Board b2 = n2.board;
            return (b1.hamming() + b1.manhattan()) - (b2.hamming() + b2.manhattan());
        }
    }

    private class SearchNode {
        private Board board;
        private SearchNode previousNode;
        private int iteration;

        public SearchNode(Board board, SearchNode previousNode, int iteration) {
            this.board = board;
            this.previousNode = previousNode;
            this.iteration = iteration;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPreviousNode() {
            return previousNode;
        }

        public int getIteration() {
            return iteration;
        }
    }
}