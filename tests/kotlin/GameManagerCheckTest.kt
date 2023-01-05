
import com.sun.source.tree.BlockTree
import com.sun.source.tree.WhileLoopTree
import com.wildercoding.chess.MultiMap
import com.wildercoding.chess.NoKingFoundException
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsCollectionContaining.hasItem
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class GameManagerCheckTest {
    lateinit var board: Board
    lateinit var gameManager: GameManager
    @Before
    fun setup(){
        board = Board()
        gameManager = GameManager(board)
    }

    fun cycleThruBoardCoords(assertLambda:(coord: Coord)->Unit){
        for (y in 0..7) {
            for (x in 0 .. 7){
                assertLambda(Coord(x,y))
            }
        }
    }
    @Test
    fun Should_NotCheck_When_BoardIsEmpty() {
        val assertLambda = {coord: Coord -> assertThat(gameManager.checkSquareCheck(coord,Color.BLACK).size,`is`(0))}
        cycleThruBoardCoords(assertLambda)
    }

    @Test
    fun Should_Check_When_InCheckByPawn() {
        val pawnLoc = Coord(3,2)
        board.addPiece(Pawn(Color.BLACK),pawnLoc)
        val testSquare = Coord(2,1)
        assertThat(gameManager.verifyPawnCheck(testSquare,Color.BLACK).asList(), hasItem(pawnLoc))
    }

    @Test
    fun Should_Check_When_InCheckByKing() {
        val testSquare = Coord(3,3)
        val kingSquares = arrayOf(
                Coord(4,4),Coord(2,4),
                Coord(4,3),Coord(2,3),
                Coord(4,2),Coord(2,2),
                Coord(3,4),Coord(3,2))

        for (pos in kingSquares) {
            board.addPiece(King(Color.WHITE),pos)
            assertThat(gameManager.verifyKingCheck(testSquare,Color.WHITE), `is`(pos))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByKnight() {
        val testSquare = Coord(3,3)
        val knightSquares = arrayOf(
                Coord(2,5),Coord(4,5),
                Coord(1,4),Coord(5,4),
                Coord(1,2),Coord(5,2),
                Coord(2,1),Coord(4,1))

        for (pos in knightSquares) {
            board.addPiece(Knight(Color.WHITE),pos)
            assertThat(gameManager.verifyKnightCheck(testSquare,Color.WHITE).asList(), hasItem(pos))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByRook() {
        val testSquare = Coord(3,3)
        val rookSquares = arrayOf(
                Coord(3,7), Coord(3,0),
                Coord(0,3), Coord(7,3))

        for (pos in rookSquares) {
            board.addPiece(Rook(Color.WHITE),pos)
            assertThat(gameManager.verifyRookCheck(testSquare,Color.WHITE).asList(), hasItem(pos))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByBishop() {
        val testSquare = Coord(3,3)
        val bishopSquares = arrayOf(
                Coord(7,7), Coord(6,0),
                Coord(0,6), Coord(0,0))

        for (pos in bishopSquares) {
            board.addPiece(Bishop(Color.WHITE),pos)
            assertThat(gameManager.verifyBishopCheck(testSquare,Color.WHITE).asList(), hasItem(pos))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_Check_When_InCheckByQueen() {
        val testSquare = Coord(3,3)
        val bishopSquares = arrayOf(
                Coord(7,7), Coord(6,0),
                Coord(0,6), Coord(0,0),
                Coord(3,7), Coord(3,0),
                Coord(0,3), Coord(7,3))

        for (pos in bishopSquares) {
            board.addPiece(Queen(Color.WHITE),pos)
            assertThat(gameManager.verifyQueenCheck(testSquare,Color.WHITE).asList(), hasItem(pos))
            board.removePiece(pos)
        }
    }

    @Test
    fun Should_ReturnTheNumberOfChecks_When_AllPIecesAreChecking() {
        val startingPos = Coord(2, 3)
        val knightPos = Coord(1, 1)
        val kingPos = Coord(1, 3)
        val queenPos = Coord(2, 6)
        val pawnPos = Coord(3, 4)
        val rookPos = Coord(5, 3)
        val bishopPos = Coord(5, 0)

        board.addPiece(Pawn(Color.BLACK), pawnPos)
        board.addPiece(Knight(Color.BLACK), knightPos)
        board.addPiece(Bishop(Color.BLACK), bishopPos)
        board.addPiece(Rook(Color.BLACK), rookPos)
        board.addPiece(King(Color.BLACK), kingPos)
        board.addPiece(Queen(Color.BLACK), queenPos)


        assertThat(gameManager.checkSquareCheck(startingPos, Color.BLACK).size, `is`(6))
    }


    @Test(expected = NoKingFoundException::class)
    fun Should_ThrowException_When_AKingIsMissing() {
        val kingLocation = Coord(4, 7)
       gameManager.findKing(Color.BLACK)
    }
    @Test
    fun Should_FindKing() {
        val kingLocation = Coord(4, 7)
        board.addPiece(King(Color.BLACK), kingLocation)

        assertThat(gameManager.findKing(Color.BLACK), `is`(kingLocation))
    }

    @Test
    fun Should_GiveCoordOfCheckReliefPieces() {
        board.addPiece(King(Color.WHITE), Coord(2, 0))
        val checkingPiecePos = Coord(3,2)
        board.addPiece(Knight(Color.BLACK), checkingPiecePos)
        val reliefPawn = Coord(4, 1)
        board.addPiece(Pawn(Color.WHITE), reliefPawn)
        board.addPiece(King(Color.BLACK), Coord(4, 7))

        assertThat(gameManager.getCheckReliefPiece().get(checkingPiecePos), `is`(reliefPawn) )

    }

    @Test
    fun Should_BoardIsTheSame_When_Simulated(){
        board.addPiece(King(Color.WHITE), Coord(2, 0))
        val checkingPiecePos = Coord(3,2)
        board.addPiece(Knight(Color.BLACK), checkingPiecePos)
        val reliefPawn = Coord(4, 1)
        board.addPiece(Pawn(Color.WHITE), reliefPawn)
        board.addPiece(King(Color.BLACK), Coord(4, 7))

        val preSimBoard = gameManager.board.toString()
        val moveRequest = MoveRequest(Coord(4,1),Coord(3,2))
        gameManager.simulateCheckRelief(moveRequest)
        val posSimBoard = gameManager.board.toString()
        assertThat(posSimBoard, `is`(preSimBoard) )
    }
    @Test
    fun Should_SimulateMove() {
        board.addPiece(King(Color.WHITE), Coord(2, 0))
        val checkingPiecePos = Coord(3,2)
        board.addPiece(Knight(Color.BLACK), checkingPiecePos)
        val reliefPawn = Coord(4, 1)
        board.addPiece(Pawn(Color.WHITE), reliefPawn)
        board.addPiece(King(Color.BLACK), Coord(4, 7))

        val moveRequest = MoveRequest(Coord(4,1),Coord(3,2))
        assertThat(gameManager.simulateCheckRelief(moveRequest), `is`(true) )
    }

    @Test
    fun Should_ReturnCheck_When_KingIsUnderAttack() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Bishop(Color.BLACK), Coord(6, 3))
        board.addPiece(King(Color.BLACK), Coord(7,7))

        assertThat(gameManager.checkForKingCheck(Color.WHITE), `is`(true))
    }
    @Test
    fun Should_NotReturnCheck_When_KingIsNotUnderAttack() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Bishop(Color.BLACK), Coord(5, 4))
        board.addPiece(King(Color.BLACK), Coord(7,7))

        assertThat(gameManager.checkForKingCheck(Color.WHITE), `is`(false))
    }

    @Test
    fun Should_ReturnSquaresKingCanMove_When_SquareIsNotUnderCheck() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Bishop(Color.BLACK), Coord(6, 3))
        board.addPiece(King(Color.BLACK), Coord(7,7))
        val safeSquares= arrayOf(
                Coord(2,0), Coord(2,1),
                Coord(3,1), Coord(4,0))


        val generatedSafeSquares = gameManager.canMoveOutofCheck()
        for (square in safeSquares) {
            assertThat(generatedSafeSquares.asList(), hasItem(square))
        }
        assertThat(generatedSafeSquares.size,  `is`(safeSquares.size))
    }
    @Test
    fun Should_ReturnSquaresKingCanMove_When_SameColorPieceIsBlocking() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Bishop(Color.BLACK), Coord(6, 3))
        board.addPiece(King(Color.BLACK), Coord(7,7))
        board.addPiece(Pawn(Color.WHITE), Coord(2,0))
        val safeSquares= arrayOf(
                Coord(2,1),
                Coord(3,1), Coord(4,0))


        val generatedSafeSquares = gameManager.canMoveOutofCheck()
        for (square in safeSquares) {
            assertThat(generatedSafeSquares.asList(), hasItem(square))
        }
        assertThat(generatedSafeSquares.size,  `is`(safeSquares.size))
    }
    @Test
    fun Should_ReturnSquaresKingCanMove_When_OppositeColorPieceIsBlocking() {
        board.addPiece(King(Color.WHITE), Coord(3, 0))
        board.addPiece(Bishop(Color.BLACK), Coord(6, 3))
        board.addPiece(King(Color.BLACK), Coord(7,7))
        board.addPiece(Knight(Color.WHITE), Coord(6,0))

        val moveMap= MultiMap<Coord,Coord>()
                moveMap.put(Coord(6,0), Coord(4,1))
                moveMap.put(Coord(6,0), Coord(5,2))

        val generatedMap = gameManager.canBlockCheck()

        for (key in moveMap.keySet()) {
            assertThat( generatedMap.keySet(), hasItem(key))
            for(value in moveMap.get(key)!!){
                assertThat(generatedMap.get(key), hasItem(value))
            }
        }
        assertThat(generatedMap.size(),  `is`(moveMap.size()))
    }
}