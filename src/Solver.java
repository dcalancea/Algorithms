import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private Board initialBoard;
    private Board twinBoard;
    private boolean solutionRetrieved = false;
    private Iterable<Board> solution;
    //private Board solutionBoard;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) {
            throw new NullPointerException();
        }

        initialBoard = initial;
        twinBoard = initial.twin();
        //solutionBoard = generateSolutionBoard(initial.dimension());
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

        int count = -1;
        for (Board board : boards) {
            count++;
        }
        return count;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(!solutionRetrieved){
            solution = computeSolution();
            solutionRetrieved = true;
        }
        return solution;
    }


    private Iterable<Board> computeSolution(){
        MinPQ<SearchNode> originalQueue = new MinPQ<>(new SearchNodeComparator());
        MinPQ<SearchNode> twinQueue = new MinPQ<>(new SearchNodeComparator());

        SearchNode originalSearchNode = new SearchNode(initialBoard, null, 0);
        SearchNode twinSearchNode = new SearchNode(twinBoard, null, 0);

        originalQueue.insert(originalSearchNode);
        twinQueue.insert(twinSearchNode);

        do {
            originalSearchNode = originalQueue.delMin();
            twinSearchNode = twinQueue.delMin();

            Iterable<Board> originalNeighbors = originalSearchNode.board.neighbors();

            Iterable<Board> twinNeighbors = twinSearchNode.board.neighbors();

            for (Board originalBoard : originalNeighbors) {
                if (originalSearchNode.previousNode == null
                        || (!originalBoard.equals(originalSearchNode.previousNode.board)))
                {
                    originalQueue.insert(new SearchNode(originalBoard, originalSearchNode, originalSearchNode.iteration + 1));
                }
            }

            for (Board twinBoard : twinNeighbors) {
                if (twinSearchNode.previousNode == null
                        || (!twinBoard.equals(twinSearchNode.previousNode.board) ))
                {
                    twinQueue.insert(new SearchNode(twinBoard, twinSearchNode, twinSearchNode.iteration + 1));
                }
            }
        }
        while (!originalSearchNode.getBoard().isGoal()
                && !twinSearchNode.getBoard().isGoal());
        if (originalSearchNode.getBoard().isGoal()) {
            return searchNodeToBoards(originalSearchNode);
        }
        return null;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    }

    private Iterable<Board> searchNodeToBoards(SearchNode node) {
        Stack<Board> result = new Stack<>();

        while (node != null) {
            result.push(node.board);
            node = node.previousNode;
        }
        return result;
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode n1, SearchNode n2) {
            Board b1 = n1.board;
            Board b2 = n2.board;
            return (n1.iteration + b1.manhattan()) - (n2.iteration + b2.manhattan());
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