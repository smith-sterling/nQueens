import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Board {
    private final int size;
    private int numQueens = 0;
    private Square[][] board;
    public Board(int size) {
        // save the size for use throughout
        this.size = size;
        
        // initialize board with actual objects
        board = new Square[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = new Square();
            }
        }
    }
    
    public void solve() {
        solveHelper(0, board);
    }
    private boolean solveHelper(int index, Square[][] board) {
        if (index >= size * size) return false;
        if (numQueens == size && verify(board)) {
            this.board = board;
            return true;
        }
        
        //if you can put a queen here, put one, and recursively check if this board is good
        if (isValidSpotForQueen(index, board)) {
            Square[][] newBoard = this.putQueen(board, index);
            numQueens++;
            if (solveHelper(index + 1, newBoard)) {
                return true;
            } else {
                //if putting a queen here didn't pan out, skip this square
                numQueens--;
                return solveHelper(index + 1, board);
            }
        } else {
            //if this square isn't safe for a queen, skip this square
            return solveHelper(index + 1, board);
        }
    }
    
    private Square[][] putQueen(Square[][] board, int index) {
        //clone the board (deep copy)
        Square[][] newBoard = clone(board);
    
        //get row and col from index
        var coor = getCoor(index);
        int row = coor.row();
        int col = coor.col();
        
        //place a queen on the new spot
        newBoard[row][col].putQueen();
        
        //mark the rest of the row and the col dangerous
        for (int i = 0; i < size; ++i) {
            //important that you don't mark this square as dangerous
            if (i != col) newBoard[row][i].addDangerousSpot(index);
            if (i != row) newBoard[i][col].addDangerousSpot(index);
        }
        
        //get all the diags and mark those dangerous too
        List<Coor> diagCoors = getDiags(coor);
        for (Coor c : diagCoors) {
            newBoard[c.row()][c.col()].addDangerousSpot(index);
        }
        return newBoard;
    }
    private List<Coor> getDiags(Coor coor) {
        List<Coor> diags = new ArrayList<>();
        
        //up-left
        int row = coor.row();
        int col = coor.col();
        while (row > 0 && col > 0) {
            row--;
            col--;
            diags.add(new Coor(row, col));
        }
        
        //up-right
        row = coor.row();
        col = coor.col();
        while (row > 0 && col < size - 1) {
            row--;
            col++;
            diags.add(new Coor(row, col));
        }
        
        //down-left
        row = coor.row();
        col = coor.col();
        while (row < size - 1 && col > 0) {
            row++;
            col--;
            diags.add(new Coor(row, col));
        }
        
        //down-right
        row = coor.row();
        col = coor.col();
        while (row < size - 1 && col < size - 1) {
            row++;
            col++;
            diags.add(new Coor(row, col));
        }
        
        return diags;
    }
    
    private boolean isValidSpotForQueen(int index, Square[][] board) {
        //get the dangerous spots and make sure that those spots are, in fact, dangerous
        Set<Integer> dangerousSpots = getSquare(index, board).getDangerousSpots();
        dangerousSpots.stream()
                .filter(spot -> !getSquare(spot, board).hasQueen())
                .forEach(dangerousSpots::remove);
        
        // if this spot is actually safe, return true.
        return dangerousSpots.isEmpty();
    }
    
    private Square getSquare(int index, Square[][] board) { return board[index / size][index % size]; }
    private Coor getCoor(int index) { return new Coor(index / size, index % size); }
    
    private boolean verify(Square[][] board) {
        //verify that each row has a queen and only one
        for (Square[] row : board) {
            if (Arrays.stream(row).filter(Square::hasQueen).count() != 1) {
                return false;
            }
        }
        
        //verify that no queen can take another
        for (int i = 0; i < size * size; ++i) {
            //if there's a queen here, make sure no other queen can take this spot
            if (getSquare(i, board).hasQueen() && !isValidSpotForQueen(i, board)) {
                return false;
            }
        }
        
        //note that you shouldn't have to check every spot that a queen here could take for a queen for every square
        // if you are correctly adding dangerous spots when a new queen is being added...
        return true;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                s.append(board[i][j].hasQueen() ? "👑" : (i % 2 == j % 2 ? "⬛" : "⬜"));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public Square[][] clone(Square[][] oldBoard) {
        //this makes a deep copy of the board by calling the deepCopy constructor of Square
        Square[][] newBoard = new Square[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                newBoard[i][j] = new Square(oldBoard[i][j]);
            }
        }
        return newBoard;
    }
}
