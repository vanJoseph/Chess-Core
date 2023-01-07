
import com.wildercoding.chess.pieces.Bishop
import com.wildercoding.chess.pieces.Pawn
import com.wildercoding.chess.pieces.Piece
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wildercoding.chess.*


class BishopTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @BeforeEach
    fun setup() {
        piece = Bishop(Color.WHITE)

        val allCoordsArrayList = arrayListOf<Coord>()
        for (y in 0..7) {
            for (x in 0..7) {
                allCoordsArrayList.add(Coord(x, y))
            }
        }
        allCoordList = allCoordsArrayList
    }

    @Test
    fun Should_GenerateAllMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4), Coord(4, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(6, 6), Coord(6, 0),
                Coord(7, 7), Coord(2, 2),
                Coord(2, 4), Coord(1, 1),
                Coord(1, 5), Coord(0, 0),
                Coord(0, 6))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4), Coord(4, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(6, 6), Coord(6, 0),
                Coord(7, 7), Coord(2, 2),
                Coord(2, 4), Coord(1, 1),
                Coord(1, 5), Coord(0, 0),
                Coord(0, 6))

        val illegalMoves = allCoordList.subtract(legalMoves.toList())

        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateAllMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(1, 1), Coord(5, 5),
                Coord(2, 2), Coord(6, 6),
                Coord(3, 3), Coord(7, 7),
                Coord(4, 4))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(1, 1), Coord(5, 5),
                Coord(2, 2), Coord(6, 6),
                Coord(3, 3), Coord(7, 7),
                Coord(4, 4))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateLegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(6, 4), Coord(6, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(4, 6), Coord(4, 0),
                Coord(3, 7))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(6, 4), Coord(6, 2),
                Coord(5, 5), Coord(5, 1),
                Coord(4, 6), Coord(4, 0),
                Coord(3, 7))
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
    @Test
    fun Should_GenerateNeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4),
                Coord(5, 5),
                Coord(6, 6),
                Coord(7, 7))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonNeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 4),
                Coord(5, 5),
                Coord(6, 6),
                Coord(7, 7))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateSeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 2),
                Coord(5, 1),
                Coord(6, 0))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonSeMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSeMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 2),
                Coord(5, 1),
                Coord(6, 0))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateNwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(0, 6),
                Coord(1, 5),
                Coord(2, 4))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonNwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateNwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(0, 6),
                Coord(1, 5),
                Coord(2, 4))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateSwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 2),
                Coord(1, 1),
                Coord(0, 0))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonSwMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val bishop = piece as Bishop
        val verifiedMoves = bishop.generateSwMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 2),
                Coord(1, 1),
                Coord(0, 0))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
    @Test
    fun Should_NotMoveToSquare_When_PassAnotherPiece() {
        val pawnLocations = arrayOf<Coord>(
                Coord(1, 1),
                Coord(2, 4),
                Coord(5, 5),
                Coord(5, 1))
        val moveToLocations = arrayOf<Coord>(
                Coord(0, 6),
                Coord(0, 0),
                Coord(6, 0),
                Coord(7, 7))
        val startLocation = Coord(3, 3)
        val board=Board()
        board.addPiece(piece, startLocation)

        // Add Pawns
        pawnLocations.forEach { coord: Coord-> board.addPiece(Pawn(Color.WHITE),coord)}

        // Try to move to the passing location
        moveToLocations.forEach { coord: Coord ->
            assertFalse(piece.verifyMove(board,startLocation,coord))
        }
    }
    @Test
    fun Should_MoveToSquare_When_NotPassAnotherPiece() {
        val pawnLocations = arrayOf<Coord>(
                Coord(0, 6),
                Coord(0, 0),
                Coord(6, 0),
                Coord(7, 7))
        val moveToLocations = arrayOf<Coord>(
                Coord(1, 1),
                Coord(2, 4),
                Coord(5, 5),
                Coord(5, 1))
        val startLocation = Coord(3, 3)
        val board=Board()
        board.addPiece(piece, startLocation)

        // Add Pawns
        pawnLocations.forEach { coord: Coord-> board.addPiece(Pawn(Color.WHITE),coord)}

        // Try to move to the passing location
        moveToLocations.forEach { coord: Coord ->
            assertTrue(piece.verifyMove(board,startLocation,coord))
        }
    }
}