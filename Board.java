import java.util.Stack;

public class Board {
  private int n, h, m, x0, y0;
  private short[][] board;
  private int[] ham, man;

  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
    n = blocks.length;
    board = new short[n][n];
    ham = new int[n*n];
    man = new int[n*n];
    h = 0; m = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        // Initialize board
        board[i][j] = (short)blocks[i][j];

        // Find 0
        if (board[i][j] == 0) {
          x0 = i; y0 = j;
        }

        // Hamming
        int x = i * n + j + 1;
        if (x < n * n && board[i][j] != x) {
          ham[x] = 1;
          h++;
        }

        // Manhattan
        if (board[i][j] != 0) {
          int k = (board[i][j] - 1) / n;
          int l = (board[i][j] - 1) % n;
          man[board[i][j]] = Math.abs(i - k) + Math.abs(j - l);
          m += man[board[i][j]];
        }
      }
    }
  }

  // board dimension n
  public int dimension() { return n; }

  // number of blocks out of place
  public int hamming() { return h; }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() { return m; }

  // is this board the goal board?
  public boolean isGoal() { return h == 0; }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    int[][] twinBoard = copyBoard();
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n - 1; j++)
        if (twinBoard[i][j] != 0 && twinBoard[i][j+1] != 0) {
          swap(twinBoard, i, j, i, j+1);
          return new Board(twinBoard);
        }
    return null;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (this.n != that.n) return false;
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        if (this.board[i][j] != that.board[i][j])
          return false;
    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    Stack<Board> Boards = new Stack<Board>();
    int[][] b = copyBoard();
    int[] dx = {-1, 0, 0, 1};
    int[] dy = {0, -1, 1, 0};
    for (int i = 0; i < 4; i++) {
      int xx = x0 + dx[i];
      int yy = y0 + dy[i];
      if (xx >= 0 && xx < n && yy >= 0 && yy < n) {
        swap(b, xx, yy, x0, y0);
        Boards.push(new Board(b));
        swap(b, xx, yy, x0, y0);
      }
    }
    return Boards;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // Copy Board
  private int[][] copyBoard() {
    int[][] b = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        b[i][j] = board[i][j];
    return b;
  }

  // swap function
  private void swap(int[][] a, int x0, int y0, int x1, int y1) {
    a[x0][y0] = a[x0][y0] ^ a[x1][y1] ^ (a[x1][y1] = a[x0][y0]);
  }
}
