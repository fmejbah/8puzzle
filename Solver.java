import java.util.Stack;
import java.util.Collections;
import java.util.PriorityQueue;

public class Solver {
  // Comparator for PriorityQueue
  private class SearchNode implements Comparable<SearchNode> {
    private int moves, priority;
    private Board board;
    private SearchNode preSearchNode;

    public SearchNode(Board b, SearchNode p) {
      board = b;
      preSearchNode = p;
      if (preSearchNode == null) moves = 0;
      else moves = preSearchNode.moves + 1;
      priority = board.manhattan() + moves;
    }

    public int compareTo(SearchNode that) {
      if (this.priority > that.priority) return  1;
      if (this.priority == that.priority) return 0;
      return -1;
    }
  }

  // Solver
  private SearchNode resultNode, twinResultNode;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    // Exception
    if (initial == null)
      throw new java.lang.NullPointerException();

    if (initial.isGoal()) {
      resultNode = new SearchNode(initial, null);
    } else {
      PriorityQueue<SearchNode> mPQ = new PriorityQueue<SearchNode>();
      PriorityQueue<SearchNode> twinPQ = new PriorityQueue<SearchNode>();
      mPQ.add(new SearchNode(initial, null));
      twinPQ.add(new SearchNode(initial.twin(), null));
      while (mPQ.size() > 0) {
        resultNode = mPQ.poll();
        if (resultNode.board.isGoal()) return;
        twinResultNode = twinPQ.poll();
        if (twinResultNode.board.isGoal()) {
          resultNode = null;
          return;
        }
        for (Board neighobrs : twinResultNode.board.neighbors())
          if ((twinResultNode.preSearchNode == null)
              || (!neighobrs.equals(twinResultNode.preSearchNode.board)))
            twinPQ.add(new SearchNode(neighobrs, twinResultNode));
        for (Board neighobrs : resultNode.board.neighbors())
          if ((resultNode.preSearchNode == null)
              || (!neighobrs.equals(resultNode.preSearchNode.board)))
            mPQ.add(new SearchNode(neighobrs, resultNode));
      }
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    if (resultNode == null) return false;
    return true;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!isSolvable()) return -1;
    return resultNode.moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable()) return null;

    Stack<Board> solution = new Stack<Board>();
    SearchNode current = resultNode;
    while (current != null) {
      solution.push(current.board);
      current = current.preSearchNode;
    }
    Collections.reverse(solution);
    return solution;
  }
}
