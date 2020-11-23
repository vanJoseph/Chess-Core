import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import wildercoding.chess.*

class NoneTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @Before
    fun setup() {
        piece = None()

        val allCoordsArrayList = arrayListOf<Coord>()
        for (y in 0..7) {
            for (x in 0..7) {
                allCoordsArrayList.add(Coord(x, y))
            }
        }
        allCoordList = allCoordsArrayList
    }

    @Test
    fun Should_GenerateNoMoves_When_InAnyPosition() {
        val assertLambda = {coord : Coord-> assertTrue(piece.generateMovesList(coord).none()) }
        allCoordList.forEach(assertLambda)
    }
}