/******************************************************************************
 *  Compilation:  javac PuzzleChecker.java
 *  Execution:    java PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class PuzzleChecker {
  // solve a slider puzzle (given below)
  public static void main(String[] args) throws IOException {
    // create initial board from file
    Scanner in;
    if(args.length > 0 )
      in = new Scanner(new File(args[0]));
    else
      in = new Scanner(System.in);
    int n = in.nextInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.nextInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      System.out.println("No solution possible");
    else {
      System.out.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        System.out.println(board);
    }
  }
}
