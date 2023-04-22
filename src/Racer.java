import java.time.Duration;

public class Racer {

    public static void main(String[] args) {
        long preSolve;
        for (int i = 1; i < 20; ++i) {
            if (i == 2 || i == 3) continue;
            
            Board board = new Board(i);
            
            preSolve = System.currentTimeMillis();
            board.solve();
            long postSolve = System.currentTimeMillis();
            
            System.out.printf("Here's with an %dx%d board, solved in %s%n", i, i, elapsedTime(preSolve, postSolve));
            
            Main.cout(board.toString());
            
        }
    }
    
    private static String elapsedTime(long preSolve, long postSolve) {
        Duration duration = Duration.ofMillis(postSolve - preSolve);
    
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        long millisPart = duration.toMillis() % 1000;
    
        if (hours > 0) {
            return String.format("%d hours, %d minutes, %d.%03d seconds", hours, minutes, seconds, millisPart);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d.%03d seconds", minutes, seconds, millisPart);
        } else {
            return String.format("%d.%03d seconds", seconds, millisPart);
        }
    }
}
