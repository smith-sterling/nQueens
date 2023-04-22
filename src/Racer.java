
public class Racer {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        for (int i = 4; i < 20; ++i) {
            Board board = new Board(i);
            board.solve();
            System.out.printf("Here's with an %dx%d board, solved in %.3f seconds%n", i, i,
                    ((double) System.currentTimeMillis() - time)/1000);
            Main.cout(board.toString());
        }
    }
}
