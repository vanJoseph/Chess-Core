

import wildercoding.chess.*;


public class BoadTest {

    void testNewGamePawnColorAndPosition() {
        Board board=new Board();
        board.initNewGame();
        Square[][] boardSquares=board.getBoard();
//        for (int i = 0; i <= 7; i++) {
//            assertTrue(boardSquares[i][1].getPiece().getColor()== Player.WHITE);
//            assertTrue(boardSquares[i][1].getPiece().getPiece()== PieceType.PAWN);
//
//            assertTrue(boardSquares[i][6].getPiece().getColor()== Player.BLACK);
//            assertTrue(boardSquares[i][6].getPiece().getPiece()== PieceType.PAWN);
//        }

    }
}
