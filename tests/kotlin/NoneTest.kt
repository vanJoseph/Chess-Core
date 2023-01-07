import com.wildercoding.chess.pieces.Piece
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import wildercoding.chess.*

class NoneTest {
    lateinit var piece: Piece
    lateinit var allCoordList: List<Coord>

    @BeforeEach
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