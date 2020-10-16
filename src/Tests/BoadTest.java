package Tests;

import org.junit.Test;
import wildercoding.chess.Board;
import wildercoding.chess.Square;

public class BoadTest {

    @Test
    void testNewGamePawnColorAndPosition() {
        Board board=new Board();
        board.initNewGame();
        Square[][] boardSquares=board.getBoard();
        for (int i = 0; i <= 7; i++) {
            boardSquares[0][1].getPiece().
        }

    }
}
