import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.ConsoleInputMethod
import wildercoding.chess.Coord
import wildercoding.chess.MoveRequest
import wildercoding.chess.MoveType

class ConsoleInputMethodTest {
    @Test
    fun When_MoveEntered_Should_GetCorrectMoveRequest(){
        val fromPos ="45"
        val toPos="55"
        val testMoveRequest = ConsoleInputMethod().validateMoveRequst(fromPos,toPos)
        val expectedMoveRequest =MoveRequest(null, Coord(4,5), Coord(5,5),MoveType.MOVE)
        assertTrue(expectedMoveRequest == testMoveRequest, "Test MoveRequest does not equal expected MoveRequest" )
    }

}