import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static int getSizeOfBoard() {
        Integer input = null;
        while (input == null) {
            try {
                input = new Scanner(System.in).nextInt();
                if (input < 1 || input == 2 || input == 3) {
                    //smaller than 1 doesn't make sense, and 2 and 3 aren't possible
                    input = null;
                    throw new InputMismatchException();
                }
                if (input > 15) cout("Good luck...");
            } catch (InputMismatchException e) {
                cout("Try again");
            }
        }
        return input;
    }
    public static void cout(Object o) { System.out.println(o); }
    
    public static void main(String[] args) {
        cout("How big of a board would you like?");
        Board board = new Board(getSizeOfBoard());
        
        board.solve();
        cout(board.toString());
    }
}