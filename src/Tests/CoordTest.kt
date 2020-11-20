import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import wildercoding.chess.Coord
import wildercoding.chess.InvalidCoordException
import java.lang.RuntimeException

class CoordTest {
    @Test
    fun Should_ThrowException_When_ParamsOutOfRange() {
        var exception: RuntimeException? = null
        val outOfRangeValues = arrayOf<Int>(-1,8)

        for (value in outOfRangeValues) {
            // Test the file to see if
            var fileTest:Coord? = null
            try {
                fileTest = Coord(value,0)
            } catch (ex: InvalidCoordException) {
                exception = ex
            }
            assertTrue(exception is InvalidCoordException, "Coord$fileTest should throw a exception.")

            var rankTest: Coord? = null
            try {
                rankTest=Coord(0,value)
            } catch (ex: InvalidCoordException) {
                exception = ex
            }
            assertTrue(exception is InvalidCoordException,"Coord$rankTest should throw a exception.")

            var fullCoordTest:Coord? = null
            try {
                fullCoordTest = Coord(value,value)
            } catch (ex: InvalidCoordException) {
                exception = ex
            }
            assertTrue(exception is InvalidCoordException,"Coord$fullCoordTest should throw a exception.")
        }
    }

    @Test
    fun Should_NotThrowException_When_ParamsAreInRange() {
        var exception: RuntimeException? = null
        var coord:Coord?=null
        for (rank in 0..7) {
            for (file in 0..7) {
                try {
                    coord=Coord(file, rank)
                } catch (ex: InvalidCoordException) {
                    exception = ex
                }
            }
        }

        assertTrue(exception == null, "Coord$coord is valid")
    }
}