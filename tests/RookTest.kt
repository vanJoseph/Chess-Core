import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*


class RookTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @Before
    fun setup() {
        piece = Rook(Color.WHITE)

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
                Coord(3, 0), Coord(0, 3),
                Coord(3, 1), Coord(1, 3),
                Coord(3, 2), Coord(2, 3),
                Coord(3, 4), Coord(4, 3),
                Coord(3, 5), Coord(5, 3),
                Coord(3, 6), Coord(6, 3),
                Coord(3, 7), Coord(7, 3),
        )
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(3, 0), Coord(0, 3),
                Coord(3, 1), Coord(1, 3),
                Coord(3, 2), Coord(2, 3),
                Coord(3, 4), Coord(4, 3),
                Coord(3, 5), Coord(5, 3),
                Coord(3, 6), Coord(6, 3),
                Coord(3, 7), Coord(7, 3),)

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
                Coord(0, 1), Coord(1, 0),
                Coord(0, 2), Coord(2, 0),
                Coord(0, 3), Coord(3, 0),
                Coord(0, 4), Coord(4, 0),
                Coord(0, 5), Coord(5, 0),
                Coord(0, 6), Coord(6, 0),
                Coord(0, 7), Coord(7, 0),)
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_When_InCornerPosition() {
        val startPos = Coord(0, 0)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(0, 1), Coord(1, 0),
                Coord(0, 2), Coord(2, 0),
                Coord(0, 3), Coord(3, 0),
                Coord(0, 4), Coord(4, 0),
                Coord(0, 5), Coord(5, 0),
                Coord(0, 6), Coord(6, 0),
                Coord(0, 7), Coord(7, 0),)
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
                Coord(7, 0), Coord(1, 3),
                Coord(7, 1), Coord(2, 3),
                Coord(7, 2), Coord(3, 3),
                Coord(7, 4), Coord(4, 3),
                Coord(7, 5), Coord(5, 3),
                Coord(7, 6), Coord(6, 3),
                Coord(7, 7), Coord(0, 3),)
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateIllegalMoves_WhenInEdgePosition() {
        val startPos = Coord(7, 3)
        val verifiedMoves = piece.generateMovesList(startPos)
        val legalMoves = arrayOf(
                Coord(7, 0), Coord(1, 3),
                Coord(7, 1), Coord(2, 3),
                Coord(7, 2), Coord(3, 3),
                Coord(7, 4), Coord(4, 3),
                Coord(7, 5), Coord(5, 3),
                Coord(7, 6), Coord(6, 3),
                Coord(7, 7), Coord(0, 3),)
        val illegalMoves = allCoordList.subtract(legalMoves.toList())
        for (move in illegalMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
    @Test
    fun Should_GenerateNorthMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateNorthMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(3, 4),
                Coord(3, 5),
                Coord(3, 6),
                Coord(3, 7))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonNorthMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateNorthMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(3, 4),
                Coord(3, 5),
                Coord(3, 6),
                Coord(3, 7))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateSouthMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateSouthMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(3, 0),
                Coord(3, 1),
                Coord(3, 2))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonSouthMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateSouthMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(3, 0),
                Coord(3, 1),
                Coord(3, 2))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateWestMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateWestMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 3),
                Coord(1, 3),
                Coord(0, 3))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonWestMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateWestMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(2, 3),
                Coord(1, 3),
                Coord(0, 3))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_GenerateEastMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateEastMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 3),
                Coord(5, 3),
                Coord(6, 3),
                Coord(7, 3))
        for (move in legalMoves) {
            assertTrue(verifiedMoves.contains(move))
        }
    }

    @Test
    fun Should_NotGenerateNonEastMoves_When_InOpenPosition() {
        val startPos = Coord(3, 3)
        val rook = piece as Rook
        val verifiedMoves = rook.generateEastMoveList(startPos)
        val legalMoves = arrayOf(
                Coord(4, 3),
                Coord(5, 3),
                Coord(6, 3),
                Coord(7, 3))
        val nonMoves = allCoordList.subtract(legalMoves.toList())
        for (move in nonMoves) {
            assertFalse(verifiedMoves.contains(move))
        }
    }
}